#!/usr/bin/env bash

echo "Doing general setup."

# Install add-apt-repository command.
apt-get install -y software-properties-common

# Add Python repo.
add-apt-repository ppa:deadsnakes/ppa

apt-get update
