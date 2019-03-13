#!/usr/bin/env bash

echo "Inserting test users to "$DB_SCHEMA" at "$DB_IP_ADDR"."

# Run our user-inserting script.
mysql -u$DB_USERNAME -p$DB_PASSWORD -h "$DB_IP_ADDR" -P "$DB_PORT" -D "$DB_SCHEMA" < "/home/vagrant/2019-team-07f/server/src/main/resources/test-data/test-users.sql" && echo "Inserted test users." || echo "Test users already inserted."
