param (
    [switch]$SkipBE,
    [switch]$SkipFE,
    [switch]$SkipCon
)

$root = Split-Path -Parent $MyInvocation.MyCommand.Path

# =========================
# LOAD .env
# =========================
$envPath = "$root\.env"

if (Test-Path $envPath) {
    Write-Host "Loading .env file..." -ForegroundColor Cyan

    Get-Content $envPath | ForEach-Object {
        if ($_ -match "^\s*([^#][^=]+)=(.*)$") {
            $key = $matches[1]
            $value = $matches[2]

            # FIX ĐÚNG POWERHELL
            Set-Item "Env:$key" $value
        }
    }
}
else {
    Write-Host ".env file not found!" -ForegroundColor Red
}

# =========================
# DOCKER
# =========================
if (-not $SkipCon) {
    Write-Host "Starting database and redis containers..." -ForegroundColor Cyan
    & docker compose -f "$root\docker-compose.yml" up -d
}
else {
    Write-Host "Skipping Containers..." -ForegroundColor Yellow
}

# =========================
# BACKEND
# =========================
if (-not $SkipBE) {
    Write-Host "Starting Backend..." -ForegroundColor Cyan
    $backendCmd = "Set-Location `"$root\backend`"; .\mvnw spring-boot:run"
    Start-Process powershell -ArgumentList "-NoExit", "-Command", $backendCmd
}
else {
    Write-Host "Skipping Backend..." -ForegroundColor Yellow
}

# =========================
# FRONTEND
# =========================
if (-not $SkipFE) {
    Write-Host "Starting Frontend..." -ForegroundColor Cyan
    $frontendCmd = "Set-Location `"$root\frontend`"; if (!(Test-Path 'node_modules')) { npm install }; npm run dev"
    Start-Process powershell -ArgumentList "-NoExit", "-Command", $frontendCmd
}
else {
    Write-Host "Skipping Frontend..." -ForegroundColor Yellow
}

Write-Host "Done." -ForegroundColor Green