package net.integrategroup.ignite.persistence.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.integrategroup.ignite.persistence.model.BankCard;
import net.integrategroup.ignite.persistence.model.VBankCard;
import net.integrategroup.ignite.persistence.repository.BankCardRepository;
import net.integrategroup.ignite.utils.SecurityUtils;

@Service
public class BankCardServiceImpl implements BankCardService {

	@Autowired
	BankCardRepository bankCardRepository;

	@Autowired
	SecurityUtils securityUtils;

	@Override
	public List<BankCard> findAll() {
		return bankCardRepository.findAll();
	}

	@Override
	public List<VBankCard> findByParticipantId(Long participantId) {
		return bankCardRepository.findByParticipantId(participantId);
	}

	@Override
	public List<VBankCard> findByProjectId(Long projectId) {
		return bankCardRepository.findByProjectId(projectId);
	}

	@Override
	public List<VBankCard> findByParticipantBankDetailsId(Long participantBankDetailsId) {
		return bankCardRepository.findByParticipantBankDetailsId(participantBankDetailsId);
	}

	@Override
	public List<VBankCard> findByIndividualId(Long individualId) {
		return bankCardRepository.findByIndividualId(individualId);
	}


	@Override
	public BankCard findByBankCardId(Long bankCardId) {
		return bankCardRepository.findByBankCardId(bankCardId);
	}

	@Override
	public BankCard save(BankCard bankCard) {
		return bankCardRepository.save(bankCard);
	}

	@Override
	public List<VBankCard> findByUsername(String username) {
		return bankCardRepository.findByUsername(username);
	}

}
