CREATE DEFINER=administrator@localhost PROCEDURE doLinkTransactionsToStatement(IN `pQuery` JSON, OUT pCount INT)
BEGIN
    declare pBankStatementId BIGINT;
    declare pParticipantBankDetailsId BIGINT;
    declare vFromDate DATETIME;
    declare vToDate DATETIME;
    declare vErrorMessage varchar(100);

    declare exit handler for sqlexception    


    	begin
        	rollback;  -- rollback any changes made in the transaction
        	resignal;  -- raise again the sql exception to the caller
    	end;
    
	start transaction;

    set pBankStatementId = JSON_UNQUOTE(JSON_EXTRACT(pQuery, '$.bankStatementId'));
    set pParticipantBankDetailsId = JSON_UNQUOTE(JSON_EXTRACT(pQuery, '$.participantBankDetailsId'));

	select TransactionDateFrom, TransactionDateTo 
		into vFromDate, vToDate 
		from BankStatement b
		where BankStatementId = pBankStatementId;

	update BankTransaction bt
		set BankStatementId = pBankStatementId
		where bt.TransactionDate >= vFromDate
		and   bt.TransactionDate <= vToDate
		and   bt.ParticipantBankDetailsId = pParticipantBankDetailsId;

	SELECT COUNT(BankTransactionId)
		INTO pCount 
		FROM BankTransaction btt
		WHERE btt.BankStatementId = pBankStatementId;

   
	commit;

END