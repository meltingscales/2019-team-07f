#!/usr/bin/env bash

echo "Inserting test users."

# Run our user-inserting script.
mysql -uroot -ppassword < "/home/vagrant/2019-team-07f/server/src/main/resources/test-data/test-users.sql" && echo "Inserted test users." || echo "Test users already inserted."
