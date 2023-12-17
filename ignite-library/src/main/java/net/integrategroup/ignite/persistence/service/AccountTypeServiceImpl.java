package net.integrategroup.ignite.persistence.service;

import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.integrategroup.ignite.persistence.model.AccountType;
import net.integrategroup.ignite.persistence.repository.AccountTypeRepository;

@Service
public class AccountTypeServiceImpl implements AccountTypeService {

	@Autowired
	EntityManager entityManager;

	@Autowired
	AccountTypeRepository accountTypeRepository;

	@Override
	public AccountType findByAccountTypeId(Long accountTypeId) {
		return accountTypeRepository.findByAccountTypeId(accountTypeId);
	}

	@Override
	public AccountType save(AccountType accountType) {
		return accountTypeRepository.save(accountType);
	}

	@Override
	public List<AccountType> findAll() {
		return accountTypeRepository.findAll();
	}



	//@Override
	//public void delete(Long participantOfficeId) {
	//	ParticipantOffice participantOffice = participantOfficeRepository.findByParticipantOfficeId(participantOfficeId);

	//	participantOfficeRepository.delete(participantOffice);
	//}

//	@Override
//	public void delete(Long participantOfficeId) {
//		StoredProcedureQuery storedProcedure = entityManager.createStoredProcedureQuery("ig_db.deleteParticipantOffice");
//		storedProcedure.registerStoredProcedureParameter(0, Long.class, ParameterMode.IN);     // participantOfficeId
//
//		storedProcedure.setParameter(0, participantOfficeId);
//
//		storedProcedure.execute();
//	}

}
