package net.integrategroup.ignite.persistence.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.integrategroup.ignite.persistence.model.IndividualCompetency;
import net.integrategroup.ignite.persistence.repository.IndividualCompetencyRepository;

@Service
public class IndividualCompetencyServiceImpl implements IndividualCompetencyService {

	@Autowired
	IndividualCompetencyRepository individualCompetencyRepository;

	@Override
	public List<IndividualCompetency> findAll() {
		return individualCompetencyRepository.findAll();
	}

	@Override
	public List<IndividualCompetency> getIndividualCompetencyForIndividualSdRole(Long individualSdRoleId) {
		return individualCompetencyRepository.getIndividualCompetencyForIndividualSdRole(individualSdRoleId);
	}



	@Override
	public IndividualCompetency save(IndividualCompetency individualCompetency) {
		return individualCompetencyRepository.save(individualCompetency);
	}

	@Override
	public IndividualCompetency findByIndividualCompetencyId(Long individualCompetencyId) {
		return individualCompetencyRepository.findByIndividualCompetencyId(individualCompetencyId);
	}



}
