#!/usr/bin/env bash

# Install add-apt-repository command.
apt-get install -y software-properties-common

# Add Python repo.
add-apt-repository ppa:deadsnakes/ppa

apt-get update
