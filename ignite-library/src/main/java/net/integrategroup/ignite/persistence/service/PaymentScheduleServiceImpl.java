package net.integrategroup.ignite.persistence.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.integrategroup.ignite.persistence.model.PaymentSchedule;
import net.integrategroup.ignite.persistence.repository.PaymentScheduleRepository;

/**
 * @author Ingrid Marais
 **/

@Service
public class PaymentScheduleServiceImpl implements PaymentScheduleService {

	@Autowired
	DatabaseService databaseService;

	@Autowired
	PaymentScheduleRepository paymentScheduleRepository;

	@Override
	public PaymentSchedule save(PaymentSchedule paymentSchedule) {
		return paymentScheduleRepository.save(paymentSchedule);
	}

	@Override
	public List<PaymentSchedule> findAllForAgreement(Long agreementBetweenParticipantsId) {
		return paymentScheduleRepository.findAllForAgreement(agreementBetweenParticipantsId);
	}

	@Override
	public PaymentSchedule findByPaymentScheduleId(Long paymentScheduleId) {
		return paymentScheduleRepository.findByPaymentScheduleId(paymentScheduleId);
	}

	//@Override
	//public void delete(PaymentSchedule paymentSchedule) {
	//	paymentScheduleRepository.delete(paymentSchedule);
	//}
}
