CREATE DEFINER=`administrator`@`localhost` PROCEDURE `deletePermission`(`pPermissionCode` VARCHAR(50))
BEGIN
    declare vCount integer;
    declare vErrorMessage varchar(100);
	declare exit handler for sqlexception
    begin
        rollback;  -- rollback any changes made in the transaction
        resignal;  -- raise again the sql exception to the caller
    end;
    
  	-- Check if the Permission is linked to a PaymentSchedule
    select count(*) into vCount from ig_db.RolePermission
    where PermissionCode = pPermissionCode;
    if (vCount > 0 ) then 
		signal sqlstate '45000'
		set message_text = 'Can not delete Permission.  It has one or more Roles linked to it';
	end if;

 	-- Check if the Permission is linked to a UiComponent
    select count(*) into vCount from ig_db.UiComponent
    where PermissionCodeRequired = pPermissionCode;
    if (vCount > 0 ) then 
		signal sqlstate '45000'
		set message_text = 'Can not delete Permission.  It has one or more UiComponents linked to it';
	end if;

	start transaction;
		delete from ig_db.Permission where PermissionCode = pPermissionCode;
	commit;

END