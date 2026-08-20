#!/bin/bash
# Reliable GitHub CI status checker for StudyMasterApp

# Extract token from git credentials reliably
CREDENTIALS_FILE="$HOME/.git-credentials"
if [ ! -f "$CREDENTIALS_FILE" ]; then
    echo "❌ Git credentials file not found: $CREDENTIALS_FILE"
    exit 1
fi

# Extract token using multiple methods
TOKEN=$(grep -oP 'https://\K[^@:]+(?=@github.com)' "$CREDENTIALS_FILE" | head -1)
if [ -z "$TOKEN" ]; then
    # Try alternative extraction for oauth2 format
    TOKEN=$(grep -oP 'https://oauth2:\K[^@]+(?=@github.com)' "$CREDENTIALS_FILE" | head -1)
fi

if [ -z "$TOKEN" ]; then
    echo "❌ No GitHub token found in git credentials"
    echo "Credentials file content:"
    cat "$CREDENTIALS_FILE"
    exit 1
fi

# Verify token format
if [[ "$TOKEN" == ghp_* || "$TOKEN" == github_pat_* ]]; then
    echo "✅ Valid token format detected"
else
    echo "⚠️  Token format may be invalid: $TOKEN"
    echo "Expected format: ghp_... or github_pat_..."
fi

# GitHub API URL
API_URL="https://api.github.com/repos/Redwan878/StudyMasterApp/actions/runs"

# Query GitHub API for workflow runs
echo "🔍 Checking GitHub CI status..."
RESPONSE=$(curl -s -H "Authorization: token $TOKEN" "$API_URL?per_page=5")

# Check for API errors
if echo "$RESPONSE" | grep -q "Bad credentials"; then
    echo "❌ Authentication failed: Bad credentials"
    echo "Token used: ${TOKEN:0:8}..."
    exit 1
fi

if echo "$RESPONSE" | grep -q "API rate limit exceeded"; then
    echo "❌ API rate limit exceeded"
    exit 1
fi

# Parse and display workflow runs
if echo "$RESPONSE" | grep -q '"workflow_runs"'; then
    echo "✅ Connected to GitHub API successfully"
    
    echo "$RESPONSE" | python -c "
import sys, json
try:
    data = json.load(sys.stdin)
    if 'workflow_runs' not in data or not data['workflow_runs']:
        print('No workflow runs found')
        exit(0)
    
    print('📋 Recent GitHub Actions runs:')
    for run in data['workflow_runs']:
        status = run.get('conclusion', run.get('status', 'unknown'))
        workflow = run.get('name', 'unnamed')
        url = run.get('html_url', '#')
        print(f'  • {workflow}: {status} - {url}')
    
except Exception as e:
    print(f'Error parsing response: {e}')
    print(f'Raw response (first 500 chars): {sys.stdin.read()[:500]}')
    exit(1)
" else
    echo "❌ Unexpected API response format"
    echo "Raw response (first 500 chars):"
    echo "$RESPONSE" | head -500
    exit 1
fi