package net.integrategroup.ignite.persistence.service;

import java.util.List;

import net.integrategroup.ignite.persistence.model.ReportParameter;

public interface ReportParameterService {

	List<ReportParameter> findByReportId(Long reportId);

	ReportParameter findByReportParameterId(Long reportParameterId);

	ReportParameter save(ReportParameter reportParameter);

	void delete(Long reportParameterId) throws Exception;

}
