#!/usr/bin/env bash

echo "Setting up Maven."

# Copy settings file over to Maven 2's home.
cp -f /home/vagrant/2019-team-07f/vagrant-config/config-files/maven/settings.xml $M2_HOME/conf/settings.xml

echo "Done setting up Maven."