#!/usr/bin/env bash

echo "Testing jep."

cd /vagrant/tools/video-to-audio/jep-example/

# Check that java and javac exist.
java -version
javac -version

# Use pom.xml to build our example Java jep app.
mvn package

mvn exec:java