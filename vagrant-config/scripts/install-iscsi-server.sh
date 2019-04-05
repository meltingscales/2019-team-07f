#!/usr/bin/env bash
echo "Configuring iscsi target"

# Install `tgt`, an iSCSI administration tool.
apt-get install -y tgt

# Make a folder to store iSCSI disks.
mkdir /var/lib/iscsi_disks

# Create a 10GB volume for back storage.
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
#sudo vim /etc/vagrant-config/config-files/iscsi/target.cnf

# Print result of `tgtadm` command.
#output=`tgtadm --mode target --op show`
#echo ${output}

echo "Done configuring iscsi target"

# open network port 3260
sudo netstat -tulpn | grep 3260

# start 'tgt'
#tgtadm -s

# Create new target
sudo tgtadm --lld iscsi --mode target --op new --tid=1 --targetname iqn.2018-05.world.srv:dlp.target01

# Add a logical unit (LUN)
sudo tgtadm --lld iscsi --mode logicalunit --op new --tid 1 --lun 1 -b /var/lib/iscsi_disks/disk01.img

# Accept the initiator from specific IP, if want to receive all initiator's connect then have to unbind the ip address and name
# then change to: tgtadm --lld iscsi --op bind --mode target --tid 1 -I ALL
sudo tgtadm --lld iscsi --op bind --mode target --tid 1 --initiator-address 10.3.0.11

# Accept the specific initiator name
sudo tgtadm --lld iscsi --op bind --mode target --tid 1 --initiator-name iqn.2018-05.world.srv:www.initiator01

# List out all active target
sudo tgtadm --lld iscsi --mode target --op show

# Create a user account for testing
sudo tgtadm --lld iscsi --mode account --op new --user ''testuser'' --password ''password''

# Add user to a target device
sudo tgtadm --lld iscsi --mode account --op bind --tid 1 --user ''testuser'' --outgoing

# Save changed config file not working
#sudo tgtadm --dump > /etc/tgt/targets.conf

# List all active accounts
sudo tgtadm --lld iscsi --mode account --op show
