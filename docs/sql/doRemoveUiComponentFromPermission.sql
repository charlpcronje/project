CREATE DEFINER=`administrator`@`localhost` PROCEDURE `doRemoveUiComponentFromPermission`(IN `pUiComponentId` BIGINT)
    NO SQL
BEGIN
    -- Source: https://stackoverflow.com/questions/19905900/mysql-transaction-roll-back-on-any-exception
    declare vCount integer;
    declare vErrorMessage varchar(100);
    
	declare exit handler for sqlexception
    begin
        rollback;  -- rollback any changes made in the transaction
        resignal;  -- raise again the sql exception to the caller
    end;

	start transaction;
		UPDATE ig_db.UiComponent
		SET    PermissionCodeRequired = NULL
		WHERE UiComponentId = pUiComponentId;
	commit;
END