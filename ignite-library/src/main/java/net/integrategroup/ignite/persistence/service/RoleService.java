package net.integrategroup.ignite.persistence.service;

import java.util.List;

import net.integrategroup.ignite.persistence.model.Permission;
import net.integrategroup.ignite.persistence.model.Role;

public interface RoleService {

	List<Role> findAll();

	Role save(Role role);

	List<Permission> findAvailablePermissions(String roleCode);

	List<Permission> findAssignedPermissions(String roleCode);


}
