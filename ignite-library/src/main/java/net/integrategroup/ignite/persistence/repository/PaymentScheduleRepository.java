package net.integrategroup.ignite.persistence.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import net.integrategroup.ignite.persistence.model.PaymentSchedule;

@Repository
public interface PaymentScheduleRepository extends CrudRepository<PaymentSchedule, Long> {

	@Query("SELECT ps FROM PaymentSchedule ps WHERE ps.agreementBetweenParticipantsId = ?1")
	List<PaymentSchedule> findAllForAgreement(Long agrrementBetweenParticipantsId);


	@Query("SELECT ps FROM PaymentSchedule ps WHERE ps.paymentScheduleId = ?1")
	PaymentSchedule findByPaymentScheduleId(Long paymentScheduleId);

}
