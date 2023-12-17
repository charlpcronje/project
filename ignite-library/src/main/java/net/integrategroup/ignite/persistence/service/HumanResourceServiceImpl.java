package net.integrategroup.ignite.persistence.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.integrategroup.ignite.persistence.model.HumanResource;
import net.integrategroup.ignite.persistence.repository.HumanResourceRepository;

@Service
public class HumanResourceServiceImpl implements HumanResourceService {

	@Autowired
	HumanResourceRepository humanResourceRepository;

	@Override
	public List<HumanResource> findAll() {
		return humanResourceRepository.findAll();
	}

	@Override
	public List<HumanResource> findHumanResources(Long participantId) {
		return humanResourceRepository.findHumanResources(participantId);
	}

	@Override
	public HumanResource save(HumanResource humanResource) {
		return humanResourceRepository.save(humanResource);
	}

	@Override
	public HumanResource findByHumanResourceId(Long humanResourceId) {
		return humanResourceRepository.findByHumanResourceId(humanResourceId);
	}

}
