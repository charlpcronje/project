package net.integrategroup.ignite.persistence.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import net.integrategroup.ignite.persistence.model.MedicalInsurancePlan;

@Repository
public interface MedicalInsurancePlanRepository extends CrudRepository<MedicalInsurancePlan, Long> {

	@Query("SELECT m FROM MedicalInsurancePlan m WHERE m.medicalInsuranceCompanyId = ?1")
	List<MedicalInsurancePlan> getPlans(Long medicalInsuranceCompanyId);

	@Query("SELECT m FROM MedicalInsurancePlan m WHERE m.medicalInsurancePlanId = ?1")
	MedicalInsurancePlan findMedicalInsurancePlan(Long medicalInsurancePlanId);
}
