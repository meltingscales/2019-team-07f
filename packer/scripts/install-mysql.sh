#!/usr/bin/env bash

echo "Installing MySQL."

# Password is 'password'.
# This also prevents any prompts from coming up.
echo "mysql-server-5.7 mysql-server/root_password password password"        | sudo debconf-set-selections
echo "mysql-server-5.7 mysql-server/root_password_again password password"  | sudo debconf-set-selections

apt-get install -y mysql-server-5.7 mysql-client