package net.integrategroup.ignite.persistence.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.integrategroup.ignite.persistence.model.TypicalFolderStructure;
import net.integrategroup.ignite.persistence.model.VTfsTree;
import net.integrategroup.ignite.persistence.model.VTypicalFolderStructure;
import net.integrategroup.ignite.persistence.repository.TypicalFolderStructureRepository;

/** @author Generated by Johannes Marais (JohannesSQL v7.7) **/
/** ******* ********* ** 2023-11-01 10:26:07 ******** ***** **/

@Service
public class TypicalFolderStructureServiceImpl implements TypicalFolderStructureService {

	@Autowired
	TypicalFolderStructureRepository typicalFolderStructureRepository;

	@Override
	public List<TypicalFolderStructure> findAll() {
		return typicalFolderStructureRepository.findAll();
	}

	@Override
	public TypicalFolderStructure getByTypicalFolderStructureId(Long typicalFolderStructureId) {
		return typicalFolderStructureRepository.getByTypicalFolderStructureId(typicalFolderStructureId);
	}

	@Override
	public TypicalFolderStructure save(TypicalFolderStructure typicalFolderStructure) {
		return typicalFolderStructureRepository.save(typicalFolderStructure);
	}

//	@Override                               //One record from View
//	public VTypicalFolderStructure getByVTypicalFolderStructureId(Long typicalFolderStructureId) {
//		return typicalFolderStructureRepository.getByVTypicalFolderStructureId(typicalFolderStructureId);
//	}


	@Override								//All in tree view
	public List<VTfsTree> findListAllInTreeView() {
		return typicalFolderStructureRepository.findListAllInTreeView();
	}

	@Override								//Top Level in tree view
	public List<VTfsTree> findListTopLevelInTreeView() {
		return typicalFolderStructureRepository.findListTopLevelInTreeView();
	}
	
	@Override								//All in view
	public List<VTypicalFolderStructure> findListAllInView() {
		return typicalFolderStructureRepository.findListAllInView();
	}	

//	@Override								//List from View without parameter
//	public List<VTypicalFolderStructure> findListTypicalFolderStructureXXX() {
//		return typicalFolderStructureRepository.findListVTypicalFolderStructureXXX();
//	}

//	@Override								//List from View that needs parameter
//	public List<VTypicalFolderStructure> findListTypicalFolderStructureXXX(Long paramName) {
//		return typicalFolderStructureRepository.findListVTypicalFolderStructureXXX(paramName);
//	}

//	@Override								//List from View that needs multiple parameters
//	public List<VTypicalFolderStructure> findListTypicalFolderStructureXXX(Long paramId, String paramName, String paramName2) {
//		return typicalFolderStructureRepository.findListVTypicalFolderStructureXXX(paramId, paramName, paramName2);
//	}

//	@Override								//List from View that needs date parameters
//	public List<VTypicalFolderStructure> findListTypicalFolderStructureXXX(Long paramId, Date fd, Date ld) {
//		return typicalFolderStructureRepository.findListVTypicalFolderStructureXXX(paramId, fd, ld);
//	}



	@Override
	public List<TypicalFolderStructure> findListTypicalFolderStructureForServiceDisciplineIdIndustry(Long serviceDisciplineId) {
		return typicalFolderStructureRepository.findListTypicalFolderStructureForServiceDisciplineIdIndustry(serviceDisciplineId);
	}

	@Override
	public List<TypicalFolderStructure> findListTypicalFolderStructureForTypicalFolderStructureIdParent(Long typicalFolderStructureId) {
		return typicalFolderStructureRepository.findListTypicalFolderStructureForTypicalFolderStructureIdParent(typicalFolderStructureId);
	}



}