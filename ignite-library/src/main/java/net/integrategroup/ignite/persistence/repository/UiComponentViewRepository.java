package net.integrategroup.ignite.persistence.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import net.integrategroup.ignite.persistence.model.UiComponentView;

@Repository
public interface UiComponentViewRepository extends CrudRepository<UiComponentView, Long> {


	@Query("SELECT " +
			"      m " +
			"  FROM " +
			"    UiComponentView m" +
			"  WHERE " + "    m.permissionCodeRequired IN " +
			"         (SELECT rp.permissionCode FROM IndividualRoles ir, RolePermission rp " +
			"                                 WHERE ir.userName = ?1 AND rp.roleCode = ir.roleCode AND ir.isActiveMember = 'Y' AND ir.allowLoginFlag = 'Y')" +
			"  ORDER BY rowOrderNo" )
	List<UiComponentView> getMenu(String username);


	@Override
	List<UiComponentView> findAll();


	@Query("SELECT b" +
			"  FROM " +
			"    UiComponentView b" +
			"  WHERE" +
			"    b.permissionCodeRequired = ?1 ")
	List<UiComponentView> findByPermissionCode(String permissionCode);


	@Query("SELECT b" +
			"  FROM " +
			"    UiComponentView b" +
			"  WHERE" +
			"    b.uiComponentId = ?1")
	UiComponentView findByUiComponentId(Long uiComponentId);



}
