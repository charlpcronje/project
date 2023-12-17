package net.integrategroup.ignite.persistence.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import net.integrategroup.ignite.persistence.model.SoqTemplateSubSchedule;

public interface SoqTemplateSubScheduleRepository extends CrudRepository<SoqTemplateSubSchedule, Long> {
	
	@Query("SELECT stss FROM SoqTemplateSubSchedule stss")
	List<SoqTemplateSubSchedule> findAll();
	
	@Query("SELECT stss FROM SoqTemplateSubSchedule stss " +
			"WHERE SoqTemplateId = ?1")
	List<SoqTemplateSubSchedule> findSubScheduleByTemplateId(Long templateId);
	
	@Query("SELECT stss FROM SoqTemplateSubSchedule stss WHERE stss.soqTemplSubScheduleId = ?1")
	SoqTemplateSubSchedule findBySubScheduleId(Long subScheduleId);
}
