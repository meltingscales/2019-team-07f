#!/usr/bin/env bash

echo "Setting up MySQL."

service mysql start

# Run our setup script.
mysql -uroot -ppassword < "/home/vagrant/2019-team-07f/server/src/main/resources/setup.sql"

# Setup our blank password.
#mysql -uroot -e "ALTER USER 'root'@'localhost' IDENTIFIED WITH mysql_native_password by 'password'; FLUSH PRIVILEGES;"

service mysql restart
