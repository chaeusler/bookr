#!/bin/bash

if [ -z "$JBOSS_HOME" ]; then
    echo "Need to set JBOSS_HOME"
    exit 1
fi

${JBOSS_HOME}/bin/jboss-cli.sh -c --file=${DIR}/setupWildfly.cli

