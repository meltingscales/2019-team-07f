#!/usr/bin/env bash

echo "Doing general setup."

apt-get update

apt install -y dos2unix

# Install add-apt-repository command.
apt-get install -y software-properties-common
apt-get install -y debconf-utils

# Install better `top` command
apt-get install -y gtop

# Install good terminal multiplexer.
apt-get install -y tmux

# Install curl, a way to send HTTP requests.
apt-get install -y curl

# Install git, a version control system.
apt-get install -y git

# Install terminal web browser.
apt-get install -y lynx

# Add Python repo.
add-apt-repository ppa:deadsnakes/ppa

apt-get update
