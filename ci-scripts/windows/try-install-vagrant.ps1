Try {
    choco install -y vagrant
} Catch {
    if($LASTEXITCODE -eq 3010) {
        Write-Host "Ignoring Vagrant reboot code."
    } else {
        Write-Host "WARNING: ``choco install -y vagrant`` command exited with error code '$LASTEXITCODE'"
    }
}