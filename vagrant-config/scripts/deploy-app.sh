#!/usr/bin/env bash

echo "Deploying app."

cd /vagrant/server/

# Make sure Tomcat is running.
systemctl restart tomcat8

# Generate WAR.
mvn clean install

# Deploy WAR to Tomcat.
mvn tomcat7:redeploy

# Show our page!
curl http://127.0.0.1:8080/searchable-video-library/index.jsp

echo "Hey buddy. Check out http://127.0.0.1:8080/searchable-video-library/index.jsp for some cool stuff."