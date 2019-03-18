Write-Output $MyInvocation.MyCommand

$changed = git show --name-only --oneline HEAD

pwd | Write-Output

Write-Output $changed

function RemoveIfChanged($artifactbuildfile, $artifactoutput, $delete = $FALSE) {
    
    if($changed.Contains($artifactbuildfile)) {

        Write-Output "Removing $artifactoutput as it was changed last commit!"

        # If should delete,
        if($delete) {
            # If path exists,
            if(Test-Path $artifactoutput -PathType Leaf) {
                del "$artifactoutput"
                Write-Output "DEL $artifactoutput"            
            }
        }
    }
}

RemoveIfChanged "packer/ubuntu-mysql.json" "packer/output/ubuntu-mysql.box"
RemoveIfChanged "packer/ubuntu-storage.json" "packer/output/ubuntu-storage.box"
RemoveIfChanged "packer/ubuntu-webserver.json" "packer/output/ubuntu-webserver.box"