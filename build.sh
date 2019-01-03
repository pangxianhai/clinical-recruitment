#!/bin/bash

function build() {
    cd $1
    build_dir=/home/www/$2
    if [ -d $build_dir ]; then
	$build_dir/bin/stop.sh
        rm -rf $build_dir
    fi
    mvn_tools.sh install
    tar_file=`find . -name '*.tar.gz'`
    mkdir -p $build_dir
    tar zxvf $tar_file -C $build_dir
    nohup $build_dir/bin/start.sh debug > /dev/null 2>&1 &
}

function build_web() {
    source_dir=/home/xavier.pang/work/clinical-recruitment/recruitment-web
    build $source_dir recruitment-web
}

if [ "$1" == "web" ];then
    build_web
else
	echo $0 "[web]"
fi

