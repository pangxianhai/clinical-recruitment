#!/bin/bash
cd `dirname $0`
BIN_DIR=`pwd`
cd ..
DEPLOY_DIR=`pwd`
CONF_DIR=$DEPLOY_DIR/conf

SERVER_NAME=`sed '/server.name/!d;s/.*=//' conf/application.properties | tr -d '\r'`
SERVER_PORT=`sed '/server.port/!d;s/.*=//' conf/application.properties | tr -d '\r'`
MEM_OPTS=`sed '/java_mem_opts/!d;s/.*=//' conf/application.properties | tr -d '\r'`
DEBUG_ADDRESS=`sed '/java_debug_address/!d;s/.*=//' conf/application.properties | tr -d '\r'`
JMX_PORT=`sed '/java_jmx_port/!d;s/.*=//' conf/application.properties | tr -d '\r'`
JMX_HOST=`sed '/java_jmx_host/!d;s/.*=//' conf/application.properties | tr -d '\r'`
LOGS_FILE=""

if [ -z "$SERVER_NAME" ]; then
    SERVER_NAME=`hostname`
fi

PIDS=`ps -ef | grep java | grep "$CONF_DIR" | awk '{print $2}'`
if [ -n "$PIDS" ]; then
    echo "ERROR: The $SERVER_NAME already started!"
    echo "PID: $PIDS"
    exit 1
fi

if [ -n "$SERVER_PORT" ]; then
    SERVER_PORT_COUNT=`netstat -tln | grep $SERVER_PORT | wc -l`
    if [ $SERVER_PORT_COUNT -gt 0 ]; then
        echo "ERROR: The $SERVER_NAME port $SERVER_PORT already used!"
        exit 1
    fi
fi

LOGS_DIR=""
if [ -n "$LOGS_FILE" ]; then
    LOGS_DIR=`dirname $LOGS_FILE`
else
    LOGS_DIR=$DEPLOY_DIR/logs
fi
if [ ! -d $LOGS_DIR ]; then
    mkdir $LOGS_DIR
fi
STDOUT_FILE=$LOGS_DIR/stdout.log

LIB_DIR=$DEPLOY_DIR/lib
LIB_JARS=`ls $LIB_DIR | grep .jar | awk '{print "'$LIB_DIR'/"$0}' | tr "\n" ":"`
JAVA_DEBUG_OPTS=""
if [ "$1" = "debug" ]; then
    JAVA_DEBUG_OPTS=" -Xdebug -Xnoagent -Djava.compiler=NONE -Xrunjdwp:transport=dt_socket,address=$DEBUG_ADDRESS,server=y,suspend=n "
fi

JAVA_JMX_OPTS=""
if [ -n "$JMX_HOST" ] && [ -n "$JMX_PORT" ]; then
    JAVA_JMX_OPTS="-Dcom.sun.management.jmxremote.port=$JMX_PORT -Djava.rmi.server.hostname=$JMX_HOST -Dcom.sun.management.jmxremote.ssl=false -Dcom.sun.management.jmxremote.authenticate=false"
fi

JAVA_MEM_OPTS=""
BITS=`java -version 2>&1 | grep -i 64-bit`
if [ -n "$BITS" ]; then
    JAVA_MEM_OPTS=" -server $MEM_OPTS -Xss256k -XX:+DisableExplicitGC -XX:+UseConcMarkSweepGC -XX:+CMSParallelRemarkEnabled -XX:+UseCMSCompactAtFullCollection -XX:LargePageSizeInBytes=128m -XX:+UseFastAccessorMethods -XX:+UseCMSInitiatingOccupancyOnly -XX:CMSInitiatingOccupancyFraction=70 "
else
    JAVA_MEM_OPTS=" -server -Xms1g -Xmx1g -XX:SurvivorRatio=2 -XX:+UseParallelGC "
fi

echo -e "Starting the $SERVER_NAME ...\c"
nohup java $JAVA_MEM_OPTS $JAVA_DEBUG_OPTS $JAVA_JMX_OPTS -classpath $CONF_DIR:$LIB_JARS com.andy.recruitment.App > $STDOUT_FILE 2>&1 &

COUNT=0
I=0
while [ $I -lt 10 ]; do
    echo -e ".\c"
    COUNT=`netstat -an | grep ":$SERVER_PORT" | grep LISTEN | wc -l`
    if [ $COUNT -eq 1 ]; then
        break
    fi
    I=$(($I+1))
    sleep 5
done

if [ $COUNT -eq 1 ]; then
    echo "OK!"
    PIDS=`ps -f | grep java | grep "$DEPLOY_DIR" | awk '{print $2}'`
    echo "PID: $PIDS"
    echo "STDOUT: $STDOUT_FILE"
else
    echo "FAILED"
fi