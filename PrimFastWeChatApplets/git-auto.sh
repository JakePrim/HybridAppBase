#!/bin/bash
# git自动化脚本 不用再写繁琐的命令 
:<<BLOCK
-u 上传多个指定文件到hexo中
-d 删除hexo中指定的多个文件
-g 开始部署
-h 查看帮助
BLOCK

cat <<- _EOF_
		-u 上传多个指定文件到hexo中 : auth.sh -u xxx xxx xxx
		-d 删除hexo中指定的多个文件 : auth.sh -d xxx xxx xxx
		-g 开始部署 : auth.sh -g
		-h 查看帮助 : auth.sh -h
	_EOF_