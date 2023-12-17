CREATE DEFINER=`administrator`@`localhost` PROCEDURE `deleteCity`(IN `pCityId` BIGINT, IN pUserId VARCHAR(50))
BEGIN
    declare pTableName varchar(50);
    declare pRecordString varchar(2000);
	
    DECLARE vCount integer;
    DECLARE vErrorMessage varchar(100);


    DECLARE exit handler for sqlexception
    BEGIN
        rollback;  -- rollback any changes made in the transaction
        resignal;  -- raise again the sql exception to the caller
    END;


    -- Check of daar rekords in ContactPoint is wat wys na City
    SELECT count(*) into vCount from ig_db.ContactPoint
    WHERE CityId = pCityId;
    IF (vCount > 0) Then
        signal sqlstate '45000'
        set message_text = 'Cannot delete this City. The City has records in the ContactPoint table linked to it. ';
    END IF;

    START TRANSACTION;
        DELETE FROM ig_db.City WHERE CityId = pCityId;
    COMMIT;

END