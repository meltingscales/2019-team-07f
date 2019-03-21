if (Get-Command choco.exe -errorAction SilentlyContinue)
{
    Write-Host("Choco is installed.")
}
else
# Choco is not installed.
{
    Write-Host("Choco is not installed. Installing...")
    Set-ExecutionPolicy Bypass -Scope Process -Force; iex ((New-Object System.Net.WebClient).DownloadString('https://chocolatey.org/install.ps1'))
}

Write-Host "Installing ruby."
choco install -y ruby


Write-Host "Installing virtualbox."
choco install -y virtualbox


Write-Host "Installing packer."
choco install -y packer
