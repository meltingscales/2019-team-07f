pipeline {
    agent any

    stages {
        stage('Build') {
            steps {
                echo 'Building..'
                powershell 'Write-Output "Hello, world!"'
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
