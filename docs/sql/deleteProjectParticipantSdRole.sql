CREATE DEFINER=`administrator`@`localhost` PROCEDURE `deleteProjectParticipantSdRole`(IN pProjectParticipantSdRoleId BIGINT)
    NO SQL
BEGIN
    -- Source: https://stackoverflow.com/questions/19905900/mysql-transaction-roll-back-on-any-exception
    declare vCount integer;
    declare vErrorMessage varchar(100);
    
	declare exit handler for sqlexception
    begin
        rollback;  -- rollback any changes made in the transaction
        resignal;  -- raise again the sql exception to the caller
    end;

 	-- Check if the ProjectParticipantSdRole has Stages linked to it
    select count(*) into vCount from PpSdRoleStage
    where ProjectParticipantSdRoleId = pProjectParticipantSdRoleId;
    if (vCount > 0 ) then 
		signal sqlstate '45000'
		set message_text = 'Can not delete Participant Service Discipline and Role.  There are Stages linked to it.';
	end if;

	-- Check if the ProjectParticipantSdRole has Remuneration Rates linked to it
    select count(*) into vCount from RemunerationRateUpline
    where ProjectParticipantSdRoleIdForRate = pProjectParticipantSdRoleId;
    if (vCount > 0 ) then 
		signal sqlstate '45000'
		set message_text = 'Can not delete Participant Service Discipline and Role.  There are Remuneration Rates linked to it.';
	end if;

	-- Check if the ProjectParticipantSdRole has Timesheet entries linked to it
    select count(*) into vCount from Timesheet
    where ProjectParticipantSdRoleId = pProjectParticipantSdRoleId;
    if (vCount > 0 ) then 
		signal sqlstate '45000'
		set message_text = 'Can not delete Participant Service Discipline and Role.  There are Timesheet Entries linked to it.';
	end if;

	start transaction;
	delete from ig_db.ProjectParticipantSdRole where ProjectParticipantSdRoleId = pProjectParticipantSdRoleId;
	commit;
END