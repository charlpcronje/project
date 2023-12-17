CREATE DEFINER=`root`@`localhost` PROCEDURE `saveProjectParentAndSubProject`(
				IN pQuery JSON, 
				IN pUserId VARCHAR(50), 
                OUT pProjectIdParent BIGINT, 
                OUT pSubProjectId BIGINT)
    NO SQL
BEGIN

	-- Business rules:saveNewIndividual
    -- When new Parent Project:
		-- Create Parent Project
        -- Add ProjectHost to Parent Project's participants
        -- Update ParentProject to link to the HostParticipant
        
        -- Create Sub Project A Initial
        -- Add ProjectHost to Parent Project's participants
        -- Copy relevant Parent Project fields into Sub Project

	-- Example of calling a stored proc with json as a parameter: call ig_db.json_test( '{"name": "TonyDB", "age": 51}')
	-- Variable`s from the JSON input
    -- Individual table:
    declare vProjectIdParent bigint default null;
    declare vSubProjectId bigint default null;
    declare vParticipantIdHost bigint;
    declare vParticipantIdLevel1 bigint;
    declare vProjectParticipantIdLevel1 bigint;
    declare vProjectNumberBigInt bigint;
    declare vProjectNumberText varchar(50);
	declare vTitle varchar(45);
    declare vSubProjNumber varchar(50);
	declare vDescription varchar(512);
	declare vIndividualIdProjectAdmin bigint;
	declare vIsActive varchar(1);
	declare vStartDate datetime;
	declare vCompletionDate datetime;
	declare vFlagSustenanceProject varchar(1) default 'N';

	declare vIsUpdate varchar(1) default 'N';

    
	-- Work variables
    declare pMaxProjectNumber bigint;
  	declare tmp varchar(50);
    declare vLatestProjectNumber bigint;
   
	-- Variables for ID's of things we've inserted
    
    -- Source: https://stackoverflow.com/questions/19905900/mysql-transaction-roll-back-on-any-exception
	declare exit handler for sqlexception
    begin
        rollback;  -- rollback any changes made in the transaction
        resignal;  -- raise again the sql exception to the caller
    end;
    
	-- Fetch all the values from the JSON
    -- ig_db.Project

    set tmp = JSON_UNQUOTE(JSON_EXTRACT(pQuery, '$.projectIdParent'));
	if ((tmp is not null) and (tmp <> '') and (tmp <> 'null')) then
		set vProjectIdParent = JSON_EXTRACT(pQuery, '$.projectIdParent');
    end if;

    set tmp = JSON_UNQUOTE(JSON_EXTRACT(pQuery, '$.projectId'));
	if ((tmp is not null) and (tmp <> '') and (tmp <> 'null')) then
		set vSubProjectId = JSON_EXTRACT(pQuery, '$.projectId');
    end if;

--     set tmp = JSON_UNQUOTE(JSON_EXTRACT(pQuery, '$.projectIdParent'));
-- 	if ((tmp is not null) and (tmp <> '') and (tmp <> 'null')) then
-- 		set vProjectIdParent = JSON_EXTRACT(pQuery, '$.projectIdParent');
--     end if;

     set tmp = JSON_UNQUOTE(JSON_EXTRACT(pQuery, '$.projectParticipantIdLevel1'));
 	if ((tmp is not null) and (tmp <> '') and (tmp <> 'null')) then
		set vProjectParticipantIdLevel1 = JSON_EXTRACT(pQuery, '$.projectParticipantIdLevel1');
    end if;

	set vParticipantIdHost = JSON_EXTRACT(pQuery, '$.participantIdHost');
	set vParticipantIdLevel1 = JSON_EXTRACT(pQuery, '$.participantIdLevel1');

    set tmp = JSON_UNQUOTE(JSON_EXTRACT(pQuery, '$.projectNumberBigInt'));
 	if ((tmp is not null) and (tmp <> '') and (tmp <> 'null')) then
		set vProjectNumberBigInt = JSON_EXTRACT(pQuery, '$.projectNumberBigInt');
	else 
		set vIsUpdate = 'Y';
        set vProjectNumberBigInt = 2; -- Not correct.  There should have been a ProjectNumber!
    end if;

	set tmp = JSON_UNQUOTE(JSON_EXTRACT(pQuery, '$.flagSustenanceProject'));
    if ((tmp = null) or (tmp = '') or (tmp = 'N')) then
		set vFlagSustenanceProject = 'N';
	ELSE
		set vFlagSustenanceProject = 'Y';
	end if;


    set vProjectNumberText = JSON_UNQUOTE(JSON_EXTRACT(pQuery, '$.projectNumberText'));
	set vTitle = JSON_UNQUOTE(JSON_EXTRACT(pQuery, '$.title'));
	set vDescription = JSON_UNQUOTE(JSON_EXTRACT(pQuery, '$.description'));
	set vSubProjNumber = JSON_UNQUOTE(JSON_EXTRACT(pQuery, '$.subProjNumber'));

    set tmp = JSON_UNQUOTE(JSON_EXTRACT(pQuery, '$.individualIdProjectAdmin'));
	if ((tmp is not null) and (tmp <> '') and (tmp <> 'null')) then
		set vIndividualIdProjectAdmin = JSON_EXTRACT(pQuery, '$.individualIdProjectAdmin');
    end if;

	set vIsActive = JSON_UNQUOTE(JSON_EXTRACT(pQuery, '$.isActive'));
    
	set tmp = JSON_UNQUOTE(JSON_EXTRACT(pQuery, '$.startDate'));
    if ((tmp is not null) and (tmp <> '') and (tmp <> 'null')) then
		set vStartDate = from_unixtime(tmp / 1000);  -- convert ms to seconds first
	end if;
    
	set tmp = JSON_UNQUOTE(JSON_EXTRACT(pQuery, '$.completionDate'));
    if ((tmp is not null) and (tmp <> '') and (tmp <> 'null')) then
		set vCompletionDate = from_unixtime(tmp / 1000);  -- convert ms to seconds first
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
		
		-- ig_db.Project 
		-- --------------------------
	   -- --------------------------
		if (vIsUpdate = 'Y') then
			if (vProjectIdParent is not null) and (vProjectIdParent <> '') then
				select 	ProjectNumberBigInt into vProjectNumberBigInt 
                from 	Project
                where	ProjectId = vProjectIdParent;
            end if;
		end if;

       -- Update ParentProject
		if (vProjectIdParent is not null) and (vProjectIdParent <> '') then
			-- update Parent Project
			update ig_db.Project
				set 
					ProjectIdParent = null, -- vProjectIdParent, 
					ProjectParticipantIdLevel1 = vProjectParticipantIdLevel1,
					ParticipantIdHost = vParticipantIdHost,
					IndividualIdProjectAdmin = null, -- Parent does not have a vIndividualIdProjectAdmin,
				    FlagSustenanceProject = vFlagSustenanceProject,
					ProjectNumberBigInt = vProjectNumberBigInt,
					ProjectNumberText = vProjectNumberText,
					ProjectNameText = CONCAT(
						CONCAT(left('0000', 4 - length(vParticipantIdHost)), vParticipantIdHost),
						' - ',
						vProjectNumberText,
						' - ', 
						vTitle),
					Title = vTitle,
					SubProjNumber = null,
					-- Description = vDescription,
					IsActive = vIsActive,
					StartDate = vStartDate,
					CompletionDate = vCompletionDate,
					LastUpdateTimestamp = current_timestamp(),
					LastUpdateUserName = pUserId 
				where ProjectId = vProjectIdParent;
				set pProjectIdParent = vProjectIdParent;

			-- Update all Sub Projects of this Parent Project
            update ig_db.Project
				set 
					ProjectParticipantIdLevel1 = vProjectParticipantIdLevel1,
					ParticipantIdHost = vParticipantIdHost,
					ProjectNumberBigInt = null, -- number only shows in Parent Project
					ProjectNumberText = vProjectNumberText,		
				    FlagSustenanceProject = vFlagSustenanceProject,
					ProjectNameText = CONCAT(
						CONCAT(left('0000', 4 - length(vParticipantIdHost)), vParticipantIdHost),
						' - ',
						vProjectNumberText,
						' - ', 
						vTitle,
						' - ', 
						subProjNumber -- The subProjNumber in the current record that is being updated
                        ),
					Title = vTitle
					-- Description = vDescription,
				where ProjectIdParent = vProjectIdParent;

			-- update specific Sub Project
			update ig_db.Project
				set 
					ProjectIdParent = vProjectIdParent, 
					ProjectParticipantIdLevel1 = vProjectParticipantIdLevel1,
					ParticipantIdHost = vParticipantIdHost,
					IndividualIdProjectAdmin = vIndividualIdProjectAdmin,
					
				    FlagSustenanceProject = vFlagSustenanceProject,
					ProjectNumberBigInt = null,
					ProjectNumberText = vProjectNumberText,
					ProjectNameText = 				CONCAT(
						CONCAT(left('0000', 4 - length(vParticipantIdHost)), vParticipantIdHost),
						' - ',
						vProjectNumberText,
						' - ', 
						vTitle,
						' - ', 
						vSubProjNumber),
					Title = vTitle,
					SubProjNumber = vSubProjNumber,
					Description = vDescription,
					IsActive = vIsActive,
					StartDate = vStartDate,
					CompletionDate = vCompletionDate,
					LastUpdateTimestamp = current_timestamp(),
					LastUpdateUserName = pUserId 
				where ProjectId = vSubProjectId;
				set pSubProjectId = vSubProjectId;
		else
			-- insert Parent Project
			insert into ig_db.Project
				(ProjectIdParent, 
				ProjectParticipantIdLevel1, 
				ParticipantIdHost, 
				IndividualIdProjectAdmin, 
				FlagSustenanceProject,
				ProjectNumberBigInt, 
				ProjectNumberText, 
				ProjectNameText, 
				Title, 
				SubProjNumber,
				Description, 
				IsActive, 
				StartDate, 
				CompletionDate, 
				LastUpdateTimestamp, 
				LastUpdateUserName)
				values
				(null, -- vProjectIdParent, 
				null, -- vProjectParticipantIdLevel1 ... add later, 
				vParticipantIdHost, -- vParticipantIdHost, 
				null, -- vIndividualIdProjectAdmin, 
				vFlagSustenanceProject,
				vProjectNumberBigInt, 
				vProjectNumberText, 
				CONCAT(
						CONCAT(left('0000', 4 - length(vParticipantIdHost)), vParticipantIdHost),
						' - ',
						vProjectNumberText,
						' - ', 
						vTitle),
				vTitle, 
                null, -- SubProjNumber
				'Parent Project', 
				vIsActive, 
				vStartDate, 
				vCompletionDate, 
				current_timestamp(), 
				pUserId);
			set pProjectIdParent = last_insert_id();
            
			insert into ig_db.ProjectParticipant
			(ProjectId, ParticipantId, ProjectParticipantIdAboveMe, Description, StartDate, EndDate, LastUpdateUserName, LastUpdateTimestamp)
			values 
			(pProjectIdParent, vParticipantIdLevel1, null, Description, vStartDate, vCompletionDate, pUserId, current_timestamp());

			set vProjectParticipantIdLevel1 = last_insert_id();
			
			update Project 
			set ProjectParticipantIdLevel1 = vProjectParticipantIdLevel1 
			where ProjectId = pProjectIdParent;

			-- Insert the Sub Project A Initial
			insert into ig_db.Project
				(ProjectIdParent, 
				ProjectParticipantIdLevel1, 
				ParticipantIdHost, 
				IndividualIdProjectAdmin, 
				FlagSustenanceProject,
				ProjectNumberBigInt, 
				ProjectNumberText, 
				ProjectNameText, 
				Title, 
				SubProjNumber,
				Description, 
				IsActive, 
				StartDate, 
				CompletionDate, 
				LastUpdateTimestamp, 
				LastUpdateUserName)
				values
				(pProjectIdParent, -- The project just added is the parent project
				vProjectParticipantIdLevel1, 
				vParticipantIdHost, 
				vIndividualIdProjectAdmin, 
					vFlagSustenanceProject,
				null, -- otherwise the Index is violated vProjectNumberBigInt, 
				vProjectNumberText, 
				CONCAT(
						CONCAT(left('0000', 4 - length(vParticipantIdHost)), vParticipantIdHost),
						' - ',
						vProjectNumberText,
						' - ', 
						vTitle,
						' - ', 
                        vSubProjNumber
                        ),
				vTitle, 
                vSubProjNumber,
				vDescription, 
				vIsActive, 
				vStartDate, 
				vCompletionDate, 
				current_timestamp(), 
				pUserId);
			set pSubProjectId = last_insert_id();

			insert into ig_db.ProjectParticipant
			(ProjectId, ParticipantId, ProjectParticipantIdAboveMe, Description, StartDate, EndDate, LastUpdateUserName, LastUpdateTimestamp)
			values 
			(pSubProjectId, vParticipantIdLevel1, null, Description, vStartDate, vCompletionDate, pUserId, current_timestamp());

			insert into ig_db.ProjectStage
			(ProjectId, OrderNumber, StageName, Description, StartDate, EndDate, LastUpdateUserName, LastUpdateTimestamp)
			values 
			(pSubProjectId, 1,  "Stage 1 - Single Stage" , "Default Single Stage", vStartDate, vCompletionDate, pUserId, current_timestamp());

		end if;

		-- ig_db.Participant
		-- --------------------------
		select 	max(p.ProjectNumberBigInt) into pMaxProjectNumber 
		from	Project p,
				ProjectParticipant pp
		where 	p.ProjectId = pProjectIdParent
				and p.ProjectId = pp.ProjectId
				and pp.ParticipantId = vParticipantIdHost
				and pp.ProjectParticipantIdAboveMe IS NULL;
				
		-- Make sure to save the biggest project number
		if (pMaxProjectNumber > vProjectNumberBigInt) then
			set vLatestProjectNumber = pMaxProjectNumber;
		else 
			set vLatestProjectNumber = vProjectNumberBigInt;
		end if;
	   
	   update ig_db.Participant
			set 
				LatestProjectNumber = vLatestProjectNumber
			where 
				ParticipantId = vParticipantIdHost;
            
	commit;
END