#!/usr/bin/env bash

sudo apt-get install -y nfs-common

if [[ ! -d $MOUNT_LOCATION ]]; then
    sudo mkdir -p $MOUNT_LOCATION
fi

sudo mount $NFS_SERVER_IP:/mnt/sharedfolder $MOUNT_LOCATION