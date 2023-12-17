package net.integrategroup.ignite.persistence.service;

import java.util.List;

import net.integrategroup.ignite.persistence.model.TapSubscription;


public interface TapSubscriptionService {

	List<TapSubscription> findAll();

	TapSubscription getByTapSubscriptionCode(String tapSubscriptionCode);            //retrieves one record, by PrimaryKey

	TapSubscription save(TapSubscription tapSubscription);



}