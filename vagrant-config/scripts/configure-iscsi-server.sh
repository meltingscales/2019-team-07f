#!/usr/bin/env bash

echo "Configuring iscsi target"

# Make a folder to store iSCSI disks.
mkdir /var/lib/iscsi_disks

# Create a 10GB volume.
dd if=/dev/zero of=/var/lib/iscsi_disks/disk01.img count=0 bs=1 seek=10000

# Restart tgt to read config file copied by file provisioner.
systemctl enable tgt
systemctl restart tgt

# Print result of `tgtadm` command.
output=`tgtadm --mode target --op show`
echo ${output}

echo "Done configuring iscsi target"
