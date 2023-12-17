package net.integrategroup.ignite.persistence.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import net.integrategroup.ignite.persistence.model.VehicleTyreAndRimType;

@Repository
public interface VehicleTyreAndRimTypeRepository extends CrudRepository<VehicleTyreAndRimType, Long> {

	@Query("SELECT a FROM VehicleTyreAndRimType a")
	List<VehicleTyreAndRimType> getVehicleTyreAndRimType();

	@Query("SELECT a FROM VehicleTyreAndRimType a WHERE VehicleTyreAndRimTypeId = ?1")
	VehicleTyreAndRimType findByVehicleTyreAndRimTypeId(Long vehicleTyreAndRimTypeId);

}
