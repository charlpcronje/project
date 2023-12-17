CREATE DEFINER=administrator@localhost PROCEDURE saveNewTimesheet(
						pQuery JSON, 
                        pUserId varchar(50), 
                        OUT pTimesheetId BIGINT)

BEGIN
	-- Example of calling a stored proc with json as a parameter: call ig_db.json_test( '{"name": "TonyDB", "age": 51}')
	-- Variables from the JSON input
    -- Timesheet table:
     
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
    -- leave saveWizardIndividualProc;
    
	-- ig_db.Timesheet
    -- --------------------------
    insert into ig_db.Timesheet
		(	ProjectParticipantSdRoleId, 
			ProjBasedRemunTypeId, 
            AssignmentId, 
            TaskId, 
            ActivityDate, 
            NumberOfUnits, 
            Description, 
            LastUpdateUserName, 
            LastUpdateTimestamp,
            
   			PpSdRoleStageId
            )
		values
		(	vProjectParticipantSdRoleId, 
			vProjBasedRemunTypeId, 
            vAssignmentId, 
            vTaskId, 
            vActivityDate, 
            vNumberOfUnits, 
            vDescription, 
            pUserId, -- vLastUpdateUserName
            current_timestamp(), -- vLastUpdateTimestamp
			
            vPpSdRoleStageId
            );

    set pTimesheetId = last_insert_id();

    -- Last 10 descriptions
     call saveTopDescriptionsUsed(vProjectParticipantSdRoleId);
    
	commit;

END