CREATE DEFINER=`administrator`@`localhost` PROCEDURE `cloneParentAndOrSubProject`(
					IN `pQuery` JSON, 
					IN `pUserId` VARCHAR(50), 
					OUT `pProjectIdParentAdded` BIGINT, 
					OUT `pProjectIdCloned` BIGINT)
    NO SQL
BEGIN
	-- Example of calling a stored proc with json as a parameter: call ig_db.json_test( '{"name": "TonyDB", "age": 51}')
	-- Variable`s from the JSON input
    -- Individual table:
    -- declare vNewParentProjectId bigint ;
    declare vNewParentProjectTitle bigint ;
    declare vProjectIdToBeClonedParent bigint ;
    declare vParticipantIdHost bigint;
    declare vCloneParentProjectToo int;
    declare vProjectNumberBigInt bigint;
    declare vProjectNumberText varchar(255);
    declare vTitle varchar(255);
    
    declare vProjectIdToBeCloned bigint ;
    declare vSubProjNumber varchar(50);
	declare vDescription varchar(512);
	declare vStartDate datetime;
	declare vCompletionDate datetime;
	declare vCloneLevel integer;
    
	-- ProjectParticipant temporary table variables
	declare vProjectParticipantIdAtTheTop bigint;

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
    
	-- If parent must be cloned too....
    
    
	set tmp = JSON_UNQUOTE(JSON_EXTRACT(pQuery, '$.cloneParentProjectToo'));
 	if ((tmp is not null) and (tmp <> '') and (tmp <> 'null')) then
		set vCloneParentProjectToo = JSON_EXTRACT(pQuery, '$.cloneParentProjectToo');
	else 
        set vCloneParentProjectToo = 0; 
    end if;

	set tmp = JSON_UNQUOTE(JSON_EXTRACT(pQuery, '$.projectNumberBigInt'));
 	if ((tmp is not null) and (tmp <> '') and (tmp <> 'null')) then
		set vProjectNumberBigInt = JSON_EXTRACT(pQuery, '$.projectNumberBigInt');
	else 
        set vProjectNumberBigInt = 2; -- Not correct.  There should have been a ProjectNumber!
    end if;

    set vProjectNumberText = JSON_UNQUOTE(JSON_EXTRACT(pQuery, '$.projectNumberText'));
	set vTitle = JSON_UNQUOTE(JSON_EXTRACT(pQuery, '$.title'));
    
	-- Fetch all the values from the JSON
	set vProjectIdToBeCloned = JSON_EXTRACT(pQuery, '$.projectIdToBeCloned');
	set vSubProjNumber = JSON_UNQUOTE(JSON_EXTRACT(pQuery, '$.subProjNumber'));
	set vDescription = JSON_UNQUOTE(JSON_EXTRACT(pQuery, '$.description'));
	
	set tmp = JSON_UNQUOTE(JSON_EXTRACT(pQuery, '$.startDate'));
    if ((tmp is not null) and (tmp <> '') and (tmp <> 'null')) then
		set vStartDate = from_unixtime(tmp / 1000);  -- convert ms to seconds first
	end if;
	
	set tmp = JSON_UNQUOTE(JSON_EXTRACT(pQuery, '$.completionDate'));
    if ((tmp is not null) and (tmp <> '') and (tmp <> 'null')) then
		set vCompletionDate = from_unixtime(tmp / 1000);  -- convert ms to seconds first
	end if;
	set vCloneLevel = JSON_UNQUOTE(JSON_EXTRACT(pQuery, '$.cloneLevel'));

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
       select vCloneParentProjectToo;
       if vCloneParentProjectToo > 0 then
		-- Create new Parent Project cloned from of the Project
		-- Select the parentProject of the Project to be cloned
		set	vProjectIdToBeClonedParent =
			(	select ProjectIdParent 
				from 	ig_db.Project p
				where	p.ProjectId = vProjectIdToBeCloned);

		set	vParticipantIdHost =
			(	select ParticipantIdHost 
				from 	ig_db.Project p
				where	p.ProjectId = vProjectIdToBeCloned);
        
		insert into ig_db.Project 
				(ProjectIdParent, ParticipantIdHost, ProjectParticipantIdLevel1, IndividualIdProjectAdmin, 
				FlagSustenanceProject,
				ProjectNumberBigInt, ProjectNumberText, ProjectNameText, Title, 
				SubProjNumber, Description, IsActive, StartDate, CompletionDate, ProjectIdClonedFrom, LastUpdateTimestamp, LastUpdateUserName)
			select 
					null, -- p.ProjectIdParent, 
                    p.ParticipantIdHost, 
                    p.ProjectParticipantIdLevel1, 
                    p.IndividualIdProjectAdmin, 
					p.FlagSustenanceProject,
                    vProjectNumberBigInt, 
                    vProjectNumberText, 
					CONCAT(CONCAT(left('0000', 4 - length(p.ParticipantIdHost)), p.ParticipantIdHost),
						' - ',
						vProjectNumberText,
						' - ', 
						vTitle),                    
                    vTitle, 
					null, -- vSubProjNumber, 
                    vDescription, "Y", vStartDate, null, 
                    vProjectIdToBeClonedParent, -- ProjectIdClonedFrom
                    current_timestamp(), pUserId 
			from 	ig_db.Project p
			where	p.ProjectId = vProjectIdToBeClonedParent;
            
       		set pProjectIdParentAdded = last_insert_id();
            
			select pProjectIdParentAdded;

			-- Insert sub project linked to the newly cloned parent project
			insert into ig_db.Project 
 				(ProjectIdParent, ParticipantIdHost, ProjectParticipantIdLevel1, IndividualIdProjectAdmin, 
				FlagSustenanceProject,
				ProjectNumberBigInt, ProjectNumberText, ProjectNameText, Title, 
 				SubProjNumber, Description, IsActive, StartDate, CompletionDate, ProjectIdClonedFrom, LastUpdateTimestamp, LastUpdateUserName)
			select 
					pProjectIdParentAdded, 
                    p.ParticipantIdHost, 
                    p.ProjectParticipantIdLevel1, 
                    p.IndividualIdProjectAdmin, 
					p.FlagSustenanceProject,
                    null, -- vProjectNumberBigInt, Only the Parent Project gets a ProjectNumberBigInt 
                    vProjectNumberText, 
					CONCAT(CONCAT(left('0000', 4 - length(p.ParticipantIdHost)), p.ParticipantIdHost),
						' - ',
						vProjectNumberText, -- p.ProjectNumberText,
						' - ', 
						vTitle, -- p.Title,
						' - ', 
						vSubProjNumber),                    
                    vTitle, 
					vSubProjNumber, vDescription, "Y", vStartDate, null, p.ProjectId, current_timestamp(), pUserId 
			from 	ig_db.Project p
			where	p.ProjectId = vProjectIdToBeCloned;

		set pProjectIdCloned = last_insert_id();

		-- ig_db.Participant
		-- --------------------------
		select 	max(p.ProjectNumberBigInt) into pMaxProjectNumber 
		from	Project p,
				ProjectParticipant pp
		where 	p.ProjectId = pProjectIdParentAdded
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


	else

		-- Create new Sub Project cloned from of the sub Project (no new parent added)
		insert into ig_db.Project 
				(ProjectIdParent, ParticipantIdHost, ProjectParticipantIdLevel1, IndividualIdProjectAdmin, 
				FlagSustenanceProject,
				ProjectNumberBigInt, ProjectNumberText, ProjectNameText, Title, 
				SubProjNumber, Description, IsActive, StartDate, CompletionDate, ProjectIdClonedFrom, LastUpdateTimestamp, LastUpdateUserName)
			select 
					p.ProjectIdParent, p.ParticipantIdHost, p.ProjectParticipantIdLevel1, p.IndividualIdProjectAdmin, 
					p.FlagSustenanceProject,
					p.ProjectNumberBigInt, p.ProjectNumberText, 
					CONCAT(CONCAT(left('0000', 4 - length(p.ParticipantIdHost)), p.ParticipantIdHost),
						' - ',
						p.ProjectNumberText,
						' - ', 
						p.Title,
						' - ', 
						vSubProjNumber),                    
                    p.Title, 
					vSubProjNumber, vDescription, "Y", vStartDate, vCompletionDate, p.ProjectId, current_timestamp(), pUserId 
			from 	ig_db.Project p
			where	p.ProjectId = vProjectIdToBeCloned;

		set pProjectIdCloned = last_insert_id();
	end if;
		-- -----------------------------------------------------------------------------------
		-- -----------------------------------------------------------------------------------
		-- -----------------------------------------------------------------------------------
		if vCloneLevel > 0 then			-- Add ProjectStages
   			insert into ig_db.ProjectStage
				(ProjectId, OrderNumber, StageName, Description, StartDate, EndDate, ProjectStageIdClonedFrom, LastUpdateUserName, LastUpdateTimestamp)
			select 
				pProjectIdCloned, ps.OrderNumber, ps.StageName, ps.Description, ps.StartDate, ps.EndDate, ps.ProjectStageId, pUserId ,current_timestamp()
			from 	ig_db.ProjectStage ps
			where	ps.ProjectId = vProjectIdToBeCloned;
		else 
			-- Add Default Project Single Stage
   			insert into ig_db.ProjectStage
				(ProjectId, OrderNumber, StageName, Description, StartDate, EndDate, ProjectStageIdClonedFrom, LastUpdateUserName, LastUpdateTimestamp)
			values 
				(pProjectIdCloned, 1,  "Stage 1 - Single Stage" , "Default Single Stage", vStartDate, vCompletionDate, null, pUserId, current_timestamp());
		end if;
		-- -----------------------------------------------------------------------------------
			
		-- -----------------------------------------------------------------------------------
		if vCloneLevel > 1 then			-- Add ProjectSds

   			insert into ig_db.ProjectSd
				(ProjectId, ServiceDisciplineId, ProjectSdIdClonedFrom, LastUpdateUserName, LastUpdateTimestamp)
			select 
				pProjectIdCloned, psd.ServiceDisciplineId, psd.ProjectSdId, pUserId ,current_timestamp()
			from 	ig_db.ProjectSd psd
			where	psd.ProjectId = vProjectIdToBeCloned;

			insert into ig_db.ProjectSdStage
				(ProjectSdId, ProjectStageId, ProjectSdStageIdClonedFrom, LastUpdateUserName, LastUpdateTimestamp)
            select 
            	psd.ProjectSdId, ps.ProjectStageId, psds.ProjectSdStageId, pUserId ,current_timestamp()
            from 
				ProjectSdStage psds
                join ProjectStage ps on (ps.ProjectStageIdClonedFrom = psds.ProjectStageId)
                join ProjectSd psd on (psd.ProjectSdIdClonedFrom = psds.ProjectSdId)
			where	psd.ProjectId = pProjectIdCloned
				and ps.ProjectId = pProjectIdCloned;
                
        end if;
		-- -----------------------------------------------------------------------------------
        
		-- -----------------------------------------------------------------------------------
		if vCloneLevel > 2 then			-- Add ProjectParticipants

            -- Insert ProjectParticipants to add clones (but not parent participants yet)
			insert into ProjectParticipant 
				(ProjectId, ParticipantId, ProjectParticipantIdAboveMe, Description, StartDate, EndDate, ProjectParticipantIdClonedFrom, LastUpdateUserName, LastUpdateTimestamp)
			select 
                pProjectIdCloned, pp.ParticipantId, pp.ProjectParticipantId, pp.Description, pp.StartDate, pp.EndDate, pp.ProjectParticipantId, pUserId, current_timestamp()
			from ProjectParticipant pp
			where	pp.ProjectId = vProjectIdToBeCloned;

			CREATE TEMPORARY TABLE tempPPAbove(
				OrigProjectId bigint, 
				OrigPPId bigint, 
				ClonedProjectId bigint, 
				ClonedPPId bigint, 
				ClonedFromPPId bigint,  
				OrigPPIdAboveMe bigint,
				ClonedPPIdAboveMe bigint
				);
				
				INSERT INTO tempPPAbove
					(
					OrigProjectId, 
					OrigPPId, 
					ClonedProjectId, 
					ClonedPPId, 
					ClonedFromPPId,  
					OrigPPIdAboveMe,
					ClonedPPIdAboveMe
					)
				select  ppo.ProjectId, 
						ppo.ProjectParticipantId, 
						ppc.ProjectId, 
						ppc.ProjectParticipantId, 
						ppc.ProjectParticipantIdAboveMe,
						ppo.ProjectParticipantIdAboveMe,
						null
				from
						ProjectParticipant ppo
						join ProjectParticipant ppc on ppo.ProjectParticipantId = ppc.ProjectParticipantIdAboveMe
				where 	ppo.ProjectId = vProjectIdToBeCloned
				and 	ppc.ProjectId = pProjectIdCloned;

				CREATE TEMPORARY TABLE temp1 
				AS SELECT * FROM tempPPAbove;
				
				update  tempPPAbove tp set tp.clonedPPIdAboveMe =
				( 	select temp1.ClonedPPId
					from 	temp1
					where 	temp1.ClonedFromPPId = tp.OrigPPIdAboveMe
				);

				-- Now update the parent ProjectParticipant
				call updatePPAboveMe();

 			drop temporary table tempPPAbove;
 			drop temporary table temp1;

        end if;
		-- -----------------------------------------------------------------------------------

		-- -----------------------------------------------------------------------------------
	 	if vCloneLevel > 3 then			-- Add ProjectParticipantSdRole and PpSdRoleStage

			insert into ProjectParticipantSdRole
				(ProjectParticipantId, ProjectSdId, SdRoleId, ProjectParticipantSdRoleIdClonedFrom, LastUpdateUserName, LastUpdateTimestamp)
				select 
					pp.ProjectParticipantId, psd.ProjectSdId, ppsdr.SdRoleId, ppsdr.ProjectParticipantSdRoleId, pUserId, current_timestamp()
				from
					ProjectParticipantSdRole ppsdr
					join ProjectParticipant pp on (pp.ProjectParticipantIdClonedFrom = ppsdr.ProjectParticipantId)
					join ProjectSd psd on (psd.ProjectSdIdClonedFrom = ppsdr.ProjectSdId)
			where	pp.ProjectId = pProjectIdCloned
					and psd.ProjectId = pProjectIdCloned;
    
			insert into PpSdRoleStage
					(ProjectParticipantSdRoleId, ProjectSdStageId, PpSdRoleStageIdClonedFrom, LastUpdateUserName, LastUpdateTimestamp)
                select
					ppsdr.ProjectParticipantSdRoleId, psds.ProjectSdStageId, ppsdrs.PpSdRoleStageId, pUserId, current_timestamp()
                from
					PpSdRoleStage ppsdrs
					join ProjectParticipantSdRole ppsdr on (ppsdr.ProjectParticipantSdRoleIdClonedFrom = ppsdrs.ProjectParticipantSdRoleId)
                    join ProjectSdStage psds on (psds.ProjectSdStageIdClonedFrom = ppsdrs.ProjectSdStageId)
                    join ProjectStage ps on (psds.ProjectStageId = ps.ProjectStageId)
                    join ProjectParticipant pp on (pp.ProjectParticipantId = ppsdr.ProjectParticipantId)
					join ProjectSd psd on (psd.ProjectSdId = ppsdr.ProjectSdId)
			where	pp.ProjectId = pProjectIdCloned
					and ps.ProjectId = pProjectIdCloned
					and psd.ProjectId = pProjectIdCloned;
            
        end if;
		-- -----------------------------------------------------------------------------------

		-- -----------------------------------------------------------------------------------
	 	if vCloneLevel > 4 then			-- Add AgreementBetweenParticipants and Recoverable Expenses

			insert into ig_db.AgreementBetweenParticipants
				(	RemunerationModelCode, ProjectParticipantId, AgreementBudget, Description, 
					FSPreSplitContractingExpDeduct, FSPreSplitContractingThirdPDeduct, FSContractedExpensesAdded, 
                    FSPreSplitOtherPartInvoices, AgreementBetweenParticipantsIdClonedFrom, LastUpdateUserName, LastUpdateTimestamp)
				select 
					abp.RemunerationModelCode, pp.ProjectParticipantId, abp.AgreementBudget, abp.Description, 
                    abp.FSPreSplitContractingExpDeduct, abp.FSPreSplitContractingThirdPDeduct, abp.FSContractedExpensesAdded, 
                    abp.FSPreSplitOtherPartInvoices, abp.AgreementBetweenParticipantsId, pUserId ,current_timestamp()
            from 
				AgreementBetweenParticipants abp
                join ProjectParticipant pp on (pp.ProjectParticipantIdClonedFrom = abp.ProjectParticipantId)
			where	pp.ProjectId = pProjectIdCloned;

			insert into ig_db.RecoverableExpense
				(	AgreementBetweenParticipantsId, ExpenseTypeId, Description, ExpenseBudget, RecoverableExpenseIdClonedFrom, 
					LastUpdateUserName, LastUpdateTimestamp)
				select 
					abp.AgreementBetweenParticipantsId, re.ExpenseTypeId, re.Description, re.ExpenseBudget, re.RecoverableExpenseId, 
                    pUserId ,current_timestamp()
            from 
				RecoverableExpense re
				join AgreementBetweenParticipants abp on (abp.AgreementBetweenParticipantsIdClonedFrom = re.AgreementBetweenParticipantsId)
                join ProjectParticipant pp 	on (pp.ProjectParticipantId = abp.ProjectParticipantId)
			where	pp.ProjectId = pProjectIdCloned;

        end if;
		-- -----------------------------------------------------------------------------------

		-- -----------------------------------------------------------------------------------
	 	if vCloneLevel > 5 then			-- Add RemunerationRateUpline and ExpenseRate
			insert into ig_db.RemunerationRateUpline
				(	AgreementBetweenParticipantsId, ParticipantIdIndividual, ProjectParticipantSdRoleIdForRate, 
					ProjBasedRemunTypeId, RatePerUnit, Description, StartDate, EndDate, RemunerationRateUplineIdClonedFrom, 
                    LastUpdateUserName, LastUpdateTimestamp)
				select 
					abp.AgreementBetweenParticipantsId, rru.ParticipantIdIndividual, ppsdr.ProjectParticipantSdRoleId, 
					rru.ProjBasedRemunTypeId, rru.RatePerUnit, rru.Description, rru.StartDate, rru.EndDate, rru.RemunerationRateUplineId, 
                    pUserId ,current_timestamp()
            from 
				RemunerationRateUpline rru
				join AgreementBetweenParticipants abp on (abp.AgreementBetweenParticipantsIdClonedFrom = rru.AgreementBetweenParticipantsId)
				join ProjectParticipantSdRole ppsdr on (ppsdr.ProjectParticipantSdRoleIdClonedFrom = rru.ProjectParticipantSdRoleIdForRate)
                join ProjectParticipant pp 	on (pp.ProjectParticipantId = abp.ProjectParticipantId)
                join ProjectParticipant ppppsdr on (ppppsdr.ProjectParticipantId = ppsdr.ProjectParticipantId)
			where	pp.ProjectId = pProjectIdCloned
				and	ppppsdr.ProjectId = pProjectIdCloned;

			insert into ig_db.ExpenseRate
				(	RecoverableExpenseId, ExpenseRatePerUnit, ExpenseHandlingPerc, MaxExpenseAmtPerUnit, 
					Description, StartDate, EndDate, ExpenseRateIdClonedFrom, LastUpdateUserName, LastUpdateTimestamp)
				select 
					re.RecoverableExpenseId, er.ExpenseRatePerUnit, er.ExpenseHandlingPerc, er.MaxExpenseAmtPerUnit, 
					er.Description, er.StartDate, er.EndDate, er.ExpenseRateId, pUserId ,current_timestamp()
            from 
				ExpenseRate er
				join RecoverableExpense re on (re.RecoverableExpenseIdClonedFrom = er.RecoverableExpenseId)
				join AgreementBetweenParticipants abp on (abp.AgreementBetweenParticipantsIdClonedFrom = re.AgreementBetweenParticipantsId)
                join ProjectParticipant pp 	on (pp.ProjectParticipantId = abp.ProjectParticipantId)
			where	
				pp.ProjectId = pProjectIdCloned;

         end if;
		-- -----------------------------------------------------------------------------------
            
	commit;
END