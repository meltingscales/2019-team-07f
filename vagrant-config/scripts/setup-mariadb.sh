apt-get install -y mariadb-server

service mysql start

mysql --host=localhost --user="root" --password="" < "/vagrant/server/src/main/resources/setup.sql"