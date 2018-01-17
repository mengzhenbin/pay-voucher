#!/usr/bin/env bash

root_path=`dirname "$0"`
cd $root_path
bin_path=`pwd`
cd ${bin_path}/..
base=`pwd`
service_configurationFile=$base/conf/logback.xml

if [ -f $base/bin/service.pid ] ; then
	echo "found service.pid , Please run stop.sh first ,then start.sh" 2>&2
    exit 1
fi

if [ ! -d $base/logs ] ; then
	mkdir -p $base/logs
fi

## set java path
if [ -z "$JAVA" ] ; then
  JAVA=$(which java)
fi

if [ -z "$JAVA" ]; then
  	echo "Cannot find a Java JDK. Please set either set JAVA or put java (>=1.8) in your PATH." 2>&2
    exit 1
fi

case "$#"
in
0 )
	;;
1 )
	if [ "$1" = "debug" ]; then
		DEBUG_PORT=$2
		DEBUG_SUSPEND="n"
		JAVA_DEBUG_OPT="-Xdebug -Xnoagent -Djava.compiler=NONE -Xrunjdwp:transport=dt_socket,address=$DEBUG_PORT,server=y,suspend=$DEBUG_SUSPEND"
	fi;;
* )
	echo "THE PARAMETERS MUST BE TWO OR LESS.PLEASE CHECK AGAIN."
	exit;;
esac

str=`file $JAVA_HOME/bin/java | grep 64-bit`
if [ -n "$str" ]; then
	JAVA_VERSION=`java -version 2>&1 |awk 'NR==1{ gsub(/"/,""); print $3 }'`
	if [[ "$JAVA_VERSION" > "1.8" ]]; then
		JAVA_OPTS="-server -Xms1024m -Xmx1024m -Xss256k -XX:SurvivorRatio=2 -XX:+UseG1GC -XX:MaxGCPauseMillis=200 -XX:+HeapDumpOnOutOfMemoryError"
	else
		JAVA_OPTS="-server -Xms1024m -Xmx1024m -Xmn512m -XX:SurvivorRatio=2 -XX:PermSize=96m -XX:MaxPermSize=256m -Xss256k -XX:-UseAdaptiveSizePolicy -XX:MaxTenuringThreshold=15 -XX:+DisableExplicitGC -XX:+UseConcMarkSweepGC -XX:+CMSParallelRemarkEnabled -XX:+UseCMSCompactAtFullCollection -XX:+UseFastAccessorMethods -XX:+UseCMSInitiatingOccupancyOnly -XX:+HeapDumpOnOutOfMemoryError"
	fi
else
	JAVA_OPTS="-server -Xms1024m -Xmx1024m -XX:NewSize=256m -XX:MaxNewSize=256m -XX:MaxPermSize=128m "
fi

JAVA_OPTS=" $JAVA_OPTS -Djava.awt.headless=true -Djava.net.preferIPv4Stack=true -Dfile.encoding=UTF-8"
JAVA_OPTS=" $JAVA_OPTS -javaagent:../../deploy/jacocoagent.jar=destfile=test.exec"
SERVICE_OPTS="-Dservice.configurationFile=$service_configurationFile"

if [ -e $service_configurationFile ]
then

    	MAIN_CLASS="com.youzan.platform.bootstrap.startup.Bootstrap"

    	for i in $base/lib/*;
    		do CLASSPATH=$i:"$CLASSPATH";
    	done
    	CLASSPATH="$base/conf:$CLASSPATH";

        echo "cd to $bin_path for workaround relative path"
        cd $bin_path

        echo LOG CONFIGURATION : $service_configurationFile
        echo CLASSPATH :$CLASSPATH
        $JAVA $JAVA_OPTS $JAVA_DEBUG_OPT $SERVICE_OPTS -classpath $CLASSPATH $MAIN_CLASS 1>>$base/logs/service.log 2>&1 &
        echo $! > $base/bin/service.pid

        echo "cd to $bin_path for continue"
        cd $bin_path
else
        echo "log configration file($service_configurationFile) is not exist,please create then first!"
fi