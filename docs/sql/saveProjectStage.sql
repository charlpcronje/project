CREATE DEFINER=`administrator`@`localhost` PROCEDURE `saveProjectStage`
					(IN pProjectStageId BIGINT, 
					IN pProjectId BIGINT, 
					IN pProjectStageTypeId bigint, 
					IN pDescription varchar(255), 
					IN pStartDate DATETIME, 
                    IN pEndDate DATETIME, 
					IN `pUserId` VARCHAR(50))
BEGIN
	-- Work variables
	declare vDayBefore datetime;
   	declare vOverlapCount int;
    declare vOpenEndedStartDate datetime;
   	declare vEndDateOk int;

    -- Source: https://stackoverflow.com/questions/19905900/mysql-transaction-roll-back-on-any-exception
	declare exit handler for sqlexception
    begin
        rollback;  -- rollback any changes made in the transaction
        resignal;  -- raise again the sql exception to the caller
    end;
	
    set vDayBefore = DATE_ADD(pStartDate, INTERVAL -1 DAY); 
    -- SELECT DATE_ADD(day, -1, pStartDate) into vDayBefore;
	
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
	-- ig_db.ProjectStage
    -- --------------------------
		-- Is it an insert?
		if isnull(pProjectStageId) then 
		-- If there is an open ended record, set EndDate to the day before new record's Start Date
        -- But first check that the vDayBefore is > the open ended record's StartDate.
        
			select 	ps.StartDate into vOpenEndedStartDate 
            from	ProjectStage ps
			where
				ps.ProjectId = pProjectId
                and 
                isnull(ps.EndDate);

			select datediff(vDayBefore, vOpenEndedStartDate) into vEndDateOk;
			if (vEndDateOk < 0) then
				-- Give error message
				signal sqlstate '45000'
				set message_text = 'Close open-ended date range.  Please re-check Project Dates';
			end if;
        
			update ProjectStage
			set
				EndDate = vDayBefore,
				LastUpdateTimestamp = current_timestamp,
				LastUpdateUserName = pUserId
			where
				ProjectId = pProjectId
                and 
                isnull(EndDate);

			insert into ProjectStage
            set ProjectId= pProjectId, 
                ProjectStageTypeId = pProjectStageTypeId,
                Description = pDescription,
                StartDate = pStartDate, 
                EndDate = pEndDate,
				LastUpdateTimestamp = current_timestamp,
				LastUpdateUserName = pUserId;
                
			set pProjectStageId = last_insert_id();
		else
			-- This is an update:
			update ig_db.ProjectStage
            set ProjectId = pProjectId, 
                ProjectStageTypeId = pProjectStageTypeId,
                Description = pDescription,
                StartDate = pStartDate, 
                EndDate = pEndDate,
				LastUpdateTimestamp = current_timestamp,
				LastUpdateUserName = pUserId
			where 
				ProjectStageId = pProjectStageId;
		end if;

        -- Does the new dates overlap with current dates?        
    	select count(1) into vOverlapCount from ProjectStage where 
			ProjectId = pProjectId 
            and
            ProjectStageId <> pProjectStageId
            and
            (
				((StartDate <= pStartDate) and (EndDate >= pEndDate)) 
				or (pStartDate between StartDate and EndDate) 
				or (pEndDate between StartDate and EndDate)
			);

    	select * from ProjectStage where 
			ProjectId = pProjectId 
            and
            ProjectStageId <> pProjectStageId
            and
            (
				((StartDate <= pStartDate) and (EndDate >= pEndDate)) 
				or (pStartDate between StartDate and EndDate) 
				or (pEndDate between StartDate and EndDate)
			);
    
		if (vOverlapCount > 0) then
			-- Give error message
			signal sqlstate '45000'
			set message_text = 'Project Stage Dates overlap.  Please re-check Project Dates';
		end if;

	commit;
END