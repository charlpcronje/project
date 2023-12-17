package net.integrategroup.ignite.persistence.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import net.integrategroup.ignite.persistence.model.IndividualSdRole;
import net.integrategroup.ignite.persistence.model.VIndividualSdRole;

@Repository
public interface IndividualSdRoleRepository extends CrudRepository<IndividualSdRole, Long> {

	@Override
	List<IndividualSdRole> findAll();

	@Query("SELECT " + "    i " + "  FROM " + "    IndividualSdRole i " + "  WHERE " + "    i.individualSdRoleId = ?1")
	IndividualSdRole findByIndividualSdRoleId(Long individualSdRoleId);

	@Query("SELECT   i FROM VIndividualSdRole i " +
			"  WHERE i.participantId = ?1")
	List<VIndividualSdRole> findByParticipant(Long participantId);


}
