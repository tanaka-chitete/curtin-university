#!/bin/bash
if ! [ -e Main.class ]; then
    javac *.java
else
    echo "make: \`Main' is up to date."
fi