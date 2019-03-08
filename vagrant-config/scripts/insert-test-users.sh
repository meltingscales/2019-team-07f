#!/usr/bin/env bash

echo "Inserting test users."

# Run our user-inserting script.
mysql -uroot -ppassword < "/vagrant/server/src/main/resources/test-data/test-users.sql" && echo "Inserted test users." || echo "Test users already inserted."