package net.integrategroup.ignite.persistence.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import net.integrategroup.ignite.utils.JsonDateSerializer;

@Entity
@Table(schema="ig_db", name="Report")
public class Report implements Serializable {
	@JsonIgnore
	@Transient
	private Logger logger = Logger.getLogger(Report.class.getName());

	private static final long serialVersionUID = 5731817973435815958L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="ReportId")
	private Long reportId;

	@Column(name="ReportFilename")
	private String reportFilename;

	@Column(name="ReportName")
	private String reportName;

	@Column(name="ReportDescription")
	private String reportDescription;

	@Column(name="PermissionCodeRequired")
	private String permissionCodeRequired;

	@Column(name="AllowExcelExportInd")
	private String allowExcelExportInd;

	@Column(name="FileExistsInd")
	private String fileExistsInd;

	@Column(name="ActiveInd")
	private String activeInd;

	@JsonSerialize(using=JsonDateSerializer.class)
	@Column(name="LastUpdateTimestamp")
	private Date lastUpdateTimestamp;

	@Column(name="LastUpdateUserName")
	private String lastUpdateUserName;

	@OneToMany(targetEntity=ReportParameter.class, fetch=FetchType.EAGER)
	@JoinColumn(name="ReportId",
	referencedColumnName="ReportId",
	updatable=false,
	insertable=false,
	nullable=true)
	private List<ReportParameter> parameters;

	public Long getReportId() {
		return reportId;
	}

	public void setReportId(Long reportId) {
		this.reportId = reportId;
	}

	public String getReportFilename() {
		return reportFilename;
	}

	public void setReportFilename(String reportFilename) {
		this.reportFilename = reportFilename;
	}

	public String getReportName() {
		return reportName;
	}

	public void setReportName(String reportName) {
		this.reportName = reportName;
	}

	public String getReportDescription() {
		return reportDescription;
	}

	public void setReportDescription(String reportDescription) {
		this.reportDescription = reportDescription;
	}

	public String getPermissionCodeRequired() {
		return permissionCodeRequired;
	}

	public void setPermissionCodeRequired(String permissionCodeRequired) {
		this.permissionCodeRequired = permissionCodeRequired;
	}

	public String getActiveInd() {
		return activeInd;
	}

	public void setActiveInd(String activeInd) {
		this.activeInd = activeInd;
	}

	public Date getLastUpdateTimestamp() {
		return lastUpdateTimestamp;
	}

	public void setLastUpdateTimestamp(Date lastUpdateTimestamp) {
		this.lastUpdateTimestamp = lastUpdateTimestamp;
	}

	public String getLastUpdateUserName() {
		return lastUpdateUserName;
	}

	public void setLastUpdateUserName(String lastUpdateUserName) {
		this.lastUpdateUserName = lastUpdateUserName;
	}

	public String getAllowExcelExportInd() {
		return allowExcelExportInd;
	}

	public void setAllowExcelExportInd(String allowExcelExportInd) {
		this.allowExcelExportInd = allowExcelExportInd;
	}

	public List<ReportParameter> getParameters() {
		return parameters;
	}

	public void setParameters(List<ReportParameter> parameters) {
		this.parameters = parameters;
	}

	public String getFileExistsInd() {
		return fileExistsInd;
	}

	public void setFileExistsInd(String fileExistsInd) {
		this.fileExistsInd = fileExistsInd;
	}
}
