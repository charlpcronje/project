CREATE DEFINER=`administrator`@`localhost` PROCEDURE `saveServiceDisciplineNew`(pQuery JSON, 
					pUserId VARCHAR(50), 
                     OUT vServiceDisciplineId BIGINT)
BEGIN
	-- Example of calling a stored proc with json as a parameter: call ig_db.json_test( '{"name": "TonyDB", "age": 51}')
	-- Variables from the JSON input
    -- ServiceDiscipline table:
	declare pAllowRoles			 			varchar(1);
	declare vServiceDisciplineCode 			varchar(50);
    declare vServiceDisciplineIdParent 		bigint default null;
    declare vServiceDisciplineIdIndustry 	bigint default null;
    declare vOrderNumber 					int;
    declare vName 							varchar(255);
    declare vDescription					varchar(255);

	declare vCount integer;
   	declare tmp varchar(50);
   	declare topLevelSd boolean default false;

    
    -- Source: https://stackoverflow.com/questions/19905900/mysql-transaction-roll-back-on-any-exception
	declare exit handler for sqlexception
    begin
        rollback;  -- rollback any changes made in the transaction
        resignal;  -- raise again the sql exception to the caller
    end;
    
	-- Fetch all the values from the JSON
    set vServiceDisciplineCode = JSON_UNQUOTE(JSON_EXTRACT(pQuery, '$.serviceDisciplineCode'));

    set tmp = JSON_UNQUOTE(JSON_EXTRACT(pQuery, '$.serviceDisciplineIdParent'));
	if ((tmp is not null) and (tmp <> '') and (tmp <> 'null')) then
		set vServiceDisciplineIdParent = JSON_UNQUOTE(JSON_EXTRACT(pQuery, '$.serviceDisciplineIdParent'));
		set vServiceDisciplineIdIndustry = JSON_UNQUOTE(JSON_EXTRACT(pQuery, '$.serviceDisciplineIdIndustry'));   -- The Industry will not be null when parent not null
	else -- ParentId = null => it is a top level service discipline. => IndustryId = SdId
		set topLevelSd = true;
    end if;

	set vOrderNumber = JSON_UNQUOTE(JSON_EXTRACT(pQuery, '$.orderNumber'));
	set vName = JSON_UNQUOTE(JSON_EXTRACT(pQuery, '$.name'));
	set vDescription = JSON_UNQUOTE(JSON_EXTRACT(pQuery, '$.description'));
	
	set pAllowRoles = 'Y';

   

    start transaction;
    -- ************************************************
    -- Write to tables
    -- ************************************************
    -- to stop anything from being saved... uncomment the line below
    -- leave saveWizardIndividualProc;
    
		insert ig_db.ServiceDiscipline
					(ServiceDisciplineCode, ServiceDisciplineIdParent, ServiceDisciplineIdIndustry, OrderNumber,   Name,  Description,  AllowRoles,  LastUpdateTimestamp, LastUpdateUserName)
			values (vServiceDisciplineCode, vServiceDisciplineIdParent, vServiceDisciplineIdIndustry, vOrderNumber, vName, vDescription, pAllowRoles, current_timestamp,   pUserId);

		set vServiceDisciplineId = last_insert_id();
	
	if (topLevelSd = true) then -- Set the Industry to itself
				update ig_db.ServiceDiscipline
				set 	ServiceDisciplineIdIndustry = vServiceDisciplineId,
						AllowRoles = 'N'
				where 	ServiceDisciplineId = vServiceDisciplineId;
	else 
			update ig_db.ServiceDiscipline
			set 	AllowRoles = 'N'
			where 	ServiceDisciplineId = vServiceDisciplineIdParent;
	end if;
	commit;
END