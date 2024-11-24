#!/usr/bin/env bash

set -e
rm -rf public target
hugo
mkdir -p target
mv public/ target/
pushd ./target/
mv public/ www_new/
cp ../_index.html ./www_new/
cat <<EOF > www_new/.htaccess
<FilesMatch "\.(php)">
    Deny from all
</FilesMatch>
EOF
tar -czf www_new.tgz www_new
scp www_new.tgz ig-fahrrad:
popd
