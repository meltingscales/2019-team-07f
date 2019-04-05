#!/usr/bin/env bash
# echo https://help.ubuntu.com/lts/serverguide/iscsi-initiator.html.en
# echo https://www.server-world.info/en/note?os=Ubuntu_18.04&p=iscsi&f=2

# install open-iSCSI
apt-get install -y open-iscsi

# check traget ip addresses (web)
sudo iscsiadm -m discovery -t st -p 10.3.0.11

# list out all the iscsi target
sudo iscsiadm -m node -o show

# connect to the target
sudo iscsiadm --mode node --targetname iqn.2018-05.world.srv:dlp.target01 --portal 10.3.0.11:3260 --login

# disconnect to all target
sudo iscsiadm --mode node --logoutall=all
