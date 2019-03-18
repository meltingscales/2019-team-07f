pipeline {
    agent any

    stages {
        stage('Build') {
            steps {
                echo 'Building..'
                powershell 'Write-Output "Hello, world!"'

                powershell 'Write-Output "dis be workspace: ${WORKSPACE}"'
                powershell 'Write-Output "dis be workspace: %WORKSPACE%"'
                bat 'dis be workspace: $WORKSPACE'
                bat 'echo "dis be workspace: $WORKSPACE"'
                bat 'dir'

                powershell '${WORKSPACE}/ci-scripts/install-deps.ps1'
                powershell '${WORKSPACE}/ci-scripts/try-install-vagrant.ps1'

                powershell 'cd ${WORKSPACE}/packer'
                powershell 'packer build ubuntu-mysql.json'
                powershell 'packer build ubuntu-storage.json'
                powershell 'packer build ubuntu-webserver.json'
                
                powershell 'cd ${WORKSPACE}/'
                powershell 'vagrant up'
            }
        }
        stage('Test') {
            steps {
                echo 'Testing..'
                powershell 'throw "Error: Software is way too good. Also, testing Jenkins."'
            }
        }
        stage('Deploy') {
            steps {
                echo 'Deploying....'
            }
        }
    }
}
