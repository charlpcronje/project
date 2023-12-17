CREATE DEFINER=`administrator`@`localhost` PROCEDURE `deleteProjectExpenseFile`(IN `pProjectExpenseFileId` BIGINT)
BEGIN


    DECLARE exit handler for sqlexception
    BEGIN
        rollback;  -- rollback any changes made in the transaction
        resignal;  -- raise again the sql exception to the caller
    END;

 

    START TRANSACTION;
        DELETE FROM ig_db.ProjectExpenseFile WHERE ProjectExpenseFileId = pProjectExpenseFileId;
    COMMIT;

END