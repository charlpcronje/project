package net.integrategroup.ignite.persistence.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import net.integrategroup.ignite.persistence.model.MaintenanceType;

@Repository
public interface MaintenanceTypeRepository extends CrudRepository<MaintenanceType, Long> {

	@Query("SELECT a FROM MaintenanceType a")
	List<MaintenanceType> getMaintenanceType();

	@Query("SELECT a FROM MaintenanceType a WHERE maintenanceTypeId = ?1")
	MaintenanceType findByMaintenanceTypeId(Long maintenanceTypeId);

	@Query("SELECT a FROM MaintenanceType a WHERE maintenanceTypeId not in (SELECT maintenanceTypeId FROM VehicleMaintenance b WHERE b.vehicleId = ?1)")
	List<MaintenanceType> findMaintenanceTypeNotLinked(Long vehicleId);

}