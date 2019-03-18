# What is this?

This is a folder that contains files that tell Packer how to
make a virtual hard disk configured with Ubuntu and some
other prerequisites for our project like Apache Tomcat, etc.

# How do I build the packer images?

If you have ruby, run `ruby build-missing.rb` and all missing images will be built, in serial.

If not, you can run the below commands:

Do NOT run these in parallel, as they (can) break each other. <!-- TODO can this be fixed? -->

`packer build ubuntu-storage.json` will build the iSCSI box.

`packer build ubuntu-webserver.json` will build the webserver.

`packer build ubuntu-mysql.json` will build the MySQL box.