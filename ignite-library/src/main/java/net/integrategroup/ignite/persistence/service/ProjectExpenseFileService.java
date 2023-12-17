package net.integrategroup.ignite.persistence.service;

import java.util.List;

import net.integrategroup.ignite.persistence.model.ProjectExpenseFile;

public interface ProjectExpenseFileService {

	List<ProjectExpenseFile> findAll();
	
	List<ProjectExpenseFile> findListProjectExpenseFileForProjectExpenseId(Long projectExpenseId);

	ProjectExpenseFile save(ProjectExpenseFile projectExpenseFile);

	ProjectExpenseFile getByProjectExpenseFileId(Long projectExpenseFileId);

	void delete(Long projectExpenseFileId);
}
