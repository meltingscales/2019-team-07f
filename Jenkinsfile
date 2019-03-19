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

                // Install dependencies.
                powershell "'${WORKSPACE}/ci-scripts/windows/install-deps.ps1'"
                powershell "'${WORKSPACE}/ci-scripts/windows/try-install-vagrant.ps1'"

                dir("${WORKSPACE}/") {
                    // Remove box files if they were changed in the most recent commit.
                    powershell "'ci-scripts/windows/remove-boxes-if-changed-in-most-recent-commit.ps1'"
                }

                dir("${WORKSPACE}/packer") {
                    // Create all missing packer box files.
                    powershell "ruby build-missing.rb"
                }

            }
        }
        stage("Deploy") {
            steps {
                echo "Deploying...."
                dir("${WORKSPACE}/") {
                    powershell "vagrant up"
                }
            }
        }
        stage("Test") {
            steps {
                echo "Testing.."

                dir("${WORKSPACE}/ci-scripts/ruby") {
                    powershell "test-webserver.rb"
                }
                
                powershell "throw 'Error: Software is way too good. Also, testing Jenkins.'"
            }
        }
    }
    post {
        always {
            echo "Cleaning..."
            
            dir("${WORKSPACE}/") {
                powershell "vagrant halt -f"
            }
            
            dir("${WORKSPACE}/") {
                powershell "vagrant destroy -f"
            }
        }
    }
}
