package net.integrategroup.ignite.persistence.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import net.integrategroup.ignite.persistence.model.PaymentMethod;

@Repository
public interface PaymentMethodRepository extends CrudRepository<PaymentMethod, String> {

	@Override
	List<PaymentMethod> findAll();

	@Query("SELECT pm FROM PaymentMethod pm WHERE paymentMethodCode = ?1")
	PaymentMethod findByPaymentMethodCode(String paymentMethodCode);

}
