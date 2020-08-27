#!/bin/bash

function execTool() {
  java -jar scripts/tools/gentool.jar "$1" "$2" "$srcDir" "$3"
}

if [ "$1" = "crud" ]; then
  srcDir="examples/crudexample"
  execTool "$1" "$2" "$3"
elif [ "$1" = "module" ]; then
  srcDir="examples/examplemodule"
  execTool "$1" "$2" "$3"
elif [ "$1" = "migration" ]; then
  echo "-- New Migration" >"app/src/main/resources/db/migration/V$(date +%s)__$2.sql"
  echo "Migration Created!"
fi
