#!/bin/bash

function build_view_dev() {
    build_dir=/Users/pangxianhai/workspace/recruitment-view
    cd /Users/pangxianhai/projects/clinical-recruitment/recruitment-view
    rm -rf ./dist/*
    npm run build-dev
    cd dist
    tar -czf dist.tar.gz ./*
    cd ..
    tar_file=`find . -name '*.tar.gz'`
    if [ -d $build_dir ]; then
	  rm -rf $build_dir
    fi
    mkdir -p $build_dir
    tar zxvf $tar_file -C $build_dir    
}

function build_manager_dev() {
    build_dir=/Users/pangxianhai/workspace/recruitment-manager
    cd /Users/pangxianhai/projects/clinical-recruitment/recruitment-manager
    rm -rf ./dist/*
    npm run build-dev
    cd dist
    tar -czf dist.tar.gz ./*
    cd ..
    tar_file=`find . -name '*.tar.gz'`
    if [ -d $build_dir ]; then
       rm -rf $build_dir
    fi
    mkdir -p $build_dir
    tar zxvf $tar_file -C $build_dir
}

function build_server_dev() {
    cd /Users/pangxianhai/projects/clinical-recruitment/recruitment-server
    build_dir=/Users/pangxianhai/workspace/recruitment-server
    gradle clean
    gradle build -x test
    supervisorctl stop recruitment-server
    if [ -d $build_dir ]; then
       rm -rf $build_dir
    fi
    mkdir -p $build_dir/logs
    cp recruitment-web/build/libs/recruitment-server.jar $build_dir/
    supervisorctl start recruitment-server
}

function build_server_product() {
    cd /Users/pangxianhai/projects/clinical-recruitment/recruitment-web
    mvn -Dmaven.test.skip=true -Pproduct package
    scp recruitment-boot/target/recruitment-boot-product.tar.gz root@59.110.230.31:/home/release
    ssh root@59.110.230.31 "/home/bin/build.sh"
}

function build_view_product() {
    cd /Users/pangxianhai/projects/clinical-recruitment/recruitment-view
    rm -rf ./dist/*
    npm run build
    cd dist
    tar -czf dist.tar.gz ./*
    cd ..
    tar_file=`find . -name '*.tar.gz'`
    scp $tar_file root@59.110.230.31:/home/release
    ssh root@59.110.230.31 "/home/bin/build-view.sh"
}

function build_manager_product() {
    cd /Users/pangxianhai/projects/clinical-recruitment/recruitment-manager
    rm -rf ./dist/*
    npm run build
    cd dist
    tar -czf manager.tar.gz ./*
    cd ..
    tar_file=`find . -name '*.tar.gz'`
    scp $tar_file root@59.110.230.31:/home/release
    ssh root@59.110.230.31 "/home/bin/build-manager.sh"
}


if [ "$1" == "server" ];then
    build_server_dev
elif [ "$1" == "server-pro" ];then
    build_server_product
elif [ "$1" == "manager" ];then
    build_manager_dev
elif [ "$1" == "manager-pro" ];then
	build_manager_product
elif [ "$1" == "view" ];then
    build_view_dev
elif [ "$1" == "view-pro" ];then
    build_view_product
else
    echo $0 "[server|server-pro|manager|manager-pro|view|view-pro]"
fi
