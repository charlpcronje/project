package net.integrategroup.ignite.persistence.service;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import net.integrategroup.ignite.persistence.model.Report;
import net.integrategroup.ignite.persistence.model.ReportParameter;
import net.integrategroup.ignite.persistence.repository.ReportParameterRepository;
import net.integrategroup.ignite.persistence.repository.ReportRepository;
import net.integrategroup.ignite.reports.ReportEngine;
import net.integrategroup.ignite.utils.IgniteConstants;
import net.integrategroup.ignite.utils.IgniteUtils;

/**
 * @author Tony De Buys
 *
 */
@Service
public class ReportServiceImpl implements ReportService {

	@Autowired
	ReportEngine reportEngine;

	@Autowired
	ReportRepository reportRepository;

	@Autowired
	ReportParameterRepository reportParameterRepository;

	@Override
	public Report findByReportId(Long reportId) {
		return reportRepository.findByReportId(reportId);
	}

	@Override
	public List<Report> findAll() {
		return reportRepository.findAll();
	}

	@Override
	public List<Report> findActiveAndValid() {
		return reportRepository.findActiveAndValid();
	}

	@Override
	public Report save(Report report) {
		String fileExistsInd = reportEngine.fileExists(report) ? IgniteConstants.FLAG_YES : IgniteConstants.FLAG_NO;

		// update the record
		report.setFileExistsInd(fileExistsInd);

		return reportRepository.save(report);
	}

	@Override
	public void delete(Report report) {
		reportRepository.delete(report);
	}

	@Override
	public List<ReportParameter> findReportParametersByReportId(Long reportId) {
		return reportParameterRepository.findByReportId(reportId);
	}

	@Override
	public Report findReportByName(String reportName) {
		return reportRepository.findReportByName(reportName);
	}

	@Override
	public ResponseEntity<?> runReport(Long reportId, Map<String, Object> parameters) {
		try {
			Report report = findByReportId(reportId);

			String generationType = reportEngine.getReportUrlParameter(parameters, IgniteConstants.GENERATION_TYPE);
			String generatedReportFilename = reportEngine.runReport(reportId, parameters);
			String baseFilename = report.getReportFilename() + IgniteUtils.getFileExtension(generatedReportFilename);

			// Download the temporary report file
			Path fileLocation = Paths.get(generatedReportFilename);
			byte[] documentBody = Files.readAllBytes(fileLocation);

			HttpHeaders responseHeaders = new HttpHeaders();
			responseHeaders.set("charset", "utf-8");
			responseHeaders.setContentLength(documentBody.length);

			if ((generationType != null) && ("inline".equalsIgnoreCase(generationType))) {
				// Display inline
				// Source: https://community.jaspersoft.com/questions/530414/ask-embedded-jasper-report-view-web-page
				responseHeaders.setContentType(MediaType.APPLICATION_PDF);
				responseHeaders.set("Content-disposition", "inline; filename=" + baseFilename);
			} else {
				// Anything else is a download...
				responseHeaders.setContentType(MediaType.APPLICATION_OCTET_STREAM);
				responseHeaders.set("Content-disposition", "attachment; filename=" + baseFilename);
			}

			return new ResponseEntity<>(documentBody, responseHeaders, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}


}
