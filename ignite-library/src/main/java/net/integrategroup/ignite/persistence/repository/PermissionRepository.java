package net.integrategroup.ignite.persistence.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import net.integrategroup.ignite.persistence.model.Permission;

@Repository
public interface PermissionRepository extends CrudRepository<Permission, String> {

	@Override
	List<Permission> findAll();

	@Query("SELECT " +
			"    p" +
			"  FROM Permission p, RolePermission rp, IndividualRoles ir " +
			"   WHERE " +
			"    p.permissionCode = rp.permissionCode" +
			"    AND rp.roleCode = ir.roleCode" +
			"    AND ir.userName = ?1")
	List<Permission> getPermissionsForUser(String username);
}
