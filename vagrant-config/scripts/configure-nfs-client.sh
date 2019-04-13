#!/usr/bin/env bash

sudo apt-get install -y nfs-common

if [[ ! -d ${MOUNT_LOCATION} ]]; then
    sudo mkdir -p ${MOUNT_LOCATION}
fi

if mount | grep ${MOUNT_LOCATION} > /dev/null; then
    echo "Already mounted ${MOUNT_LOCATION}. Not mounting again."
else
    echo "${MOUNT_LOCATION} is NOT mounted. Mounting..."
    sudo mount ${NFS_SERVER_IP}:/mnt/sharedfolder ${MOUNT_LOCATION}
fi
