#!/usr/bin/env bash

# Install normal deps
sudo apt -y install ruby

# Purge existing vbox for proper installation
sudo apt autoremove virtualbox-dkms

# Install VirtualBox tools.
sudo apt -y install build-essential linux-headers-`uname -r` dkms virtualbox-dkms

# Install Vagrant.
sudo apt -y install vagrant

# Install VirtualBox.
sudo apt -y install virtualbox

# Pre-accept virtualbox-ext-pack license.
echo virtualbox-ext-pack virtualbox-ext-pack/license select true | sudo debconf-set-selections

# Install virtualbox-ext-pack
sudo apt -y install virtualbox-ext-pack
