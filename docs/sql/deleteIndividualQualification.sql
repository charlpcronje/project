CREATE DEFINER=`administrator`@`localhost` PROCEDURE `deleteIndividualQualification`(IN `pIndividualQualificationId` BIGINT, IN pUserId VARCHAR(50))
BEGIN

	
    DECLARE vCount integer;
    DECLARE vErrorMessage varchar(100);



    DECLARE exit handler for sqlexception
    BEGIN
        rollback;  -- rollback any changes made in the transaction
        resignal;  -- raise again the sql exception to the caller
    END;



    START TRANSACTION;
        DELETE FROM ig_db.IndividualQualification WHERE IndividualQualificationId = pIndividualQualificationId;

    COMMIT;

END