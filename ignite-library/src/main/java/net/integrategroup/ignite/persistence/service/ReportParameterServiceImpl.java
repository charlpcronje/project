package net.integrategroup.ignite.persistence.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.integrategroup.ignite.persistence.model.ReportParameter;
import net.integrategroup.ignite.persistence.repository.ReportParameterRepository;

@Service
public class ReportParameterServiceImpl implements ReportParameterService {

	@Autowired
	ReportParameterRepository reportParameterRepository;

	@Override
	public List<ReportParameter> findByReportId(Long reportId) {
		return reportParameterRepository.findByReportId(reportId);
	}

	@Override
	public ReportParameter findByReportParameterId(Long reportParameterId) {
		return reportParameterRepository.findByReportParameterId(reportParameterId);
	}

	@Override
	public ReportParameter save(ReportParameter reportParameter) {
		return reportParameterRepository.save(reportParameter);
	}

	@Override
	public void delete(Long reportParameterId) throws Exception {
		ReportParameter reportParameter = reportParameterRepository.findByReportParameterId(reportParameterId);

		if (reportParameter == null) {
			throw new Exception("Report Parameter not found");
		}
		reportParameterRepository.delete(reportParameter);
	}

}
