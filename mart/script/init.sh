#!/bin/bash

CONTAINER_NAME=martdb
POSTGRES_DB=sandbox
POSTGRES_USER=sandbox
POSTGRES_PASSWORD=sandbox
POSTGRES_PORT=56391

if [ -z "$(docker ps -q -f name=$CONTAINER_NAME)" ]; then
    if [ "$(docker ps -aq -f name=$CONTAINER_NAME)" ]; then
        echo "Removing existing stopped container..."
        docker rm $CONTAINER_NAME
    fi
    echo "Starting new PostGIS container..."
    docker run --name $CONTAINER_NAME -d \
        -e POSTGRES_USER=$POSTGRES_USER \
        -e POSTGRES_PASSWORD=$POSTGRES_PASSWORD \
        -e POSTGRES_DB=$POSTGRES_DB \
        -p $POSTGRES_PORT:5432 \
        postgis/postgis
    echo "PostGIS Docker container started."
else
    echo "PostGIS Docker container is already running."
fi
