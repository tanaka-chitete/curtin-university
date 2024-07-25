#!/bin/bash
read file1 file2
if [ file1 -nt file2 ]; then
    echo $file1 is newer than $file2
else
    echo $file2 is newer than $file1
fi
