package net.integrategroup.ignite.persistence.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.integrategroup.ignite.persistence.model.StudyInstitute;
import net.integrategroup.ignite.persistence.repository.StudyInstituteRepository;

/** @author Generated by Johannes Marais (JohannesSQL v7.7) **/
/** ******* ********* ** 2023-10-16 19:51:59 ******** ***** **/

@Service
public class StudyInstituteServiceImpl implements StudyInstituteService {

	@Autowired
	StudyInstituteRepository studyInstituteRepository;

	@Override
	public List<StudyInstitute> findAll() {
		return studyInstituteRepository.findAll();
	}

	@Override
	public StudyInstitute getByStudyInstituteId(Long studyInstituteId) {
		return studyInstituteRepository.getByStudyInstituteId(studyInstituteId);
	}

	@Override
	public StudyInstitute save(StudyInstitute studyInstitute) {
		return studyInstituteRepository.save(studyInstitute);
	}

//	@Override                               //One record from View
//	public VStudyInstitute getByVStudyInstituteId(Long studyInstituteId) {
//		return studyInstituteRepository.getByVStudyInstituteId(studyInstituteId);
//	}




}