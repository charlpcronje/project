package net.integrategroup.ignite.persistence.service;

import java.util.List;

import net.integrategroup.ignite.persistence.model.BankCard;
import net.integrategroup.ignite.persistence.model.VBankCard;

public interface BankCardService {

	List<BankCard> findAll();

	List<VBankCard> findByParticipantId(Long participantId);
	List<VBankCard> findByProjectId(Long projectId);

	List<VBankCard> findByParticipantBankDetailsId(Long participantBankDetailsId);

	List<VBankCard> findByIndividualId(Long individualId);

	BankCard findByBankCardId(Long bankCardId);

	BankCard save(BankCard bankCard);

	List<VBankCard> findByUsername(String username);


}
