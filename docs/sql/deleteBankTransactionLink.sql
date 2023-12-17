CREATE DEFINER=`administrator`@`localhost` PROCEDURE `deleteBankTransactionLink`(pBankTransactionLinkId BigInt)
BEGIN
    declare vCount integer;
    declare vErrorMessage varchar(100);
	declare vBankTransactionId bigint;
	declare vProjectExpenseId  bigint;
	
	declare exit handler for sqlexception
    begin
        rollback;  -- rollback any changes made in the transaction
        resignal;  -- raise again the sql exception to the caller
    end;
    

	start transaction;
	
		SELECT 	BankTransactionId into vBankTransactionId 
		FROM 	BankTransactionLink 
		WHERE	BankTransactionLinkId = pBankTransactionLinkId;

		SELECT 	ProjectExpenseId into  vProjectExpenseId  
		FROM 	BankTransactionLink 
		WHERE	BankTransactionLinkId = pBankTransactionLinkId;
		
		UPDATE	BankTransaction
		SET		FullyLinked = "N"
		WHERE	BankTransactionId = vBankTransactionId;

		UPDATE	ProjectExpense
		SET		FullyLinked = "N"
		WHERE	ProjectExpenseId = vProjectExpenseId;

		
		delete from ig_db.BankTransactionLink where BankTransactionLinkId = pBankTransactionLinkId;
	commit;

END