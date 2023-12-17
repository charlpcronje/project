package net.integrategroup.ignite.persistence.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import net.integrategroup.ignite.persistence.model.MedicalInsuranceCompany;


@Repository
public interface MedicalInsuranceCompanyRepository extends CrudRepository<MedicalInsuranceCompany, Long> {

	@Override
	List<MedicalInsuranceCompany> findAll();

	@Query("SELECT m FROM MedicalInsuranceCompany m WHERE m.medicalInsuranceCompanyId = ?1")
	MedicalInsuranceCompany findByMedicalInsuranceCompanyId(Long medicalInsuranceCompanyId);

}
