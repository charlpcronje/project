CREATE DEFINER=administrator@localhost PROCEDURE deleteRolePermission(IN pRolePermissionId BIGINT)
    NO SQL
BEGIN


    declare vErrorMessage varchar(100);
	declare exit handler for sqlexception
    begin
        rollback;  -- rollback any changes made in the transaction
        resignal;  -- raise again the sql exception to the caller
    end;
    
 

	start transaction;
		delete from ig_db.RolePermission 
        where RolePermissionId = pRolePermissionId;
	commit;

END