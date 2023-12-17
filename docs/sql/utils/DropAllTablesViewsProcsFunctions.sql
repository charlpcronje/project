-- Run this to create a script  (The script drops all Database objects).
-- Click on results.  
-- CTRL-A (to select all).  
-- Right-click, Copy Row (tab separated).  
-- Paste in a new SQL Tab, run to execute.
-- Refresh the Schema

SELECT @schemaName := 'ig_db';    -- ig_db    --ig_db    put the correct database here.

Select '-- Select all rows below, Right-click, Copy Row (tab separated), Paste in a new SQL Tab'
UNION
Select concat('-- FOR: ', @schemaName, '  ', now(), '  Ignore the line above this one, Save this as ''', @schemaName, ' Drop ALLES.sql''')
UNION
Select '-- To delete all Database Objects, run this script.  --'
UNION
Select '-- -----------------------------------------  --'
UNION
Select '-- THIS SCRIPT DELETES ALL DATABASE OBJECTS!  --'
UNION
Select '-- =========================================  --'
UNION
Select '-- All Foreign Keys:'
UNION

SELECT concat('alter table ',table_schema,'.',table_name,' DROP FOREIGN KEY ',constraint_name,';')
FROM   information_schema.table_constraints
WHERE  constraint_type='FOREIGN KEY'	AND    table_schema = @schemaName

UNION
Select '--             --'
UNION
Select '-- All Functions:'
UNION

SELECT concat('DROP FUNCTION IF EXISTS ', @schemaName,'.',routine_name, '; --  ', routine_schema, ' Function with datatype:', ' ',  data_type)
FROM   information_schema.routines
WHERE  routine_schema = @schemaName     AND    	routine_type = 'FUNCTION'

UNION
Select '--              --'
UNION
Select '-- All Procedures:'
UNION

SELECT concat('DROP PROCEDURE IF EXISTS ', @schemaName,'.',routine_name, '; --  ', routine_schema, ' Procedure')
FROM   information_schema.routines
WHERE  routine_schema = @schemaName     AND    	routine_type = 'PROCEDURE'

UNION
Select '--          --'
UNION
Select '-- All Tables:'
UNION

SELECT concat('DROP TABLE IF EXISTS ', @schemaName,'.', table_name, '; --  ',  table_schema, ' Table')
FROM   information_schema.TABLES 
WHERE  TABLE_SCHEMA LIKE @schemaName    AND 	TABLE_TYPE LIKE 'BASE_TABLE' 
    
UNION
Select '--         --'
UNION
Select '-- All Views:'
UNION

SELECT concat('DROP VIEW IF EXISTS ', @schemaName,'.',table_name, '; --  ', table_schema, ' View')
FROM   information_schema.TABLES 
WHERE  TABLE_SCHEMA LIKE @schemaName    AND 	TABLE_TYPE LIKE 'VIEW'
    
UNION
Select '-- --------------------------------------'
UNION
Select '-- Run all of the above in a new SQL Tab.'
UNION
Select concat('-- ''C:\\Users\\hugo\\Downloads\\', @schemaName, ' Drop ALLES.sql''', ' (this file)')
UNION
Select '-- THIS SCRIPT DELETES ALL DATABASE OBJECTS!'
UNION
Select '-- Remember to Refresh the Schema.'
UNION
Select concat('-- ', now(), ' ------------------')
UNION
Select '-- -- -- -- -- -- -- -- -- -- ----'
UNION
Select '-- -- -- -- -- -- -- -- -- -- -- -'
UNION
Select '-- -- -- -- -- -- -- -- -- -- - --'
UNION
Select '-- The following is to only remove data from tables, before an import. --'
UNION
Select '/* '
UNION
Select concat('USE ', @schemaName, ';')
UNION
Select 'SET foreign_key_checks = 0;'
UNION
SELECT concat('TRUNCATE ', @schemaName,'.', table_name, ';')
FROM   information_schema.TABLES 
WHERE  TABLE_SCHEMA LIKE @schemaName    AND 	TABLE_TYPE LIKE 'BASE_TABLE' 
UNION
Select 'SET foreign_key_checks = 1;'
UNION
Select '*/ '

