#!/bin/sh

echo "********************************************************"
echo "Waiting for the eureka server to start on port $EUREKASERVER_PORT"
echo "********************************************************"
while ! `nc -z eurekaserver  $EUREKASERVER_PORT`; do sleep 3; done
echo "******* Eureka Server has started"

echo "********************************************************"
echo "Waiting for the configuration server to start on port $CONFIGSERVER_PORT"
echo "********************************************************"
while ! `nc -z configserver $CONFIGSERVER_PORT `; do sleep 3; done
echo ">>>>>>>>>>>> Configuration Server has started"

echo "********************************************************"
echo "Waiting for the database server to start on port $DATABASESERVER_PORT"
echo "********************************************************"
while ! `nc -z database $DATABASESERVER_PORT`; do sleep 3; done
echo ">>>>>>>>>>>> Database Server has started"

echo "********************************************************"
echo "Starting License Server with Configuration Service :  $CONFIGSERVER_URL";
echo "********************************************************"

echo "********************************************************"
echo "Waiting for the zookeeper server to start on port $KAFKASERVER_PORT"
echo "********************************************************"
while ! `nc -z zookeeper $KAFKASERVER_PORT`; do sleep 10; done
echo "*******  zookeeper Server has started"

echo "********************************************************"
echo "Waiting for the REDIS server to start  on port $REDIS_PORT"
echo "********************************************************"
while ! `nc -z redis $REDIS_PORT`; do sleep 10; done
echo "******* REDIS has started"

echo "********************************************************"
echo "Starting License Server with Configuration Service via Eureka :  $EUREKASERVER_URI"
echo "Using Kafka Server: $KAFKASERVER_URI"
echo "Using ZK    Server: $ZKSERVER_URI"
echo "********************************************************"

java -Dspring.cloud.config.uri=$CONFIGSERVER_URL  \
     -Dserver.port=$SERVER_PORT \
     -Dspring.profiles.active=$PROFILE \
     -Deureka.client.serviceUrl.defaultZone=$EUREKASERVER_URI \
     -Dsecurity.oauth2.resource.userInfoUri=$AUTHSERVER_URI   \
     -Dsigning.key=$JWT_KEY                          \
     -Dspring.cloud.stream.kafka.binder.zkNodes=$ZKSERVER_URI          \
     -Dspring.cloud.stream.kafka.binder.brokers=$KAFKASERVER_URI             \
     -jar /usr/local/licensingservice/@project.build.finalName@.jar