@echo off
:: Check GitHub CI status for StudyMasterApp

:: Extract token from git credentials
set "CREDENTIALS_FILE=%USERPROFILE%\.git-credentials"
if not exist "%CREDENTIALS_FILE%" (
    echo ❌ Git credentials file not found: %CREDENTIALS_FILE%
    exit /b 1
)

:: Extract token using curl and jq (if available)
where curl >nul 2>nul
if %ERRORLEVEL% neq 0 (
    echo ❌ curl not found. Please install curl or Git for Windows.
    exit /b 1
)

where jq >nul 2>nul
if %ERRORLEVEL% neq 0 (
    echo ⚠️  jq not found. Installing jq for better JSON parsing...
    curl -sL -o jq.exe https://github.com/jqlang/jq/releases/download/jq-1.7.1/jq-win64.exe
)

:: Extract token
for /f "tokens=2 delims=:/@" %%a in ('findstr "github.com" "%CREDENTIALS_FILE%"') do set "TOKEN=%%a"

if "%TOKEN%"=="" (
    echo ❌ No GitHub token found in git credentials
    type "%CREDENTIALS_FILE%"
    exit /b 1
)

:: Verify token format
echo %TOKEN% | findstr /b "ghp_ github_pat_" >nul
if %ERRORLEVEL% equ 0 (
    echo ✅ Valid token format detected
) else (
    echo ⚠️  Token format may be invalid: %TOKEN:~0,8%...
    echo Expected format: ghp_... or github_pat_...
)

:: GitHub API URL
set "API_URL=https://api.github.com/repos/Redwan878/StudyMasterApp/actions/runs?per_page=5"

:: Query GitHub API for workflow runs
echo 🔍 Checking GitHub CI status...
curl -s -H "Authorization: token %TOKEN%" "%API_URL%" > ci_response.json

:: Check for API errors
findstr /c:"Bad credentials" ci_response.json >nul
if %ERRORLEVEL% equ 0 (
    echo ❌ Authentication failed: Bad credentials
    del ci_response.json
    exit /b 1
)

findstr /c:"API rate limit exceeded" ci_response.json >nul
if %ERRORLEVEL% equ 0 (
    echo ❌ API rate limit exceeded
    del ci_response.json
    exit /b 1
)

:: Check if jq is available
where jq >nul 2>nul
if %ERRORLEVEL% equ 0 (
    echo ✅ Connected to GitHub API successfully
    echo 📋 Recent GitHub Actions runs:
    jq -r ".workflow_runs[] | \"  • \(.name): \(.conclusion // .status) - \(.html_url)\"" ci_response.json
    
    :: Check for in-progress runs
    jq -r ".workflow_runs[] | select(.status == \"in_progress\") | \"🚀 In-progress: \(.name) - \(.html_url)\"" ci_response.json
) else (
    echo ✅ Connected to GitHub API successfully
    echo 📋 Recent GitHub Actions runs (simplified output):
    findstr /c:"\"name\"" ci_response.json
    findstr /c:"\"conclusion\"" ci_response.json
    findstr /c:"\"status\"" ci_response.json
    findstr /c:"\"html_url\"" ci_response.json
)

del ci_response.json 2>nul