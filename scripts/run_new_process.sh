#nohup java -jar ./script/build/libs/demo-0.0.1-SNAPSHOT.jar &
# run_new_was.sh


CURRENT_PORT=$(cat /etc/nginx/conf.d/service-url.inc | grep -Po '[0-9]+' | tail -1)
TARGET_PORT=0

if [ ${CURRENT_PORT} -eq 8081 ]; then
  TARGET_PORT=8082
elif [ ${CURRENT_PORT} -eq 8082 ]; then
  TARGET_PORT=8081
else
  echo "> [$NOW_TIME] No WAS is connected to nginx"
fi

TARGET_PID=$(lsof -Fp -i TCP:${TARGET_PORT} | grep -Po 'p[0-9]+' | grep -Po '[0-9]+')

if [ ! -z ${TARGET_PID} ]; then
  echo "> Kill WAS running at ${TARGET_PORT}."
  sudo kill ${TARGET_PID}
fi
#JAR_NAME=$(ls -tr ./script/build/libs/demo-0.0.1-SNAPSHOT.jar | tail -n 1)
#echo "> JAR Name: $JAR_NAME"
#chmod +x $JAR_NAME
nohup java -jar -Dserver.port=${TARGET_PORT} /home/ubuntu/*.jar
echo "> Now new WAS runs at ${TARGET_PORT}."
exit 0