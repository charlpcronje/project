package net.integrategroup.ignite.persistence.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.integrategroup.ignite.persistence.model.RecoverableExpense;
import net.integrategroup.ignite.persistence.model.VRecoverableExpense;
import net.integrategroup.ignite.persistence.repository.RecoverableExpenseRepository;

// @author Ingrid Marais

@Service
public class RecoverableExpenseServiceImpl implements RecoverableExpenseService {

	@Autowired
	DatabaseService databaseService;

	@Autowired
	RecoverableExpenseRepository recoverableExpenseRepository;

	@Override
	public List<VRecoverableExpense> findAllForAgreement(Long agreementBetweenParticipantsId) {
		return recoverableExpenseRepository.findAllForAgreement(agreementBetweenParticipantsId);
	}

	@Override
	public RecoverableExpense save(RecoverableExpense recoverableExpense) {
		return recoverableExpenseRepository.save(recoverableExpense);
	}

	@Override
	public RecoverableExpense findByRecoverableExpenseId(Long recoverableExpenseId) {
		return recoverableExpenseRepository.findByRecoverableExpenseId(recoverableExpenseId);
	}

}
