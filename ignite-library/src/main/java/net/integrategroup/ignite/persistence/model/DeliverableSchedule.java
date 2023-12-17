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

//  @author Ulrich van Staden

@Entity
@Table(schema = "ig_db", name = "DeliverableSchedule")
public class DeliverableSchedule implements Serializable {

	private static final long serialVersionUID = -8105293298932603161L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "DeliverableScheduleId")
	private Long deliverableScheduleId;
	
	@Column(name = "ProjectId")
	private Long projectId;
	
	@Column(name = "ProjectSdStageId")
	private Long projectSdStageId;
	
	@Column(name = "Name")
	private String name;
	
	@Column(name = "Description")
	private String description;
	
	@JsonSerialize(using = JsonDateSerializer.class)
	@Column(name = "ScheduleStartDate")
	private Date scheduleStartDate;
	
	@Column(name = "BufferPeriodDays")
	private int bufferPeriodDays;
	
	@JsonSerialize(using = JsonDateSerializer.class)
	@Column(name = "PreferedCompleteionDate")
	private Date preferedCompleteionDate;
	
	@Column(name = "PostponeExpiryPeriod")
	private int postponeExpiryPeriod;
	
	@Column(name = "QueryResponsePeriod")
	private int queryResponsePeriod;
	
	@Column(name = "QueryOrRejectPeriod")
	private int queryOrRejectPeriod;

	@JsonSerialize(using = JsonDateSerializer.class)
	@Column(name = "LastUpdateTimestamp")
	private Date lastUpdateTimestamp;

	@Column(name = "LastUpdateUserName")
	private String lastUpdateUserName;

	public Long getDeliverableScheduleId() {
		return deliverableScheduleId;
	}

	public void setDeliverableScheduleId(long deliverableScheduleId) {
		this.deliverableScheduleId = deliverableScheduleId;
	}
	
	public Long getProjectId() {
		return projectId;
	}

	public void setProjectId(long projectId) {
		this.projectId = projectId;
	}
	
	public Long getProjectSdStageId() {
		return projectSdStageId;
	}

	public void setProjectSdStageId(long projectSdStageId) {
		this.projectSdStageId = projectSdStageId;
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
	
	public Date getScheduleStartDate() {
		return scheduleStartDate;
	}

	public void setScheduleStartDate(Date scheduleStartDate) {
		this.scheduleStartDate = scheduleStartDate;
	}
	
	public int getQueryOrRejectPeriod() {
		return queryOrRejectPeriod;
	}

	public void setQueryOrRejectPeriod(int queryOrRejectPeriod) {
		this.queryOrRejectPeriod = queryOrRejectPeriod;
	}
	
	
	public Date getPreferedCompleteionDate() {
		return preferedCompleteionDate;
	}

	public void setPreferedCompleteionDate(Date preferedCompleteionDate) {
		this.preferedCompleteionDate = preferedCompleteionDate;
	}
	
	public int getPostponeExpiryPeriod() {
		return bufferPeriodDays;
	}

	public void setPostponeExpiryPeriod(int postponeExpiryPeriod) {
		this.postponeExpiryPeriod = postponeExpiryPeriod;
	}
	
	public int getQueryResponsePeriod() {
		return queryResponsePeriod;
	}

	public void setQueryResponsePeriod(int queryResponsePeriod) {
		this.queryResponsePeriod = queryResponsePeriod;
	}
	
	public int getBufferPeriodDays() {
		return bufferPeriodDays;
	}

	public void setBufferPeriodDays(int bufferPeriodDays) {
		this.bufferPeriodDays = bufferPeriodDays;
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
