#!/bin/bash
if ! [ -e Main.class ]; then
    echo "zsh: no such file or directory: Main.class"
elif [ "$#" -ne 2 ]; then
    java Main $1
elif [ "$#" -ne 3 ]; then
    java Main $1 $2
fi
