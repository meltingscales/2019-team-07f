#!/usr/bin/env bash

echo "Start generating keystore file..."

keytool -genkeypair -alias tomcatSSL -keystore newkey.p12 -storetype pkcs12 -keyalg RSA -dname "CN=$WEB_IP_ADDR,OU=$TEAM_NAME,O=$TEAM_ORG,L=$CITY,S=$STATE,C=$COUNTRY" -keypass password -storepass password
keytool -exportcert -alias tomcatSSL -file newkey.cer -keystore newkey.p12 -storetype pkcs12 -storepass password
keytool -importcert -keystore newkey.jks -alias tomcatSSL -file newkey.cer -v -trustcacerts -noprompt -storepass password
keytool -list -v -keystore newkey.jks -storepass password
echo "Finishing create keystore file"

# Create Certificate Signing Request (CSR)
echo "Start creating certificate from cert request..."
keytool -certreq -keyalg RSA -alias tomcatSSL -file /home/vagrant/mycert.csr -keystore key.jks
echo "Finsihing create certificate file"
