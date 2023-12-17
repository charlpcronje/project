DROP PROCEDURE IF EXISTS deleteProvince;
CREATE OR REPLACE PROCEDUREDEFINER=`administrator`@`localhost` PROCEDURE `deleteProvince`(pProvinceId BIGINT)

BEGIN
	
    DECLARE vCount integer;
    DECLARE vErrorMessage varchar(100);

    DECLARE exit handler for sqlexception
    BEGIN
        rollback;  -- rollback any changes made in the transaction
        resignal;  -- raise again the sql exception to the caller
    END;

  
    -- Check of daar rekords in City is wat wys na Province
    SELECT count(*) into vCount from ig_db.City
    WHERE ProvinceId = pProvinceId;
    IF (vCount > 0) Then
        signal sqlstate '45000'
        set message_text = 'Cannot delete this Province. The Province has records in the City table linked to it. ';
    END IF;

    START TRANSACTION;
        DELETE FROM ig_db.Province WHERE ProvinceId = pProvinceId;
    COMMIT;

END