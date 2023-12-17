package net.integrategroup.ignite.persistence.service;

import java.util.List;

import net.integrategroup.ignite.persistence.model.IndividualSdRole;
import net.integrategroup.ignite.persistence.model.VIndividualSdRole;

public interface IndividualSdRoleService {

	List<IndividualSdRole> findAll();

	List<VIndividualSdRole> findByParticipant(Long participantId);

	IndividualSdRole save(IndividualSdRole individualSdRole);

	IndividualSdRole findByIndividualSdRoleId(Long individualSdRoleId);

	// void delete(IndividualSdRole individualSdRole);
	// We rather call a stored procedure to do the checks before deleting, this will give the user better info about foreign key violations


}
