CREATE DEFINER=`administrator`@`localhost` PROCEDURE `deleteParticipantBankDetails`(`pParticipantBankDetailsId` BIGINT)
BEGIN
    declare vCount integer;
    declare vErrorMessage varchar(100);
	declare exit handler for sqlexception
    begin
        rollback;  -- rollback any changes made in the transaction
        resignal;  -- raise again the sql exception to the caller
    end;
    
  	-- Check if the ParticipantBankDetailsId is set as the default Bank Details for the Particpant
    select count(*) into vCount from ig_db.Participant
    where ParticipantBankDetailsIdDefault = pParticipantBankDetailsId;
    if (vCount > 0 ) then 
		signal sqlstate '45000'
		set message_text = 'Can not delete Participant Bank Details.  Is is set as the Default Bank Details for the Participant';
	end if;
	
	start transaction;
		delete from ig_db.ParticipantBankDetails where ParticipantBankDetailsId = pParticipantBankDetailsId;
	commit;

END