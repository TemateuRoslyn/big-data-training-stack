#!/bin/sh

# ATTENTION, CETTE INSTRUCTION VA STOPPER TOUS LES CONTAINERS
# sudo docker kill $(sudo docker ps -q)

sudo docker-compose up -d --build
