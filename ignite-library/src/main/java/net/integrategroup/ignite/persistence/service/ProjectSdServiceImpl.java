package net.integrategroup.ignite.persistence.service;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.integrategroup.ignite.persistence.model.ProjectSd;
import net.integrategroup.ignite.persistence.repository.ProjectSdRepository;
import net.integrategroup.ignite.utils.SecurityUtils;

@Service
public class ProjectSdServiceImpl implements ProjectSdService {

	@Autowired
	EntityManager entityManager;

	@Autowired
	ProjectSdRepository projectSdRepository;

	@Autowired
	SecurityUtils securityUtils;

	@Override
	public ProjectSd findByProjectSdId(Long projectSdId) {
		return projectSdRepository.findByProjectSdId(projectSdId);
	}

	@Override
	public ProjectSd save(ProjectSd projectSd) {
		return projectSdRepository.save(projectSd);
	}


}
