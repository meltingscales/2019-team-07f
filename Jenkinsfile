import hudson.model.*;
import hudson.util.*;
import hudson.scm.*;
import hudson.plugins.accurev.*

//def thr = Thread.currentThread();
//def build = thr?.executable;

//def changeSet = build.getChangeSet();

//def changes = changeSet.getItems();

pipeline {
    agent any

    stages {
        stage("Build") {
            steps {
                echo "Building.."

                powershell "'${WORKSPACE}/ci-scripts/install-deps.ps1'"
                powershell "'${WORKSPACE}/ci-scripts/try-install-vagrant.ps1'"

                dir("${WORKSPACE}/packer") {

                    script {
                        if (! fileExists('./output/ubuntu-mysql.box')) {
                            
                            print 'Building mySQL box as it does not exist.'
                            powershell "packer build ubuntu-mysql.json"
                        } else {
                            print 'MySQL box exists.'
                        }
                    }
                    
                    script {
                        if (! fileExists('./output/ubuntu-storage.box')) {
                            
                            print 'Building storage box as it does not exist.'
                            powershell "packer build ubuntu-storage.json"
                        } else {
                            print 'storage box exists.'
                        }
                    }

                    script {
                        if (! fileExists('./output/ubuntu-webserver.box')) {
                            
                            print 'Building webserver box as it does not exist.'
                            powershell "packer build ubuntu-webserver.json"
                        } else {
                            print 'webserver box exists.'
                        }
                    }

                }

                dir("${WORKSPACE}/"){
                    powershell "vagrant up"
                }
            }
        }
        stage("Test") {
            steps {
                echo "Testing.."
                powershell "throw 'Error: Software is way too good. Also, testing Jenkins.'"
            }
        }
        stage("Deploy") {
            steps {
                echo "Deploying...."
            }
        }
        
        stage("Clean") {
            steps {
                echo "Cleaning..."
            }
        }
    }
}
