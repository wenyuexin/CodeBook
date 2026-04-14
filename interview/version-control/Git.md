## Git 

### 1\. Git 中的 `git fetch` 和 `git pull` 有什么区别？

**参考答案**：

- `git fetch`：从远程仓库下载最新的提交、分支和引用，但**不会自动合并**到当前工作分支。它只是将远程更新保存到本地远程跟踪分支（如 `origin/main`），你需要手动执行 `git merge` 或 `git rebase` 来整合。

- `git pull` = `git fetch` + `git merge`（默认行为，也可配置为 rebase）。它会直接拉取并合并到当前分支，简化操作但可能引入自动合并冲突。

**建议**：在团队协作中，推荐先 `git fetch` 查看远程变化，再决定是 `merge` 还是 `rebase`，避免意外的自动合并。

---

### 2\. 如何撤销已经 `git add` 暂存的文件？如何撤销已经 `git commit` 的提交？

**参考答案**：

- **撤销暂存（未 commit）**：\
  `git reset HEAD <file>` 或 `git restore --staged <file>`（Git 2.23+）。\
  这会将文件从暂存区移除，但保留工作区的修改。

- **撤销最近一次 commit（保留修改）**：\
  `git reset --soft HEAD~1`\
  撤销提交，修改保留在暂存区和工作区。

- **撤销最近一次 commit（不保留修改）**：\
  `git reset --hard HEAD~1`\
  完全丢弃最后一次提交及其修改，**危险操作**，慎用。

- **已经 push 到远程，想撤销**：\
  `git revert <commit-hash>` 生成一个反向提交，安全且不破坏历史。\
  或者 `git reset --hard <old-commit>` 然后 `git push --force`，但会改写远程历史，需团队协调。

---

### 3\. 解释 Git 中的 `merge` 和 `rebase` 的区别，以及各自的优缺点。

**参考答案**：

|特性|Merge|Rebase|
|-|-|-|
|**原理**|创建一个新的合并提交（merge commit），保留分支历史|将当前分支的提交“重放”到目标分支上，形成线性历史|
|**历史记录**|保留真实的分支合并时间线和分叉|线性、干净，但丢失了分支的并行信息|
|**冲突解决**|只需解决一次冲突，产生一个合并提交|可能每个被重放的提交都需要解决冲突，多次解决|
|**适用场景**|公共分支（如 main/develop），保留协作历史|本地分支整理，使提交历史更整洁，便于 code review|

**优点**：

- Merge：非破坏性，保留完整上下文，适合多人协作。

- Rebase：历史线性，没有多余的合并提交，便于回溯和 cherry-pick。

**缺点**：

- Merge：历史图可能变得杂乱，出现“网状”分支。

- Rebase：改写提交哈希，不应在公共分支上使用；冲突解决过程可能重复。

**原则**：对公共分支永远不要用 `rebase`；对本地未推送的分支可以随意 `rebase`。

---

### 4\. 什么是 Git 的“分离头指针”（detached HEAD）状态？如何解决？

**参考答案**：

当 HEAD 指向一个具体的提交哈希而不是一个分支引用时，Git 处于“分离头指针”状态。常见于执行 `git checkout <commit-hash>` 或 `git checkout tag-name`。

**特点**：在此状态下进行的任何新提交都不属于任何分支，一旦切换到其他分支，这些提交可能被垃圾回收丢失。

**解决方法**：

- 如果只是想查看历史，可以暂时处于该状态，查看完切回分支。

- 如果需要保存在此状态下的修改，创建一个新分支：\
  `git switch -c <new-branch-name>` 或 `git checkout -b <new-branch-name>`。

---

### 5\. 如何解决 Git 合并冲突？请描述完整步骤。

**参考答案**：

1. 执行 `git merge` 或 `git rebase` 时，Git 提示冲突，冲突文件会标记为 `both modified`。

2. 使用 `git status` 查看冲突文件列表。

3. 手动编辑冲突文件，Git 会在文件中用 `<<<<<<<`、`=======`、`>>>>>>>` 标记两个分支的差异。保留需要的代码，删除标记行。

4. 对每个冲突文件修改后，执行 `git add <file>` 标记为已解决。

