#!/usr/bin/env bash

REPOSITORY=/home/ec2-user/my-item-stock-batch
cd $REPOSITORY

#./gradlew build

APP_NAME=my-item-stock-batch
JAR_NAME=$(ls $REPOSITORY/build/libs/ | grep '.jar' | tail -n 1)
JAR_PATH=$REPOSITORY/build/libs/$JAR_NAME

CURRENT_PID=$(pgrep -f $APP_NAME)

if [ -z $CURRENT_PID ]
then
  echo "> 종료할것 없음."
else
  echo "> kill -15 $CURRENT_PID"
  kill -15 $CURRENT_PID
  sleep 5
fi

echo "> $JAR_PATH 배포"
nohup java -jar $JAR_PATH > /dev/null 2> /dev/null < /dev/null &

at -M now + 2 minute <<< $'service codedeploy-agent restart'