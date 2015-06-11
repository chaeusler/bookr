# Release Notes
[Curreltly located on github] (https://github.com/chaeusler/bookr/issues)

# Setup instructions on a single machine
These instructions fit the installation on a single machine running Postgresql und Wilfly.

## Preconditions
- Postgresql 9.4 is installed and running
- Wildfly 8.2 is installed and runnning

## Setup database
create a Database named bookr with the username bookr and password bookr. If you want to change them, you need to adapt the datasource settings from wildfly.

apply the Sql located in DDL scripts against the database:

psql -d=bokr -U=bookr -f FILENAME

## Configure Wildfly
JBOSS_HOME needs to be set.

execute `./setupWildfly.sh`

## Configure https (if you want to)
follow [Configuring https] (http://blog.eisele.net/2015/01/ssl-with-wildfly-8-and-undertow.html)

## Deploy application
cp *.war $JBOSS_HOME/standalone/deployments