package net.integrategroup.ignite.persistence.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.integrategroup.ignite.persistence.model.IndividualSdRole;
import net.integrategroup.ignite.persistence.model.VIndividualSdRole;
import net.integrategroup.ignite.persistence.repository.IndividualSdRoleRepository;

@Service
public class IndividualSdRoleServiceImpl implements IndividualSdRoleService {

	@Autowired
	IndividualSdRoleRepository individualSdRoleRepository;

	@Override
	public List<IndividualSdRole> findAll() {
		return individualSdRoleRepository.findAll();
	}

	@Override
	public List<VIndividualSdRole> findByParticipant(Long participantId) {
		return individualSdRoleRepository.findByParticipant(participantId);
	}

	@Override
	public IndividualSdRole save(IndividualSdRole individualSdRole) {
		return individualSdRoleRepository.save(individualSdRole);
	}

	@Override
	public IndividualSdRole findByIndividualSdRoleId(Long individualSdRoleId) {
		return individualSdRoleRepository.findByIndividualSdRoleId(individualSdRoleId);
	}


}
