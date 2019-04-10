# -*- mode: ruby -*-
# vi: set ft=ruby :

require 'yaml'

# Build script settings.
RUN_TESTS = false # Run tests?
DESTROY_DB = false # Destroy the database?
ENGAGE_CAKE = true # Engage cake? yes
DEPLOY = true # Deploy the app?
INSERT_TEST_DATA = true # Insert test data upon provision step?
CREATE_SSL = false # Create an SSL Certificate

USE_PUBLIC_REPO = false # Use a public repository URL in case the private one is no more or inaccessible?


# Load YAML file containing IP addresses, as well as other variables.
VARIABLES = YAML.load_file('variables.yml')

# Clones the Github repo into a box.
def clone_repo(box, vars = VARIABLES, public_repo = USE_PUBLIC_REPO)

  if public_repo

    # Clone repo.
    box.vm.provision :shell, path: 'vagrant-config/scripts/clone-repo.sh', env: {
        :REPO_URL => vars['public-repo-url']
    }

  else

    # Copy SSH private key.
    box.vm.provision 'file', source: vars['ssh-key-location'], destination: '/home/vagrant/id_rsa'

    # Copy SSH config.
    box.vm.provision 'file', source: './vagrant-config/config-files/ssh/config', destination: '/home/vagrant/config'

    # Set up SSH keys.
    box.vm.provision :shell, path: 'vagrant-config/scripts/setup-ssh-keys.sh'

    # Clone repo.
    box.vm.provision :shell, path: 'vagrant-config/scripts/clone-repo.sh', env: {
        :REPO_URL => vars['private-repo-url'],
        :REPO_PATH => vars['repo_location'],
    }

  end

end

