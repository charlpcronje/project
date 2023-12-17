CREATE DEFINER=`administrator`@`localhost` PROCEDURE `deleteParticipantOffice`(`pParticipantOfficeId` BIGINT)
BEGIN
    declare vCount integer;
    declare vErrorMessage varchar(100);
	declare exit handler for sqlexception
    begin
        rollback;  -- rollback any changes made in the transaction
        resignal;  -- raise again the sql exception to the caller
    end;
    
  	-- Check if the ParticipantOfficeId is set as the default Office for the Particpant
    select count(*) into vCount from ig_db.Participant
    where ParticipantOfficeIdDefault = pParticipantOfficeId;
    if (vCount > 0 ) then 
		signal sqlstate '45000'
		set message_text = 'Can not delete Participant Office.  The Participant Office is set as the Default Office';
	end if;

   -- Check if there are any Contact Points linked to this ParticipantOffice
    select count(*) into vCount from ig_db.ContactPoint
    where ParticipantOfficeId = pParticipantOfficeId;
    if (vCount > 0 ) then 
		signal sqlstate '45000'
		set message_text = 'Can not delete Participant Office.  There are Contact Points linked to the Office';
	end if;
	
	start transaction;
		delete from ig_db.ParticipantOffice where participantOfficeId = pParticipantOfficeId;
	commit;

END