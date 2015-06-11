#!/bin/bash

if [ -z "$JBOSS_HOME" ]; then
    echo "Need to set JBOSS_HOME"
    exit 1
fi

DIR=$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )

echo "starting standalone - please be patient"
mkdir ${DIR}/build
nohup ${JBOSS_HOME}/bin/standalone.sh > build/wildflyOutput.log &

sleep 20

echo "configuring"
${JBOSS_HOME}/bin/jboss-cli.sh -c --file=${DIR}/setupWildfly.cli

