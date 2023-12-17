package net.integrategroup.ignite.persistence.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import net.integrategroup.ignite.persistence.model.RemunerationModel;

@Repository
public interface RemunerationModelRepository extends CrudRepository<RemunerationModel, String> {

	@Override
	List<RemunerationModel> findAll();

	@Query("SELECT rm FROM RemunerationModel rm WHERE remunerationModelCode = ?1")
	RemunerationModel findByRemunerationModelCode(String remunerationModelCode);

}
