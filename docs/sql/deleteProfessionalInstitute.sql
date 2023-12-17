CREATE DEFINER=`administrator`@`localhost` PROCEDURE `deleteProfessionalInstitute`(IN `pProfessionalInstituteId` BIGINT)
    NO SQL
BEGIN

	
    DECLARE vCount integer;
    DECLARE vErrorMessage varchar(100);



    DECLARE exit handler for sqlexception
    BEGIN
        rollback;  -- rollback any changes made in the transaction
        resignal;  -- raise again the sql exception to the caller
    END;



    -- Check of daar rekords in IndividualProfRegistration is wat wys na ProfessionalInstitute
    SELECT count(*) into vCount from ig_db.IndividualProfRegistration
    WHERE ProfessionalInstituteId = pProfessionalInstituteId;
    IF (vCount > 0) Then
        signal sqlstate '45000'
        set message_text = 'Cannot delete this Professional Institute. The Professional Institute has Individuals linked to it. ';
    END IF;

    START TRANSACTION;
        DELETE FROM ig_db.ProfessionalInstitute WHERE ProfessionalInstituteId = pProfessionalInstituteId;

    COMMIT;

END