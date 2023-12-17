package net.integrategroup.ignite.persistence.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import net.integrategroup.ignite.persistence.model.TapSubscription;

@Repository
public interface TapSubscriptionRepository extends CrudRepository<TapSubscription, String> {

	@Override
	List<TapSubscription> findAll();

	@Query("Select i   FROM  TapSubscription i    WHERE  i.tapSubscriptionCode = ?1")
	TapSubscription getByTapSubscriptionCode(String tapSubscriptionCode);


}