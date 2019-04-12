#!/usr/bin/env bash

sudo apt install -y nfs-kernel-server
sudo apt install -y dos2unix

# Copy config file.
sudo mv /tmp/exports /etc/exports

# Remove CRLF from config file
sudo dos2unix /etc/exports

if [[ ! -d /mnt/sharedfolder ]]; then
    sudo mkdir -p /mnt/sharedfolder

    sudo chown nobody:nogroup /mnt/sharedfolder

    sudo chmod 777 /mnt/sharedfolder
fi

sudo exportfs -a

sudo systemctl restart nfs-kernel-server

sudo ufw allow from ${SUBNET_IP}/24 to any port nfs
sudo ufw allow 22

yes | ufw enable

sudo ufw status
