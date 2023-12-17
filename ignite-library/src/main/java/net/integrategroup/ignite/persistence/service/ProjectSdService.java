package net.integrategroup.ignite.persistence.service;

import net.integrategroup.ignite.persistence.model.ProjectSd;

public interface ProjectSdService {

	public ProjectSd findByProjectSdId(Long projectSdId);

	public ProjectSd save(ProjectSd projectSd);

	// We rather call a stored procedure to do the checks before deleting, this will give the user better info about foreign key violations

}
