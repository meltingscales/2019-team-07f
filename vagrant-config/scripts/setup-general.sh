#!/usr/bin/env bash

echo "Doing general setup."

# Install add-apt-repository command.
apt-get install -y software-properties-common

# Install terminal web browser.
apt-get install -y lynx

# Add Python repo.
add-apt-repository ppa:deadsnakes/ppa

apt-get update
