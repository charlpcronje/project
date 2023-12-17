package net.integrategroup.ignite.persistence.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import net.integrategroup.ignite.persistence.model.Branch;

@Repository
public interface BranchRepository extends CrudRepository<Branch, Long> {

	@Override
	List<Branch> findAll();

	@Query("SELECT b" +
			"  FROM " +
			"    Branch b" +
			"  WHERE" +
			"    b.bankId = ?1")
	List<Branch> findByBankId(Long bankId);

	@Query("SELECT b" +
			"  FROM " +
			"    Branch b" +
			"  WHERE" +
			"    b.branchId = ?1")
	Branch findByBranchId(Long branchId);

}
