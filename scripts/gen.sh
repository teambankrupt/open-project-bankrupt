#!/bin/bash

if [ "$1" = "crud" ]; then
  srcDir="examples/crudexample/crudexample"
elif [ "$1" = "module" ]; then
  srcDir="examples/examplemodule"
fi

echo $srcDir

java -jar scripts/tools/gentool.jar "$1" "$2" "$srcDir" "$3"