5. 所有冲突解决后：

   - 若为 `merge`：执行 `git commit`（Git 会生成默认的合并提交信息）。

   - 若为 `rebase`：执行 `git rebase --continue`。

6. 可选：使用 `git mergetool` 调用可视化合并工具（如 Beyond Compare、KDiff3）。

**注意**：如果冲突复杂或想放弃合并，可用 `git merge --abort` 或 `git rebase --abort` 回到合并前状态。

---

### 6\. 解释 Git 工作区的三种状态（已修改、已暂存、已提交）以及对应的 Git 命令。

**参考答案**：

Git 文件有三种状态，对应不同的存储位置：

|状态|说明|典型命令|
|-|-|-|
|**已修改 (modified)**|文件在工作区被修改但尚未暂存|`git status` 显示红色|
|**已暂存 (staged)**|修改已通过 `git add` 加入暂存区，等待提交|`git add`，`git status` 显示绿色|
|**已提交 (committed)**|暂存区内容已通过 `git commit` 存入本地仓库|`git commit`，`git log` 可查看|

**状态转换流程**：

```
工作区（修改） --git add--> 暂存区（暂存） --git commit--> 本地仓库（提交）
       ^                                 |
       |------ git restore <file> -------|
       |------ git reset HEAD <file> ----| (从暂存区回退到工作区)
```

---

### 7\. 什么是 Git 的 `git stash`？在什么场景下使用？

**参考答案**：

`git stash` 用于临时保存当前工作目录和暂存区的修改，将工作区恢复到干净的 HEAD 状态。常用于以下场景：

- 需要紧急切换分支修复 bug，但当前修改还未完成不想提交。

- 拉取远程代码时与本地修改冲突，先 stash 再 pull 再 stash pop。

- 测试不同的代码方案，快速切换上下文。

**常用命令**：

- `git stash save "message"`：保存修改并添加描述。

- `git stash list`：列出所有 stash。

- `git stash pop`：恢复最近一次 stash 并删除记录（相当于 `git stash apply` + `git stash drop`）。

- `git stash apply`：恢复但不删除 stash 记录。

- `git stash drop`：删除某个 stash。

- `git stash clear`：清空所有 stash。

---

### 8\. 解释 Git Flow 工作流的核心分支模型及其作用。

**参考答案**：

Git Flow 是一种经典的分支管理模型，由 Vincent Driessen 提出，主要包含以下分支：

|分支|名称|作用|
|-|-|-|
|**主分支**|`main` / `master`|生产环境代码，每个提交都对应一个发布版本，打 tag。|
|**开发分支**|`develop`|集成最新开发功能，是所有 feature 分支的源和目标。|
|**功能分支**|`feature/*`|从 `develop` 拉出，用于开发单个功能，完成后合并回 `develop`。|
|**发布分支**|`release/*`|从 `develop` 拉出，用于准备发布，只做 bug 修复和文档更新，完成后合并到 `main` 和 `develop`。|
|**热修复分支**|`hotfix/*`|从 `main` 拉出，紧急修复生产问题，完成后合并到 `main` 和 `develop`（或当前发布分支）。|

**优点**：结构清晰，适合有固定发布周期的项目。\
**缺点**：分支较多，对小型项目可能过重；现代 CI/CD 下常简化（如 GitHub Flow）。

---

### 9\. 如何删除本地分支和远程分支？

**参考答案**：

- **删除本地分支**：\
  `git branch -d <branch-name>`（安全删除，要求分支已合并）\
  `git branch -D <branch-name>`（强制删除，无论是否合并）

- **删除远程分支**：\
  `git push origin --delete <branch-name>` 或 `git push origin :<branch-name>`。

删除后，其他同事使用 `git fetch --prune` 或 `git remote prune origin` 清理本地的远程跟踪分支。

---

### 10\. 解释 Git 中 `git cherry-pick` 的作用及使用场景。

**参考答案**：

`git cherry-pick` 用于将**一个或多个指定的提交**复制到当前分支，生成新的提交（哈希值不同）。它不会合并整个分支，而是选择性应用某些提交。

**使用场景**：

- 从其他分支挑选特定的 bug 修复补丁到当前分支。

