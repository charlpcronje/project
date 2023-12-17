package net.integrategroup.ignite.persistence.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.integrategroup.ignite.persistence.model.PaymentType;
import net.integrategroup.ignite.persistence.repository.PaymentTypeRepository;

@Service
public class PaymentTypeServiceImpl implements PaymentTypeService {

	@Autowired
	PaymentTypeRepository paymentTypeRepository;

	@Override
	public List<PaymentType> findAll() {
		return paymentTypeRepository.findAll();
	}

	@Override
	public PaymentType findByPaymentTypeId(Long paymentTypeId) {
		return paymentTypeRepository.findByPaymentTypeId(paymentTypeId);
	}

	@Override
	public PaymentType save(PaymentType paymentType) {
		return paymentTypeRepository.save(paymentType);
	}

	/*
	public void delete(PaymentType paymentType) {
		paymentTypeRepository.delete(paymentType);
	}
	 */

}
