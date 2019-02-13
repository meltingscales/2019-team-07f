#!/usr/bin/env bash

echo "Installing Maven."

# Install maven.
apt-get install -y maven

# If M2_HOME isn't set, set it permanently.
if [[ -z "${M2_HOME}" ]]; then
    echo 'M2_HOME="/usr/share/maven"' >> /etc/environment
    export M2_HOME=/usr/share/maven
fi