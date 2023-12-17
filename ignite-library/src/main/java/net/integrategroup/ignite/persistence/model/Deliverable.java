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
@Table(schema = "ig_db", name = "Deliverable")
public class Deliverable implements Serializable {
	private static final long serialVersionUID = 1434320460827680226L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "DeliverableId")
	private Long deliverableId;
	
	@Column(name = "DeliverableScheduleId")
	private Long deliverableScheduleId;

	@Column(name = "DeliverableTypeAndFormatId")
	private Long deliverableTypeAndFormatId;
	
	@Column(name = "DeliverableStatusCode")
	private String deliverableStatusCode;
	
	@Column(name = "ProjectParticipantIdRequestedBy")
	private Long projectParticipantIdRequestedBy;

	@Column(name = "ProjectParticipantIdResponsible")
	private Long projectParticipantIdResponsible;
	
	@Column(name = "ProjectParticipantIdApprovedBy")
	private Long projectParticipantIdApprovedBy;
	
	@Column(name = "DeliverableName")
	private String deliverableName;

	@Column(name = "Description")
	private String description;
	
	@Column(name = "ApprovalRequired")
	private String approvalRequired;
	
	@JsonSerialize(using = JsonDateSerializer.class)
	@Column(name = "ActualStartDate")
	private Date actualStartDate;
	
	@Column(name = "DaysPlannedToComplete")
	private int daysPlannedToComplete;
	
	@JsonSerialize(using = JsonDateSerializer.class)
	@Column(name = "ActualCompletionDate")
	private Date actualCompletionDate;
	@JsonSerialize(using = JsonDateSerializer.class)
	@Column(name = "ApprovalOrOfficialDate")
	private Date approvalOrOfficialDate;

	@JsonSerialize(using = JsonDateSerializer.class)
	@Column(name = "LastUpdateTimestamp")
	private Date lastUpdateTimestamp;

	@Column(name = "LastUpdateUserName")
	private String lastUpdateUserName;

	public Long getDeliverableId() {
		return deliverableId;
	}

	public void setDeliverableId(Long deliverableId) {
		this.deliverableId = deliverableId;
	}

	public String getDeliverableName() {
		return deliverableName;
	}

	public void setDeliverableName(String name) {
		this.deliverableName = name;
	}

	public String getApprovalRequired() {
		return approvalRequired;
	}

	public void setApprovalRequired(String approvalRequired) {
		this.approvalRequired = approvalRequired;
	}
	
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	public Date getActualStartDate() {
		return actualStartDate;
	}

	public void setActualStartDate(Date actualStartDate) {
		this.actualStartDate = actualStartDate;
	}
	
	public Date getActualCompletionDate() {
		return lastUpdateTimestamp;
	}

	public void setActualCompletionDate(Date actualCompletionDate) {
		this.actualCompletionDate = actualCompletionDate;
	}
	
	public int getDaysPlannedToComplete() {
		return daysPlannedToComplete;
	}

	public void setDaysPlannedToComplete(int daysPlannedToComplete) {
		this.daysPlannedToComplete = daysPlannedToComplete;
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
