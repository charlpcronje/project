package net.integrategroup.ignite.persistence.service;

import java.util.List;

import net.integrategroup.ignite.persistence.model.RecoverableExpense;
import net.integrategroup.ignite.persistence.model.VRecoverableExpense;

public interface RecoverableExpenseService {

	List<VRecoverableExpense> findAllForAgreement(Long agreementBetweenParticipantsId);

	RecoverableExpense save(RecoverableExpense recoverableExpense);

	RecoverableExpense findByRecoverableExpenseId(Long expensAgreementId);

	//void delete(Long projectParticipantId, String username) throws SQLException;
	// We rather call a stored procedure to do the checks before deleting, this will give the user better info about foreign key violations

}
