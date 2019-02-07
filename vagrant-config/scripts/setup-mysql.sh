
# Empty password.
# This also prevents any prompts from coming up.
echo "mysql-server-5.7 mysql-server/root_password password password"        | sudo debconf-set-selections
echo "mysql-server-5.7 mysql-server/root_password_again password password"  | sudo debconf-set-selections

apt-get install -y mysql-server-5.7 mysql-client

service mysql start

# Run our setup script.
mysql -uroot -ppassword < "/vagrant/server/src/main/resources/setup.sql"

# Setup our blank password.
#mysql -uroot -e "ALTER USER 'root'@'localhost' IDENTIFIED WITH mysql_native_password by 'password'; FLUSH PRIVILEGES;"

service mysql restart