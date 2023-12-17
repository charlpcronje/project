package net.integrategroup.ignite.persistence.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.integrategroup.ignite.persistence.model.Branch;
import net.integrategroup.ignite.persistence.repository.BranchRepository;

@Service
public class BranchServiceImpl implements BranchService {

	@Autowired
	BranchRepository branchRepository;

	@Override
	public List<Branch> findAll() {
		return branchRepository.findAll();
	}

	@Override
	public List<Branch> findByBankId(Long bankId) {
		return branchRepository.findByBankId(bankId);
	}

	@Override
	public Branch save(Branch branch) {
		return branchRepository.save(branch);
	}

	@Override
	public Branch findByBranchId(Long branchId) {
		return branchRepository.findByBranchId(branchId);
	}

	//@Override
	//public void delete(Branch branch) {
	//	branchRepository.delete(branch);
	//}

}
