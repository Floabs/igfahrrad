#!/usr/bin/env bash

# we resize out full resolution images to an appropriate size with ImageMagick
# TODO: we should use os.FileExists in hugo template for these files and Os.panick() if we do not find them.

IMAGE_BASE=./static/images

set -e
rm -rf ./static/images/small
mkdir -p ./static/images/small
pushd ./static/images
#    magick bikes_in_forest.webp -resize 512 small_bikes_in_forest.webp
    # cut out to top part of the image to load faster:
    magick bikes_in_forest.webp -crop 1770x480+0+0 ./small/cropped_home.png
    pushd small
        magick cropped_home.png -quality 50 -define webp:lossless=true bikes_in_forest.webp
        rm cropped_home.png
    popd
    magick bike-on-wood.jpg -geometry 256x ./small/bike-on-wood-256.png
    magick bike-on-wood.jpg -geometry 932x ./small/bike-on-wood-512.png
    magick bike-on-wood.jpg -geometry 1320x ./small/bike-on-wood-1024.png
    pushd small
        magick bike-on-wood-256.png -quality 50 -define webp:lossless=true bike-on-wood-256.webp
        magick bike-on-wood-512.png -quality 50 -define webp:lossless=true bike-on-wood-512.webp
        magick bike-on-wood-1024.png -quality 50 -define webp:lossless=true bike-on-wood-1024.webp
        rm bike-on-wood-*.png
    popd
popd
