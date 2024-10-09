#!/bin/env bash
hugo --verbose --minify --destination www --environment production --baseURL https://www.ig-fahrrad.at/
tar -czf www.tgz www
scp www.tgz igfahrrad:

