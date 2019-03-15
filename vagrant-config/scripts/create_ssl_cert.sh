#!/usr/bin/env bash

# Create the SSL Certificate.
sudo openssl req -x509 -nodes -days 365 -newkey rsa:2048 -keyout /etc/ssl/private/apache-selfsigned.key -out /etc/ssl/certs/apache-selfsigned.crt

# Prompts shows up
Country Name (2 letter code) [AU]:US
State or Province Name (full name) [Some-State]:
Locality Name (eg, city) []:
Organization Name (eg, company) [Internet Widgits Pty Ltd]:
Organizational Unit Name (eg, section) []:
Common Name (e.g. server FQDN or YOUR name) []:10.0.0.11
Email Address []:

# Create Diffie-Hellman group
sudo openssl dhparam -out /etc/ssl/certs/dhparam.pem 2048

# Configure Apache to Use SSL (instead of nano we use vim)
sudo vim /etc/apache2/conf-available/ssl-params.conf

# Change SSLOpenSSLConfCmd DHParameters diretion to where we store the Diffie-Hellman File
In ssl-params.conf file type following:
SSLOpenSSLConfCmd DHParameters "/etc/ssl/certs/dhparam.pem"

# Modify the Default Apache SSL Virtual Host File.
sudo vim /etc/apache2/sites-available/default-ssl.conf
In default-ssl.conf change following lines:
ServerAdmin admin@gmail.com
ServerName 10.0.0.11
SSLCertificateFile      /etc/ssl/certs/apache-selfsigned.crt
SSLCertificateKeyFile /etc/ssl/private/apache-selfsigned.key

# Redirect to HTTPS
sudo vim /etc/apache2/sites-available/000-default.conf
In the 000-default.conf file change this line:
Redirect permanent "/" "https://10.0.0.11/"

# Adjust the Firewall.
sudo apt-get install ufw
sudo ufw app list
sudo ufw allow 'Apache Full'
sudo ufw delete allow 'Apache'
