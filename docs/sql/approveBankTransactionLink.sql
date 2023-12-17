CREATE DEFINER=`administrator`@`localhost` PROCEDURE `approveBankTransactionLink`(pBankTransactionLinkId BigInt, pParticipantId Bigint)
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
		
		
		Update BankTransactionLink
		SET 	linkApproved = (SELECT CURRENT_TIMESTAMP()),
				participantIdApprovedLink = pParticipantId
		WHERE	bankTransactionLinkId	= pBankTransactionLinkId;
		
		UPDATE	BankTransaction
		SET		FullyLinked = "Y"
		WHERE	BankTransactionId = vBankTransactionId;

		UPDATE	ProjectExpense
		SET		FullyLinked = "Y"
		WHERE	ProjectExpenseId = vProjectExpenseId;


	commit;

END