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
@Table(schema = "ig_db", name = "soqsubschedule")
public class SoqSubSchedule implements Serializable {


	private static final long serialVersionUID = -5717379331781225810L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "SoqSubScheduleId")
	private Long SoqSubScheduleId;
	
	@Column(name = "ScheduleOfQuantitiesId")
	private Long ScheduleOfQuantitiesId;
	
	@Column(name = "ProjectParticipantIdResponsible")
	private Long ProjectParticipantIdResponsible;

	@Column(name = "Name")
	private String Name;

	@Column(name = "Description")
	private Long Description;

	@Column(name = "OrderNumber")
	private Long OrderNumber;

	@Column(name = "SubReportHeader1")
	private Long SubReportHeader1;

	@Column(name = "SubReportHeader2")
	private Long SubReportHeader2;

	@Column(name = "PageNumberPrefix")
	private Long PageNumberPrefix;

	@Column(name = "PageNumberPostFix")
	private Long PageNumberPostFix;

	@Column(name = "StartPageNumber")
	private Long StartPageNumber;

	@Column(name = "LastUpdateTimestamp")
	private Long lastUpdateTimestamp;

	@Column(name = "LastUpdateUserName")
	private Long lastUpdateUserName;

	public Long getSoqSubScheduleId() {
		return SoqSubScheduleId;
	}

	public void setSoqSubScheduleId(Long soqSubScheduleId) {
		SoqSubScheduleId = soqSubScheduleId;
	}

	public Long getScheduleOfQuantitiesId() {
		return ScheduleOfQuantitiesId;
	}

	public void setScheduleOfQuantitiesId(Long scheduleOfQuantitiesId) {
		ScheduleOfQuantitiesId = scheduleOfQuantitiesId;
	}

	public Long getProjectParticipantIdResponsible() {
		return ProjectParticipantIdResponsible;
	}

	public void setProjectParticipantIdResponsible(Long projectParticipantIdResponsible) {
		ProjectParticipantIdResponsible = projectParticipantIdResponsible;
	}

	public String getName() {
		return Name;
	}

	public void setName(String name) {
		Name = name;
	}

	public Long getDescription() {
		return Description;
	}

	public void setDescription(Long description) {
		Description = description;
	}

	public Long getOrderNumber() {
		return OrderNumber;
	}

	public void setOrderNumber(Long orderNumber) {
		OrderNumber = orderNumber;
	}

	public Long getSubReportHeader1() {
		return SubReportHeader1;
	}

	public void setSubReportHeader1(Long subReportHeader1) {
		SubReportHeader1 = subReportHeader1;
	}

	public Long getSubReportHeader2() {
		return SubReportHeader2;
	}

	public void setSubReportHeader2(Long subReportHeader2) {
		SubReportHeader2 = subReportHeader2;
	}

	public Long getPageNumberPrefix() {
		return PageNumberPrefix;
	}

	public void setPageNumberPrefix(Long pageNumberPrefix) {
		PageNumberPrefix = pageNumberPrefix;
	}

	public Long getPageNumberPostFix() {
		return PageNumberPostFix;
	}

	public void setPageNumberPostFix(Long pageNumberPostFix) {
		PageNumberPostFix = pageNumberPostFix;
	}

	public Long getStartPageNumber() {
		return StartPageNumber;
	}

	public void setStartPageNumber(Long startPageNumber) {
		StartPageNumber = startPageNumber;
	}

	public Long getLastUpdateTimestamp() {
		return lastUpdateTimestamp;
	}

	public void setLastUpdateTimestamp(Long lastUpdateTimestamp) {
		this.lastUpdateTimestamp = lastUpdateTimestamp;
	}

	public Long getLastUpdateUserName() {
		return lastUpdateUserName;
	}

	public void setLastUpdateUserName(Long lastUpdateUserName) {
		this.lastUpdateUserName = lastUpdateUserName;
	}

	

}