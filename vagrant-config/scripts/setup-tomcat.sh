#!/usr/bin/env bash

# Taken from https://linuxize.com/post/how-to-install-tomcat-9-on-ubuntu-18-04/#step-4-create-a-systemd-unit-file

# Set up Tomcat user and give it privileges.
cat /etc/passwd | grep tomcat >/dev/null 2>&1
if [ $? -eq 0 ] ; then
    echo "Tomcat user exists. Not creating."
else
    echo "Tomcat user does NOT exist. Creating."
    useradd -r -m -U -d /opt/tomcat -s /bin/false tomcat
fi

# Download Tomcat.
# TODO Embed this file to prevent security issues from arising.
# TODO Validate hash of file.
wget http://www-eu.apache.org/dist/tomcat/tomcat-9/v9.0.14/bin/apache-tomcat-9.0.14.tar.gz -P /tmp

# Extract the downloaded Tomcat into an install dir.
tar xf /tmp/apache-tomcat-9.0.14.tar.gz -C /opt/tomcat

# Remove our temp file.
rm -f /tmp/apache-tomcat-9.0.14.tar.gz

# Create a symlink to /opt/tomcat/latest.
ln -s /opt/tomcat/apache-tomcat-9.0.14 /opt/tomcat/latest

# Set up Tomcat's users.
cp -f /vagrant/vagrant-config/config-files/tomcat-users.xml /opt/tomcat/latest/conf/tomcat-users.xml

# Make tomcat user the owner of tomcat directory.
chown -RH tomcat: /opt/tomcat/latest

# Make sure tomcat bin/ folder's binaries are executable.
chmod o+x /opt/tomcat/latest/bin/

# Set up tomcat as a system service.
cp -f /vagrant/vagrant-config/config-files/tomcat.service /etc/systemd/system/tomcat.service

# Tell systemd that we have a new unit file.
systemctl daemon-reload