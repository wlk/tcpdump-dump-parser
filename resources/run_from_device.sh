#!/bin/bash
#$1 - device
/usr/sbin/tcpdump -v -t -n -A -q -i $1 > $1.txt
