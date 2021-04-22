cd ${COMPOSE_PATH}
docker-compose stop $COMPOSE_SERVICE_NAME
docker-compose build --build-arg JAR_FILE=$JAR_FILE $COMPOSE_SERVICE_NAME 
docker-compose up -d $COMPOSE_SERVICE_NAME
#remove dangling images
docker rmi $(docker images -qa -f 'dangling=true')



