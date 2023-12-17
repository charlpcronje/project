CREATE DEFINER=`administrator`@`localhost` PROCEDURE `deleteBank`(`pBankId` BIGINT)
BEGIN
    declare vCount integer;
    declare vErrorMessage varchar(100);
	declare exit handler for sqlexception
    begin
        rollback;  -- rollback any changes made in the transaction
        resignal;  -- raise again the sql exception to the caller
    end;
    
  	-- Check if the BankId has Branches
    select count(*) into vCount from ig_db.Branch
    where BankId = pBankId;
    if (vCount > 0 ) then 
		signal sqlstate '45000'
		set message_text = 'Can not delete Bank.  The Bank has Branches linked to it.';
	end if;

    -- Check if there are any ParticipantBankDetails linked to this Bank
    select count(*) into vCount from ig_db.ParticipantBankDetails
    where BankId = pBankId;
    if (vCount > 0 ) then 
		signal sqlstate '45000'
		set message_text = 'Can not delete Bank.  There are Bank Details linked to it.';
	end if;
	
	start transaction;
		delete from ig_db.Bank where BankId = pBankId;
	commit;

END