#!/bin/bash
sudo fuser -k 8080/tcp
sudo nohup java -Xmx512m -Dserver.port=8080 -Dcom.sun.management.jmxremote -Dcom.sun.management.jmxremote.port=7070 -Dcom.sun.management.jmxremote.authenticate=false -Dcom.sun.management.jmxremote.ssl=false -Djava.rmi.server.hostname=localhost -jar app-dev/app-dev.jar >> app-dev/app-dev.log >&1 &
