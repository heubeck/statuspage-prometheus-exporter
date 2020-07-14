#!/bin/sh

echo "Starting with configuration at ${STATUS_CONFIG_FILE}"
cat "$STATUS_CONFIG_FILE"

/deployments/application -Dquarkus.http.host=0.0.0.0 -Dquarkus.http.port="${PORT:-8080}"
