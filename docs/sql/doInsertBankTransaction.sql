CREATE DEFINER=administrator@localhost PROCEDURE doInsertBankTransaction(pParticipantBankDetailsId BIGINT, pDescription1 VARCHAR(255), pTransactionDate  DATETIME, pAmount DECIMAL(20,2))
BEGIN
    declare vCount integer;
    declare vErrorMessage varchar(100);
    declare vAssignmentId BIGINT;
	
    DECLARE done INT DEFAULT FALSE;

	declare exit handler for sqlexception    
    begin
        rollback;  -- rollback any changes made in the transaction
        resignal;  -- raise again the sql exception to the caller
    end;
    
	start transaction;

        select count(*) into vCount from BankTransaction b 
		where b.ParticipantBankDetailsId = pParticipantBankDetailsId
		and  b.Description1 = pDescription1
		and  b.TransactionDate = pTransactionDate
		and  b.Amount = pAmount;
    
		IF vCount < 1 THEN 
			INSERT BankTransaction (ParticipantBankDetailsId, Description1, TransactionDate, Amount)
			values (pParticipantBankDetailsId, pDescription1, pTransactionDate, pAmount);
		END IF;
   
	commit;

END