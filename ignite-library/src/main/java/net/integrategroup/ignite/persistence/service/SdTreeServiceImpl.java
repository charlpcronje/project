package net.integrategroup.ignite.persistence.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.integrategroup.ignite.persistence.model.VServiceDiscipline;
import net.integrategroup.ignite.persistence.repository.SdTreeRepository;

@Service
public class SdTreeServiceImpl implements SdTreeService {

	@Autowired
	SdTreeRepository sdTreeRepository;

	@Override
	public List<VServiceDiscipline> getTree() {
		return sdTreeRepository.findTopLevel();
	}

	@Override
	public List<VServiceDiscipline> getChildren(Long serviceDisciplineIdParent) {
		return sdTreeRepository.findChildren(serviceDisciplineIdParent);
	}

//	@Override
//	public IndividualCompetency getLinkedIndividualCompetency(Long individualId, Long serviceDisciplineId) {
//		return sdTreeRepository.getLinkedIndividualCompetency(individualId, serviceDisciplineId);
//	}

}
