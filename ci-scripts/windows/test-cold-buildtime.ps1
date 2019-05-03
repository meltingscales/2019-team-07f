#
# This script will run a build of the repository from a cold start in a temporary directory,
# and record how long that cold start takes. Akin to building with a newly-bought laptop.
#

Push-Location ./

$TEMP_FOLDER = $Env:TEMP

if(-not $TEMP_FOLDER) { #Linux or OSX doesn't have $Env:TEMP
    $TEMP_FOLDER = "/tmp"
}

$TEMP_DIR = Join-Path $TEMP_FOLDER -ChildPath "2019-team-07f"

if(-not(Test-Path $TEMP_DIR))
{
    mkdir $TEMP_DIR
}

$LOG_PATH = Join-Path $TEMP_DIR -ChildPath "log";

# Paths for log files.
$START_TIME_PATH = Join-Path -Path $LOG_PATH -ChildPath "start_time.txt";
$END_TIME_PATH = Join-Path -Path $LOG_PATH -ChildPath "end_time.txt";

$REPO_URL = "https://github.com/henryfbp/2019-team-07f";
$REPO_CLONE_PATH = Join-Path $TEMP_DIR -ChildPath "repo";

if(Test-Path $LOG_PATH)
{
    Remove-Item -Recurse -Force $LOG_PATH
}

if (-not(Test-Path $LOG_PATH))
{
    mkdir $LOG_PATH
}

if (-Not(Test-Path $REPO_CLONE_PATH))
{
    Set-Location $TEMP_DIR
    # Clone repo if it doesn't exist.
    git clone $REPO_URL $REPO_CLONE_PATH
}

if (Test-Path $REPO_CLONE_PATH)
{
    Set-Location $REPO_CLONE_PATH
    # Update cloned repo.
	git stash
	git pull
}


# Destroy VMs if they exist.
Set-Location $REPO_CLONE_PATH
ruby destroy-everything.rb -f

# Record date before we begin
Get-Date > $START_TIME_PATH;

# Go to packer folder and build VMs.
Set-Location (Join-Path $REPO_CLONE_PATH -ChildPath "packer");

ruby "build-missing.rb"

# Go back up from packer to repo folder.
Set-Location $REPO_CLONE_PATH
# Bring VMs up.
vagrant up

# Record date after we finish.
Get-Date > $END_TIME_PATH

vagrant halt -f

Write-Output "Start time:"
Get-Content $START_TIME_PATH

Write-Output "End time:"
Get-Content $END_TIME_PATH

Pop-Location