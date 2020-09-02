#!/bin/bash
set -e

source /app/venv/bin/activate
echo `pwd`
echo "Executing" "$@"
export FLASK_APP=app

exec "$@"
