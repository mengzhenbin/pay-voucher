#!/usr/bin/env bash

get_pid() {
	STR=$1
	PID=$2
    if [ ! -z "$PID" ]; then
    	JAVA_PID=`ps -ef|grep "$STR"|grep "$PID"|grep -v grep|awk '{print $2}'`
	else
		JAVA_PID=`ps -ef|grep "$STR"|grep -v grep|awk '{print $2}'`
    fi
    echo $JAVA_PID;
}

base=`dirname $0`/..
pidfile=$base/bin/service.pid
if [ ! -f "$pidfile" ];then
	echo "service-server is not running. exists"
	exit
fi

pid=`cat $pidfile`
if [ "$pid" == "" ] ; then
	echo "can not find pid. exists"
	exit
fi

echo -e "`hostname`: stopping service-server $pid ... "
kill $pid
rm $pidfile