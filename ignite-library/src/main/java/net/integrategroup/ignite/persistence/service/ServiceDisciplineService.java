package net.integrategroup.ignite.persistence.service;

import java.util.List;

import net.integrategroup.ignite.persistence.model.ServiceDiscipline;
import net.integrategroup.ignite.persistence.model.VServiceDiscipline;

public interface ServiceDisciplineService {


	List<VServiceDiscipline> findAllSss();

	List<VServiceDiscipline> findLevel0List();

	List<VServiceDiscipline> findChildren(Long serviceDisciplineId);

	List<VServiceDiscipline> expandChildren(String serviceDisciplineArray);



	ServiceDiscipline findByServiceDisciplineId(Long serviceDisciplineId);

	ServiceDiscipline save(ServiceDiscipline serviceDiscipline);

	VServiceDiscipline findByVServiceDisciplineId(Long serviceDisciplineId);
}
