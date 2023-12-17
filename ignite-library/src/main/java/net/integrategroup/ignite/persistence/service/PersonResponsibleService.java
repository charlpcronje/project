package net.integrategroup.ignite.persistence.service;

import java.util.List;

import net.integrategroup.ignite.persistence.model.PersonResponsible;

public interface PersonResponsibleService {

	List<PersonResponsible> findAll();

	PersonResponsible save(PersonResponsible personResponsible);

	PersonResponsible findByPersonResponsibleId(Long personResponsibleId);

	//void delete(PersonResponsible personResponsible);
	// We rather call a stored procedure to do the checks before deleting, this will give the user better info about foreign key violations

}
