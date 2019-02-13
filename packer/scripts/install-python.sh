#!/usr/bin/env bash

echo "Installing Python."

# Python dev for compiling tools for jep.
apt-get install -y python3.6-dev

# Install Python's pip
apt-get install -y python3.6 python3-pip
python3.6 -m pip install pipenv
