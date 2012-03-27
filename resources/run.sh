#!/bin/bash
#dumps file from $1 into required format
/usr/sbin/tcpdump -v -t -n -A -q -r $1 > $1.txt
