#!/usr/bin/env bash
set -e
hugo --verbose --minify --destination www --environment production --baseURL https://www.ig-fahrrad.at/
cp ./.htaccess _index.html www/
tar -czf www.tgz www
scp www.tgz igfahrrad:
