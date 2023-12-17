package net.integrategroup.ignite.persistence.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.integrategroup.ignite.persistence.model.ProjectExpenseFile;
import net.integrategroup.ignite.persistence.repository.ProjectExpenseFileRepository;

@Service
public class ProjectExpenseFileServiceImpl implements ProjectExpenseFileService {

	@Autowired
	ProjectExpenseFileRepository projectExpenseFileRepository;

	@Override
	public List<ProjectExpenseFile> findAll() {
		return projectExpenseFileRepository.findAll();
	}
	
	@Override
	public List<ProjectExpenseFile> findListProjectExpenseFileForProjectExpenseId(Long projectExpenseId) {
		return projectExpenseFileRepository.findListProjectExpenseFileForProjectExpenseId(projectExpenseId);
	}

	@Override
	public ProjectExpenseFile save(ProjectExpenseFile projectExpenseFile) {
		return projectExpenseFileRepository.save(projectExpenseFile);
	}

	@Override
	public ProjectExpenseFile getByProjectExpenseFileId(Long projectExpenseFileId) {
		return projectExpenseFileRepository.getByProjectExpenseFileId(projectExpenseFileId);
	}

	@Override
	public void delete(Long projectExpenseFileId) {
		projectExpenseFileRepository.deleteById(projectExpenseFileId);

	}
}
