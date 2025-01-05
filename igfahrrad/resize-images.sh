#!/usr/bin/env bash

# we resize out full resolution images to an appropriate size with ImageMagick
IMAGE_BASE=./static/images

set -e

pushd ./static/images
    magick bikes_in_forest.webp -resize 512 small_bikes_in_forest.webp
popd
