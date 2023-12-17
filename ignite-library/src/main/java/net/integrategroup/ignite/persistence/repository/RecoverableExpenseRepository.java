package net.integrategroup.ignite.persistence.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import net.integrategroup.ignite.persistence.model.RecoverableExpense;
import net.integrategroup.ignite.persistence.model.VRecoverableExpense;

@Repository
public interface RecoverableExpenseRepository extends CrudRepository<RecoverableExpense, Long> {

	@Query("SELECT rv FROM VRecoverableExpense rv WHERE rv.agreementBetweenParticipantsId = ?1")
	List<VRecoverableExpense> findAllForAgreement(Long agreementBetweenParticipantsId);

	@Query("SELECT e FROM RecoverableExpense e WHERE e.recoverableExpenseId = ?1")
	RecoverableExpense findByRecoverableExpenseId(Long RecoverableExpenseId);

}
