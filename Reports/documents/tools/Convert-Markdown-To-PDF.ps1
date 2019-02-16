$Word=New-Object -ComObject Word.Application

# TODO This path is hard-coded.
$Files=Get-ChildItem("..\*.md")

ForEach ($File In $Files) {

    $PDFFilePath = (Get-Item $File.ToString()).BaseName + ".pdf"

    Write-Host($File.ToString() + " -> " + $PDFFilePath)

    # TODO This path is hard-coded as well. Fix that.
    Invoke-Expression("pandoc --toc --variable urlcolor=cyan '$File' --pdf-engine=xelatex -o '../$PDFFilePath'")
}