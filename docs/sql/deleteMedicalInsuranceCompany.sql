CREATE DEFINER=`administrator`@`localhost` PROCEDURE `deleteMedicalInsuranceCompany`(IN `pMedicalInsuranceCompanyId` BIGINT)
BEGIN
	
    DECLARE vCount integer;
    DECLARE vErrorMessage varchar(100);


    DECLARE exit handler for sqlexception
    BEGIN
        rollback;  -- rollback any changes made in the transaction
        resignal;  -- raise again the sql exception to the caller
    END;

 

    -- Check of daar rekords in MainMemberMedicalInsurance is wat wys na MedicalInsuranceCompany
    SELECT count(*) into vCount from ig_db.MainMemberMedicalInsurance
    WHERE MedicalInsuranceCompanyId = pMedicalInsuranceCompanyId;
    IF (vCount > 0) Then
        signal sqlstate '45000'
        set message_text = 'Cannot delete this MedicalInsuranceCompany. The Medical Insurance Company has records in the MainMemberMedicalInsurance table linked to it. ';
    END IF;

    -- Check of daar rekords in MedicalInsurancePlan is wat wys na MedicalInsuranceCompany
    SELECT count(*) into vCount from ig_db.MedicalInsurancePlan
    WHERE MedicalInsuranceCompanyId = pMedicalInsuranceCompanyId;
    IF (vCount > 0) Then
        signal sqlstate '45000'
        set message_text = 'Cannot delete this MedicalInsuranceCompany. The Medical Insurance Company has Medical Insurance Plans linked to it. ';
    END IF;

    START TRANSACTION;
        DELETE FROM ig_db.MedicalInsuranceCompany WHERE MedicalInsuranceCompanyId = pMedicalInsuranceCompanyId;
    COMMIT;

END