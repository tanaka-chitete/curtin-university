#!/bin/bash
if ! [ -e AStar.class ]; then
    echo "zsh: no such file or directory: AStar.class"
else
    java AStar $1 $2
fi
