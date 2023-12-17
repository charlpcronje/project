CREATE DEFINER=`administrator`@`localhost` PROCEDURE `deleteTypicalFolderStructure`(IN `pTypicalFolderStructureId` BIGINT)
BEGIN

    DECLARE vCount integer;
    DECLARE vErrorMessage varchar(100);


    DECLARE exit handler for sqlexception
    BEGIN
        rollback;  -- rollback any changes made in the transaction
        resignal;  -- raise again the sql exception to the caller
    END;

    -- Check of daar rekords in TypicalFolderStructure is wat wys na TypicalFolderStructure
    SELECT count(*) into vCount from ig_db.TypicalFolderStructure
    WHERE TypicalFolderStructureIdParent = pTypicalFolderStructureId;
    IF (vCount > 0) Then
        signal sqlstate '45000'
        set message_text = 'Cannot delete this Folder. It is the parent to at least one other Folder. ';
    END IF;

    START TRANSACTION;
        DELETE FROM ig_db.TypicalFolderStructure WHERE TypicalFolderStructureId = pTypicalFolderStructureId;
 
    COMMIT;

END