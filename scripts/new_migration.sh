#!/bin/bash
echo "-- New Migration" > "application/src/main/resources/db/migration/V`date +%s`__$1.sql"
