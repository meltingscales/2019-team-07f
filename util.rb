
# Clones the Github repo into a box.
def clone_repo(box, vars, public_repo)

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