package net.integrategroup.ignite.persistence.service;

import java.util.List;

import net.integrategroup.ignite.persistence.model.ResourceRemuneration;

public interface ResourceRemunerationService {

	List<ResourceRemuneration> findAllForHumanResource(Long nonProjectRelatedAgreementId);

	ResourceRemuneration save(ResourceRemuneration resourceRemuneration);

	ResourceRemuneration findByResourceRemunerationId(Long resourceRemunerationId);

}
