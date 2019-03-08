#!/usr/bin/env bash

echo "Doing general setup."

# Install add-apt-repository command.
apt-get install -y software-properties-common

apt-get install -y debconf-utils

# Install curl, a way to send HTTP requests.
apt-get install -y curl

# Install git, a version control system.
apt-get install -y git

# Install terminal web browser.
apt-get install -y lynx

# Add Python repo.
add-apt-repository ppa:deadsnakes/ppa

apt-get update
