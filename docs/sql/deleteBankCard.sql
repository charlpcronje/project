CREATE DEFINER=administrator@localhost PROCEDURE deleteBankCard(pBankCardId BIGINT)
BEGIN
    declare vCount integer;
    declare vErrorMessage varchar(100);
	declare exit handler for sqlexception
    begin
        rollback;  -- rollback any changes made in the transaction
        resignal;  -- raise again the sql exception to the caller
    end;
    
  	-- Check if the BankCard has been used in ProjectExpense
    select count(*) into vCount from ig_db.ProjectExpense
    where BankCardIdUsed = pBankCardId;
    if (vCount > 0 ) then 
		signal sqlstate '45000'
		set message_text = 'Can not delete Bank Card.  The Bank Card has been linked to one or more Project Expenses.';
	end if;

	start transaction;
		delete from ig_db.BankCard where BankCardId = pBankCardId;
	commit;

END