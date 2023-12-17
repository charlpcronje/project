CREATE DEFINER=`administrator`@`localhost` PROCEDURE `deleteRemunerationModel`(IN `pRemunerationModelCode` varchar(50))
    NO SQL
BEGIN
    declare vCount integer;
    declare vErrorMessage varchar(100);
	declare exit handler for sqlexception
    begin
        rollback;  -- rollback any changes made in the transaction
        resignal;  -- raise again the sql exception to the caller
    end;

    -- Check of daar rekords in AgreementBetweenParticipants is wat wys na RemunerationModel
    SELECT count(*) into vCount from ig_db.AgreementBetweenParticipants
    WHERE RemunerationModelCode = pRemunerationModelCode;
    IF (vCount > 0) Then
        signal sqlstate '45000'
        set message_text = 'Cannot delete this RemunerationModel. The RemunerationModel has records in the AgreementBetweenParticipants table linked to it. ';
    END IF;

	start transaction;
		delete from ig_db.RemunerationModel where RemunerationModelCode = pRemunerationModelCode;
	commit;


END