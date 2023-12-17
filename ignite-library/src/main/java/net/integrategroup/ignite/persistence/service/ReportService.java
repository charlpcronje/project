package net.integrategroup.ignite.persistence.service;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;

import net.integrategroup.ignite.persistence.model.Report;
import net.integrategroup.ignite.persistence.model.ReportParameter;

/**
 * @author Tony De Buys
 *
 */
public interface ReportService {

	Report findByReportId(Long reportId);

	List<Report> findAll();

	List<Report> findActiveAndValid();

	Report save(Report report);

	void delete(Report report);

	List<ReportParameter> findReportParametersByReportId(Long reportId);

	Report findReportByName(String reportName);

	ResponseEntity<?> runReport(Long reportId, Map<String, Object> parameters);

}
