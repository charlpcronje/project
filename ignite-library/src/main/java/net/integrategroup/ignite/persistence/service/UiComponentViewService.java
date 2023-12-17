package net.integrategroup.ignite.persistence.service;

import java.util.List;

import net.integrategroup.ignite.persistence.model.UiComponentView;

public interface UiComponentViewService {

	List<UiComponentView> getMenu(String username);


	List<UiComponentView> findAll();

	List<UiComponentView> findByPermissionCode(String permissionCode);


	UiComponentView save(UiComponentView uiComponentId);

	UiComponentView findByUiComponentId(Long uiComponentId);

}
