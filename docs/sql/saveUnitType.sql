CREATE DEFINER=administrator@localhost PROCEDURE saveUnitType
							(	IN pQuery JSON, 
								IN pUserId VARCHAR(50))
    NO SQL

BEGIN
	-- Example of calling a stored proc with json as a parameter: call ig_db.json_test( '{"name": "TonyDB", "age": 51}')
	-- Variable`s from the JSON input
    -- Individual table:
    declare vUnitTypeCode varchar(50);
    declare vServiceDisciplineIdGroup varchar(50);
    declare vName varchar(255); 
    declare vDescription varchar(255);
	-- -- Local variables
    
    -- Source: https://stackoverflow.com/questions/19905900/mysql-transaction-roll-back-on-any-exception
	declare exit handler for sqlexception
    begin
        rollback;  -- rollback any changes made in the transaction
        resignal;  -- raise again the sql exception to the caller
    end;
    
	-- Fetch all the values from the JSON
    -- ig_db.Timesheet

    set vUnitTypeCode = JSON_UNQUOTE(JSON_EXTRACT(pQuery, '$.unitTypeCode')); 
    set vName = JSON_UNQUOTE(JSON_EXTRACT(pQuery, '$.name'));
    set vDescription = JSON_UNQUOTE(JSON_EXTRACT(pQuery, '$.description'));

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
		-- leave saveProjectProc;
		
		-- ig_db.UnitType
		-- --------------------------
		if (vUnitTypeCode is not null) and (vUnitTypeCode <> '') then

			-- update UnitType
			update ig_db.UnitType
				set 
					Name = vName, 
					Description = vDescription,
					LastUpdateTimestamp = current_timestamp,
					LastUpdateUserName = pUserId
	
				where UnitTypeCode = vUnitTypeCode;
				
		end if;

	commit;
END