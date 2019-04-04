#!/usr/bin/env bash
echo "Configuring iscsi target"

# Install `tgt`, an iSCSI administration tool.
apt-get install -y tgt

# Make a folder to store iSCSI disks.
mkdir /var/lib/iscsi_disks

# Create a 10GB volume.
dd if=/dev/zero of=/var/lib/iscsi_disks/disk01.img count=0 bs=1 seek=10000

# Restart tgt to read config file copied by file provisioner.
systemctl enable tgt
systemctl restart tgt

# Delete swp file if it exists
#if [[ -d /var/tmp/target01.conf.swp ]]; then
#    echo "Deleting swp file..."
#    rm -rf /var/tmp/target01.conf.swp
#fi

# start config file of target
sudo vim /etc/vagrant-config/config-files/iscsi/target.cnf

# Print result of `tgtadm` command.
output=`tgtadm --mode target --op show`
echo ${output}

echo "Done configuring iscsi target"

# start 'tgt'
chkconfig tgtd on

# Add a logical unit (LUN)
tgtadm --lld iscsi --mode logicalunit --op new --tid 1 --lun 1 -b /var/lib/iscsi_disks/disk01.img

# Accept the initiator from specific IP
tgtadm --lld iscsi --op bind --mode target --tid 1 --initiator-address 10.3.0.11

# List out all active target
tgtadm --lld iscsi --mode target --op show
