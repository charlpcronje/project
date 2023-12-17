package net.integrategroup.ignite.persistence.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.integrategroup.ignite.persistence.model.VPpSdRoleStage;
import net.integrategroup.ignite.persistence.repository.VPpSdRoleStageRepository;

@Service
public class VPpSdRoleStageServiceImpl implements VPpSdRoleStageService {

	@Autowired
	VPpSdRoleStageRepository vPpSdRoleStageRepository;


	@Override
	public List<VPpSdRoleStage> findVPpSdRoleStageByPpSdRid(Long projectParticipantSdRoleId) {
		return vPpSdRoleStageRepository.findVPpSdRoleStageByPpSdRid(projectParticipantSdRoleId);
	}

	@Override
	public List<VPpSdRoleStage> findVPpSdRoleStageByProjectSdId(Long projectSdId) {
		return vPpSdRoleStageRepository.findVPpSdRoleStageByProjectSdId(projectSdId);
	}

	@Override
	public VPpSdRoleStage findByPpSdRoleStageId(Long ppSdRoleStageId) {
		return vPpSdRoleStageRepository.findByPpSdRoleStageId(ppSdRoleStageId);
	}


}
