# -*- mode: ruby -*-
# vi: set ft=ruby :

# Build script settings.
RUN_TESTS =         false # Run tests?
DESTROY_DB =        false # Destroy the database?
ENGAGE_CAKE =       true # Engage cake?
DEPLOY =            true # Deploy the app?
INSERT_TEST_DATA =  true # Insert test data upon provision step?

# Port/debug settings. These should all be false once deploy time comes.
MYSQL_DEBUG =       false # Debug for MySQL. Opens MySQL port.

# All Vagrant configuration is done below. The "2" in Vagrant.configure
# configures the configuration version (we support older styles for
# backwards compatibility). Please don't change it unless you know what
# you're doing.
Vagrant.configure("2") do |config|
  # The most common configuration options are documented and commented below.
  # For a complete reference, please see the online documentation at
  # https://docs.vagrantup.com.

  # Every Vagrant development environment requires a box. You can search for
  # boxes at https://vagrantcloud.com/search.
  config.vm.box = "./packer/output/ubuntu-webserver.box"

  # Disable automatic box update checking. If you disable this, then
  # boxes will only be checked for updates when the user runs
  # `vagrant box outdated`. This is not recommended.
  # config.vm.box_check_update = false

  # Create a forwarded port mapping which allows access to a specific port
  # within the machine from a port on the host machine. In the example below,
  # accessing "localhost:8080" will access port 80 on the guest machine.
  # NOTE: This will enable public access to the opened port
  # config.vm.network "forwarded_port", guest: 80, host: 8080

  # Create a forwarded port mapping which allows access to a specific port
  # within the machine from a port on the host machine and only allow access
  # via 127.0.0.1 to disable public access
  # config.vm.network "forwarded_port", guest: 8080, host: 8080, host_ip: "127.0.0.1"   

  # SSH settings.
  config.ssh.insert_key = false
  config.ssh.username = "vagrant"
  config.ssh.password = "vagrant"
  
  # Expose HTTP Port.
  config.vm.network "forwarded_port", guest: 8080, host: 8080, host_ip: "127.0.0.1"
  
  if MYSQL_DEBUG then
  
    # Expose MySQL Port.
    config.vm.network "forwarded_port", guest: 3306, host: 3306, host_ip: "127.0.0.1"
    
  end

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

  # Disable synced folder by default to enforce cloning from git.
#   config.vm.synced_folder ".", "/vagrant", disabled: true

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
  config.vm.provision "file", source: "~/.ssh/id_rsa", destination: "/home/vagrant/id_rsa"

  # Copy SSH config.
  config.vm.provision "file", source: "./vagrant-config/config-files/ssh/config", destination: "/home/vagrant/config"
  
  # Clone from git repository.
  config.vm.provision "shell", inline: <<-SCRIPT
	
	# Make ssh folders if they don't exist.
	[ -d /home/vagrant/.ssh ] || 	 mkdir -p /home/vagrant/.ssh
	[ -d /root/.ssh ] ||           mkdir -p /root/.ssh

    # Copy key.
	cp -v /home/vagrant/id_rsa /home/vagrant/.ssh/
        
	# Set up read/write access.
	chmod 700 "/home/vagrant/.ssh"
	chmod 600 "/home/vagrant/.ssh/id_rsa"

	# Copy config.
	cp -v /home/vagrant/config /home/vagrant/.ssh/config
	cp -v /home/vagrant/config /root/.ssh/config
		
	# Delete cloned repo if it exists
	if [ -d /tmp/testing ]; then
		rm -rf /tmp/testing
	fi	
	
	# Clone team repo into temp folder to test.
	sudo git clone git@github.com:illinoistech-itm/2019-team-07f.git /tmp/testing
  
  SCRIPT

  # Set up Python.
  config.vm.provision :shell, path: "vagrant-config/scripts/setup-python.sh"

  # Set up Tomcat.
  config.vm.provision :shell, path: "vagrant-config/scripts/setup-tomcat.sh"
  
  # Set up Maven.
  config.vm.provision :shell, path: "vagrant-config/scripts/setup-maven.sh"

  # Set up MySQL.
  config.vm.provision :shell, path: "vagrant-config/scripts/setup-mysql.sh"
  
  # Refresh environment variables.
  config.vm.provision :shell, path: "vagrant-config/scripts/refresh-env.sh" #TODO does this actually work? Is a restart required?
  
  ## And now for the tests!
  if RUN_TESTS then
    # Test our Python libraries.
    config.vm.provision :shell, path: "vagrant-config/scripts/test-python.sh", run: "always"

    # Test jep.
    config.vm.provision :shell, path: "vagrant-config/scripts/test-jep.sh", run: "always"
  end
  
  if DESTROY_DB then 
    # Destroy database.
    config.vm.provision :shell, path: "vagrant-config/scripts/destroy-db.sh", run: "always"

    # Set up MariaDB.
    config.vm.provision :shell, path: "vagrant-config/scripts/setup-mariadb.sh"
  end
  
  if DEPLOY then
    # Deploy our app.
    config.vm.provision :shell, path: "vagrant-config/scripts/deploy-app.sh", run: "always"
  
    # At this point, going to http://127.0.0.1:8080/searchable-video-library/ should yield some HTML page.
  end
  
  if INSERT_TEST_DATA then

      config.vm.provision :shell, path: "vagrant-config/scripts/insert-test-users.sh"

  end
  
end
