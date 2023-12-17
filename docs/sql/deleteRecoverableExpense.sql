CREATE DEFINER=`administrator`@`localhost` PROCEDURE `deleteRecoverableExpense`(`pRecoverableExpenseId` BIGINT)
BEGIN
    declare vCount integer;
    declare vErrorMessage varchar(100);
	declare exit handler for sqlexception
    begin
        rollback;  -- rollback any changes made in the transaction
        resignal;  -- raise again the sql exception to the caller
    end;
    
  	-- Check if the ExpenseAgreement has Project Expenses linked to it
    -- select count(*) into vCount from ig_db.ProjectExpense
    -- where ExpenseAgreementId = pExpenseAgreementId;
    -- if (vCount > 0 ) then 
	-- 	signal sqlstate '45000'
	-- 	set message_text = 'Can not delete Expense Agreement.  There are Project Expenses linked to it.';
	-- end if;

	start transaction;
		delete from ig_db.RecoverableExpense where RecoverableExpenseId = pRecoverableExpenseId;
	commit;

END