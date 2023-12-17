package net.integrategroup.ignite.persistence.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import net.integrategroup.ignite.utils.JsonDateSerializer;

@Entity
@Table(schema = "ig_db", name = "SoqTemplSubSchedule")
public class SoqTemplateSubSchedule implements Serializable {

	private static final long serialVersionUID = 3884565720721415163L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "SoqTemplSubScheduleId")
	private Long soqTemplSubScheduleId;
	
	@Column(name = "SoqTemplateId")
	private Long soqTemplateId;
	
	@Column(name = "Name")
	private String name;
	
	@Column(name = "Description")
	private String description;
	
	@Column(name = "OrderNumber")
	private Long orderNumber;
	
	@Column(name = "SubReportHeader1")
	private String subReportHeader1;
	
	@Column(name = "SubReportHeader2")
	private String subReportHeader2;
	
	@Column(name = "PageNumberPrefix")
	private String pageNumberPrefix;
	
	@Column(name = "PageNumberPostFix")
	private String pageNumberPostFix;
	
	@Column(name = "StartPageNumber")
	private Long startPageNumber;
	
	@JsonSerialize(using = JsonDateSerializer.class)
	@Column(name = "LastUpdateTimestamp")
	private Date lastUpdateTimestamp;

	@Column(name = "LastUpdateUserName")
	private String lastUpdateUserName;

	public Long getSoqTemplSubScheduleId() {
		return soqTemplSubScheduleId;
	}

	public void setSoqTemplSubScheduleId(Long soqTemplSubScheduleId) {
		this.soqTemplSubScheduleId = soqTemplSubScheduleId;
	}

	public Long getSoqTemplateId() {
		return soqTemplateId;
	}

	public void setSoqTemplateId(Long soqTemplateId) {
		this.soqTemplateId = soqTemplateId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Long getOrderNumber() {
		return orderNumber;
	}

	public void setOrderNumber(Long orderNumber) {
		this.orderNumber = orderNumber;
	}

	public String getSubReportHeader1() {
		return subReportHeader1;
	}

	public void setSubReportHeader1(String subReportHeader1) {
		this.subReportHeader1 = subReportHeader1;
	}

	public String getSubReportHeader2() {
		return subReportHeader2;
	}

	public void setSubReportHeader2(String subReportHeader2) {
		this.subReportHeader2 = subReportHeader2;
	}

	public String getPageNumberPrefix() {
		return pageNumberPrefix;
	}

	public void setPageNumberPrefix(String pageNumberPrefix) {
		this.pageNumberPrefix = pageNumberPrefix;
	}

	public String getPageNumberPostFix() {
		return pageNumberPostFix;
	}

	public void setPageNumberPostFix(String pageNumberPostFix) {
		this.pageNumberPostFix = pageNumberPostFix;
	}

	public Long getStartPageNumber() {
		return startPageNumber;
	}

	public void setStartPageNumber(Long startPageNumber) {
		this.startPageNumber = startPageNumber;
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

	
	
	
}
