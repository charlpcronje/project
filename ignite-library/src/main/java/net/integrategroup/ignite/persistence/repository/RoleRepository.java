package net.integrategroup.ignite.persistence.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import net.integrategroup.ignite.persistence.model.Permission;
import net.integrategroup.ignite.persistence.model.Role;

@Repository
public interface RoleRepository extends CrudRepository<Role, String> {

	@Override
	List<Role> findAll();

	@Query("SELECT p FROM Permission p WHERE p.permissionCode NOT IN (SELECT rp.permissionCode FROM RolePermission rp WHERE rp.roleCode = ?1)")
	List<Permission> findAvailablePermissions(String roleCode);

	@Query("SELECT p FROM Permission p, RolePermission rp WHERE p.permissionCode = rp.permissionCode and rp.roleCode = ?1")
	List<Permission> findAssignedPermissions(String roleCode);
}
