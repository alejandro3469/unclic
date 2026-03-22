#!/usr/bin/env bash
# Comprueba enlaces locales en .md bajo web-cuantica/ (no http(s), no #anchors solas).
# Uso: bash scripts/validate-markdown-links.sh
set -euo pipefail
ROOT="$(cd "$(dirname "$0")/.." && pwd)"
cd "$ROOT"
python3 << 'PY'
import re, os, pathlib, sys
root = pathlib.Path(".").resolve()
broken = []
for md in sorted(root.rglob("*.md")):
    if "node_modules" in str(md):
        continue
    try:
        text = md.read_text(encoding="utf-8", errors="replace")
    except OSError:
        continue
    for m in re.finditer(r'\]\(([^)]+)\)', text):
        raw = m.group(1).strip()
        if raw.startswith("http") or raw.startswith("#") or raw.startswith("mailto:"):
            continue
        path = raw.split("#", 1)[0].split(" ", 1)[0].strip()
        if not path or path.endswith("/"):
            continue
        target = (md.parent / path).resolve()
        try:
            target.relative_to(root)
        except ValueError:
            continue
        if not target.exists():
            broken.append(f"{md.relative_to(root)} -> {raw}")

if broken:
    print("Enlaces rotos:", len(broken), file=sys.stderr)
    for b in broken:
        print(" ", b, file=sys.stderr)
    sys.exit(1)
print("OK: enlaces relativos en documentación")
PY
