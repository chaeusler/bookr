#!/bin/bash

if [ -z "$JBOSS_HOME" ]; then
    echo "Need to set JBOSS_HOME"
    exit 1
fi

DIR=$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )
cp postgresql-9.4-1201-jdbc41.jar /tmp/

${JBOSS_HOME}/bin/jboss-cli.sh -c --file=${DIR}/setupWildfly.cli

rm /tmp/postgresql-9.4-1201-jdbc41.jar

