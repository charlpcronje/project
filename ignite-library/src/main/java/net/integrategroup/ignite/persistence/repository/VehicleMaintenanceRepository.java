package net.integrategroup.ignite.persistence.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import net.integrategroup.ignite.persistence.model.VehicleMaintenance;

@Repository
public interface VehicleMaintenanceRepository extends CrudRepository<VehicleMaintenance, Long> {

	@Override
	List<VehicleMaintenance> findAll();

	@Query("SELECT b" +
			"  FROM " +
			"    VehicleMaintenance b" +
			"  WHERE" +
			"    b.vehicleId = ?1")
	List<VehicleMaintenance> findByVehicleId(Long vehicleId);

	@Query("SELECT b FROM VehicleMaintenance b WHERE b.vehicleMaintenanceId = ?1")
	VehicleMaintenance findByVehicleMaintenanceId(Long vehicleMaintenanceId);
}
