package net.integrategroup.ignite.persistence.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import net.integrategroup.ignite.persistence.model.CompetencyLevel;

@Repository
public interface CompetencyLevelRepository extends CrudRepository<CompetencyLevel, Long> {

	@Override
	List<CompetencyLevel> findAll();

	@Query("SELECT cl FROM CompetencyLevel cl WHERE cl.competencyLevelId = ?1")
	CompetencyLevel findByCompetencyLevelId(Long competencyLevelId);

}
