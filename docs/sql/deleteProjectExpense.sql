CREATE DEFINER=`administrator`@`localhost` PROCEDURE `deleteProjectExpense`(IN `pProjectExpenseId` BIGINT, IN pUserId VARCHAR(50))
BEGIN
	
    DECLARE vCount integer;
    DECLARE vErrorMessage varchar(100);

    DECLARE exit handler for sqlexception
    BEGIN
        rollback;  -- rollback any changes made in the transaction
        resignal;  -- raise again the sql exception to the caller
    END;

    -- Check of daar rekords in BankTransactionLink is wat wys na ProjectExpense
    SELECT count(*) into vCount from ig_db.BankTransactionLink
    WHERE ProjectExpenseId = pProjectExpenseId;
    IF (vCount > 0) Then
        signal sqlstate '45000'
        set message_text = 'Cannot delete this ProjectExpense. The ProjectExpense has records in the BankTransactionLink table linked to it. ';
    END IF;

    -- Check of daar rekords in ProjectExpenseFile is wat wys na ProjectExpense
    SELECT count(*) into vCount from ig_db.ProjectExpenseFile
    WHERE ProjectExpenseId = pProjectExpenseId;
    IF (vCount > 0) Then
        signal sqlstate '45000'
        set message_text = 'Cannot delete this Project Expense. It has files linked to it. ';
    END IF;

    START TRANSACTION;
        DELETE FROM ig_db.ProjectExpense WHERE ProjectExpenseId = pProjectExpenseId;

    COMMIT;

END