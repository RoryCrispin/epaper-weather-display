#!/usr/bin/python2
#Rory Crispin -- rozzles.com -- 2015

from xml.dom import minidom
import datetime
import codecs

import pywapi

result = pywapi.get_weather_from_yahoo('UKXX3856', 'metric')
iconCodes = ["056", "073", "073", "01e", "01e", "064", "01c", "064", "01c", "01c", "015", "019", "019", "064", "064",
             "064", "064", "015", "0b6", "063", "014", "014", "062", "050", "050", "076", "013", "013", "013", "002",
             "002", "02e", "00d", "02e", "00d", "017", "072", "01e", "01e", "01e", "01a", "064", "064", "064", "013",
             "01d", "064", "01d", "00d"]

day_one =  datetime.datetime.now()

highs = [None] * 4
lows = [None] * 4
icons = [None] * 4
for i in range(0, 4):
    icons[i] = str(iconCodes[int(result['forecasts'][i]['code'])])
    highs[i] = str(result['forecasts'][i]['high'])
    lows[i]  = str(result['forecasts'][i]['low'])
sunsetTime = result['astronomy']['sunset']
#
# Preprocess SVG
#

# Open SVG to process
output = codecs.open('weather-script-preprocess.svg', 'r', encoding='utf-8').read()

# Insert icons and temperatures

output = output.replace('ICON_ONE', icons[0])
output = output.replace('ICON_TWO', icons[1])
output = output.replace('ICON_THREE', icons[2])
output = output.replace('ICON_FOUR', icons[3])

output = output.replace('HIGH_ONE', str(highs[0])).replace('HIGH_TWO', str(highs[1])).replace('HIGH_THREE',
                                                                                              str(highs[2])).replace(
    'HIGH_FOUR', str(highs[3]))
output = output.replace('LOW_ONE', str(lows[0])).replace('LOW_TWO', str(lows[1])).replace('LOW_THREE',
                                                                                          str(lows[2])).replace(
    'LOW_FOUR', str(lows[3]))

# # Insert days of week
one_day = datetime.timedelta(days=1)
days_of_week = ['Monday', 'Tuesday', 'Wednesday', 'Thursday', 'Friday', 'Saturday', 'Sunday']
output = output.replace('DAY_THREE', days_of_week[(day_one + 2 * one_day).weekday()]).replace('DAY_FOUR', days_of_week[
    (day_one + 3 * one_day).weekday()])

output = output.replace('SUNSET', sunsetTime)
# Write output
codecs.open('weather-script-output.svg', 'w', encoding='utf-8').write(output)
