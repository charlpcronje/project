package net.integrategroup.ignite.persistence.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.integrategroup.ignite.persistence.model.UiComponentView;
import net.integrategroup.ignite.persistence.repository.UiComponentViewRepository;

@Service
public class UiComponentViewServiceImpl implements UiComponentViewService {

	@Autowired
	UiComponentViewRepository uiComponentViewRepository;

	@Override
	public List<UiComponentView> getMenu(String username) {
		return uiComponentViewRepository.getMenu(username);
	}


	@Override
	public List<UiComponentView> findAll() {
		return uiComponentViewRepository.findAll();
	}

	@Override
	public List<UiComponentView> findByPermissionCode(String permissionCode) {
		return uiComponentViewRepository.findByPermissionCode(permissionCode);
	}

	@Override
	public UiComponentView save(UiComponentView uiComponentView) {
		return uiComponentViewRepository.save(uiComponentView);
	}

	@Override
	public UiComponentView findByUiComponentId(Long uiComponentId) {
		return uiComponentViewRepository.findByUiComponentId(uiComponentId);
	}





}
