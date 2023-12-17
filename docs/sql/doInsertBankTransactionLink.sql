CREATE DEFINER=`administrator`@`localhost` PROCEDURE `doInsertBankTransactionLink`(IN pParticipantBankDetailsId BIGINT, 
				IN 	pFromDate  	datetime,
				IN 	pToDate  	datetime,
                OUT pLinkedCount BIGINT)
BEGIN
    declare vCount integer;
    declare vErrorMessage varchar(100);
	
    DECLARE done INT DEFAULT FALSE;
	DECLARE cProjectExpenseId bigint 	DEFAULT null;
	DECLARE cPurchaseDate 	datetime 	DEFAULT null;
	DECLARE cNumberOfUnits 	decimal(12,2) DEFAULT null;
	DECLARE cAmountPerUnit 	decimal(20,2) DEFAULT null;
	DECLARE cBankReference 	varchar(60) DEFAULT null;
	
    DECLARE btBankTransactionId bigint  DEFAULT null;
	
	DECLARE team_cursor CURSOR FOR
		select 	ProjectExpenseId, PurchaseDate, NumberOfUnits, AmountPerUnit, BankReference   
		from 	projectExpense 
		where 	ParticipantBankDetailsIdUsed = pParticipantBankDetailsId
		and   	FullyLinked <> "Y"
		AND		PurchaseDate >= pFromDate
		AND		PurchaseDate <= pToDate;
		
	DECLARE CONTINUE HANDLER FOR NOT FOUND SET done = TRUE;
    
	declare exit handler for sqlexception    
    begin
        rollback;  -- rollback any changes made in the transaction
        resignal;  -- raise again the sql exception to the caller
    end;
	
	set pLinkedCount = 0;
    
	start transaction;
		
			OPEN team_cursor;
			teams_loop:
			LOOP
				FETCH team_cursor INTO cProjectExpenseId, cPurchaseDate, cNumberOfUnits, cAmountPerUnit, cBankReference;
				IF done THEN 
					LEAVE teams_loop;
				END IF;

				SELECT COUNT(*) INTO vCount FROM BankTransaction q 
				WHERE 	q.ParticipantBankDetailsId = pParticipantBankDetailsId
				AND		ABS(q.Amount) = (cNumberOfUnits * cAmountPerUnit)
				AND		DateDiff(q.TransactionDate, cPurchaseDate) >= 0
				AND		DateDiff(q.TransactionDate, cPurchaseDate) <= 6;
				
				IF vCount = 1 then 
				
					SELECT 	BankTransactionId INTO btBankTransactionId FROM BankTransaction q 
					WHERE 	q.ParticipantBankDetailsId = pParticipantBankDetailsId
					AND		ABS(q.Amount) = (cNumberOfUnits * cAmountPerUnit)
					AND		DateDiff(q.TransactionDate, cPurchaseDate) >= 0
					AND		DateDiff(q.TransactionDate, cPurchaseDate) <= 6;
				
					INSERT	INTO  BankTransactionLink  (BankTransactionId,    ProjectExpenseId, LinkAmount,  AutomaticCursorLinkCreated, InvoiceId)
												VALUES (btBankTransactionId, cProjectExpenseId, (cNumberOfUnits * cAmountPerUnit), (SELECT CURRENT_TIMESTAMP()), null);
					UPDATE  BankTransaction
					SET		FullyLinked = "Y"
					WHERE	BankTransactionId = btBankTransactionId;
					
					UPDATE  ProjectExpense
					SET		FullyLinked = "Y"
					WHERE	ProjectExpenseId = cProjectExpenseId;
				
					SET pLinkedCount := pLinkedCount + 1;
				
				END IF;
            
			END LOOP teams_loop;
			
			CLOSE team_cursor;
  -- SET pLinkedCount = 22;   -- net om te toets.
	commit;

END