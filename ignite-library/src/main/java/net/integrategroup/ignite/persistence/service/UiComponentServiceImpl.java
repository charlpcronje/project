package net.integrategroup.ignite.persistence.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.integrategroup.ignite.persistence.model.UiComponent;
import net.integrategroup.ignite.persistence.repository.UiComponentRepository;

@Service
public class UiComponentServiceImpl implements UiComponentService {

	@Autowired
	UiComponentRepository uiComponentRepository;



	@Override
	public List<UiComponent> findAll() {
		return uiComponentRepository.findAll();
	}


	@Override
	public UiComponent save(UiComponent uiComponent) {
		return uiComponentRepository.save(uiComponent);
	}

	@Override
	public UiComponent findByUiComponentId(Long uiComponentId) {
		return uiComponentRepository.findByUiComponentId(uiComponentId);
	}





}
