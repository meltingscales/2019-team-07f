#!/usr/bin/env bash

echo "Setting up Maven."

# Copy settings file over to Maven 2's home.
cp -f /vagrant/vagrant-config/config-files/maven/settings.xml $M2_HOME/conf/settings.xml