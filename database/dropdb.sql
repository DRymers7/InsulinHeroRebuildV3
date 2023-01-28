-- **************************************************************
-- This script destroys the database and associated users
-- **************************************************************

-- The following line terminates any active connections to the database so that it can be destroyed
SELECT pg_terminate_backend(pid)
FROM pg_stat_activity
WHERE datname = "${env:DB_NAME}";

DROP DATABASE insulin_hero_rebuild_db;

DROP USER insulin_hero_rebuild_db_owner;
DROP USER insulin_hero_rebuild_db_appuser;
