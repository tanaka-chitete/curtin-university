#!/bin/bash
if ! [ -e houses ]; then
    make
    ./houses input.txt output.txt << EOF
    2
    WANO
EOF
else
    echo "Executable exists"
fi
