<#
.SYNOPSIS
Check GitHub CI status for StudyMasterApp

.DESCRIPTION
This script checks the GitHub Actions workflow runs for the StudyMasterApp repository
using the GitHub API and a token extracted from git credentials.
#>

# Extract token from git credentials
$credentialsFile = "$env:USERPROFILE\.git-credentials"
if (-not (Test-Path $credentialsFile)) {
    Write-Host "❌ Git credentials file not found: $credentialsFile"
    exit 1
}

$token = Select-String -Path $credentialsFile -Pattern 'https://([^@:]+)@github.com' | ForEach-Object { $_.Matches.Groups[1].Value }

if (-not $token) {
    # Try alternative pattern for oauth2 format
    $token = Select-String -Path $credentialsFile -Pattern 'https://oauth2:([^@]+)@github.com' | ForEach-Object { $_.Matches.Groups[1].Value }
}

if (-not $token) {
    Write-Host "❌ No GitHub token found in git credentials"
    Write-Host "Credentials file content:"
    Get-Content $credentialsFile
    exit 1
}

# Verify token format
if ($token -match '^ghp_|^github_pat_') {
    Write-Host "✅ Valid token format detected"
} else {
    Write-Host "⚠️  Token format may be invalid: $($token.Substring(0, [Math]::Min(8, $token.Length)))..."
    Write-Host "Expected format: ghp_... or github_pat_..."
}

# GitHub API URL
$apiUrl = "https://api.github.com/repos/Redwan878/StudyMasterApp/actions/runs?per_page=5"

# Query GitHub API for workflow runs
Write-Host "🔍 Checking GitHub CI status..."
try {
    $headers = @{ "Authorization" = "token $token" }
    $response = Invoke-RestMethod -Uri $apiUrl -Headers $headers -Method Get

    # Check for API errors
    if ($response -is [System.Management.Automation.ErrorRecord]) {
        Write-Host "❌ API request failed: $($response.Exception.Message)"
        exit 1
    }

    if (-not $response.workflow_runs) {
        Write-Host "No workflow runs found"
        exit 0
    }

    Write-Host "✅ Connected to GitHub API successfully"
    Write-Host "📋 Recent GitHub Actions runs:"
    
    foreach ($run in $response.workflow_runs) {
        $status = if ($run.conclusion) { $run.conclusion } else { $run.status }
        $workflow = $run.name
        $url = $run.html_url
        Write-Host "  • $workflow: $status - $url"
    }

    # Check for in-progress runs
    $inProgress = $response.workflow_runs | Where-Object { $_.status -eq 'in_progress' }
    if ($inProgress) {
        Write-Host "\n🚀 In-progress runs:"
        foreach ($run in $inProgress) {
            $workflow = $run.name
            $url = $run.html_url
            Write-Host "  • $workflow - $url"
        }
    }

} catch {
    Write-Host "❌ Error checking GitHub CI status: $_"
    if ($_.Exception.Response) {
        $errorResponse = $_.Exception.Response
        $errorStream = $errorResponse.GetResponseStream()
        $reader = New-Object System.IO.StreamReader($errorStream)
        $errorDetails = $reader.ReadToEnd()
        Write-Host "API Error Details: $errorDetails"
    }
    exit 1
}