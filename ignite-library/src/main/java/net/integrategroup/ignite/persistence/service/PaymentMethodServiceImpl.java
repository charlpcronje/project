package net.integrategroup.ignite.persistence.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.integrategroup.ignite.persistence.model.PaymentMethod;
import net.integrategroup.ignite.persistence.repository.PaymentMethodRepository;

@Service
public class PaymentMethodServiceImpl implements PaymentMethodService {

	@Autowired
	PaymentMethodRepository paymentMethodRepository;

	@Override
	public List<PaymentMethod> findAll() {
		return paymentMethodRepository.findAll();
	}

	@Override
	public PaymentMethod findByPaymentMethodCode(String paymentMethodCode) {
		return paymentMethodRepository.findByPaymentMethodCode(paymentMethodCode);
	}

	@Override
	public PaymentMethod save(PaymentMethod paymentMethod) {
		return paymentMethodRepository.save(paymentMethod);
	}

}
