package net.integrategroup.ignite.persistence.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import net.integrategroup.ignite.persistence.model.ProjectStageType;

@Repository
public interface ProjectStageTypeRepository extends CrudRepository<ProjectStageType, Long> {

	@Override
	List<ProjectStageType> findAll();

	@Query("SELECT f FROM ProjectStageType f WHERE projectStageTypeId = ?1")
	ProjectStageType findByProjectStageTypeId(Long projectStageTypeId);

}
