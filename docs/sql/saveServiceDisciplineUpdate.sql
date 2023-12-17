CREATE DEFINER=`administrator`@`localhost` PROCEDURE `saveServiceDisciplineUpdate`(IN `pQuery` JSON, 
					IN `pUserId` VARCHAR(50), 
					OUT `vServiceDisciplineId` BIGINT)
    NO SQL
BEGIN
	-- Example of calling a stored proc with json as a parameter: call ig_db.json_test( '{"name": "TonyDB", "age": 51}')
	-- Variables from the JSON input
    -- ServiceDiscipline table:
	declare	pAllowRoles VARCHAR(1);
    
	declare vServiceDisciplineCode 			varchar(50);
    declare vServiceDisciplineIdParent 	bigint default null;
    declare vServiceDisciplineIdIndustry 	bigint;
    declare vOrderNumber 					int;
    declare vName 							varchar(255);
    declare vDescription					varchar(255);
	
	declare vCount integer;
    declare tmp varchar(50);
    
    -- Source: https://stackoverflow.com/questions/19905900/mysql-transaction-roll-back-on-any-exception
	declare exit handler for sqlexception
    begin
        rollback;  -- rollback any changes made in the transaction
        resignal;  -- raise again the sql exception to the caller
    end;
    
	-- Fetch all the values from the JSON
    -- ig_db.Individual
    set vServiceDisciplineId = JSON_UNQUOTE(JSON_EXTRACT(pQuery, '$.serviceDisciplineId'));
    set vServiceDisciplineCode = JSON_UNQUOTE(JSON_EXTRACT(pQuery, '$.serviceDisciplineCode'));

    
    set tmp = JSON_UNQUOTE(JSON_EXTRACT(pQuery, '$.serviceDisciplineIdParent'));
	if ((tmp is not null) and (tmp <> '') and (tmp <> 'null')) then
		set vServiceDisciplineIdParent = JSON_UNQUOTE(JSON_EXTRACT(pQuery, '$.serviceDisciplineIdParent'));
    end if;
    
	set vServiceDisciplineIdIndustry  = JSON_UNQUOTE(JSON_EXTRACT(pQuery, '$.serviceDisciplineIdIndustry'));
	set vOrderNumber = JSON_UNQUOTE(JSON_EXTRACT(pQuery, '$.orderNumber'));
	set vName = JSON_UNQUOTE(JSON_EXTRACT(pQuery, '$.name'));
	set vDescription = JSON_UNQUOTE(JSON_EXTRACT(pQuery, '$.description'));
	
	select count(*) into vCount from ig_db.ServiceDiscipline
    where ServiceDisciplineIdParent = vServiceDisciplineId;
    if (vCount > 0 ) then 
		set pAllowRoles = 'N';
	ELSE
		set pAllowRoles = 'Y';
	end if;

     
    start transaction;
		-- ************************************************
		-- Write to tables
		-- ************************************************
		-- to stop anything from being saved... uncomment the line below
		-- leave saveWizardIndividualProc;
		
		update ig_db.ServiceDiscipline
					set ServiceDisciplineIdParent = vServiceDisciplineIdParent,
						ServiceDisciplineIdIndustry  = vServiceDisciplineIdIndustry,
						ServiceDisciplineCode = vServiceDisciplineCode,
						OrderNumber =     				vOrderNumber,
						Name =     						vName,
						Description =     				vDescription,
						AllowRoles = 					pAllowRoles,
						LastUpdateTimestamp = current_timestamp,
						LastUpdateUserName = pUserId
					where 
						ServiceDisciplineId = vServiceDisciplineId;

		update ig_db.ServiceDiscipline
				set 	AllowRoles = 'N'
				where 	ServiceDisciplineId = vServiceDisciplineIdParent;
	
	commit;
END