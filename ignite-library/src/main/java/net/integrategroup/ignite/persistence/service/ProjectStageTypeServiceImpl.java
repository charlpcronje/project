package net.integrategroup.ignite.persistence.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.integrategroup.ignite.persistence.model.ProjectStageType;
import net.integrategroup.ignite.persistence.repository.ProjectStageTypeRepository;

@Service
public class ProjectStageTypeServiceImpl implements ProjectStageTypeService {

	@Autowired
	ProjectStageTypeRepository projectStageTypeRepository;

	@Override
	public List<ProjectStageType> findAll() {
		return projectStageTypeRepository.findAll();
	}

	@Override
	public ProjectStageType findByProjectStageTypeId(Long projectStageTypeId) {
		return projectStageTypeRepository.findByProjectStageTypeId(projectStageTypeId);
	}

	@Override
	public ProjectStageType save(ProjectStageType projectStageType) {
		return projectStageTypeRepository.save(projectStageType);
	}

	/*
	@Override
	public void delete(ProjectStageType projectStageType) {
		projectStageTypeRepository.delete(projectStageType);
	}
	*/

}
