
# Empty password.
# This also prevents any prompts from coming up.
debconf-set-selections <<< 'mysql-server-5.6 mysql-server/root_password password'
debconf-set-selections <<< 'mysql-server-5.6 mysql-server/root_password_again password'

apt-get install -y mysql-server mysql-client

service mysql start

# Run our setup script.
mysql -uroot < "/vagrant/server/src/main/resources/setup.sql"

# Setup our blank password.
mysql -uroot -e "ALTER USER 'root'@'localhost' IDENTIFIED WITH mysql_native_password by 'password'; FLUSH PRIVILEGES;"

service mysql restart