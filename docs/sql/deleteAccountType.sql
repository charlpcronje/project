CREATE DEFINER=`administrator`@`localhost` PROCEDURE `deleteAccountType`(pAccountTypeId BIGINT)
BEGIN
    declare vCount integer;
    declare vErrorMessage varchar(100);
	declare exit handler for sqlexception
    begin
        rollback;  -- rollback any changes made in the transaction
        resignal;  -- raise again the sql exception to the caller
    end;
    
  	-- Check if the      has been used in Account
    select count(*) into vCount from ig_db.ParticipantBankDetails
    where AccountTypeId = pAccountTypeId;
    if (vCount > 0 ) then 
		signal sqlstate '45000'
		set message_text = 'Can not delete Account Type.  The Account Type has been linked Participant Bank Detail';
	else 
		start transaction;
			delete from ig_db.AccountType where AccountTypeId = pAccountTypeId;
		commit;
	end if;

END