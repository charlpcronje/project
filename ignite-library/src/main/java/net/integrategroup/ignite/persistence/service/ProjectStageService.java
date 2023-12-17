package net.integrategroup.ignite.persistence.service;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import net.integrategroup.ignite.persistence.model.ProjectStage;

public interface ProjectStageService {

	public ProjectStage findByProjectStageId(Long projectStageId);

	public ProjectStage save(ProjectStage projectStage);

	//public void delete(ProjectStage projectStage);
	// We rather call a stored procedure to do the checks before deleting, this will give the user better info about foreign key violations


	public ProjectStage save(Long projectStageId, Long projectId, Long projectStageTypeId, String description,
			Date startDate, Date endDate) throws SQLException;

	List<ProjectStage> findAll();

}
