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

choco install -y ruby
choco install -y virtualbox
choco install -y packer
