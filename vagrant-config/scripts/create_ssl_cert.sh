#!/usr/bin/env bash

# Create the SSL Certificate.
#sudo openssl req -x509 -nodes -days 365 -newkey rsa:1024 \
#-keyout /etc/ssl/private/apache-selfsigned.key -out /etc/ssl/certs/apache-selfsigned.crt \
#-subj "/C=$COUNTRY/ST=$STATE/L=$CITY/O=$TEAM_ORG/OU=$TEAM_NAME/CN=$WEB_IP_ADDR" 2>/dev/null

# Create Diffie-Hellman group
#sudo openssl dhparam -out /etc/ssl/certs/dhparam.pem 1024

# Configure Apache to Use SSL
#sudo vi /etc/apache2/conf-available/ssl-params.conf

# Change SSLOpenSSLConfCmd DHParameters diretion to where we store the Diffie-Hellman File
#In ssl-params.conf file type following:
#SSLOpenSSLConfCmd DHParameters "/etc/ssl/certs/dhparam.pem"

# Modify the Default Apache SSL Virtual Host File.
#sudo vim /etc/apache2/sites-available/default-ssl.conf
#In default-ssl.conf change following lines:
#ServerAdmin admin@gmail.com
#ServerName 10.0.0.11
#SSLCertificateFile      /etc/ssl/certs/apache-selfsigned.crt
#SSLCertificateKeyFile /etc/ssl/private/apache-selfsigned.key

# Redirect to HTTPS
#sudo vim /etc/apache2/sites-available/000-default.conf
#In the 000-default.conf file change this line:
#Redirect permanent "/" "https://10.0.0.11/"

# Using Apache Tomcat
# Create Keystore file (password for tomcat:password)
echo "Start generating keystore file..."

#keytool -genkey -alias tomcatSSL \
#  -keysize 2048 \
#  -storetype PKCS12 -keyalg RSA  \
#  -storepass password \
#  -keypass password \
#  -keystore key.jks \
#  -dname "CN=$WEB_IP_ADDR/OU=$TEAM_NAME/O=$TEAM_ORG/L=$CITY/ST=$STATE/C=$COUNTRY" \
#  -noprompt \
keytool -delete -alias tomcatSSL -keystore newkey.jks
keytool -genkeypair -alias tomcatSSL -keyalg RSA -dname "CN=$WEB_IP_ADDR,OU=$TEAM_NAME,O=$TEAM_ORG,L=$CITY,S=$STATE,C=$COUNTRY" -keypass password -keystore newkey.jks -storepass password
keytool -genkeypair -alias tomcatSSL -keystore newkey.p12 -storetype pkcs12 -keyalg RSA -dname "CN=$WEB_IP_ADDR,OU=$TEAM_NAME,O=$TEAM_ORG,L=$CITY,S=$STATE,C=$COUNTRY" -keypass password -storepass password
keytool -exportcert -alias tomcatSSL -file newkey.cer -keystore newkey.p12 -storetype pkcs12 -storepass password
keytool -importcert -keystore newkey.jks -alias tomcatSSL -file newkey.cer -v -trustcacerts -noprompt -storepass password
keytool -list -v -keystore newkey.jks -storepass password
echo "Finishing create keystore file"

# Create Certificate Signing Request (CSR)
#echo "Start creating certificate from cert request..."
#keytool -certreq -keyalg RSA -alias tomcatSSL -file /home/vagrant/mycert.csr -keystore key.jks
#echo "Finsihing create certificate file"

#sudo openssl pkcs12 -export -in mycert.crt -inkey key.jks -out keycrt.p12 -certfile mycert_new.crt
#keytool -importkeystore -alias tomcatSSL -destkeystore tomcat.keystore -srckeystore keycrt.p12 -srcstoretype pkcs12

# Import root SSL
#keytool -import -alias root -keystore key.jks -trustcacerts -file certreq.csr
# Import intermed SSL
#keytool -import -alias intermed -keystore key.jks -trustcacerts -file certreq.csr
# Import SSL into keystore
#keytool -import -alias tomcatSSL -keystore key.jks -trustcacerts -file /home/vagrant/certreq.csr

# Adjust the Firewall.
echo "Installing and Checking fire wall setting..."
sudo apt-get install ufw
sudo ufw enable
sudo ufw allow ssh
sudo ufw app list

echo "restart Tomcat 8"
systemctl restart tomcat8
