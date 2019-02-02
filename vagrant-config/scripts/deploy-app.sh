#!/usr/bin/env bash

cd /vagrant/server/

# Make sure Tomcat is running.
sh /opt/tomcat/latest/bin/shutdown.sh
sh /opt/tomcat/latest/bin/startup.sh

# Generate WAR.
mvn clean install

# Deploy WAR to Tomcat.
mvn tomcat7:redeploy

# Show our page!
curl http://127.0.0.1:8081/searchable-video-library/index.jsp
