# -*- mode: ruby -*-
# vi: set ft=ruby :

require 'yaml'

# Build script settings.
RUN_TESTS = false # Run tests?
DESTROY_DB = false # Destroy the database?
ENGAGE_CAKE = true # Engage cake? yes
DEPLOY = true # Deploy the app?
INSERT_TEST_DATA = true # Insert test data upon provision step?


# Load YAML file containing IP addresses.
variables = YAML.load_file("variables.yml")

# All Vagrant configuration is done below. The "2" in Vagrant.configure
# configures the configuration version (we support older styles for
# backwards compatibility). Please don't change it unless you know what
# you're doing.
Vagrant.configure("2") do |config|
  # The most common configuration options are documented and commented below.
  # For a complete reference, please see the online documentation at
  # https://docs.vagrantup.com.

  # Disable synced folder by default to enforce cloning from git!
  config.vm.synced_folder '.', '/vagrant', disabled: true


  # SSH settings.
  config.ssh.insert_key = false
  config.ssh.username = "vagrant"
  config.ssh.password = "vagrant"

  # MySQL box.
  config.vm.define "db" do |db|

    db.vm.box = "./packer/output/ubuntu-mysql.box"

    db.vm.provider "virtualbox" do |vb|
      vb.gui = false

      vb.memory = "1024"
    end

    db.vm.provision "shell", inline: "echo \"Hey! I'm the mySQL server! I currently do NOTHING!\"", run: "always"

    # Copy SSH private key.
    db.vm.provision "file", source: "~/.ssh/id_rsa", destination: "/home/vagrant/id_rsa"

    # Copy SSH config.
    db.vm.provision "file", source: "./vagrant-config/config-files/ssh/config", destination: "/home/vagrant/config"

    # Clone from git repository.
    db.vm.provision :shell, path: "vagrant-config/scripts/clone-repo.sh"

    # Sourced from https://stackoverflow.com/questions/24867252/allow-two-or-more-vagrant-vms-to-communicate-on-their-own-network
    # This creates a private network specified in a configuration file.
    db.vm.network "private_network", ip: variables['db_ip']
    db.vm.hostname = variables['db_hostname']

    # Copy my.cnf to MySQL server.
    db.vm.provision "file", source: "./vagrant-config/config-files/mysql/my.cnf", destination: "/tmp/my.cnf"
    db.vm.provision "shell", inline: <<-SCRIPT
      
      sudo mv /tmp/my.cnf /etc/mysql/my.cnf

    SCRIPT

    # Set up MySQL.
    db.vm.provision :shell, path: "vagrant-config/scripts/setup-mysql.sh"

    # Set up MySQL users to allow web box to connect to db box's MySQL server.
    db.vm.provision :shell, env: {
        :WEB_IP_ADDR => variables['web_ip'],
        :USERNAME => variables['db_username'],
        :PASSWORD => variables['db_password'],
        :DB_SCHEMA => variables['db_schema']
    },
                    path: "vagrant-config/scripts/setup-mysql-users.sh"


  end

  # Webserver box.
  config.vm.define "web" do |web|

    web.vm.box = "./packer/output/ubuntu-webserver.box"

    web.vm.provider "virtualbox" do |vb|
      vb.gui = false

      vb.memory = "1024"
    end


    # Create a forwarded port mapping which allows access to a specific port
    # within the machine from a port on the host machine. In the example below,
    # accessing "localhost:8080" will access port 80 on the guest machine.
    # NOTE: This will enable public access to the opened port
    # config.vm.network "forwarded_port", guest: 80, host: 8080

    # Create a forwarded port mapping which allows access to a specific port
    # within the machine from a port on the host machine and only allow access
    # via 127.0.0.1 to disable public access
    # config.vm.network "forwarded_port", guest: 8080, host: 8080, host_ip: "127.0.0.1"

    # Expose HTTP Port to host computer.
    web.vm.network "forwarded_port", guest: 8080, host: 8080, host_ip: "127.0.0.1"

    # Add webserver to a private network.
    web.vm.network "private_network", ip: variables['web_ip']
    web.vm.hostname = variables['web_hostname']

    # Test that the webserver can ping the database server.
    web.vm.provision "shell", run: "always", env: {:DB_IP_ADDR => variables['db_ip']},
                     inline: <<-SCRIPT
      
    # Ping DB once.
    ping -c1 $DB_IP_ADDR
    
    # If last error code is zero (success), then...
    if [ "$?" = 0 ]; then
        echo "MySQL server at $DB_IP_ADDR is ping-able!"
    else
        echo "MySQL server at $DB_IP_ADDR could not be pinged! Halting!"

        false # This will force an error.
    fi

    SCRIPT

    # Test that the web box can connect to the MySQL server running on the database box.
    web.vm.provision "shell", run: "always", env: {
        :DB_IP_ADDR => variables['db_ip'],
        :USERNAME => variables['db_username'],
        :PASSWORD => variables['db_password'],
        :DB_SCHEMA => variables['db_schema']
    }, inline: <<-SCRIPT
      
    # Ping DB once.
    ping -c1 $DB_IP_ADDR

    # Show all tables to verify connection.
    mysql -u$USERNAME -p$PASSWORD -h $DB_IP_ADDR $DB_SCHEMA -e "SHOW TABLES;"

    # Create a test table.
    mysql -u$USERNAME -p$PASSWORD -h $DB_IP_ADDR $DB_SCHEMA -e "CREATE TABLE test (id integer);"

    # Drop that table.
    mysql -u$USERNAME -p$PASSWORD -h $DB_IP_ADDR $DB_SCHEMA -e "DROP TABLE test;"

    echo "MySQL connection OK!"

    SCRIPT


    # Create a private network, which allows host-only access to the machine
    # using a specific IP.
    # config.vm.network "private_network", ip: "192.168.33.10"

    # Create a public network, which generally matched to bridged network.
    # Bridged networks make the machine appear as another physical device on
    # your network.
    # config.vm.network "public_network"

    # Share an additional folder to the guest VM. The first argument is
    # the path on the host to the actual folder. The second argument is
    # the path on the guest to mount the folder. And the optional third
    # argument is a set of non-required options.
    # config.vm.synced_folder "../data", "/vagrant_data"

    # Provider-specific configuration so you can fine-tune various
    # backing providers for Vagrant. These expose provider-specific options.
    # Example for VirtualBox:
    #
    # config.vm.provider "virtualbox" do |vb|
    #   # Display the VirtualBox GUI when booting the machine
    #   vb.gui = true
    #
    #   # Customize the amount of memory on the VM:
    #   vb.memory = "1024"
    # end
    #
    # View the documentation for the provider you are using for more
    # information on available options.

    # Enable provisioning with a shell script. Additional provisioners such as
    # Puppet, Chef, Ansible, Salt, and Docker are also available. Please see the
    # documentation for more information about their specific syntax and use.
    # config.vm.provision "shell", inline: <<-SHELL
    #   apt-get update
    #   apt-get install -y apache2
    # SHELL

    # Copy SSH private key.
    web.vm.provision "file", source: "~/.ssh/id_rsa", destination: "/home/vagrant/id_rsa"

    # Copy SSH config.
    web.vm.provision "file", source: "./vagrant-config/config-files/ssh/config", destination: "/home/vagrant/config"

    # Clone from git repository.
    web.vm.provision :shell, path: "vagrant-config/scripts/clone-repo.sh"

    # Set up Python.
    web.vm.provision :shell, path: "vagrant-config/scripts/setup-python.sh"

    # Set up Tomcat.
    web.vm.provision :shell, path: "vagrant-config/scripts/setup-tomcat.sh"

    # Set up Maven.
    web.vm.provision :shell, path: "vagrant-config/scripts/setup-maven.sh"

    # Set up MySQL.
    web.vm.provision :shell, path: "vagrant-config/scripts/setup-mysql.sh"

    # Refresh environment variables.
    web.vm.provision :shell, path: "vagrant-config/scripts/refresh-env.sh" #TODO does this actually work? Is a restart required?

    # Run tests.
    if RUN_TESTS
      # Test our Python libraries.
      web.vm.provision :shell, path: "vagrant-config/scripts/test-python.sh", run: "always"

      # Test jep.
      web.vm.provision :shell, path: "vagrant-config/scripts/test-jep.sh", run: "always"
    end

    if DESTROY_DB
      # Destroy database.
      web.vm.provision :shell, path: "vagrant-config/scripts/destroy-db.sh", run: "always"
    end

    if DEPLOY
      # Deploy our app.
      web.vm.provision :shell, path: "vagrant-config/scripts/deploy-app.sh", run: "always"

      # At this point, going to http://127.0.0.1:8080/searchable-video-library/ should yield some HTML page.
    end

    if INSERT_TEST_DATA

      web.vm.provision :shell, path: "vagrant-config/scripts/insert-test-users.sh"

    end

  end

end
