#!/bin/sh

cd "$(dirname "$0")"

python2 weather-script.py
rsvg-convert --background-color=white -o weather-script-output.png weather-script-output.svg
pngcrush -c 0 -ow weather-script-output.png


#If you need to copy the file to a webserver location uncomment this line
#cp -f weather-script-output.png /home/pi/pyImages/weather-script-output0.png
