#!/usr/bin/env bash

# Install normal deps
sudo apt -y install ruby

# Purge existing vbox for proper installation
sudo apt -y autoremove virtualbox-dkms

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
    echo "Error 100! Could not find virtualbox-ext-pack...Installing with curl."

    # Filename of the extpack.
    VBOX_EXT_FILENAME=Oracle_VM_VirtualBox_Extension_Pack-6.0.4.vbox-extpack

    # License key for unattended installations.
    # This is not a security risk, just proof I've accepted the license once.
    LICENSE_KEY=56be48f923303c8cababb0bb4c478284b688ed23f16d775d729b89a2e8e5f9e

    # If the extpack doesn't exist, then
    if [[ ! -f /tmp/${VBOX_EXT_FILENAME} ]]; then
        # Download it.
        curl https://download.virtualbox.org/virtualbox/6.0.4/${VBOX_EXT_FILENAME} --output /tmp/${VBOX_EXT_FILENAME}
    fi


    # Install it.
    echo "y" | sudo VBoxManage extpack install /tmp/${VBOX_EXT_FILENAME}

fi
