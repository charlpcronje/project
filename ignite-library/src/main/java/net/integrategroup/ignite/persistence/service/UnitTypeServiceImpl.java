package net.integrategroup.ignite.persistence.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.integrategroup.ignite.persistence.model.UnitType;
import net.integrategroup.ignite.persistence.repository.UnitTypeRepository;

@Service
public class UnitTypeServiceImpl implements UnitTypeService {

	@Autowired
	UnitTypeRepository unitTypeRepository;

	@Override
	public List<UnitType> findAll() {
		return unitTypeRepository.findAll();
	}

	@Override
	public UnitType findByUnitTypeCode(String unitTypeCode) {
		return unitTypeRepository.findByUnitTypeCode(unitTypeCode);
	}

	@Override
	public UnitType save(UnitType unitType) {
		return unitTypeRepository.save(unitType);
	}

	/*
	@Override
	public void delete(UnitType unitType) {
		unitTypeRepository.delete(unitType);
	}
	*/

}
