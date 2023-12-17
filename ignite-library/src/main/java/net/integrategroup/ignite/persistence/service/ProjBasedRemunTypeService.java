package net.integrategroup.ignite.persistence.service;

import java.util.List;

import net.integrategroup.ignite.persistence.model.ProjBasedRemunType;

public interface ProjBasedRemunTypeService {

	List<ProjBasedRemunType> findAll();

	List<ProjBasedRemunType> getTimeBasedProjBasedRemunType();

	ProjBasedRemunType findByProjBasedRemunTypeId(Long projBasedRemunTypeId);

	ProjBasedRemunType save(ProjBasedRemunType ProjBasedRemunType);

}
