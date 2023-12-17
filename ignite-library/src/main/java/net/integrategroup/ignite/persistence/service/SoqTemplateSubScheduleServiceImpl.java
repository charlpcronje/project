package net.integrategroup.ignite.persistence.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.integrategroup.ignite.persistence.model.SoqTemplateSubSchedule;
import net.integrategroup.ignite.persistence.repository.SoqTemplateSubScheduleRepository;

@Service
public class SoqTemplateSubScheduleServiceImpl implements SoqTemplateSubScheduleService {
	
	@Autowired
	DatabaseService databaseService;
	
	@Autowired
	SoqTemplateSubScheduleRepository soqTemplateSubScheduleRepository;

	@Override
	public List<SoqTemplateSubSchedule> findAll() {
		return soqTemplateSubScheduleRepository.findAll();
	}

	@Override
	public List<SoqTemplateSubSchedule> findSubScheduleByTemplateId(Long templateId) {
		return soqTemplateSubScheduleRepository.findSubScheduleByTemplateId(templateId);
	}

	@Override
	public SoqTemplateSubSchedule findBySubScheduleId(Long subScheduleId) {
		return soqTemplateSubScheduleRepository.findBySubScheduleId(subScheduleId);
	}

	@Override
	public SoqTemplateSubSchedule save(SoqTemplateSubSchedule soqTemplateSubSchedule) {
		return soqTemplateSubScheduleRepository.save(soqTemplateSubSchedule);
	}

}
