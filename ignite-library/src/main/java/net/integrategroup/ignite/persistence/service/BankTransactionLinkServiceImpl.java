package net.integrategroup.ignite.persistence.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.integrategroup.ignite.persistence.model.BankTransactionLink;
import net.integrategroup.ignite.persistence.model.VBankTransactionLink;
import net.integrategroup.ignite.persistence.repository.BankTransactionLinkRepository;

/**
 * @author Ingrid Marais
 *
 */
@Service
public class BankTransactionLinkServiceImpl implements BankTransactionLinkService {

	@Autowired
	DatabaseService databaseService;

	@Autowired
	BankTransactionLinkRepository bankTransactionLinkRepository;

	@Override
	public List<BankTransactionLink> findAll() {
		return bankTransactionLinkRepository.findAll();
	}

	@Override
	public BankTransactionLink save(BankTransactionLink bankTransactionLink) {
		return bankTransactionLinkRepository.save(bankTransactionLink);
	}

	@Override
	public BankTransactionLink findByBankTransactionLinkId(Long bankTransactionLinkId) {
		return bankTransactionLinkRepository.findByBankTransactionLinkId(bankTransactionLinkId);
	}

	@Override
	public List<VBankTransactionLink> getBankTransactionLinkForParticipantBankDetailsId(Long participantBankDetailsId) {
		return bankTransactionLinkRepository.getBankTransactionLinkForParticipantBankDetailsId(participantBankDetailsId);
	}

	@Override
	public List<VBankTransactionLink> getBankTransactionLinkForBankTransactionId(Long bankTransactionId) {
		return bankTransactionLinkRepository.getBankTransactionLinkForBankTransactionId(bankTransactionId);
	}
}

