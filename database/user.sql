-- ********************************************************************************
-- This script creates the database users and grants them the necessary permissions
-- ********************************************************************************

CREATE USER insulin_hero_rebuild_db_owner
WITH PASSWORD 'insulinhero';

GRANT ALL
ON ALL TABLES IN SCHEMA public
TO insulin_hero_rebuild_db_owner;

GRANT ALL
ON ALL SEQUENCES IN SCHEMA public
TO insulin_hero_rebuild_db_owner;

CREATE USER insulin_hero_rebuild_db_appuser
WITH PASSWORD 'insulinhero';

GRANT SELECT, INSERT, UPDATE, DELETE
ON ALL TABLES IN SCHEMA public
TO insulin_hero_rebuild_db_appuser;

GRANT USAGE, SELECT
ON ALL SEQUENCES IN SCHEMA public
TO insulin_hero_rebuild_db_appuser;
