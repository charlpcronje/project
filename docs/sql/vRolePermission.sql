SET collation_connection = 'utf8mb4_0900_ai_ci';

CREATE OR REPLACE
   
VIEW vRolePermission AS
    SELECT 
		rp.RolePermissionId AS RolePermissionId,
		rp.RoleCode			AS RoleCode,
		rp.PermissionCode	AS PermissionCode,
		r.Name				AS RoleName,
		r.Description		AS RoleDescription,
		p.Name				AS PermissionName,
		p.Description		AS PermissionDescription

    FROM
        RolePermission rp
		LEFT JOIN Permission p ON (rp.PermissionCode = p.PermissionCode)		
		LEFT JOIN `Role` r ON (rp.RoleCode = r.RoleCode)
		
		

