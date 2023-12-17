package net.integrategroup.ignite.persistence.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import net.integrategroup.ignite.persistence.model.PaymentType;

@Repository
public interface PaymentTypeRepository extends CrudRepository<PaymentType, Long> {

	@Override
	List<PaymentType> findAll();

	@Query("SELECT f FROM PaymentType f WHERE paymentTypeId = ?1")
	PaymentType findByPaymentTypeId(Long paymentTypeId);

}
