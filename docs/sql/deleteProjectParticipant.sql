CREATE DEFINER=`administrator`@`localhost` PROCEDURE `deleteProjectParticipant`(IN `pProjectParticipantId` BIGINT)
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

	-- Check if there are any ProjectParticipant Children linked to this ProjectParticipant
    select count(*) into vCount from ig_db.ProjectParticipant
    where ProjectParticipantIdAboveMe = pProjectParticipantId;
    if (vCount > 0 ) then 
		signal sqlstate '45000'
		set message_text = 'Can not delete Project Participant.  It has associated Project Participant Children.';
	end if;

	-- Check if there are any ProjectSdRoles linked to this ProjectParticipant
    select count(*) into vCount from ig_db.ProjectParticipantSdRole
    where ProjectParticipantId = pProjectParticipantId;
    if (vCount > 0 ) then 
		signal sqlstate '45000'
		set message_text = 'Can not delete Project Participant.  It has associated Project Service Disciplines and Roles.';
	end if;

	-- Check if there are any AgreementBetweenParticipants linked to this ProjectParticipant
    select count(*) into vCount from ig_db.AgreementBetweenParticipants
    where ProjectParticipantId = pProjectParticipantId;
    if (vCount > 0 ) then 
		signal sqlstate '45000'
		set message_text = 'Can not delete Project Participant.  It has associated Agreements between Participants.';
	end if;
	-- Check if there are any ProjectExpense linked to this ProjectParticipant
    select count(*) into vCount from ig_db.ProjectExpense
    where ProjectParticipantIdPayer = pProjectParticipantId;
    if (vCount > 0 ) then 
		signal sqlstate '45000'
		set message_text = 'Can not delete Project Participant.  It has associated Project Expenses.';
	end if;

	-- Check if this is the top level participant of the project (Client)
    select count(*) into vCount from ig_db.Project
    where ProjectParticipantIdLevel1 = pProjectParticipantId;
    if (vCount > 0 ) then 
		signal sqlstate '45000'
		set message_text = 'Can not delete Project Participant.  It is the Top Level Participant of the Project.';
	end if;

	start transaction;
	delete from ig_db.ProjectParticipant where ProjectParticipantId = pProjectParticipantId;
	commit;
END