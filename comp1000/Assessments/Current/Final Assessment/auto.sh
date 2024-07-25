#!/bin/bash
if ! [ -e rpg ]; then
    make
    ./rpg hero.txt enemies.txt 5.0
else
    echo "executable exists"
fi
