package net.integrategroup.ignite.persistence.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.integrategroup.ignite.persistence.model.TopDescriptionsUsed;
import net.integrategroup.ignite.persistence.repository.TopDescriptionsUsedRepository;

@Service
public class TopDescriptionsUsedServiceImpl implements TopDescriptionsUsedService {

	@Autowired
	TopDescriptionsUsedRepository topDescriptionsUsedRepository;



	@Override
	public List<TopDescriptionsUsed> findAll() {
		return topDescriptionsUsedRepository.findAll();
	}


	@Override
	public List<TopDescriptionsUsed> findByProjectParticipantId(Long projectParticipantId) {
		return topDescriptionsUsedRepository.findByProjectParticipantId(projectParticipantId);
	}

}
