package net.integrategroup.ignite.persistence.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.integrategroup.ignite.persistence.model.Bank;
import net.integrategroup.ignite.persistence.repository.BankRepository;
import net.integrategroup.ignite.persistence.repository.BranchRepository;

@Service
public class BankServiceImpl implements BankService {

	@Autowired
	BankRepository bankRepository;

	@Autowired
	BranchRepository branchRepository;

	@Override
	public List<Bank> findAll() {
		return bankRepository.findAll();
	}

	@Override
	public Bank save(Bank bank) {
		return bankRepository.save(bank);
	}

	@Override
	public Bank findByBankId(Long bankId) {
		return bankRepository.findByBankId(bankId);
	}

}
