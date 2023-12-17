package net.integrategroup.ignite.persistence.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import net.integrategroup.ignite.persistence.model.MedicalDependant;

@Repository
public interface MedicalDependantRepository extends CrudRepository<MedicalDependant, Long> {

	@Query("SELECT "
			+ "    NEW net.integrategroup.ignite.model.MedicalDependantDto("
			+ "	       md.medicalDependantId,"
			+ "	       md.mainMemberMedicalInsuranceId,"
			+ "	       md.individualIdDependant,"
			+ "	       md.description,"
			+ "        CONCAT(i.firstName, ' ', i.lastName),"
			+ "	       md.lastUpdateTimestamp,"
			+ "	       md.lastUpdateUserName"
			+ "      )"
			+ "    FROM"
			+ "		 MedicalDependant md,"
			+ "		 MainMemberMedicalInsurance mmmi,"
			+ "      Individual i"
			+ "    WHERE"
			+ "		 md.mainMemberMedicalInsuranceId = mmmi.mainMemberMedicalInsuranceId "
			+ "      AND i.individualId = md.individualIdDependant"
			+ "      AND mmmi.individualId = ?1")
	List<MedicalDependant> getDependants(Long individualId);


}
