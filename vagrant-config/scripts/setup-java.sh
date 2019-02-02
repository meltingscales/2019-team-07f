#!/usr/bin/env bash

# Pre-accept liscense.
echo debconf shared/accepted-oracle-license-v1-1 select true | debconf-set-selections 
echo debconf shared/accepted-oracle-license-v1-1 seen   true | debconf-set-selections
echo debconf shared/accepted-oracle-license-v1-2 select true | debconf-set-selections 
echo debconf shared/accepted-oracle-license-v1-2 seen   true | debconf-set-selections

# Install Java.
apt-get install -y default-jdk

# If JAVA_HOME isn't set, set it permanently.
if [[ -z "${JAVA_HOME}" ]]; then
    export JAVA_HOME=/usr/lib/jvm/java-1.8.0-openjdk-amd64
    echo 'JAVA_HOME="/usr/lib/jvm/java-1.8.0-openjdk-amd64"' >> /etc/environment
fi

