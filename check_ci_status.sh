#!/bin/bash
# Check GitHub CI status for StudyMasterApp

# Extract token from git credentials
TOKEN=$(cat ~/.git-credentials | grep -oP '(?<=https://)[^@]+(?=@github.com)' | head -1)

if [ -z "$TOKEN" ]; then
    echo "No GitHub token found in git credentials"
    exit 1
fi

# Query GitHub API for workflow runs
echo "Checking GitHub CI status..."
curl -s -H "Authorization: token $TOKEN" \
    "https://api.github.com/repos/Redwan878/StudyMasterApp/actions/runs?per_page=5" | \
    python -c "
import sys, json
runs = json.load(sys.stdin)['workflow_runs']
for r in runs:
    print(f'Run {r[\"id\"]}: {r[\"name\"]} - {r.get(\"conclusion\", r.get(\"status\", \"unknown\"))}')
"