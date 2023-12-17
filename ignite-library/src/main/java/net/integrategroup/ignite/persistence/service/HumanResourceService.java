package net.integrategroup.ignite.persistence.service;

import java.util.List;

import net.integrategroup.ignite.persistence.model.HumanResource;

public interface HumanResourceService {

	List<HumanResource> findAll();

	List<HumanResource> findHumanResources(Long participantId);

	HumanResource save(HumanResource humanResource);

	HumanResource findByHumanResourceId(Long humanResourceId);

}
