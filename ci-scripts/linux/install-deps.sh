#!/usr/bin/env bash

# Install normal deps
sudo apt -y install ruby

# Purge existing vbox for proper installation
sudo apt autoremove virtualbox-dkms

# Install VirtualBox tools.
sudo apt -y install build-essential linux-headers-$(uname -r) dkms virtualbox-dkms

# Install Vagrant.
sudo apt -y install vagrant

# Install VirtualBox.
sudo apt -y install virtualbox

# Pre-accept virtualbox-ext-pack license.
echo virtualbox-ext-pack virtualbox-ext-pack/license select true | sudo debconf-set-selections

# Try to install virtualbox-ext-pack
(
    sudo apt -y install virtualbox-ext-pack
)

if [[ $? = 100 ]]; then
    echo "Error 100! Could not find virtualbox-ext-pack...Installing with wget."

    VBOX_EXT_FILENAME=Oracle_VM_VirtualBox_Extension_Pack-6.0.4.vbox-extpack

    # Download it.
    curl https://download.virtualbox.org/virtualbox/6.0.4/${VBOX_EXT_FILENAME} --output /tmp/${VBOX_EXT_FILENAME}

    # Install it.
    VBoxManage extpack install /tmp/${VBOX_EXT_FILENAME}

fi
