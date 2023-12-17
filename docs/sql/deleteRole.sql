CREATE DEFINER=`administrator`@`localhost` PROCEDURE `deleteRole`(`pRoleCode` VARCHAR(50))
BEGIN
    declare vCount integer;
    declare vErrorMessage varchar(100);
	declare exit handler for sqlexception
    begin
        rollback;  -- rollback any changes made in the transaction
        resignal;  -- raise again the sql exception to the caller
    end;
    
  	-- Check if the BranchCode has been used in RolePermission
    select count(*) into vCount from ig_db.RolePermission
    where RoleCode = pRoleCode;
    if (vCount > 0 ) then 
		signal sqlstate '45000'
		set message_text = 'Can not delete Role.  The Role has Permissions linked to it.';
	end if;
	
 	-- Check if the BranchCode has been used in RolePermission
    select count(*) into vCount from ig_db.SystemMember
    where RoleCode = pRoleCode;
    if (vCount > 0 ) then 
		signal sqlstate '45000'
		set message_text = 'Can not delete Role.  The Role has been used.';
	end if;	

	start transaction;
		delete from ig_db.Role where RoleCode = pRoleCode;
	commit;

END