- 将某个功能的部分提交提前集成到发布分支。

- 误提交到错误分支时，可以 cherry-pick 到正确分支后删除原分支错误提交。

**示例**：\
`git cherry-pick <commit-hash>`\
`git cherry-pick commit1 commit2`\
`git cherry-pick commit1..commit3`（范围）

**注意**：如果存在冲突，需要手动解决并执行 `git cherry-pick --continue`；放弃可使用 `--abort`。

---

### 11\. 什么是 Git 的“三棵树”？它们如何协同工作？

**参考答案**：

Git 的“三棵树”指的是：

1. **工作目录（Working Directory）**：实际文件系统上的文件，可编辑。

2. **暂存区（Staging Area / Index）**：即将进入下一次提交的文件快照列表。

3. **仓库（Repository / HEAD）**：已提交的永久对象数据库，包含所有历史版本。

**协同过程**：

- 修改工作目录文件 → 工作树改变。

- `git add` → 将工作树的快照写入暂存区。

- `git commit` → 将暂存区内容打包为一个新的提交对象，存入仓库，HEAD 指向新提交。

- `git checkout` → 从仓库中取出某个提交，覆盖暂存区和工作目录（根据参数不同行为略有差异）。

这种设计允许精细控制：可以只暂存部分修改，也可以放弃暂存。

---

### 12\. 如何配置 Git 别名？举一个例子说明其作用。

**参考答案**：

Git 别名（alias）用于简化常用命令，通过 `git config` 设置。

**全局配置示例**：

```bash
git config --global alias.co checkout
git config --global alias.br branch
git config --global alias.ci commit
git config --global alias.st status
git config --global alias.lg "log --oneline --graph --all"
```

配置后，输入 `git co main` 等价于 `git checkout main`；`git lg` 显示漂亮的日志图。

**作用**：提高日常操作效率，减少打字量，尤其适合团队统一命令风格。

---

### 13\. 请说明 `git reset` 三种模式（--soft, --mixed, --hard）的区别。

**参考答案**：

`git reset` 用于移动 HEAD 和分支指针，并可选地重置暂存区和工作区。

|模式|HEAD 移动|暂存区|工作区|典型用途|
|-|-|-|-|-|
|`--soft`|是|保留原内容|保留原内容|撤销 commit 但保留修改，重新组织提交|
|`--mixed`（默认）|是|重置为 HEAD 的内容|保留原内容|撤销 commit 和 add，修改保留在工作区|
|`--hard`|是|重置为 HEAD 的内容|重置为 HEAD 的内容|彻底丢弃所有修改，危险|

**示例**：

- `git reset --soft HEAD~1`：撤销最近一次提交，修改回到暂存区。

- `git reset HEAD~1`（即 mixed）：撤销提交并取消暂存，修改回到工作区。

- `git reset --hard HEAD~1`：完全回退，丢失修改。

---

### 14\. 如何查看某一行代码的最后一次修改是谁、什么时候提交的？

**参考答案**：

使用 `git blame <file>` 命令。它会逐行显示每一行的最后修改提交哈希、作者、时间和行号。

常用选项：

- `git blame -L 10,20 <file>`：只查看第 10 到 20 行。

- `git blame -C <file>`：检测同一文件内移动的代码行。

- `git blame -M <file>`：检测跨文件移动的代码行。

**场景**：定位 bug 引入的提交，追溯代码变更责任。

---

### 15\. 在团队协作中，如何避免和减少 Git 合并冲突？

**参考答案**：

1. **频繁拉取最新代码**：每天开始工作前 `git pull --rebase`，及时整合上游变更。

2. **小粒度提交**：每个功能或修复独立分支，避免长时间不合并。

3. **分工明确**：避免多人同时修改同一文件的同一区域。

4. **使用 rebase 而非 merge**：在本地分支使用 `git pull --rebase` 保持线性历史，减少无意义的合并提交。

5. **提前沟通**：修改公共配置文件或核心模块前，通知团队成员。

6. **代码规范**：统一格式化规则（如 .editorconfig），减少因空格、换行符产生的冲突。

7. **CI 检查**：合并前通过 CI 运行自动化测试和静态分析，及早发现潜在冲突。