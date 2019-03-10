#!/usr/bin/env bash

# Make ssh folders if they don't exist.
[[ -d /home/vagrant/.ssh ]] || 	 mkdir -p /home/vagrant/.ssh
[[ -d /root/.ssh ]] ||           mkdir -p /root/.ssh

# Copy key.
cp -v /home/vagrant/id_rsa /home/vagrant/.ssh/

# Set up read/write access.
chmod 700 "/home/vagrant/.ssh"
chmod 600 "/home/vagrant/.ssh/id_rsa"

# Copy config.
cp -v /home/vagrant/config /home/vagrant/.ssh/config
cp -v /home/vagrant/config /root/.ssh/config

# Delete cloned repo if it exists
if [[ -d /home/vagrant/2019-team-07f/ ]]; then
    rm -rf /home/vagrant/2019-team-07f/
fi

# Clone team repo into /home/vagrant/2019-team-07f/
sudo git clone git@github.com:illinoistech-itm/2019-team-07f.git /home/vagrant/2019-team-07f/

# Give vagrant user ownership of git repo
sudo chown vagrant:vagrant /home/vagrant/2019-team-07f/

