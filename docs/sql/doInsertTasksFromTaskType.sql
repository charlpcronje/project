CREATE DEFINER=administrator@localhost PROCEDURE doInsertTasksFromTaskType(pAssignmentTypeId BIGINT, pAssignmentId BIGINT)
BEGIN
    declare vCount integer;
    declare vErrorMessage varchar(100);
	
    DECLARE done INT DEFAULT FALSE;

	DECLARE cTaskId bigint DEFAULT null;
	DECLARE cRevisionDate datetime DEFAULT null;
	DECLARE cName varchar(50) DEFAULT null;
	DECLARE cDescription varchar(255) DEFAULT null;
    DECLARE cTimeOfDay datetime DEFAULT null;
    DECLARE cStartDate datetime DEFAULT null;
    

	DECLARE half_time_goals BIGINT;
    
	DECLARE team_cursor CURSOR FOR
		select TaskId, StartDate, Name, Description   from task where AssignmentId = pAssignmentId;
		
	DECLARE CONTINUE HANDLER FOR NOT FOUND SET done = TRUE;
    
	declare exit handler for sqlexception    
    begin
        rollback;  -- rollback any changes made in the transaction
        resignal;  -- raise again the sql exception to the caller
    end;
    
	start transaction;
		
        IF pAssignmentTypeId = 0 then
        
			select Name into cName from Assignment q WHERE q.AssignmentId = pAssignmentId;
			select Description into cDescription from Assignment qq WHERE qq.AssignmentId = pAssignmentId;
			select TimeOfDay into cTimeOfDay from Assignment qqq WHERE qqq.AssignmentId = pAssignmentId;
			select StartDate into cStartDate from Assignment qqqq WHERE qqqq.AssignmentId = pAssignmentId;
        
			INSERT INTO Task (AssignmentId, OrderNumber, Name, Description, TimeOfDay, StartDate, Completed)
            values (pAssignmentId, '1', cName, cDescription, cTimeOfDay, cStartDate, 'N');
            
            SET cTaskId = LAST_INSERT_ID();
            
            INSERT INTO TaskRevision (TaskId, RevisionNumber, RevisionDate, Name, Description)
								values (cTaskId, 0, cStartDate, cName, cDescription);

		ELSE
			INSERT INTO Task (AssignmentId, OrderNumber, Name, Description, DurationDays, DurationHours, TimeOfDay, Completed)
				(SELECT (SELECT pAssignmentId), TaskOrderNumber, Name, Description, DurationDays, DurationHours, TimeOfDay, 'N'
			  FROM tasktype tt
			  WHERE tt.AssignmentTypeId = pAssignmentTypeId);
	   
			OPEN team_cursor;
			teams_loop:
			LOOP
				FETCH team_cursor INTO half_time_goals, cRevisionDate, cName, cDescription;
			IF done THEN 
				LEAVE teams_loop;
			END IF;
			
			insert into taskRevision (TaskId, RevisionNumber, RevisionDate, Name, Description)
								values(half_time_goals, 0, cRevisionDate, cName, cDescription);
			
			END LOOP teams_loop;
			
			CLOSE team_cursor;
            
		END IF;

    
	commit;

END