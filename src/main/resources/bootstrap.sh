#!/usr/bin/env bash

RUNNER=$( find . -maxdepth 1 -name '*-runner' )
if [[ ! -z "$RUNNER" ]]
then
    $RUNNER -Djava.io.tmpdir=/tmp
fi