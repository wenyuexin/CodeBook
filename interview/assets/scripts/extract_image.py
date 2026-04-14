# !/usr/bin/env python3
# -*- coding: utf-8 -*-  
"""
Markdown 远程图片提取工具

功能：
    1. 遍历 interview 目录下的所有 md 文件
    2. 提取 markdown 中的远程图片链接（http/https）
    3. 下载图片到 interview/image 目录，文件名使用 UUID
    4. 更新 md 文件中的图片引用为本地路径

特性：
    - 支持配置 INCLUDE_FILES 指定处理的文件
    - 支持配置 SKIP_FILES 跳过指定文件
    - 已处理的文件（包含本地图片引用）会自动跳过
    - 下载失败时保留原始远程链接

使用：
    python extract_image.py
"""
import re
import uuid
import requests
from pathlib import Path
from urllib.parse import urlparse


# ==================== 配置参数 ====================

# 只处理指定的文件列表
# 支持三种格式：
#   - 文件名：'计算机网络.md'
#   - 相对路径：'interview/计算机网络.md'
#   - 绝对路径：'/Users/xxx/CodeBook/interview/计算机网络.md'
# 设为 None 或空列表表示处理所有文件
# 示例：INCLUDE_FILES = ['JVM.md', 'interview/Redis.md']
INCLUDE_FILES = []

# 跳过的文件列表
# 支持格式同上，设为 None 或空列表表示不跳过任何文件
# 示例：SKIP_FILES = ['计算机网络.md', 'interview/服务注册.md']
SKIP_FILES = []

# ==================== 配置结束 ====================


def download_image(url: str, save_dir: Path) -> str:
    """
    下载图片并保存到本地
    返回本地文件名（包含扩展名）
    """
    try:
        # 发送请求下载图片
        headers = {
            'User-Agent': 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) '
                          'AppleWebKit/537.36 (KHTML, like Gecko) '
                          'Chrome/91.0.4472.124 Safari/537.36'
        }
        response = requests.get(url, headers=headers, timeout=30)
        response.raise_for_status()
        
        # 确定文件扩展名
        ext = '.png'  # 默认扩展名
        
        # 从 Content-Type 获取扩展名
        content_type = response.headers.get('Content-Type', '')
        if 'jpeg' in content_type or 'jpg' in content_type:
            ext = '.jpg'
        elif 'png' in content_type:
            ext = '.png'
        elif 'gif' in content_type:
            ext = '.gif'
        elif 'webp' in content_type:
            ext = '.webp'
        elif 'svg' in content_type:
            ext = '.svg'
        else:
            # 尝试从 URL 获取扩展名
            parsed_url = urlparse(url)
            path = parsed_url.path
            # 移除查询参数中的特殊字符
            path = path.split('?')[0]
            path = path.split('#')[0]
            
            if path.endswith('.jpg') or path.endswith('.jpeg'):
                ext = '.jpg'
            elif path.endswith('.png'):
                ext = '.png'
            elif path.endswith('.gif'):
                ext = '.gif'
            elif path.endswith('.webp'):
                ext = '.webp'
            elif path.endswith('.svg'):
                ext = '.svg'
        
        # 生成 UUID 文件名
        filename = f"{uuid.uuid4()}{ext}"
        filepath = save_dir / filename
        
        # 保存图片
        with open(filepath, 'wb') as f:
            f.write(response.content)
        
        print(f"Downloaded: {url} -> {filename}")
        return filename
        
    except Exception as e:
        print(f"Failed to download {url}: {e}")
        return None


def process_markdown_file(md_path: Path, image_dir: Path) -> None:
    """
    处理单个 markdown 文件
    下载其中的远程图片并更新引用
    """
    print(f"\nProcessing: {md_path.name}")
    
    # 读取文件内容
    with open(md_path, 'r', encoding='utf-8') as f:
        content = f.read()
    
    # 匹配远程图片链接的正则表达式
    # 格式: ![alt](http://... 或 https://...)
    # 注意：已经指向本地的 image/xxx 路径不会被匹配
    pattern = r'!\[([^\]]*)\]\((https?://[^)]+)\)'
    
    # 检查是否已经有本地图片引用
    local_pattern = r'!\[([^\]]*)\]\(\.\./assets/images/[^)]+\)'
    if re.search(local_pattern, content):
        print(f"  Warning: File already contains local image references")
        print(f"  Skipping to avoid duplicate downloads")
        return
    
    # 记录是否需要更新文件
    modified = False
    
    def replace_image(match):
        nonlocal modified
        alt_text = match.group(1)
        url = match.group(2)
        
        # 下载图片
        filename = download_image(url, image_dir)
        
        if filename:
            modified = True
            # 返回新的本地引用路径
            # md 文件在 interview/xxx/ 子目录，images 在 interview/assets/images/
            # 所以相对路径是 ../assets/images/filename
            return f'![{alt_text}](../assets/images/{filename})'
        else:
            # 下载失败，保留原链接
            return match.group(0)
    
    # 替换所有匹配的图片链接
    new_content = re.sub(pattern, replace_image, content)
    
    # 如果有修改，重写文件
    if modified:
        with open(md_path, 'w', encoding='utf-8') as f:
            f.write(new_content)
        print(f"Updated: {md_path.name}")


def normalize_file_path(file_path: str, base_dir: Path) -> str:
    """
    将文件路径标准化为文件名
    支持三种格式：
    - 文件名：'计算机网络.md'
    - 相对路径：'interview/计算机网络.md'
    - 绝对路径：'/Users/xxx/CodeBook/interview/计算机网络.md'
    """
    path = Path(file_path)
    
    # 绝对路径：直接取文件名
    if path.is_absolute():
        return path.name
    
    # 相对路径包含目录：取文件名
    if len(path.parts) > 1:
        return path.name
    
    # 纯文件名：直接返回
    return str(path)


def main():
    """
    主函数：遍历 interview 目录下的所有 md 文件并处理
    """
    # 获取脚本所在目录的祖父目录（interview 目录）
    # 脚本路径: interview/assets/scripts/extract_image.py
    script_dir = Path(__file__).parent
    interview_dir = script_dir.parent.parent
    image_dir = interview_dir / 'assets' / 'images'
    
    print(f"Interview directory: {interview_dir}")
    print(f"Image directory: {image_dir}")
    
    # 获取所有 md 文件（递归查找所有子目录）
    all_md_files = list(interview_dir.rglob('*.md'))
    
    # 根据配置筛选文件
    if INCLUDE_FILES:
        # 只处理指定的文件（支持多种路径格式）
        include_names = {normalize_file_path(f, interview_dir) 
                         for f in INCLUDE_FILES}
        md_files = [f for f in all_md_files if f.name in include_names]
        print(f"Include files: {INCLUDE_FILES}")
    else:
        md_files = all_md_files
    
    # 跳过指定的文件
    if SKIP_FILES:
        # 支持多种路径格式
        skip_names = {normalize_file_path(f, interview_dir) 
                      for f in SKIP_FILES}
        md_files = [f for f in md_files if f.name not in skip_names]
        print(f"Skip files: {SKIP_FILES}")
    
    print(f"Files to process: {len(md_files)}")
    
    # 确保 image 目录存在
    image_dir.mkdir(parents=True, exist_ok=True)
    
    # 处理每个文件
    for md_path in md_files:
        process_markdown_file(md_path, image_dir)
    
    print("\nDone!")
    

if __name__ == '__main__':
    main()