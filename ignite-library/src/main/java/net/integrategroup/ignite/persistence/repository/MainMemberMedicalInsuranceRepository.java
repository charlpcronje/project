package net.integrategroup.ignite.persistence.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import net.integrategroup.ignite.persistence.model.MainMemberMedicalInsurance;

@Repository
public interface MainMemberMedicalInsuranceRepository extends CrudRepository<MainMemberMedicalInsurance, Long> {

	@Query("SELECT m FROM MainMemberMedicalInsurance m WHERE m.individualId = ?1")
	MainMemberMedicalInsurance findByIndividualId(Long individualId);

}
