#!/usr/bin/env bash

echo "Setting up Tomcat."

# Set up Tomcat's users.
cp -f /vagrant/vagrant-config/config-files/tomcat/tomcat-users.xml /var/lib/tomcat8/conf/tomcat-users.xml

# Set up Tomcat's configuration.
cp -f /vagrant/vagrant-config/config-files/tomcat/server.xml /var/lib/tomcat8/conf/server.xml
