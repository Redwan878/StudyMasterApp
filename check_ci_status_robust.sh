#!/bin/bash
# Robust GitHub CI status checker for StudyMasterApp

# Extract token from git credentials
CREDENTIALS_FILE="$HOME/.git-credentials"
if [ ! -f "$CREDENTIALS_FILE" ]; then
    echo "❌ Git credentials file not found: $CREDENTIALS_FILE"
    exit 1
fi

TOKEN=$(grep -oP '(?<=https://)[^@]+(?=@github.com)' "$CREDENTIALS_FILE" | head -1)
if [ -z "$TOKEN" ]; then
    echo "❌ No GitHub token found in git credentials"
    exit 1
fi

# GitHub API URL
API_URL="https://api.github.com/repos/Redwan878/StudyMasterApp/actions/runs"

# Query GitHub API for workflow runs
echo "🔍 Checking GitHub CI status..."
RESPONSE=$(curl -s -H "Authorization: token $TOKEN" "$API_URL?per_page=5")

# Check for API errors
if echo "$RESPONSE" | grep -q "Bad credentials"; then
    echo "❌ Authentication failed: Bad credentials"
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
    
    # Check for in-progress runs
    in_progress = [r for r in data['workflow_runs'] if r.get('status') == 'in_progress']
    if in_progress:
        print('\\n🚀 In-progress runs:')
        for run in in_progress:
            workflow = run.get('name', 'unnamed')
            url = run.get('html_url', '#')
            print(f'  • {workflow} - {url}')
    
except Exception as e:
    print(f'Error parsing response: {e}')
    print(f'Raw response: {sys.stdin.read()}')
    exit(1)
" else
    echo "❌ Unexpected API response format"
    echo "Raw response:"
    echo "$RESPONSE" | head -20
    exit 1
fi