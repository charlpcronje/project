package net.integrategroup.ignite.persistence.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.integrategroup.ignite.persistence.model.ProjBasedRemunType;
import net.integrategroup.ignite.persistence.repository.ProjBasedRemunTypeRepository;

@Service
public class ProjBasedRemunTypeServiceImpl implements ProjBasedRemunTypeService {

	@Autowired
	ProjBasedRemunTypeRepository projBasedRemunTypeRepository;

	@Override
	public List<ProjBasedRemunType> findAll() {
		return projBasedRemunTypeRepository.findAll();
	}

	@Override
	public List<ProjBasedRemunType> getTimeBasedProjBasedRemunType() {
		return projBasedRemunTypeRepository.getTimeBasedProjBasedRemunType();
	}

	@Override
	public ProjBasedRemunType findByProjBasedRemunTypeId(Long projBasedRemunTypeId) {
		return projBasedRemunTypeRepository.findByProjBasedRemunTypeId(projBasedRemunTypeId);
	}

	/*
	@Override
	public void delete(RemunTypeProjBased remunTypeProjBased) {
		remunTypeProjBasedRepository.delete(remunTypeProjBased);
	}
	*/

	@Override
	public ProjBasedRemunType save(ProjBasedRemunType projBasedRemunType) {
		return projBasedRemunTypeRepository.save(projBasedRemunType);
	}

}
