CREATE DEFINER=`administrator`@`localhost` PROCEDURE `deleteExpenseType`(`pExpenseTypeId` BIGINT)
BEGIN
    declare vCount integer;
    declare vErrorMessage varchar(100);
	declare exit handler for sqlexception
    begin
        rollback;  -- rollback any changes made in the transaction
        resignal;  -- raise again the sql exception to the caller
    end;
    
  	-- Check if the ExpenseTypeId is linked to a Project Agreement
    select count(*) into vCount from ig_db.recoverableExpense
    where ExpenseTypeId = pExpenseTypeId;
    if (vCount > 0 ) then 
		signal sqlstate '45000'
		set message_text = 'Can not delete Expense Type.  The Expense Type is used in the Recoverable Expense Table';
	end if;

	start transaction;
		delete from ig_db.ExpenseType where ExpenseTypeId = pExpenseTypeId;
	commit;

END