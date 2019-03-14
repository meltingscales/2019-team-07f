#!/bin/sh

echo "Configuring iscsi target"

apt -y install targetcli-fb #installation of administration tools

targetcli					#enter the admin console
cd backstores/fileio
create disk01 /var/lib/iscsi_disks/disk01.img 10G  # create a disk-image with the name "disk01" on /var/lib/iscsi_disks/disk01.img with 10G
cd /iscsi 

create iqn.2019-03.localdomain.iscsi:dlp.target01 # create a target
										  # naming rule : [ iqn.(year)-(month).(reverse of domain name):(any name you like) ]

cd iqn.2019-03.localdomain.iscsi:dlp.target01/tpg1/luns 

# set LUN
create /backstores/fileio/disk01 
cd ../acls 
create iqn.2019-03.localdomain.iscsi:www.initiator01 # set ACL (it's the IQN of an initiator you permit to connect)
cd iqn.2019-03.localdomain.iscsi:www.initiator01 

# set UserID for authentication
set auth userid=vagrant 
set auth password=vagrant 
exit 
echo "Finish configuring iscsi target"
		