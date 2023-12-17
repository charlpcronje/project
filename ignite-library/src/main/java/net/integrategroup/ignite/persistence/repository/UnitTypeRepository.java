package net.integrategroup.ignite.persistence.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import net.integrategroup.ignite.persistence.model.UnitType;

@Repository
public interface UnitTypeRepository extends CrudRepository<UnitType, String> {

	@Override
	List<UnitType> findAll();

	@Query("SELECT f FROM UnitType f WHERE unitTypeCode = ?1")
	UnitType findByUnitTypeCode(String unitTypeCode);

}
