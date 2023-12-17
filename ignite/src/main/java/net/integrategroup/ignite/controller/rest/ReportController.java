package net.integrategroup.ignite.controller.rest;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import net.integrategroup.ignite.filemanager.FileManager;
import net.integrategroup.ignite.persistence.model.Report;
import net.integrategroup.ignite.persistence.model.ReportParameter;
import net.integrategroup.ignite.persistence.service.ReportParameterService;
import net.integrategroup.ignite.persistence.service.ReportService;
import net.integrategroup.ignite.reports.ReportEngine;
import net.integrategroup.ignite.utils.IgniteConstants;
import net.integrategroup.ignite.utils.IgniteUtils;
import net.integrategroup.ignite.utils.MapUtils;


/**
 * @author Tony De Buys
 *
 */
@RestController
@RequestMapping("/rest/ignite/v1/report")
@MultipartConfig(fileSizeThreshold = IgniteConstants.UPLOAD_FILE_SIZE_LIMIT_MB * 1024 * 1024) // mb -> kb -> bytes
public class ReportController {

	@Autowired
	ReportService reportService;

	@Autowired
	ReportParameterService reportParameterService;

	@Autowired
	ReportEngine reportEngine;

	@GetMapping("/active")
	public ResponseEntity<?> getActiveReports() {
		try {
			List<Report> reports = reportEngine.getActiveReports();

			return ResponseEntity.ok(reports);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@GetMapping()
	public ResponseEntity<?> getAllReports() {
		try {
			List<Report> reports = reportEngine.getAllReports();

			return ResponseEntity.ok(reports);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@PostMapping()
	public ResponseEntity<?> saveReport(@RequestBody HashMap<String, Object> data) {
		try {
			MapUtils mu = new MapUtils();
			Report report = null;

			Long reportId = mu.getAsLongOrNull(data, "reportId");
			String reportFilename = mu.getAsStringOrNull(data, "reportFilename");
			String reportName = mu.getAsStringOrNull(data, "reportName");
			String reportDescription = mu.getAsStringOrNull(data, "reportDescription");
			String permissionCodeRequired = mu.getAsStringOrNull(data, "permissionCodeRequired");
			String activeInd = mu.getAsStringOrNull(data, "activeInd");
			String allowExcelExportInd = mu.getAsStringOrNull(data, "allowExcelExportInd");

			if ((reportId == null) || (reportId == -1)) {
				report = new Report();
			} else {
				report = reportService.findByReportId(reportId);
			}

			report.setReportFilename(reportFilename);
			report.setReportName(reportName);
			report.setReportDescription(reportDescription);
			report.setPermissionCodeRequired(permissionCodeRequired);
			report.setAllowExcelExportInd(allowExcelExportInd);
			report.setActiveInd(activeInd);

			report = reportService.save(report);

			return ResponseEntity.ok(report);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@GetMapping("/run/{reportId}")
	public ResponseEntity<?> runReport(@PathVariable("reportId") Long reportId, @RequestParam Map<String, Object> parameters) {
		return reportService.runReport(reportId, parameters);
	}

	@PostMapping("/delete")
	public ResponseEntity<?> deleteReport(@RequestBody HashMap<String, Object> data) {
		try {
			MapUtils mu = new MapUtils();

			Long reportId = mu.getAsLongOrNull(data, "reportId");

			Report report = reportService.findByReportId(reportId);
			if (report == null) {
				throw new Exception("The report was not found");
			}
			reportService.delete(report);

			return ResponseEntity.ok().build();
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@PostMapping("/file-manager/delete")
	public ResponseEntity<?> fileManagerDelete(@RequestBody HashMap<String, Object> data) {
		try {
			MapUtils mu = new MapUtils();

			String id = mu.getAsStringOrNull(data, "id");

			reportEngine.getFileManager().delete(id);

			return ResponseEntity.ok().build();
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@GetMapping("/file-manager")
	public ResponseEntity<?> getFileManager(HttpServletRequest request) {
		try {
			Object result = null;

			String directory = reportEngine.getReportsPath();
			String dirParam = request.getParameter("id");
			String action = request.getParameter("action");

			if ((dirParam != null) && (!dirParam.isEmpty())) {
				directory = dirParam;
			}

			FileManager fm = new FileManager(reportEngine.getReportsPath());

			if (action == null) {
				result = fm.getDiskEntities(directory);
			} else {
				if ("download".equalsIgnoreCase(action)) {
					String filename = IgniteUtils.getTerminatedPath(reportEngine.getReportsPath()) + directory;
					File f = new File(filename);
					byte[] documentBody = fm.getFileAsByteArray(filename);

					HttpHeaders responseHeaders = new HttpHeaders();
					responseHeaders.set("charset", "utf-8");
					responseHeaders.setContentType(MediaType.APPLICATION_OCTET_STREAM);
					responseHeaders.setContentLength(documentBody.length);
					responseHeaders.set("Content-disposition", "attachment; filename=" + f.getName());

					return new ResponseEntity<>(documentBody, responseHeaders, HttpStatus.OK);
				} else {
					result = fm.executeAction(action, directory, request);
				}
			}

			return ResponseEntity.ok(result);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@GetMapping("/parameter/{reportId}")
	public ResponseEntity<?> getReportParameters(@PathVariable("reportId") Long reportId) {
		try {
			List<ReportParameter> reportParameters = reportParameterService.findByReportId(reportId);

			return ResponseEntity.ok(reportParameters);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@GetMapping("/parameter/html/{reportId}/{inline}")
	public ResponseEntity<?> getReportParametersHtml(@PathVariable("reportId") Long reportId,
			@PathVariable(name="inline", required=false) Boolean inline) {
		try {
			if (inline == null) {
				inline = false;
			}

			String html = reportEngine.getReportParametersHtml(reportId, inline);
			Map<String, Object> result = new HashMap<>();

			result.put("html", html);

			return ResponseEntity.ok(result);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@PostMapping("/parameter")
	public ResponseEntity<?> saveReportParameter(@RequestBody HashMap<String, Object> data) {
		try {
			MapUtils mu = new MapUtils();

			Long reportParameterId = mu.getAsLongOrNull(data, "reportParameterId");
			Long reportId = mu.getAsLongOrNull(data, "reportId");
			String parameterName = mu.getAsStringOrNull(data, "parameterName");
			String parameterLabel = mu.getAsStringOrNull(data, "parameterLabel");
			String parameterType = mu.getAsStringOrNull(data, "parameterType");
			String selectSql = mu.getAsStringOrNull(data, "selectSql");
			Integer orderNo = mu.getAsIntegerOrNull(data, "orderNo");

			ReportParameter reportParameter = null;
			if (reportParameterId == null) {
				reportParameter = new ReportParameter();
			} else {
				reportParameter = reportParameterService.findByReportParameterId(reportParameterId);
			}

			reportParameter.setReportId(reportId);
			reportParameter.setParameterName(parameterName);
			reportParameter.setParameterLabel(parameterLabel);
			reportParameter.setParameterType(parameterType);
			reportParameter.setSelectSql(selectSql);
			reportParameter.setOrderNo(orderNo);

			reportParameter = reportParameterService.save(reportParameter);

			return ResponseEntity.ok().body(reportParameter);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}


	@PostMapping("/parameter/delete")
	public ResponseEntity<?> deleteReportParameter(@RequestBody HashMap<String, Object> data) {
		try {
			MapUtils mu = new MapUtils();

			Long reportParameterId = mu.getAsLongOrNull(data, "reportParameterId");

			reportParameterService.delete(reportParameterId);

			return ResponseEntity.ok().build();
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	public Long findReportByName(String reportName) {
		Report r = reportService.findReportByName(reportName);

		if (r == null) {
			return null;
		}

		return r.getReportId();
	}

}
