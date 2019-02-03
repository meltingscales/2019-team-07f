#!/usr/bin/env bash

echo "Setting up Tomcat."

# Taken from https://linuxize.com/post/how-to-install-tomcat-9-on-ubuntu-18-04/#step-4-create-a-systemd-unit-file
# and also https://www.digitalocean.com/community/tutorials/how-to-install-apache-tomcat-8-on-ubuntu-16-04

# Install Tomcat.
apt-get install -y tomcat8 tomcat8-docs tomcat8-admin tomcat8-examples

# Set up Tomcat's users.
cp -f /vagrant/vagrant-config/config-files/tomcat/tomcat-users.xml /var/lib/tomcat8/conf/tomcat-users.xml

# Set up Tomcat's configuration.
cp -f /vagrant/vagrant-config/config-files/tomcat/server.xml /var/lib/tomcat8/conf/server.xml

# If CATALINA_HOME isn't set, set it permanently.
if [[ -z "${CATALINA_HOME}" ]]; then
    echo 'CATALINA_HOME="/usr/share/tomcat8"' >> /etc/environment
    export CATALINA_HOME=/usr/share/tomcat8
fi