package net.integrategroup.ignite.persistence.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.ParameterMode;
import javax.persistence.StoredProcedureQuery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.integrategroup.ignite.persistence.model.Permission;
import net.integrategroup.ignite.persistence.model.Role;
import net.integrategroup.ignite.persistence.model.RolePermission;
import net.integrategroup.ignite.persistence.repository.RolePermissionRepository;
import net.integrategroup.ignite.utils.SecurityUtils;

@Service
public class RolePermissionServiceImpl implements RolePermissionService {

	@Autowired
	EntityManager entityManager;

	@Autowired
	RolePermissionRepository rolePermissionRepository;

	@Autowired
	SecurityUtils securityUtils;

	@Override
	public RolePermission assign(String roleCode, String permissionCode) {
		RolePermission rolePermission = new RolePermission();

		rolePermission.setRoleCode(roleCode);
		rolePermission.setPermissionCode(permissionCode);

		rolePermission = rolePermissionRepository.save(rolePermission);

		return rolePermission;
	}

	@Override
	public void revoke(String roleCode, String permissionCode) {
		rolePermissionRepository.revoke(roleCode, permissionCode);
	}

	@Override
	public void clone(String existingRole, String newRole) {
		StoredProcedureQuery storedProcedure = entityManager.
				createStoredProcedureQuery("idi.p_cloneRole");

		storedProcedure.registerStoredProcedureParameter(0, String.class, ParameterMode.IN);     // existingRole
		storedProcedure.registerStoredProcedureParameter(1, String.class, ParameterMode.IN);     // newRole
		storedProcedure.registerStoredProcedureParameter(2, String.class, ParameterMode.IN);     // username

		storedProcedure
		.setParameter(0, existingRole)
		.setParameter(1, newRole)
		.setParameter(2, securityUtils.getUsername());

		storedProcedure.execute();
	}



	@Override
	public List<RolePermission> getRolePermissionbyRole(String roleCode) {
		return rolePermissionRepository.getRolePermissionbyRole(roleCode);
	}

	@Override
	public List<RolePermission> getRolePermissionbyPermission(String permissionCode) {
		return rolePermissionRepository.getRolePermissionbyPermission(permissionCode);
	}


	@Override
	public List<Permission> getPermissionNotLinked(String roleCode) {
		return rolePermissionRepository.getPermissionNotLinked(roleCode);
	}

	@Override
	public List<Role> getRoleNotLinked(String permissionCode) {
		return rolePermissionRepository.getRoleNotLinked(permissionCode);
	}



	@Override
	public RolePermission findByRolePermissionId(Long rolePermissionId) {
		return rolePermissionRepository.findByRolePermissionId(rolePermissionId);
	}

	@Override
	public RolePermission save(RolePermission rolePermission) {
		return rolePermissionRepository.save(rolePermission);
	}





}
