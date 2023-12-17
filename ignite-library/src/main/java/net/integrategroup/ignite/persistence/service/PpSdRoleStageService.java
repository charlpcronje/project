package net.integrategroup.ignite.persistence.service;

import java.util.List;

import net.integrategroup.ignite.persistence.model.PpSdRoleStage;

public interface PpSdRoleStageService {

	List<PpSdRoleStage> findPpSdRoleStageByPpSdRid(Long projectParticipantSdRoleId);

	PpSdRoleStage findByPpSdRoleStageId(Long ppSdRoleStageId);

	PpSdRoleStage save(PpSdRoleStage ppSdRoleStage);

	// void delete(Vehicle vehicle);
	// We rather call a stored procedure to do the checks before deleting, this will give the user better info about foreign key violations


}
