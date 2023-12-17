package net.integrategroup.ignite.persistence.service;

import java.util.List;

import net.integrategroup.ignite.persistence.model.MedicalInsuranceCompany;
import net.integrategroup.ignite.persistence.model.MedicalInsurancePlan;

public interface MedicalInsuranceCompanyService {

	List<MedicalInsuranceCompany> findAll();

	MedicalInsuranceCompany findByMedicalInsuranceCompanyId(Long medicalInsuranceCompanyId);

	MedicalInsuranceCompany save(MedicalInsuranceCompany medicalInsuranceCompany);

	List<MedicalInsurancePlan> getPlans(Long medicalInsuranceCompanyId);

	MedicalInsurancePlan findMedicalInsurancePlan(Long medicalInsurancePlanId);

	MedicalInsurancePlan save(MedicalInsurancePlan medicalInsurancePlan);

}
