#!/bin/bash
export PGPASSWORD="${env:DB_PASS}"
BASEDIR=$(dirname $0)
DATABASE=${env:DB_NAME}
psql -U postgres -f "$BASEDIR/dropdb.sql" &&
createdb -U postgres $DATABASE &&
psql -U postgres -d $DATABASE -f "$BASEDIR/schema.sql" &&
psql -U postgres -d $DATABASE -f "$BASEDIR/user.sql"
