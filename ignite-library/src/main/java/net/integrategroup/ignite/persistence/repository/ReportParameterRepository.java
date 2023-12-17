package net.integrategroup.ignite.persistence.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import net.integrategroup.ignite.persistence.model.ReportParameter;

@Repository
public interface ReportParameterRepository extends CrudRepository<ReportParameter, Long> {

	@Query("SELECT rp FROM ReportParameter rp WHERE rp.reportParameterId = ?1")
	ReportParameter findByReportParameterId(Long reportParameterId);

	@Query("SELECT rp FROM ReportParameter rp WHERE rp.reportId = ?1 ORDER BY orderNo")
	List<ReportParameter> findByReportId(Long reportId);

}
