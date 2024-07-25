#!/bin/bash
if ! [ -e Search.class ]; then
    javac *.java
else
    echo "make: \`Search' is up to date."
fi