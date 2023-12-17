package net.integrategroup.ignite.persistence.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import net.integrategroup.ignite.persistence.model.UiComponent;

@Repository
public interface UiComponentRepository extends CrudRepository<UiComponent, Long> {

	@Override
	List<UiComponent> findAll();

	@Query("SELECT b" +
			"  FROM " +
			"    UiComponent b" +
			"  WHERE" +
			"    b.uiComponentId = ?1")
	UiComponent findByUiComponentId(Long uiComponentId);

}
