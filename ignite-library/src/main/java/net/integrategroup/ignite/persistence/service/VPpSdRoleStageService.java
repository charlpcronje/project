package net.integrategroup.ignite.persistence.service;

import java.util.List;

import net.integrategroup.ignite.persistence.model.VPpSdRoleStage;

public interface VPpSdRoleStageService {

	List<VPpSdRoleStage> findVPpSdRoleStageByPpSdRid(Long projectParticipantSdRoleId);

	List<VPpSdRoleStage> findVPpSdRoleStageByProjectSdId(Long projectSdId);

	VPpSdRoleStage findByPpSdRoleStageId(Long ppSdRoleStageId);

	// void delete(Vehicle vehicle);
	// We rather call a stored procedure to do the checks before deleting, this will give the user better info about foreign key violations


}
