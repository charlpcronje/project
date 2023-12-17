CREATE DEFINER=`administrator`@`localhost` PROCEDURE `deleteRoleOnAProject`(IN `pRoleOnAProjectId` BIGINT)
BEGIN
	
    DECLARE vCount integer;
    DECLARE vErrorMessage varchar(100);

    DECLARE exit handler for sqlexception
    BEGIN
        rollback;  -- rollback any changes made in the transaction
        resignal;  -- raise again the sql exception to the caller
    END;


    -- Check of daar rekords in PersonResponsible is wat wys na RoleOnAProject
    SELECT count(*) into vCount from ig_db.PersonResponsible
    WHERE RoleOnAProjectId = pRoleOnAProjectId;
    IF (vCount > 0) Then
        signal sqlstate '45000'
        set message_text = 'Cannot delete this RoleOnAProject. It has records in the Person Responsible table linked to it. ';
    END IF;

    -- Check of daar rekords in SdRole is wat wys na RoleOnAProject
    SELECT count(*) into vCount from ig_db.SdRole
    WHERE RoleOnAProjectId = pRoleOnAProjectId;
    IF (vCount > 0) Then
        signal sqlstate '45000'
        set message_text = 'Cannot delete this RoleOnAProject. It has records in the SdRole table linked to it. ';
    END IF;

    START TRANSACTION;
        DELETE FROM ig_db.RoleOnAProject WHERE RoleOnAProjectId = pRoleOnAProjectId;

    COMMIT;

END