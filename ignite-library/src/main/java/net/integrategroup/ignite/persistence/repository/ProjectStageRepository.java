package net.integrategroup.ignite.persistence.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import net.integrategroup.ignite.persistence.model.ProjectStage;

@Repository
public interface ProjectStageRepository extends CrudRepository<ProjectStage, Long> {

	@Query("SELECT ps FROM ProjectStage ps WHERE projectStageId = ?1")
	ProjectStage findByProjectStageId(Long projectStageId);

}
