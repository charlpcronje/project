CREATE DEFINER=`administrator`@`localhost` PROCEDURE `deleteExpenseTypeParent`(`pExpenseTypeParentId` BIGINT)
BEGIN
    declare vCount integer;
    declare vErrorMessage varchar(100);
	declare exit handler for sqlexception
    begin
        rollback;  -- rollback any changes made in the transaction
        resignal;  -- raise again the sql exception to the caller
    end;
    
  	-- Check if the ExpenseTypeParent has any Children
    select count(*) into vCount from ig_db.ExpenseType
    where ExpenseTypeParentId = pExpenseTypeParentId;
    if (vCount > 0 ) then 
		signal sqlstate '45000'
		set message_text = 'Can not delete Expense Type Parent.  It has one or more Expense Type Children linked to it.';
	end if;

	start transaction;
		delete from ig_db.ExpenseTypeParent where ExpenseTypeParentId = pExpenseTypeParentId;
	commit;
  
  
END