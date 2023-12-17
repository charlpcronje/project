CREATE DEFINER=`administrator`@`localhost` PROCEDURE `deleteStudyInstitute`(IN `pStudyInstituteId` BIGINT, IN pUserId VARCHAR(50))
BEGIN

	
    DECLARE vCount integer;
    DECLARE vErrorMessage varchar(100);

 

    DECLARE exit handler for sqlexception
    BEGIN
        rollback;  -- rollback any changes made in the transaction
        resignal;  -- raise again the sql exception to the caller
    END;



    -- Check of daar rekords in InstituteQualification is wat wys na StudyInstitute
    SELECT count(*) into vCount from ig_db.InstituteQualification
    WHERE StudyInstituteId = pStudyInstituteId;
    IF (vCount > 0) Then
        signal sqlstate '45000'
        set message_text = 'Cannot delete this Study Institute. The Study Institute has Individuals linked to it. ';
    END IF;

    START TRANSACTION;
        DELETE FROM ig_db.StudyInstitute WHERE StudyInstituteId = pStudyInstituteId;

    COMMIT;

END