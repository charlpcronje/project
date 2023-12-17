package net.integrategroup.ignite.persistence.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import net.integrategroup.ignite.persistence.model.Permission;
import net.integrategroup.ignite.persistence.model.Role;
import net.integrategroup.ignite.persistence.model.RolePermission;
import net.integrategroup.ignite.persistence.model.RolePermissionPrimaryKey;

@Repository
public interface RolePermissionRepository extends CrudRepository<RolePermission, Long> {

	@Transactional
	@Modifying
	@Query("DELETE FROM RolePermission rp WHERE rp.roleCode = ?1 AND rp.permissionCode = ?2")
	void revoke(String roleCode, String permissionCode);

	@Query("SELECT rr "
			+ " FROM RolePermission rr "
			+ " WHERE 	rr.roleCode = ?1 ")
	List<RolePermission> getRolePermissionbyRole(String roleCode);

	@Query("SELECT rr "
			+ " FROM RolePermission rr "
			+ " WHERE 	rr.permissionCode = ?1 ")
	List<RolePermission> getRolePermissionbyPermission(String permissionCode);

	@Query("SELECT p "
			+ " FROM Permission p "
			+ " WHERE 	p.permissionCode not in (SELECT rp.permissionCode FROM RolePermission rp WHERE rp.roleCode = ?1)")
	List<Permission> getPermissionNotLinked(String roleCode);

	@Query("SELECT r "
			+ " FROM Role r "
			+ " WHERE 	r.roleCode not in (SELECT rp.roleCode FROM RolePermission rp WHERE rp.permissionCode = ?1)")
	List<Role> getRoleNotLinked(String permissionCode);


	RolePermission findByRolePermissionId(Long rolePermissionId);

}

