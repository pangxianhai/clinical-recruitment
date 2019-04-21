#!/bin/bash

function build() {
    if [ "$3" == "" ];then
	return;
    fi
    cd $1
    build_dir=$3/$2
    if [ -d $build_dir ]; then
	$build_dir/bin/stop.sh
        rm -rf $build_dir/*
    fi
    mvn_tools.sh install
    tar_file=`find . -name '*.tar.gz'`
    mkdir -p $build_dir
    tar zxvf $tar_file -C $build_dir
    nohup $build_dir/bin/start.sh debug > /dev/null 2>&1 &
}

function build_view() {
    build_dir=/Users/pangxianhai/www/recruitment-view
    cd /Users/pangxianhai/code/java/clinical-recruitment/recruitment-view
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

function work_build_web() {
    source_dir=/home/xavier.pang/work/clinical-recruitment/recruitment-web
    build $source_dir recruitment-web /home/www

}

function build_product() {
    cd /Users/pangxianhai/code/java/clinical-recruitment/recruitment-web
    mvn -Dmaven.test.skip=true -Pproduct package
    scp recruitment-boot/target/recruitment-boot-product.tar.gz root@59.110.230.31:/home/release
    ssh root@59.110.230.31 "/home/bin/build.sh"
}

function build_view_product() {
    cd /Users/pangxianhai/code/java/clinical-recruitment/recruitment-view
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
    cd /Users/pangxianhai/code/java/clinical-recruitment/recruitment-manager
    rm -rf ./dist/*
    npm run build
    cd dist
    tar -czf manager.tar.gz ./*
    cd ..
    tar_file=`find . -name '*.tar.gz'`
    scp $tar_file root@59.110.230.31:/home/release
    ssh root@59.110.230.31 "/home/bin/build-manager.sh"
}


if [ "$1" == "web" ];then
    work_build_web
elif [ "$1" == "pro" ];then
    build_product
elif [ "$1" == "view" ];then
    build_view
elif [ "$1" == "pro-view" ];then
    build_view_product
elif [ "$1" == "pro-manager" ];then
    build_manager_product
else
    echo $0 "[web|pro|view|pro-view|pro-manager]"
fi

