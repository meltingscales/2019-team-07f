# What is this?

This is a folder that contains files that tell Packer how to
make a virtual hard disk configured with Ubuntu and some
other prerequisites for our project like Apache Tomcat, etc.

# How do I build the packer images?

`packer build -force ubuntu-webserver.json` will build the webserver.
`packer build -force ubuntu-mysql.json` will build the MySQL box.
