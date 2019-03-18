#!/usr/bin/env bash

echo "Setting up Git SSH keys."

# Make ssh folders if they don't exist.
[[ -d /home/vagrant/.ssh ]] || 	 mkdir -p /home/vagrant/.ssh
[[ -d /root/.ssh ]] ||           mkdir -p /root/.ssh

# Copy key.
cp -v /home/vagrant/id_rsa /home/vagrant/.ssh/

# Set up read/write access.
chmod 700 "/home/vagrant/.ssh"
chmod 600 "/home/vagrant/.ssh/id_rsa"

# Copy config.
cp -v /home/vagrant/config /home/vagrant/.ssh/config
cp -v /home/vagrant/config /root/.ssh/config

echo "Done setting up Git SSH keys."