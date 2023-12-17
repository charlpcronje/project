CREATE DEFINER=`administrator`@`localhost` PROCEDURE `deleteBankAccountType`(`pBankAccountTypeId` BIGINT)
BEGIN
    declare vCount integer;
    declare vErrorMessage varchar(100);
	declare exit handler for sqlexception
    begin
        rollback;  -- rollback any changes made in the transaction
        resignal;  -- raise again the sql exception to the caller
    end;
    
  	-- Check if the BankAccountType has been used in ParticipantBankDetails
    select count(*) into vCount from ig_db.ParticipantBankDetails
    where BankAccountTypeId = pBankAccountTypeId;
    if (vCount > 0 ) then 
		signal sqlstate '45000'
		set message_text = 'Can not delete Bank Account Type.  The Bank Account Type has been linked to Participants Bank Details.';
	end if;

	start transaction;
		delete from ig_db.BankAccountType where BankAccountTypeId = pBankAccountTypeId;
	commit;

END