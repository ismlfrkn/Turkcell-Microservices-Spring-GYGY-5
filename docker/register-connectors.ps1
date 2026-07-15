# Kafka Connect'e docker/connectors altındaki tüm connector config'lerini (yeniden) kaydeder.
# PUT .../config idempotenttir: connector yoksa oluşturur, varsa günceller.

$connectUrl = "http://localhost:8083"
$connectorsDir = Join-Path $PSScriptRoot "connectors"

Get-ChildItem -Path $connectorsDir -Filter "*.json" | ForEach-Object {
    $connectorName = $_.BaseName
    $configBody = Get-Content -Path $_.FullName -Raw

    Write-Host "Kaydediliyor: $connectorName"
    try {
        Invoke-RestMethod -Uri "$connectUrl/connectors/$connectorName/config" -Method Put -ContentType "application/json" -Body $configBody
        Write-Host "OK: $connectorName" -ForegroundColor Green
    } catch {
        Write-Host "HATA: $connectorName -> $($_.Exception.Message)" -ForegroundColor Red
    }
}
