
CREATE DEFINER=`administrator`@`localhost` PROCEDURE `deleteResourceRemunType`(`pResourceRemunTypeId` BIGINT)
BEGIN
    declare vCount integer;
    declare vErrorMessage varchar(100);
	declare exit handler for sqlexception
    begin
        rollback;  -- rollback any changes made in the transaction
        resignal;  -- raise again the sql exception to the caller
    end;
    
  	-- Check if there are any associated NonProjectRemuneration
    select count(*) into vCount from ig_db.ResourceRemuneration
    where ResourceRemunTypeId = pResourceRemunTypeId;
    if (vCount > 0 ) then 
		signal sqlstate '45000'
		set message_text = 'Can not delete Resource related Remuneration Type.  It has associated Resource Remunerations.';
	end if;
	start transaction;
		delete from ig_db.ResourceRemunType where ResourceRemunTypeId = pResourceRemunTypeId;
	commit;


END