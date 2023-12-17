package net.integrategroup.ignite.persistence.service;

import java.util.List;

import net.integrategroup.ignite.persistence.model.UnitType;

public interface UnitTypeService {

	public List<UnitType> findAll();

	public UnitType findByUnitTypeCode(String unitTypeCode);

	public UnitType save(UnitType unitType);

	//public void delete(UnitType unitType);
	// We rather call a stored procedure to do the checks before deleting, this will give the user better info about foreign key violations


}
