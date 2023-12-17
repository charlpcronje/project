package net.integrategroup.ignite.persistence.service;

import java.util.List;

import net.integrategroup.ignite.persistence.model.NonProjectRelatedAgreement;
import net.integrategroup.ignite.persistence.model.VHumanResourceUnionList;
import net.integrategroup.ignite.persistence.model.VNonProjectRelatedAgreement;

public interface NonProjectRelatedAgreementService {

	List<NonProjectRelatedAgreement> findAll();

	NonProjectRelatedAgreement save(NonProjectRelatedAgreement nonProjectRelatedAgreement);

	List<VNonProjectRelatedAgreement> getNonProjectRelatedAgreementForParticipant(Long participantId);

	NonProjectRelatedAgreement findByNonProjectRelatedAgreementId(Long nonProjectRelatedAgreementId);

	List<VHumanResourceUnionList> findHrByParticipantId(Long participantId);

	List<VNonProjectRelatedAgreement> getWhoParticipantIsAResourceOf(Long participantId);

	//void delete(NonProjectRelatedAgreement participantBankDetails);
	// We rather call a stored procedure to do the checks before deleting, this will give the user better info about foreign key violations

}
