package net.integrategroup.ignite.persistence.service;

import java.util.List;

import net.integrategroup.ignite.persistence.model.VServiceDiscipline;

public interface SdTreeService {

	List<VServiceDiscipline> getTree();

	List<VServiceDiscipline> getChildren(Long serviceDisciplineIdParent);

	// IndividualCompetency getLinkedIndividualCompetency(Long individualId, Long serviceDisciplineId);

}
