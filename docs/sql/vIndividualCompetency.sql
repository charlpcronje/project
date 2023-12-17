CREATE OR REPLACE
DEFINER=`administrator`@`localhost` PROCEDURE `saveIndividualCompetency`(
						`pQuery` JSON, `pUserId` VARCHAR(50)
)
saveIndividualCompetencyProc:
BEGIN
	-- Example of calling a stored proc with json as a parameter: call ig_db.json_test( '{"name": "TonyDB", "age": 51}')
	-- Variables from the JSON input
    -- Individual table:
	declare vIndividualId bigint;
	declare vIndividualSDId bigint;
	declare vServiceDisciplineCode varchar(50);
	declare vIndividualCompetencyId bigint;
	declare vRoleOnAProjectId bigint;
	declare vCompetencyLevelCode varchar(50);
    
	-- -- Local variables
    -- Work variables
    declare individualSDCount int;
    declare roleCount int;
	declare tmp varchar(50);
   
	-- Variables for ID's of things we've inserted
    
    -- Source: https://stackoverflow.com/questions/19905900/mysql-transaction-roll-back-on-any-exception
	declare exit handler for sqlexception
    begin
        rollback;  -- rollback any changes made in the transaction
        resignal;  -- raise again the sql exception to the caller
    end;
    
	-- Fetch all the values from the JSON
    -- ig_db.Individual
    set vIndividualId = JSON_UNQUOTE(JSON_EXTRACT(pQuery, '$.individualId'));
    set vIndividualSDId = JSON_UNQUOTE(JSON_EXTRACT(pQuery, '$.individualSDId'));
    set vServiceDisciplineCode = JSON_UNQUOTE(JSON_EXTRACT(pQuery, '$.serviceDisciplineCode'));
    set vIndividualCompetencyId = JSON_UNQUOTE(JSON_EXTRACT(pQuery, '$.individualCompetencyId'));
    set vRoleOnAProjectId = JSON_UNQUOTE(JSON_EXTRACT(pQuery, '$.roleOnAProjectId'));
    set vCompetencyLevelCode = JSON_UNQUOTE(JSON_EXTRACT(pQuery, '$.competencyLevelCode'));
	
	-- ************************************************
    -- Validations
    --
    -- Most validations should be done in the UI
    -- We double check special validations here that 
    -- database constraints won't pick up
	-- ************************************************
    
    start transaction;
    -- ************************************************
    -- Write to tables
    -- ************************************************
    -- to stop anything from being saved... uncomment the line below
    -- leave saveWizardIndividualProc;
    
	-- ig_db.IndividualSD
    -- --------------------------
	-- Ensure that we do not add duplicate service disciplines to the IndividualSD table
    select count(1) into individualSDCount from ig_db.IndividualSD where IndividualId = vIndividualId and  ServiceDisciplineCode = vServiceDisciplineCode ;
	if (individualSDCount > 0) then
		select IndividualSDId into vIndividualSDId from ig_db.IndividualSD where IndividualId = vIndividualId and  ServiceDisciplineCode = vServiceDisciplineCode ;
	else
		insert into ig_db.IndividualSD
			(IndividualId, ServiceDisciplineCode, LastUpdateTimeStamp, LastUpdateUserName)
			values
			(vIndividualId, vServiceDisciplineCode, current_timestamp(), pUserId);
		set vIndividualSDId = last_insert_id();
	end if;

	-- Ensure that we do not add duplicate roles for a service discipline to the IndividualCompetency table
    select 	count(1) into roleCount from ig_db.IndividualCompetency 
    where 	IndividualSDId = vIndividualSDId 
			and  RoleOnAProjectId = vRoleOnAProjectId ;
		
    if (roleCount > 0) then  -- just update the competency for the role
		update ig_db.IndividualCompetency
		set CompetencyLevelCode = vCompetencyLevelCode
		where IndividualId = vIndividualId and  ServiceDisciplineCode = vServiceDisciplineCode;
    else
		insert into ig_db.IndividualCompetency
            (IndividualSDId, RoleOnAProjectId, CompetencyLevelCode, LastUpdateTimeStamp, LastUpdateUserName)
			values
            (vIndividualSDId, vRoleOnAProjectId, vCompetencyLevelCode, current_timestamp(), pUserId);
		set vIndividualCompetencyId = last_insert_id();
	end if;
    
	commit;

END