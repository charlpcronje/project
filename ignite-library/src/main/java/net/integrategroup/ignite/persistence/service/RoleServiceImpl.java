package net.integrategroup.ignite.persistence.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.integrategroup.ignite.persistence.model.Permission;
import net.integrategroup.ignite.persistence.model.Role;
import net.integrategroup.ignite.persistence.repository.RolePermissionRepository;
import net.integrategroup.ignite.persistence.repository.RoleRepository;
import net.integrategroup.ignite.utils.SecurityUtils;

@Service
public class RoleServiceImpl implements RoleService {

	@Autowired
	SecurityUtils securityUtils;

	@Autowired
	RoleRepository roleRepository;

	@Autowired
	RolePermissionRepository rolePermissionRepository;

	@Override
	public List<Role> findAll() {
		return roleRepository.findAll();
	}

	@Override
	public Role save(Role role) {
		return roleRepository.save(role);
	}

	@Override
	public List<Permission> findAvailablePermissions(String roleCode) {
		return roleRepository.findAvailablePermissions(roleCode);
	}

	@Override
	public List<Permission> findAssignedPermissions(String roleCode) {
		return roleRepository.findAssignedPermissions(roleCode);
	}

}
