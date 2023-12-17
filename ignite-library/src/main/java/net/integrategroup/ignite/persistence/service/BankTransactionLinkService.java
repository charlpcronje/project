package net.integrategroup.ignite.persistence.service;

import java.util.List;

import net.integrategroup.ignite.persistence.model.BankTransactionLink;
import net.integrategroup.ignite.persistence.model.VBankTransactionLink;

public interface BankTransactionLinkService {

	List<BankTransactionLink> findAll();

	BankTransactionLink save(BankTransactionLink bankTransactionLink);

	BankTransactionLink findByBankTransactionLinkId(Long bankTransactionLinkId);

	List<VBankTransactionLink> getBankTransactionLinkForParticipantBankDetailsId(Long participantBankDetailsId);

	List<VBankTransactionLink> getBankTransactionLinkForBankTransactionId(Long bankTransactionId);

}
