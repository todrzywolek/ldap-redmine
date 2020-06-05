#!/bin/bash

# build redmine-backend docker image if not exists
if [[ "$(docker images -q todrzywolek/redmine-backend:latest 2> /dev/null)" == "" ]]; then
  cd ./redmine-backend
  ./build.sh
  cd ..
fi

# build redmine-frontend docker image if not exists
if [[ "$(docker images -q todrzywolek/redmine-frontend:latest 2> /dev/null)" == "" ]]; then
  cd ./redmine-frontend
  ./build_docker_image.sh
  cd ..
fi

docker-compose up