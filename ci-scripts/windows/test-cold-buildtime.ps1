#
# This script will run a build of the repository from a cold start in a temporary directory,
# and record how long that cold start takes. Akin to building with a newly-bought laptop.
#
$TEMP_DIR = Join-Path $Env:TEMP -ChildPath "2019-team-07f"

$LOG_PATH = Join-Path $TEMP_DIR -ChildPath "log";

# Paths for log files.
$START_TIME_PATH = Join-Path -Path $LOG_PATH -ChildPath "start_time.txt";
$END_TIME_PATH = Join-Path -Path $LOG_PATH -ChildPath "end_time.txt";

$REPO_URL = "https://github.com/illinoistech-itm/2019-team-07f";
$REPO_CLONE_PATH = Join-Path $TEMP_DIR -ChildPath "repo";

# Record date before we begin
Get-Date > $START_TIME_PATH;


if (-not(Test-Path $LOG_PATH))
{
    mkdir.exe $LOG_PATH
}

if (Test-Path $REPO_CLONE_PATH)
{
    # Delete cloned repo directory after vagrant up runs.
    Remove-Item -Force -Recurse $REPO_CLONE_PATH
}

if (-Not(Test-Path $REPO_CLONE_PATH))
{
    Set-Location $TEMP_DIR
    # Clone repo if it doesn't exist.
    git.exe clone $REPO_URL $REPO_CLONE_PATH
}


# Go to packer folder and build VMs.
Set-Location (Join-Path $REPO_CLONE_PATH -ChildPath "packer");

ruby.exe "build-missing.rb"

# Go back up from packer to repo folder.
Set-Location $REPO_CLONE_PATH
# Bring VMs up.
vagrant.exe up

# Record date after we finish.
Get-Date > $END_TIME_PATH

vagrant.exe halt -f

# Go back up from repo clone path to parent directory
Pop-Location

Write-Output "Start time:"
Get-Content $START_TIME_PATH

Write-Output "End time:"
Get-Content $START_TIME_PATH