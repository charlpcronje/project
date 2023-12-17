package net.integrategroup.ignite.persistence.service;


import java.util.List;

import net.integrategroup.ignite.persistence.model.ProjectSdStage;

public interface ProjectSdStageService {


	List<ProjectSdStage> findAll();

	List<ProjectSdStage> findProjectSdStageByProjectSd(Long projectSdId);

	ProjectSdStage findByProjectSdStageId(Long projectSdStageId);




	ProjectSdStage findByProjectStageId(Long projectSdStageId);

	ProjectSdStage save(ProjectSdStage projectSdStage);


}
