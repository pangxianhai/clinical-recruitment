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

function work_build_web() {
	source_dir=/home/xavier.pang/work/clinical-recruitment/recruitment-web
	build $source_dir recruitment-web /home/www

}

function home_build_web() {
    source_dir=/d/work/clinical-recruitment/recruitment-web
    build $source_dir recruitment-web /d/run
}

function build_product() {
    cd D:\work\clinical-recruitment\recruitment-web
    mvn -Dmaven.test.skip=true -Pproduct package
    scp recruitment-boot/target/recruitment-boot-product.tar.gz root@59.110.230.31:/home/release
    ssh root@59.110.230.31 "/home/bin/build.sh"
}

if [ "$1" == "web" ];then
    work_build_web
elif [ "$1" == "webh" ];then
    home_build_web
elif [ "$1" == "pro" ];then
    build_product
else
    echo $0 "[web|webh|pro]"
fi

