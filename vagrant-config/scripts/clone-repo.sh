#!/usr/bin/env bash

apt-get -y install git #TODO this shouldn't be needed, git should be installed in the packer build step. Fix this.

# Delete cloned repo if it exists
if [[ -d /home/vagrant/2019-team-07f/ ]]; then
    echo "Deleting repo..."
    rm -rf /home/vagrant/2019-team-07f/
fi

echo "Cloning repo..."
# Clone team repo into /home/vagrant/2019-team-07f/
sudo git clone ${REPO_URL} /home/vagrant/2019-team-07f/

# Give vagrant user ownership of git repo
sudo chown vagrant:vagrant /home/vagrant/2019-team-07f/

echo "Done cloning repo."