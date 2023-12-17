CREATE DEFINER=`root`@`localhost` PROCEDURE `quickDeleteProject`(pProjectId bigint)
BEGIN

 -- Source: https://stackoverflow.com/questions/19905900/mysql-transaction-roll-back-on-any-exception
	declare exit handler for sqlexception
    begin
        rollback;  -- rollback any changes made in the transaction
        resignal;  -- raise again the sql exception to the caller
    end;
  
      start transaction;

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