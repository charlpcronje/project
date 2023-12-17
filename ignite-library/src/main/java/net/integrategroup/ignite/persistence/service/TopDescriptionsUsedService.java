package net.integrategroup.ignite.persistence.service;

import java.util.List;

import net.integrategroup.ignite.persistence.model.TopDescriptionsUsed;

public interface TopDescriptionsUsedService {

	List<TopDescriptionsUsed> findAll();

	List<TopDescriptionsUsed> findByProjectParticipantId(Long projectParticipantId);


	//void delete(Bank bank);
	// We rather call a stored procedure to do the checks before deleting, this will give the user better info about foreign key violations

}
