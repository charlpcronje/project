package net.integrategroup.ignite.persistence.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.integrategroup.ignite.persistence.model.PpSdRoleStage;
import net.integrategroup.ignite.persistence.repository.PpSdRoleStageRepository;

@Service
public class PpSdRoleStageServiceImpl implements PpSdRoleStageService {

	@Autowired
	PpSdRoleStageRepository ppSdRoleStageRepository;


	@Override
	public List<PpSdRoleStage> findPpSdRoleStageByPpSdRid(Long projectParticipantSdRoleId) {
		return ppSdRoleStageRepository.findPpSdRoleStageByPpSdRid(projectParticipantSdRoleId);
	}

	@Override
	public PpSdRoleStage findByPpSdRoleStageId(Long ppSdRoleStageId) {
		return ppSdRoleStageRepository.findByPpSdRoleStageId(ppSdRoleStageId);
	}

	@Override
	public PpSdRoleStage save(PpSdRoleStage ppSdRoleStage) {
		return ppSdRoleStageRepository.save(ppSdRoleStage);
	}


}
