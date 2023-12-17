package net.integrategroup.ignite.persistence.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import net.integrategroup.ignite.persistence.model.NonProjectRelatedAgreement;
import net.integrategroup.ignite.persistence.model.VHumanResourceUnionList;
import net.integrategroup.ignite.persistence.model.VNonProjectRelatedAgreement;

@Repository
public interface NonProjectRelatedAgreementRepository extends CrudRepository<NonProjectRelatedAgreement, Long> {

	@Override
	List<NonProjectRelatedAgreement> findAll();

	@Query("SELECT npra FROM VNonProjectRelatedAgreement npra WHERE"
			+ "    npra.participantIdPayer = ?1")
	List<VNonProjectRelatedAgreement> getNonProjectRelatedAgreementForParticipant(Long participantId);

	@Query("SELECT b FROM NonProjectRelatedAgreement b WHERE" +
			"    b.nonProjectRelatedAgreementId = ?1")
	NonProjectRelatedAgreement findByNonProjectRelatedAgreementId(Long nonProjectRelatedAgreementId);


	@Query("SELECT hr FROM VHumanResourceUnionList hr WHERE"
			+ "    hr.participantIdPayer = ?1")
//			+ "    order by hr.name")
	List<VHumanResourceUnionList> findHrByParticipantId(Long participantId);

	@Query("SELECT npra FROM VNonProjectRelatedAgreement npra WHERE"
			+ "    npra.participantIdBeneficiary = ?1")
	List<VNonProjectRelatedAgreement> getWhoParticipantIsAResourceOf(Long participantId);




}
