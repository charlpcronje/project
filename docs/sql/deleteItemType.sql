CREATE DEFINER=`administrator`@`localhost` PROCEDURE `deleteItemType`(`pItemTypeId` BIGINT)
BEGIN
    declare vCount integer;
    declare vErrorMessage varchar(100);
	declare exit handler for sqlexception
    begin
        rollback;  -- rollback any changes made in the transaction
        resignal;  -- raise again the sql exception to the caller
    end;
    
  	-- Check if the ItemType is linked to a Vehicle
    select count(*) into vCount from ig_db.VehicleItem
    where ItemTypeId = pItemTypeId;
    if (vCount > 0 ) then 
		signal sqlstate '45000'
		set message_text = 'Can not delete Item Type.  It has been linked to a Vehicle.';
	end if;


	start transaction;
		delete from ig_db.ItemType where ItemTypeId = pItemTypeId;
	commit;

END