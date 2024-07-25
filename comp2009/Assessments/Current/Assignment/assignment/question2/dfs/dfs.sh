#!/bin/bash
if ! [ -e Search.class ]; then
    echo "zsh: no such file or directory: Search.class"
elif [ "$#" -ne 2 ]; then
    java Search $1
elif [ "$#" -ne 3 ]; then
    java Search $1 $2
fi