# All Vagrant configuration is done below. The "2" in Vagrant.configure
# configures the configuration version (we support older styles for
# backwards compatibility). Please don't change it unless you know what
# you're doing.
Vagrant.configure('2') do |config|
  # The most common configuration options are documented and commented below.
  # For a complete reference, please see the online documentation at
  # https://docs.vagrantup.com.

  # SSH key doesn't exist on host?
  unless File.exist? File.expand_path(VARIABLES['ssh-key-location'])

    # They want to use a private repo?
    unless USE_PUBLIC_REPO
      puts "SSH key located at #{File.expand_path(VARIABLES['ssh-key-location'])} does not exist, yet you want to use a private repository."

      puts "`vagrant up` will surely fail due to the `git clone` command failing."

      puts "Halting!"

      exit(1)
    end
  end
  # Disable synced folder by default to enforce cloning from git!
  config.vm.synced_folder '.', '/vagrant', disabled: true


  # SSH settings.
  config.ssh.insert_key = false
  config.ssh.username = 'vagrant'
  config.ssh.password = 'vagrant'

  # iSCSI box.
  config.vm.define 'iscsi' do |iscsi|

    iscsi.vm.box = './packer/output/ubuntu-storage.box'

    iscsi.vm.network 'private_network', ip: VARIABLES['iscsi']['ip']
    iscsi.vm.hostname = VARIABLES['iscsi']['hostname']

    iscsi.vm.provider 'virtualbox' do |vb|
      vb.gui = false

      vb.memory = '512'
    end

    # Copy my.cnf to MySQL server.
    iscsi.vm.provision 'file', source: './vagrant-config/config-files/iscsi/target.cnf', destination: '/tmp/target.cnf'
    iscsi.vm.provision 'shell', inline: <<-SCRIPT

      sudo mv /tmp/target.cnf /etc/iscsi/target.cnf

    SCRIPT

    # testing on install and configure iscsi target
    iscsi.vm.provision :shell, path: 'vagrant-config/scripts/configure-iscsi-server.sh'

    # Expose port for monitoring via netdata.
    iscsi.vm.network 'forwarded_port', guest: 19999, host: VARIABLES['iscsi']['netdata-port'], host_ip: '127.0.0.1'


  end

  # MySQL box.
  config.vm.define 'db' do |db|

    db.vm.box = './packer/output/ubuntu-mysql.box'

    # Sourced from https://stackoverflow.com/questions/24867252/allow-two-or-more-vagrant-vms-to-communicate-on-their-own-network
    # This creates a private network specified in a configuration file.
    db.vm.network 'private_network', ip: VARIABLES['db']['ip']
    db.vm.hostname = VARIABLES['db']['hostname']


    db.vm.provider 'virtualbox' do |vb|
      vb.gui = false

      vb.memory = '1024'
    end


    # Expose port for monitoring via netdata.
    db.vm.network 'forwarded_port', guest: 19999, host: VARIABLES['db']['netdata-port'], host_ip: '127.0.0.1'

    # Clone GitHub repo.
    clone_repo(db)

    # Copy my.cnf to MySQL server.
    db.vm.provision 'file', source: './vagrant-config/config-files/mysql/my.cnf', destination: '/tmp/my.cnf'
    db.vm.provision 'shell', inline: <<-SCRIPT

      sudo mv /tmp/my.cnf /etc/mysql/my.cnf

    SCRIPT

    # Set up MySQL.
    db.vm.provision :shell, path: 'vagrant-config/scripts/setup-mysql.sh'

    # Set up MySQL users to allow web box to connect to db box's MySQL server.
    db.vm.provision :shell, env: {
        :WEB_IP_ADDR => VARIABLES['web']['ip'],
        :USERNAME => VARIABLES['db']['username'],
        :PASSWORD => VARIABLES['db']['password'],
        :DB_SCHEMA => VARIABLES['db']['schema']
    },
                    path: 'vagrant-config/scripts/setup-mysql-users.sh'


  end

  # Webserver box.
  config.vm.define 'web' do |web|

    web.vm.box = './packer/output/ubuntu-webserver.box'

    web.vm.provider 'virtualbox' do |vb|
      vb.gui = false

      vb.memory = '1024'
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
    web.vm.network 'forwarded_port', guest: 8080, host: 8080, host_ip: '127.0.0.1'

    # Expose port for monitoring via netdata.
    web.vm.network 'forwarded_port', guest: 19999, host: VARIABLES['web']['netdata-port'], host_ip: '127.0.0.1'

    # Add webserver to a private network.
    web.vm.network 'private_network', ip: VARIABLES['web']['ip']
    web.vm.hostname = VARIABLES['web']['hostname']

    # Test that the webserver can ping the database server.
    web.vm.provision 'shell', run: 'always', env: {:DB_IP_ADDR => VARIABLES['db']['ip']},
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


    # Test that the webserver can ping the iSCSI server.
    web.vm.provision 'shell', run: 'always', env: {:ISCSI_IP_ADDR => VARIABLES['iscsi']['ip']},
                     inline: <<-SCRIPT

    # Ping iSCSI box once.
    ping -c1 $ISCSI_IP_ADDR

    # If last error code is zero (success), then...
    if [ "$?" = 0 ]; then
        echo "iSCSI box at $ISCSI_IP_ADDR is ping-able!"
    else
        echo "iSCSI server at $ISCSI_IP_ADDR could not be pinged! Halting!"

        false # This will force an error.
    fi

    SCRIPT

    # Test that the web box can connect to the MySQL server running on the database box.
    web.vm.provision 'shell', run: 'always', env: {
        :DB_IP_ADDR => VARIABLES['db']['ip'],
        :USERNAME => VARIABLES['db']['username'],
        :PASSWORD => VARIABLES['db']['password'],
        :DB_SCHEMA => VARIABLES['db']['schema']
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

    # Set up an iSCSI client.
    # web.vm.provision :shell, path: 'vagrant-config/scripts/configure-iscsi-client.sh', env: {
    #     TARGET_IP: VARIABLES['iscsi']['ip'] #TODO Make iSCSI target work so initiator works.
    # }

    config.vm.provision 'ffmpeg', type: 'shell', inline: 'sudo add-apt-repository ppa:jonathonf/ffmpeg-4 ; sudo apt install ffmpeg -y'

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

    clone_repo(web)

    # Modify database.properties to make web box's app use db box's database
    web.vm.provision :shell, env: {
        :DB_IP_ADDR => VARIABLES['db']['ip'],
        :DB_PORT => VARIABLES['db']['port'],
        :DB_USERNAME => VARIABLES['db']['username'],
        :DB_PASSWORD => VARIABLES['db']['password'],
        :DB_SCHEMA => VARIABLES['db']['schema'],
    },
                     inline: <<-SCRIPT

		# Go to where database.properties file is.
		cd /home/vagrant/2019-team-07f/server/WebContent/META-INF/

		# Delete it!
		rm database.properties

		# Make an empty one.
		touch database.properties

		# Populate it with the correct information.
		echo "host=$DB_IP_ADDR:$DB_PORT" >> database.properties
		echo "database=$DB_SCHEMA" >> database.properties
		echo "user=$DB_USERNAME" >> database.properties
		echo "password=$DB_PASSWORD" >> database.properties

		# Show the contents of the file.
		cat database.properties

    SCRIPT

    # Refresh environment variables.
    web.vm.provision :shell, path: 'vagrant-config/scripts/refresh-env.sh' #TODO does this actually work? Is a restart required?

    # Set up maven.
    web.vm.provision :shell, path: 'vagrant-config/scripts/setup-maven.sh'

    # Set up tomcat.
    web.vm.provision :shell, path: 'vagrant-config/scripts/setup-tomcat.sh'

    # Run tests.
    if RUN_TESTS
      # Test our Python libraries.
      web.vm.provision :shell, path: 'vagrant-config/scripts/test-python.sh', run: 'always'

      # Test jep.
      web.vm.provision :shell, path: 'vagrant-config/scripts/test-jep.sh', run: 'always'
    end

    if CREATE_SSL
      # Try to create SSL Certificate
      web.vm.provision :shell, path: 'vagrant-config/scripts/create_ssl_cert.sh', run: 'always', env: {
          :WEB_IP_ADDR => VARIABLES['web']['ip'],
          :TEAM_NAME => VARIABLES['ssl-cert']['team-name'],
          :TEAM_ORG => VARIABLES['ssl-cert']['team-org'],
          :COUNTRY => VARIABLES['ssl-cert']['country'],
          :STATE => VARIABLES['ssl-cert']['state'],
      }
    end

    if DESTROY_DB
      # Destroy database.
      web.vm.provision :shell, path: 'vagrant-config/scripts/destroy-db.sh', run: 'always'
    end

    if DEPLOY
      # Deploy our app.
      web.vm.provision :shell, path: 'vagrant-config/scripts/deploy-app.sh', run: 'always', env: {
          :REPO_PATH => VARIABLES['repo_location'],
      }

      # At this point, going to http://127.0.0.1:8080/searchable-video-library/ should yield some HTML page.
    end

    # If we want to insert test data like test users,
    if INSERT_TEST_DATA

      web.vm.provision :shell, path: 'vagrant-config/scripts/insert-test-users.sh', env: {
          :REPO_PATH => VARIABLES['repo_location'],
          :DB_IP_ADDR => VARIABLES['db']['ip'],
          :DB_PORT => VARIABLES['db']['port'],
          :DB_USERNAME => VARIABLES['db']['username'],
          :DB_PASSWORD => VARIABLES['db']['password'],
          :DB_SCHEMA => VARIABLES['db']['schema'],
      }

    end

  end

end
