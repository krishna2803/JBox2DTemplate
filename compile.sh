#!/bin/bash
for file in bin/*.class; do
    if test -f $file; then
        rm $file;
    fi
done;
javac -classpath "lib/*" -d "bin/" src/*.java

./run.sh