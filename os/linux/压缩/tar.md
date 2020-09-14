# tar命令

z:代表的是压缩

c:代表的是打包

x:代表的是解压

v:代表的是过程

f:代表的是指定文件名

因此

zcvf :  打包压缩   

例如: (tar  -zcvf  xxx.tar.gz  aaa.txt  bbb.txt  ccc.txt)  把aaa.txt  bbb.txt  ccc.txt打包压缩为一个名叫xxx.tar.gz 压缩包

xvf：解压缩

例如(tar -xvf  xxx.tar.gz  -C/usr)  -C代表解压的位置 把xxx.tar.gz解压缩到根目录下的usr目录

