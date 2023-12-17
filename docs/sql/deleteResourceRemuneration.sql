CREATE DEFINER=`administrator`@`localhost` PROCEDURE `deleteResourceRemuneration`(`pResourceRemunerationId` BIGINT)
BEGIN
    declare vCount integer;
    declare vErrorMessage varchar(100);
	declare exit handler for sqlexception
    begin
        rollback;  -- rollback any changes made in the transaction
        resignal;  -- raise again the sql exception to the caller
    end;
	start transaction;
		delete from ig_db.ResourceRemuneration where ResourceRemunerationId = pResourceRemunerationId;
	commit;

END