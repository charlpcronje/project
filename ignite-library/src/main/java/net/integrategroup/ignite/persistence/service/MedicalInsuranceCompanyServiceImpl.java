package net.integrategroup.ignite.persistence.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.integrategroup.ignite.persistence.model.MedicalInsuranceCompany;
import net.integrategroup.ignite.persistence.model.MedicalInsurancePlan;
import net.integrategroup.ignite.persistence.repository.MedicalInsuranceCompanyRepository;
import net.integrategroup.ignite.persistence.repository.MedicalInsurancePlanRepository;

@Service
public class MedicalInsuranceCompanyServiceImpl implements MedicalInsuranceCompanyService {

	@Autowired
	MedicalInsuranceCompanyRepository repository;

	@Autowired
	MedicalInsurancePlanRepository planRepository;

	@Override
	public List<MedicalInsuranceCompany> findAll() {
		return repository.findAll();
	}

	@Override
	public MedicalInsuranceCompany save(MedicalInsuranceCompany medicalInsuranceCompany) {
		return repository.save(medicalInsuranceCompany);
	}

	@Override
	public MedicalInsuranceCompany findByMedicalInsuranceCompanyId(Long medicalInsuranceCompanyId) {
		return repository.findByMedicalInsuranceCompanyId(medicalInsuranceCompanyId);
	}

	@Override
	public List<MedicalInsurancePlan> getPlans(Long medicalInsuranceCompanyId) {
		return planRepository.getPlans(medicalInsuranceCompanyId);
	}

	@Override
	public MedicalInsurancePlan findMedicalInsurancePlan(Long medicalInsurancePlanId) {
		return planRepository.findMedicalInsurancePlan(medicalInsurancePlanId);
	}

	@Override
	public MedicalInsurancePlan save(MedicalInsurancePlan medicalInsurancePlan) {
		return planRepository.save(medicalInsurancePlan);
	}
}
