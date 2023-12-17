package net.integrategroup.ignite.persistence.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import net.integrategroup.ignite.persistence.model.PersonResponsible;

@Repository
public interface PersonResponsibleRepository extends CrudRepository<PersonResponsible, Long> {

	@Override
	List<PersonResponsible> findAll();

	@Query("SELECT b FROM PersonResponsible b WHERE b.personResponsibleId = ?1")
	PersonResponsible findByPersonResponsibleId(Long personResponsibleId);

}
