#!/bin/bash
if ! [ -e AStar.class ]; then
    javac *.java
else
    echo "make: \`AStar' is up to date."
fi