package net.integrategroup.ignite.persistence.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import net.integrategroup.ignite.persistence.model.AssignmentType;

@Repository
public interface AssignmentTypeRepository extends CrudRepository<AssignmentType, Long> {

	@Query("SELECT a FROM AssignmentType a")
	List<AssignmentType> getAssignmentType();

	@Query("SELECT a FROM AssignmentType a WHERE assignmentTypeID = ?1")
	AssignmentType findByAssignmentTypeId(Long assignmentTypeId);

}
