package net.integrategroup.ignite.persistence.service;

import java.util.List;

import net.integrategroup.ignite.persistence.model.Permission;
import net.integrategroup.ignite.persistence.model.Role;
import net.integrategroup.ignite.persistence.model.RolePermission;

public interface RolePermissionService {

	RolePermission assign(String roleCode, String permissionCode);

	void revoke(String roleCode, String permissionCode);

	void clone(String existingRole, String newRole);


	List<RolePermission> getRolePermissionbyRole(String roleCode);

	List<RolePermission> getRolePermissionbyPermission(String permissionCode);


	List<Permission> getPermissionNotLinked(String roleCode);

	List<Role> getRoleNotLinked(String permissionCode);


	RolePermission findByRolePermissionId(Long rolePermissionId);

	RolePermission save(RolePermission rolePermission);

}
