# 构建 Linux 下可执行文件
CGO_ENABLED=0 GOOS=linux GOARCH=amd64 go build -o recruitment-user

# 构建 docker 镜像
#docker build -t recruitment-user:latest .