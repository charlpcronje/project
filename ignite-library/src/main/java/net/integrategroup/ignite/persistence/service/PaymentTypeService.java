package net.integrategroup.ignite.persistence.service;

import java.util.List;

import net.integrategroup.ignite.persistence.model.PaymentType;

public interface PaymentTypeService {

	public List<PaymentType> findAll();

	public PaymentType findByPaymentTypeId(Long paymentTypeId);

	public PaymentType save(PaymentType paymentType);

	//public void delete(PaymentType paymentType);
	// We rather call a stored procedure to do the checks before deleting, this will give the user better info about foreign key violations


}
