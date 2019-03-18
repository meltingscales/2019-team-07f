#!/usr/bin/env bash

echo "Setting up MySQL."

service mysql start

# Run our setup script.
mysql -uroot -ppassword < "/home/vagrant/2019-team-07f/server/src/main/resources/setup.sql"

service mysql restart

echo "Done setting up MySQL."