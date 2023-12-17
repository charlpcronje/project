CREATE DEFINER=`administrator`@`localhost` PROCEDURE `deleteParticipantStatus`(`pParticipantStatusId` BIGINT)
BEGIN
    declare vCount integer;
    declare vErrorMessage varchar(100);
	declare exit handler for sqlexception
    begin
        rollback;  -- rollback any changes made in the transaction
        resignal;  -- raise again the sql exception to the caller
    end;
    
  	-- Check if the ParticipantStatus has been linked to Participants
    select count(*) into vCount from ig_db.Participant
    where ParticipantStatusId = pParticipantStatusId;
    if (vCount > 0 ) then 
		signal sqlstate '45000'
		set message_text = 'Can not delete Participant Status.  It has one or more Participants linked to it';
	end if;
	
	start transaction;
		delete from ig_db.ParticipantStatus where ParticipantStatusId = pParticipantStatusId;
	commit;


END