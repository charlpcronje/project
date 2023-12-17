package net.integrategroup.ignite.persistence.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.integrategroup.ignite.persistence.model.Permission;
import net.integrategroup.ignite.persistence.repository.PermissionRepository;
import net.integrategroup.ignite.utils.SecurityUtils;

@Service
public class PermissionServiceImpl implements PermissionService {

	@Autowired
	SecurityUtils securityUtils;

	@Autowired
	PermissionRepository permissionRepository;

	@Override
	public List<Permission> findAll() {
		return permissionRepository.findAll();
	}

	@Override
	public Permission save(Permission permission) {
		return permissionRepository.save(permission);
	}

	@Override
	public List<Permission> getPermissionsForUser(String username) {
		return permissionRepository.getPermissionsForUser(username);
	}

}
