package net.integrategroup.ignite.persistence.service;

import java.util.List;

import net.integrategroup.ignite.persistence.model.IndividualCompetency;

public interface IndividualCompetencyService {

	List<IndividualCompetency> findAll();

	List<IndividualCompetency> getIndividualCompetencyForIndividualSdRole(Long individualSdRoleId);


	IndividualCompetency save(IndividualCompetency individualCompetency);

	IndividualCompetency findByIndividualCompetencyId(Long individualCompetencyId);



}
