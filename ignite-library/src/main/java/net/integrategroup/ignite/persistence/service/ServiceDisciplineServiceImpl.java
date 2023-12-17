package net.integrategroup.ignite.persistence.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.integrategroup.ignite.persistence.model.ServiceDiscipline;
import net.integrategroup.ignite.persistence.model.VServiceDiscipline;
import net.integrategroup.ignite.persistence.repository.ServiceDisciplineRepository;

// @author Ingrid Marais

@Service
public class ServiceDisciplineServiceImpl implements ServiceDisciplineService {

	@Autowired
	DatabaseService databaseService;

	@Autowired
	ServiceDisciplineRepository serviceDisciplineRepository;

	@Override
	public List<VServiceDiscipline> findAllSss() {
		return serviceDisciplineRepository.findAllSss();
	}

	@Override
	public List<VServiceDiscipline> findLevel0List() {
		return serviceDisciplineRepository.findLevel0List();
	}

	@Override
	public List<VServiceDiscipline> findChildren(Long serviceDisciplineId) {
		return serviceDisciplineRepository.findChildren(serviceDisciplineId);
	}

	@Override
	public List<VServiceDiscipline> expandChildren(String serviceDisciplineArray) {
		return serviceDisciplineRepository.expandChildren(serviceDisciplineArray);
	}

	@Override
	public ServiceDiscipline findByServiceDisciplineId(Long serviceDisciplineId) {
		return serviceDisciplineRepository.findByServiceDisciplineId(serviceDisciplineId);
	}

	@Override
	public ServiceDiscipline save(ServiceDiscipline serviceDiscipline) {
		return serviceDisciplineRepository.save(serviceDiscipline);
	}

	@Override
	public VServiceDiscipline findByVServiceDisciplineId(Long serviceDisciplineId) {
		return serviceDisciplineRepository.findByVServiceDisciplineId(serviceDisciplineId);
	}

}
