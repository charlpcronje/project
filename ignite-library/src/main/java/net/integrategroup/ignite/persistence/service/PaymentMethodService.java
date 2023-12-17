package net.integrategroup.ignite.persistence.service;

import java.util.List;

import net.integrategroup.ignite.persistence.model.PaymentMethod;

public interface PaymentMethodService {

	public List<PaymentMethod> findAll();

	public PaymentMethod findByPaymentMethodCode(String paymentMethodCode);

	public PaymentMethod save(PaymentMethod paymentMethod);

}
