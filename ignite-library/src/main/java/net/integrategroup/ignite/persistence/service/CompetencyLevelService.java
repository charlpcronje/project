package net.integrategroup.ignite.persistence.service;

import java.util.List;

import net.integrategroup.ignite.persistence.model.CompetencyLevel;

public interface CompetencyLevelService {

	CompetencyLevel findByCompetencyLevelId(Long competencyLevelId);

	List<CompetencyLevel> findAll();

	CompetencyLevel save(CompetencyLevel competencyLevel);

	//void delete(Long competencyLevelId);

}
