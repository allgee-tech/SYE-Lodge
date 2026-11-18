#!/usr/bin/env bash
# Dump the SYE Lodge database from the production Compose stack.
#
#   sudo bash deploy/backup.sh
#
# Writes backups/sye-lodge-YYYY-MM-DD-HHMM.dump and keeps 14 days by default
# (override with BACKUP_RETENTION_DAYS).
set -euo pipefail

cd "$(dirname "$0")/.."

RETENTION_DAYS="${BACKUP_RETENTION_DAYS:-14}"
STAMP="$(date +%Y-%m-%d-%H%M)"
mkdir -p backups

docker compose -f docker-compose.prod.yml exec -T db \
    pg_dump -U sye_lodge_app -d sye_lodge -Fc > "backups/sye-lodge-${STAMP}.dump"

find backups -type f -name 'sye-lodge-*.dump' -mtime +"${RETENTION_DAYS}" -delete

echo "Backup written to backups/sye-lodge-${STAMP}.dump"
