#!/usr/bin/env bash

for env in $( cat /etc/environment ); do export $(echo $env | sed -e 's/"//g'); done