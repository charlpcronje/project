package net.integrategroup.ignite.persistence.service;

import java.sql.SQLException;
import java.util.List;

import net.integrategroup.ignite.persistence.model.MainMemberMedicalInsurance;
import net.integrategroup.ignite.persistence.model.MedicalDependant;

public interface IndividualMedicalService {

	MainMemberMedicalInsurance getMainMemberMedical(Long individualId);

	List<MedicalDependant> getDependants(Long individualId);

	void saveMainMemberMedicalInsurance(Long individualId, Long medicalInsuranceCompanyId,
										Long medicalInsurancePlanId, String insuranceNumber,
										String description) throws Exception;

	void deleteDependant(Long medicalDependantId) throws SQLException;

	void saveDependant(Long medicalDependantId, Long mainMemberMedicalInsuranceId, Long individualIdDependant,
			String description) throws SQLException;

}
