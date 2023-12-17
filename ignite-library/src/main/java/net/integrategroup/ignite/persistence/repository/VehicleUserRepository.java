package net.integrategroup.ignite.persistence.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import net.integrategroup.ignite.model.VehicleUserDto;
import net.integrategroup.ignite.persistence.model.VehicleUser;

@Repository
public interface VehicleUserRepository extends CrudRepository<VehicleUser, Long>{

	@Override
	List<VehicleUser> findAll();

	@Query("SELECT vu" +
			" FROM " +
			"VehicleUser vu" +
			" WHERE " +
			"vehicleUserID = ?1")
	VehicleUser findbyVehicleUserId(Long vehicleUserId);

	@Query("SELECT " +
			"     new net.integrategroup.ignite.model.VehicleUserDto(" +
			"           vu.vehicleUserId, vu.vehicleId, vu.participantId, " +
			"           vu.description, vu.lastUpdateTimestamp, vu.lastUpdateUserName, " +
			"           p.systemName)" +
		    " FROM " +
			"     VehicleUser vu," +
		    "     Participant p" +
		    " WHERE" +
			"     vu.vehicleId = ?1" +
		    " AND " +
			"     p.participantId = vu.participantId"
			)
	List<VehicleUserDto> findVehicleUserByVehicleId(Long vehicleId);

}
