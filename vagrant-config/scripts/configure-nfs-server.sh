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

# Allow nfs mounting from any
sudo ufw allow from ${SUBNET_IP}/24 to any port nfs

# Allow SSH from only host ip
sudo ufw allow from ${HOST_IP} to any port ssh

# Allow netdata from only host ip
sudo ufw allow from ${HOST_IP} to any port 19999

yes | ufw enable

sudo ufw status
