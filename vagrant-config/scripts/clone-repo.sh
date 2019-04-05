#!/usr/bin/env bash

apt-get -y install git #TODO this shouldn't be needed, git should be installed in the packer build step. Fix this.

# Delete cloned repo if it exists
if [[ -d ${REPO_PATH} ]]; then
    echo "Deleting repo..."
    rm -rf ${REPO_PATH}
fi

echo "Cloning repo..."
# Clone team repo into /home/vagrant/2019-team-07f/
sudo git clone ${REPO_URL} ${REPO_PATH}

# Give vagrant user ownership of git repo
sudo chown vagrant:vagrant ${REPO_PATH}

echo "Done cloning repo."