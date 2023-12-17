package net.integrategroup.ignite.persistence.service;

import java.util.List;

import net.integrategroup.ignite.persistence.model.Branch;

public interface BranchService {

	List<Branch> findAll();

	List<Branch> findByBankId(Long bankId);

	Branch save(Branch branch);

	Branch findByBranchId(Long branchId);

	// void delete(Branch branch);
	// We rather call a stored procedure to do the checks before deleting, this will give the user better info about foreign key violations


}
