#!/usr/bin/env bash
echo "Configuring iscsi target"

INITIATOR_IP=10.3.0.11
INITIATOR_NAME=web

USERNAME=testuser
PASSWORD=password

TARGET_NAME=iscsi
ISCSI_DISK_FOLDER=/var/lib/iscsi_disks/
ISCSI_DISK_PATH=/var/lib/iscsi_disks/disk01.img

TARGET_ID=1
LUN_ID=1

# Install `tgt`, an iSCSI administration tool.
apt-get install -y tgt


if [[ ! -d ${ISCSI_DISK_FOLDER} ]]; then
    echo "Making iscsi folder..."
    # Make a folder to store iSCSI disks.
    mkdir -p ${ISCSI_DISK_FOLDER}
fi

# If file does not exist,
if [[ ! -e ${ISCSI_DISK_PATH} ]]; then
    echo "Making iscsi disk..."
    # Create a 10GB volume for back storage.
    dd if=/dev/zero of=${ISCSI_DISK_PATH} count=1024 bs=1048576 seek=100000
fi

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

echo "Done configuring iscsi target."

# Check if port 3260 is open
sudo netstat -tulpn | grep 3260

# start 'tgt'
#tgtadm -s

# Create new target
sudo tgtadm --lld iscsi --mode target --op new --tid ${TARGET_ID} --targetname ${TARGET_NAME}

# Add a logical unit (LUN)
sudo tgtadm --lld iscsi --mode logicalunit --op new --tid ${TARGET_ID} --lun ${LUN_ID} -b ${ISCSI_DISK_PATH}

# Accept the initiator from specific IP, if want to receive all initiator's connect then have to unbind the ip address and name
# then change to: tgtadm --lld iscsi --op bind --mode target --tid ${TARGET_ID} -I ALL
sudo tgtadm --lld iscsi --op bind --mode target --tid ${TARGET_ID} --initiator-address ${INITIATOR_IP}

# Accept the specific initiator name
sudo tgtadm --lld iscsi --op bind --mode target --tid ${TARGET_ID} --initiator-name ${INITIATOR_NAME}

# List out all active target
sudo tgtadm --lld iscsi --mode target --op show

# Create a user account for testing
sudo tgtadm --lld iscsi --mode account --op new --user ${USERNAME} --password ${PASSWORD}

# Add user to a target device
sudo tgtadm --lld iscsi --mode account --op bind --tid ${TARGET_ID} --user ${USERNAME} --outgoing

# Save changed config file not working
#sudo tgtadm --dump > /etc/tgt/targets.conf

# List all active accounts
sudo tgtadm --lld iscsi --mode account --op show
