CREATE DEFINER=`administrator`@`localhost` PROCEDURE `deleteInstituteQualification`(IN `pInstituteQualificationId` BIGINT, IN pUserId VARCHAR(50))
BEGIN

	
    DECLARE vCount integer;
    DECLARE vErrorMessage varchar(100);



    DECLARE exit handler for sqlexception
    BEGIN
        rollback;  -- rollback any changes made in the transaction
        resignal;  -- raise again the sql exception to the caller
    END;



    -- Check of daar rekords in IndividualQualification is wat wys na InstituteQualification
    SELECT count(*) into vCount from ig_db.IndividualQualification
    WHERE InstituteQualificationId = pInstituteQualificationId;
    IF (vCount > 0) Then
        signal sqlstate '45000'
        set message_text = 'Cannot delete this Institute Qualification. The Institute Qualification has Individuals linked to it. ';
    END IF;

    START TRANSACTION;
        DELETE FROM ig_db.InstituteQualification WHERE InstituteQualificationId = pInstituteQualificationId;

    COMMIT;

END