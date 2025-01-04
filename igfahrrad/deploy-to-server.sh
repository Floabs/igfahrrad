#!/usr/bin/env bash

set -e
rm -rf public target
hugo
mkdir -p target
mv public/ target/
pushd ./target/
mv public/ www_new/
cp ../_index.html ./www_new/
tar -czf www_new.tgz www_new
scp www_new.tgz igfahrrad:
popd
