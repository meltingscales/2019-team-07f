#!/usr/bin/env bash

echo "Deploying app."

cd /home/vagrant/2019-team-07f/server/

# Make sure Tomcat is running.
systemctl restart tomcat8

# Generate WAR.
mvn clean install

# Deploy WAR to Tomcat.
mvn tomcat7:redeploy

# Show our page!
curl http://localhost:8080/searchable-video-library/

echo "Hey buddy. Check out http://localhost:8080/searchable-video-library/ for some cool stuff."
