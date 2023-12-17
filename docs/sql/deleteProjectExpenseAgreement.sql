CREATE DEFINER=`administrator`@`localhost` PROCEDURE `deleteProjectExpenseAgreement`(IN `pProjectExpenseAgreementId` BIGINT)
    NO SQL
BEGIN
    -- Source: https://stackoverflow.com/questions/19905900/mysql-transaction-roll-back-on-any-exception
    declare vCount integer;
    declare vErrorMessage varchar(100);
    
	declare exit handler for sqlexception
    begin
        rollback;  -- rollback any changes made in the transaction
        resignal;  -- raise again the sql exception to the caller
    end;

	-- Check if there are any ProjextExpenses linked to this ProjectExpenseAgreement
    select count(*) into vCount from ig_db.ProjectExpense
    where ProjectExpenseAgreementId = pProjectExpenseAgreementId;
    if (vCount > 0 ) then 
		signal sqlstate '45000'
		set message_text = 'Can not delete Project Expense Agreement.  It has associated Project Expense.';
	end if;

	start transaction;
    
	delete from ig_db.ProjectExpenseAgreement where ProjectExpenseAgreementId = pProjectExpenseAgreementId;
	commit;
END