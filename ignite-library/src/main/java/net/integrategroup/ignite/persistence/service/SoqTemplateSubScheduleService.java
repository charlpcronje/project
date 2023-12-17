package net.integrategroup.ignite.persistence.service;

import java.util.List;

import net.integrategroup.ignite.persistence.model.SoqTemplateSubSchedule;

public interface SoqTemplateSubScheduleService {
	
	List<SoqTemplateSubSchedule> findAll();
	
	List<SoqTemplateSubSchedule> findSubScheduleByTemplateId(Long templateId);
	
	SoqTemplateSubSchedule findBySubScheduleId(Long subScheduleId);
	
	SoqTemplateSubSchedule save(SoqTemplateSubSchedule soqTemplateSubSchedule);
}
