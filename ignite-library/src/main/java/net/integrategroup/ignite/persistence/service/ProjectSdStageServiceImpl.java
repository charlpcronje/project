package net.integrategroup.ignite.persistence.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.integrategroup.ignite.persistence.model.ProjectSdStage;
import net.integrategroup.ignite.persistence.repository.ProjectSdStageRepository;
import net.integrategroup.ignite.utils.SecurityUtils;

@Service
public class ProjectSdStageServiceImpl implements ProjectSdStageService {

	@Autowired
	ProjectSdStageRepository projectSdStageRepository;

	@Autowired
	SecurityUtils securityUtils;

	@Override
	public List<ProjectSdStage> findAll() {
		return projectSdStageRepository.findAll();
	}

	@Override
	public List<ProjectSdStage> findProjectSdStageByProjectSd(Long projectSdId) {
		return projectSdStageRepository.findProjectSdStageByProjectSd(projectSdId);
	}



	@Override
	public ProjectSdStage findByProjectSdStageId(Long projectSdStageId) {
		return projectSdStageRepository.findByProjectSdStageId(projectSdStageId);
	}

	@Override
	public ProjectSdStage save(ProjectSdStage projectSdStage) {
		return projectSdStageRepository.save(projectSdStage);
	}

	@Override
	public ProjectSdStage findByProjectStageId(Long projectSdStageId) {
		// TODO Auto-generated method stub
		return null;
	}



}
