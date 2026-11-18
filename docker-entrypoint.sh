#!/bin/sh
# SYE Lodge container entrypoint.
#
# The container starts as root only so it can take ownership of mounted
# storage (managed platforms mount persistent disks as root). It then drops to the
# unprivileged "sye_lodge" user and starts the application.
set -eu

UPLOAD_DIR="${UPLOAD_DIR:-/app/uploads}"
LOG_FILE="${LOG_FILE:-/app/logs/sye_lodge.log}"
LOG_DIR="$(dirname "$LOG_FILE")"

mkdir -p "$UPLOAD_DIR" "$LOG_DIR"

if [ "$(id -u)" = "0" ]; then
    chown -R sye_lodge:sye_lodge "$UPLOAD_DIR" "$LOG_DIR" 2>/dev/null || true
    APP_UID="$(id -u sye_lodge)"
    APP_GID="$(id -g sye_lodge)"
    if command -v setpriv >/dev/null 2>&1; then
        exec setpriv --reuid="$APP_UID" --regid="$APP_GID" --init-groups \
            java -jar /app/sye_lodge.jar "$@"
    fi
    # Fallback for minimal base images without util-linux.
    exec chroot --userspec="$APP_UID:$APP_GID" / java -jar /app/sye_lodge.jar "$@"
fi

exec java -jar /app/sye_lodge.jar "$@"
