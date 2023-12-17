package net.integrategroup.ignite.persistence.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.integrategroup.ignite.persistence.model.TapSubscription;
import net.integrategroup.ignite.persistence.repository.TapSubscriptionRepository;

@Service
public class TapSubscriptionServiceImpl implements TapSubscriptionService {

	@Autowired
	TapSubscriptionRepository tapSubscriptionRepository;

	@Override
	public List<TapSubscription> findAll() {
		return tapSubscriptionRepository.findAll();
	}

	@Override
	public TapSubscription getByTapSubscriptionCode(String tapSubscriptionCode) {
		return tapSubscriptionRepository.getByTapSubscriptionCode(tapSubscriptionCode);
	}

	@Override
	public TapSubscription save(TapSubscription tapSubscription) {
		return tapSubscriptionRepository.save(tapSubscription);
	}



}