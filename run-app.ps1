# Usage: .\run-app.ps1 [envFile]
# Example: .\run-app.ps1 .env.prod  (or .env for dev)

param (
    [string]$envFile = ".env"  # default to .env if nothing is passed
)

Write-Host "Using environment file: $envFile"

# Load environment variables from the specified file
Get-Content $envFile | ForEach-Object {
    if ($_ -match "^\s*([^#][^=]*)=(.*)$") {
        $name = $matches[1].Trim()
        $value = $matches[2].Trim()
        [System.Environment]::SetEnvironmentVariable($name, $value, "Process")
    }
}

# Start Spring Boot app
mvn spring-boot:run
