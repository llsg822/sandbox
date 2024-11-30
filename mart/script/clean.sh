#!/bin/bash

CONTAINER_NAME=martdb

# Stop the container if it is running
if [ "$(docker ps -q -f name=$CONTAINER_NAME)" ]; then
    echo "Stopping the container..."
    docker stop $CONTAINER_NAME
fi

# Remove the container if it exists
if [ "$(docker ps -aq -f name=$CONTAINER_NAME)" ]; then
    echo "Removing the container..."
    docker rm $CONTAINER_NAME
fi

echo "Cleanup completed."