CREATE DEFINER=administrator@localhost PROCEDURE saveTimesheet
							(	IN pQuery JSON, 
								IN pUserId VARCHAR(50))
    NO SQL

BEGIN
	-- Example of calling a stored proc with json as a parameter: call ig_db.json_test( '{"name": "TonyDB", "age": 51}')
	-- Variable`s from the JSON input
    -- Individual table:
    declare vTimesheetId bigint; 
    declare vProjectParticipantSdRoleId bigint; 
    declare vProjBasedRemunTypeId bigint;
    declare vAssignmentId bigint;
    declare vTaskId bigint;
    declare vPpSdRoleStageId bigint;
    declare vActivityDate date;
    declare vNumberOfUnits decimal(12,2);
    declare vDescription varchar(255);

	-- -- Local variables
    
	-- Work variables
	declare tmp varchar(50);
   
	-- Variables for ID's of things we've inserted
    
    -- Source: https://stackoverflow.com/questions/19905900/mysql-transaction-roll-back-on-any-exception
	declare exit handler for sqlexception
    begin
        rollback;  -- rollback any changes made in the transaction
        resignal;  -- raise again the sql exception to the caller
    end;
    
	-- Fetch all the values from the JSON
    -- ig_db.Timesheet
    set vTimesheetId = JSON_UNQUOTE(JSON_EXTRACT(pQuery, '$.timesheetId')); 
    set vProjectParticipantSdRoleId = JSON_UNQUOTE(JSON_EXTRACT(pQuery, '$.projectParticipantSdRoleId')); 
    set vProjBasedRemunTypeId = JSON_UNQUOTE(JSON_EXTRACT(pQuery, '$.projBasedRemunTypeId'));

    set tmp = JSON_UNQUOTE(JSON_EXTRACT(pQuery, '$.assignmentId'));
	if ((tmp is not null) and (tmp <> '') and (tmp <> 'null')) then
		set vAssignmentId = JSON_UNQUOTE(JSON_EXTRACT(pQuery, '$.assignmentId'));
	end if;
	
    set tmp = JSON_UNQUOTE(JSON_EXTRACT(pQuery, '$.taskId'));
	if ((tmp is not null) and (tmp <> '') and (tmp <> 'null')) then
		set vTaskId = JSON_UNQUOTE(JSON_EXTRACT(pQuery, '$.taskId'));
	end if;

	set tmp = JSON_UNQUOTE(JSON_EXTRACT(pQuery, '$.ppSdRoleStageId'));
	if ((tmp is not null) and (tmp <> '') and (tmp <> 'null')) then
		set vPpSdRoleStageId = JSON_UNQUOTE(JSON_EXTRACT(pQuery, '$.ppSdRoleStageId'));
	end if;

    set vNumberOfUnits = JSON_UNQUOTE(JSON_EXTRACT(pQuery, '$.numberOfUnits'));
    set vDescription = JSON_UNQUOTE(JSON_EXTRACT(pQuery, '$.description'));
    
	set tmp = JSON_UNQUOTE(JSON_EXTRACT(pQuery, '$.activityDate'));
	if ((tmp is not null) and (tmp <> '') and (tmp <> 'null')) then
			set vActivityDate = from_unixtime(tmp / 1000);  -- convert ms to seconds first
	end if;

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
		
		-- ig_db.Timesheet
		-- --------------------------
		if (vTimesheetId is not null) and (vTimesheetId <> '') then

			-- update Timesheet
			update ig_db.Timesheet
				set 
					ProjectParticipantSdRoleId = vProjectParticipantSdRoleId, 
					ProjBasedRemunTypeId = vProjBasedRemunTypeId, 
					AssignmentId = vAssignmentId, 
					TaskId = vTaskId, 
					ActivityDate = vActivityDate, 
					NumberOfUnits = vNumberOfUnits, 
					Description = vDescription, 
					LastUpdateTimestamp = current_timestamp(),
					LastUpdateUserName = pUserId,

                    PpSdRoleStageId = vPpSdRoleStageId

				where TimesheetId = vTimesheetId;
			call saveTopDescriptionsUsed(vProjectParticipantSdRoleId);            
		end if;

	commit;
END