package net.integrategroup.ignite.persistence.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import net.integrategroup.ignite.persistence.model.TopDescriptionsUsed;

@Repository
public interface TopDescriptionsUsedRepository extends CrudRepository<TopDescriptionsUsed, Long> {

	@Override
	List<TopDescriptionsUsed> findAll();

	@Query("SELECT t FROM TopDescriptionsUsed t WHERE t.projectParticipantId = ?1 order by topDescriptionsUsedId asc")
	List<TopDescriptionsUsed> findByProjectParticipantId(Long projectParticipantId);
}
