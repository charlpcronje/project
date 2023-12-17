package net.integrategroup.ignite.persistence.service;

import java.util.List;

import net.integrategroup.ignite.persistence.model.Permission;

public interface PermissionService {

	List<Permission> findAll();

	Permission save(Permission permission);

	List<Permission> getPermissionsForUser(String username);

}
