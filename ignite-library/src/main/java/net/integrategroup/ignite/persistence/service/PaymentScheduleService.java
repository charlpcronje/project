package net.integrategroup.ignite.persistence.service;

import java.util.List;

import net.integrategroup.ignite.persistence.model.PaymentSchedule;

public interface PaymentScheduleService {

	PaymentSchedule save(PaymentSchedule paymentSchedule);

	List<PaymentSchedule> findAllForAgreement(Long agreementBetweenParticipantsId);

	PaymentSchedule findByPaymentScheduleId(Long paymentScheduleId);

	//void delete(PaymentSchedule paymentSchedule);
	// We rather call a stored procedure to do the checks before deleting, this will give the user better info about foreign key violations


}
