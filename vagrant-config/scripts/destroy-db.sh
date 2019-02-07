service mysql start

mysql --host=localhost --user="root" --password="" < "/vagrant/server/src/main/resources/destroy-db.sql"