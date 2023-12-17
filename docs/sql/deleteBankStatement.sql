CREATE DEFINER=`administrator`@`localhost` PROCEDURE `deleteBankStatement`(IN `pBankStatementId` BIGINT)
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

	-- Check if there are any BankTransaction' linked to this BankStatement
    select count(*) into vCount from ig_db.BankTransaction
    where BankStatementId = pBankStatementId;

    if (vCount > 0 ) then 
		signal sqlstate '45000'
		set message_text = 'Can not delete Bank Statement.  There are Bank Transactions linked to it.';
	end if;

	start transaction;
	delete from ig_db.BankStatement where BankStatementId = pBankStatementId;
	commit;
END