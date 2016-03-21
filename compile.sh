#!/usr/bin/env bash

mkdir -p out/production/ShiFuMi/{server,socket,client,client/layout,layout}

javac src/client/*.java -cp ./src/:./src/client -Xlint:unchecked -d out/production/ShiFuMi/
javac src/client/layout/*.java -cp ./src/ -Xlint:unchecked -d out/production/ShiFuMi/layout
javac src/client/layout/*.java -cp ./src/ -Xlint:unchecked -d out/production/ShiFuMi/client/layout
javac src/socket/*.java -cp ./src/ -d out/production/ShiFuMi/socket
javac src/server/*.java -cp ./src/ -d out/production/ShiFuMi/server
