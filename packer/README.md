# What is this?

This is a folder that contains files that tell Packer how to
make a virtual hard disk configured with Ubuntu and some
other prerequisites for our project like Apache Tomcat, etc.

# How do I build the packer image?

`packer build ubuntu-vagrant.json`.

That generates a file at `/packer/output/ubuntu-vagrant.box`.