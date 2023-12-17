CREATE DEFINER=`administrator`@`localhost` PROCEDURE `deleteIndividualProfRegistration`(IN `pIndividualProfRegistrationId` BIGINT, IN pUserId VARCHAR(50))
BEGIN

	
    DECLARE vCount integer;
    DECLARE vErrorMessage varchar(100);



    DECLARE exit handler for sqlexception
    BEGIN
        rollback;  -- rollback any changes made in the transaction
        resignal;  -- raise again the sql exception to the caller
    END;



    START TRANSACTION;
        DELETE FROM ig_db.IndividualProfRegistration WHERE IndividualProfRegistrationId = pIndividualProfRegistrationId;

    COMMIT;

END