
Write-Output "Checking if choco is installed."

if (Get-Command choco.exe -errorAction SilentlyContinue)
{
    Write-Output("Choco is installed.")
}
else
# Choco is not installed.
{
    Write-Output("Choco is not installed. Installing...")
    Set-ExecutionPolicy Bypass -Scope Process -Force; iex ((New-Object System.Net.WebClient).DownloadString('https://chocolatey.org/install.ps1'))
}

Write-Output "Enabling global confirmation."
choco feature enable -n=allowGlobalConfirmation


Write-Output "Installing ruby."
choco install -y ruby


Write-Output "Installing virtualbox."
choco install -y virtualbox


Write-Output "Installing packer."
choco install -y packer
