package net.integrategroup.ignite.persistence.service;

import java.util.List;

import net.integrategroup.ignite.persistence.model.UiComponent;

public interface UiComponentService {


	List<UiComponent> findAll();


	UiComponent save(UiComponent uiComponentId);

	UiComponent findByUiComponentId(Long uiComponentId);

}
