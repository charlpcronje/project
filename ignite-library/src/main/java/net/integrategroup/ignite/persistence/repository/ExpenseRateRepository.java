package net.integrategroup.ignite.persistence.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import net.integrategroup.ignite.persistence.model.ExpenseRate;
import net.integrategroup.ignite.persistence.model.VExpenseRate;

@Repository
public interface ExpenseRateRepository extends CrudRepository<ExpenseRate, Long> {

	@Query("SELECT ver FROM VExpenseRate ver "
			+ " WHERE ver.recoverableExpenseId = ?1")
	List<VExpenseRate> getExpenseRates(Long recoverableExpenseId);

	@Query("SELECT ver FROM VExpenseRate ver "
			+ " WHERE ver.recoverableExpenseId = ?1"
			+ " AND ((ver.startDate <= now()) and ((ver.endDate is null) or (ver.endDate >= now())))")
	List<VExpenseRate> getExpenseRatesCurrent(Long recoverableExpenseId);

}


