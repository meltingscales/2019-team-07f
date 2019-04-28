#!/bin/bash -eux

# Apt cleanup.
apt update

# Delete unneeded files.
rm -f /home/vagrant/*.sh

#echo "Writing zeroes to free space to save space. This may take a while."
## Zero out the rest of the free space using dd, then delete the written file.
#dd if=/dev/zero of=/EMPTY bs=1M
#rm -f /EMPTY
#
## Add `sync` so Packer doesn't quit too early, before the large file is deleted.
#sync