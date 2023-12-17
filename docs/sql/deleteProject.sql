CREATE DEFINER=`root`@`localhost` PROCEDURE `deleteProject`(pProjectId bigint)
BEGIN
    -- Source: https://stackoverflow.com/questions/19905900/mysql-transaction-roll-back-on-any-exception
    declare vCount integer;
    declare vErrorMessage varchar(100);
    declare vProjectIdParent bigint;
    declare vChildrenCount integer;
    declare vMessage varchar(100);
    
	declare exit handler for sqlexception
    begin
        rollback;  -- rollback any changes made in the transaction
        resignal;  -- raise again the sql exception to the caller
    end;

    select 	ProjectIdParent into vProjectIdParent 
    from 	ig_db.Project
    where	ProjectId = pProjectId;

	-- Check if there are any Timesheet entries linked to this Project
    select 	count(t.TimesheetId) into vCount 
    from 	Timesheet t
			join ProjectParticipantSdRole ppsdr on ppsdr.ProjectParticipantSdRoleId = t.ProjectParticipantSdRoleId
            join ProjectParticipant pp on pp.ProjectParticipantId = ppsdr.ProjectParticipantId
    where 	pp.ProjectId = pProjectId;
    if (vCount > 0 ) then 
		signal sqlstate '45000'
		set message_text = 'Can not delete Project.  The Project has Timesheet entries linked to it.' ;
	end if;

	-- Check if there are any Project Expense entries linked to this Project
    select 	count(pe.ProjectExpenseId) into vCount 
    from 	ProjectExpense pe
            join ProjectParticipant pp on pe.ProjectParticipantIdPayer = pp.ProjectParticipantId
    where 	pp.ProjectId = pProjectId;
    if (vCount > 0 ) then 
		signal sqlstate '45000'
		set message_text = 'Can not delete Project.  The Project has Project Expense entries linked to it.';
	end if;

	-- Check if there are any Remuneration Rate entries linked to this Project
    select 	count(rru.RemunerationRateUplineId) into vCount 
    from 	RemunerationRateUpline rru
            join AgreementBetweenParticipants abp on rru.AgreementBetweenParticipantsId = abp.AgreementBetweenParticipantsId
            join ProjectParticipant pp on abp.ProjectParticipantId = pp.ProjectParticipantId
    where 	pp.ProjectId = pProjectId;
    if (vCount > 0 ) then 
		signal sqlstate '45000'
		set message_text = 'Can not delete Project.  The Project has Remuneration Rates setup.';
	end if;

	-- Check if there are any Expense Rate entries linked to this Project
    select 	count(er.ExpenseRateId) into vCount 
    from 	ExpenseRate er
			join RecoverableExpense re on er.RecoverableExpenseId = re.RecoverableExpenseId
            join AgreementBetweenParticipants abp on re.AgreementBetweenParticipantsId = abp.AgreementBetweenParticipantsId
            join ProjectParticipant pp on abp.ProjectParticipantId = pp.ProjectParticipantId
    where 	pp.ProjectId = pProjectId;
    if (vCount > 0 ) then 
		signal sqlstate '45000'
		set message_text = 'Can not delete Project.  The Project has Recoverable Expense Rates setup.';
	end if;

	-- Check if there are any ProjectParticipants linked to this Project

	-- Check if this Project is the only child of the ParentProject
    select 	count(*) into vChildrenCount 
    from 	ig_db.Project
    where 	ProjectIdParent = vProjectIdParent;

	start transaction;
		update ig_db.Project set ProjectParticipantIdLevel1 = null,	ProjectIdParent = null where ProjectId = pProjectId;
        
        if (vChildrenCount = 1) then -- delete the Parent Project
			update ig_db.Project set ProjectParticipantIdLevel1 = null, ProjectIdParent = null	where ProjectId = vProjectIdParent;
            
            update Participant set ProjectIdSustenance = null where ProjectIdSustenance = vProjectIdParent;

			delete from ig_db.ProjectStage where projectId = vProjectIdParent;
			delete from ig_db.ProjectParticipant where projectId = vProjectIdParent;
			delete from ig_db.Project where projectId = vProjectIdParent;
		end if;
        
		 update ProjectParticipant set ProjectParticipantIdAboveMe = null where Projectid = pProjectId;

		 -- delete PpSdRoleStage 1
		 delete ppsdrs.*
		 from 	PpSdRoleStage ppsdrs
		 join	ProjectParticipantSdRole ppsdr on (ppsdrs.ProjectParticipantSdRoleId = ppsdr.ProjectParticipantSdRoleId)
		 join	ProjectParticipant pp on (ppsdr.ProjectParticipantId = pp.ProjectParticipantId)
		 where 	pp.ProjectId = pProjectId;


		 -- delete ProjectSdStage 2
		 delete psds.*
		 from 	ProjectSdStage psds
		 join 	ProjectSd psd on (psds.ProjectSdId = psd.ProjectSdId)
		 where  psd.ProjectId = pProjectId;

		-- delete ExpenseRateUpline 4
		delete 	er.*
		from 	ExpenseRate er
				join RecoverableExpense re on (re.RecoverableExpenseId = er.RecoverableExpenseId)
				join AgreementBetweenParticipants abp on (re.AgreementBetweenParticipantsId = abp.AgreementBetweenParticipantsId)
				join ProjectParticipant pp on (abp.ProjectParticipantId = pp.ProjectParticipantId)
		where   pp.ProjectId = pProjectId;

		-- delete RemunerationRateUpline 3
		delete 	rru.*
		from 	RemunerationRateUpline rru
				join AgreementBetweenParticipants abp on (rru.AgreementBetweenParticipantsId = abp.AgreementBetweenParticipantsId)
				join ProjectParticipant pp on (abp.ProjectParticipantId = pp.ProjectParticipantId)
		where   pp.ProjectId = pProjectId;

		 -- delete ProjectParticipantSdRole 5

		 delete ppsdr.*
		 from 	ProjectParticipantSdRole ppsdr
		 join	ProjectParticipant pp on (ppsdr.ProjectParticipantId = pp.ProjectParticipantId)
		 where 	pp.ProjectId = pProjectId;

		-- delete RecoverableExpense 6
        
		delete 	re.*
        
		from 	RecoverableExpense re
				join AgreementBetweenParticipants abp on (re.AgreementBetweenParticipantsId = abp.AgreementBetweenParticipantsId)
				join ProjectParticipant pp on (abp.ProjectParticipantId = pp.ProjectParticipantId)
		where   pp.ProjectId = pProjectId;

		-- delete AgreementBetweenParticipants 7
		delete 	abp.*
		from 	AgreementBetweenParticipants abp
				join ProjectParticipant pp on (abp.ProjectParticipantId = pp.ProjectParticipantId)
		where   pp.ProjectId = pProjectId;

		-- delete ProjectParticipant 8
		delete from ProjectParticipant where ProjectId = pProjectId;

		-- delete ProjectStage 9
		 delete from ProjectStage where ProjectId = pProjectId;
		 
		 -- delete ProjectSd 10
		 delete from ProjectSd where ProjectId = pProjectId;
		 
		-- delete Project 11
		 delete from Project where ProjectId = pProjectId;
        
	commit;
END