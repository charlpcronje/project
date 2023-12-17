CREATE DEFINER=`root`@`localhost` PROCEDURE `doInsertBankStatement`(p1 BIGINT, p2 VARCHAR(255), p3 VARCHAR(255), p4 VARCHAR(255), p5 VARCHAR(255), p6 VARCHAR(255), p7 VARCHAR(255))
BEGIN
    declare vErrorMessage varchar(100);
    declare vAssignmentId BIGINT;
	
    DECLARE done INT DEFAULT FALSE;

	declare exit handler for sqlexception    
    begin
        rollback;  -- rollback any changes made in the transaction
        resignal;  -- raise again the sql exception to the caller
    end;
    
	start transaction;

 
			INSERT BankStatement (ParticipantBankDetailsId, Description, StatementNumber, StatementDate, TransactionDateFrom, TransactionDateTo)
			values (p1, p2, p3, p4, p5, p6, LOAD_FILE(p7));

   
	commit;
END