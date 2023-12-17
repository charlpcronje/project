package net.integrategroup.ignite.persistence.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.integrategroup.ignite.persistence.model.ResourceRemuneration;
import net.integrategroup.ignite.persistence.repository.ResourceRemunerationRepository;

@Service
public class ResourceRemunerationServiceImpl implements ResourceRemunerationService {

	@Autowired
	ResourceRemunerationRepository resourceRemunerationRepository;

	@Override
	public List<ResourceRemuneration> findAllForHumanResource(Long nonProjectRelatedAgreementId) {

		return resourceRemunerationRepository.findAllForHumanResource(nonProjectRelatedAgreementId);
	}

	@Override
	public ResourceRemuneration save(ResourceRemuneration resourceRemuneration) {

		return resourceRemunerationRepository.save(resourceRemuneration);
	}

	@Override
	public ResourceRemuneration findByResourceRemunerationId(Long resourceRemunerationId) {

		return resourceRemunerationRepository.findByResourceRemunerationId(resourceRemunerationId);

	}

}
