package net.integrategroup.ignite.persistence.service;

import java.util.List;

import net.integrategroup.ignite.persistence.model.ProjectStageType;

public interface ProjectStageTypeService {

	public List<ProjectStageType> findAll();

	public ProjectStageType findByProjectStageTypeId(Long projectStageTypeId);

	public ProjectStageType save(ProjectStageType projectStageType);

	//public void delete(ProjectStageType projectStageType);
	// We rather call a stored procedure to do the checks before deleting, this will give the user better info about foreign key violations


}
