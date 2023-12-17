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
@Table(schema = "ig_db", name = "DeliverableTemplate")
public class DeliverableTemplate implements Serializable {

	private static final long serialVersionUID = 525918783838240057L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "DeliverableTemplateId")
	private Long deliverableTemplateId;
	
	@Column(name = "DeliverableScheduleTemplateId")
	private Long deliverableScheduleTemplateId;
	
	@Column(name = "DeliverableTypeAndFormatId")
	private Long deliverableTypeAndFormatId;
	
	@Column(name = "DeliverableName")
	private String deliverableName;
	
	@Column(name = "Description")
	private String description;
	
	@Column(name = "ApprovalRequired")
	private String approvalRequired;

	@JsonSerialize(using = JsonDateSerializer.class)
	@Column(name = "LastUpdateTimestamp")
	private Date lastUpdateTimestamp;

	@Column(name = "LastUpdateUserName")
	private String lastUpdateUserName;

	public Long getDeliverableScheduleTemplateId() {
		return deliverableScheduleTemplateId;
	}

	public void setDeliverableScheduleTemplateId(long deliverableScheduleTemplateId) {
		this.deliverableScheduleTemplateId = deliverableScheduleTemplateId;
	}
	
	public String getDeliverableName() {
		return deliverableName;
	}

	public void setDeliverableName(String deliverableName) {
		this.deliverableName = deliverableName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	public String getApprovalRequired() {
		return approvalRequired;
	}

	public void setApprovalRequired(String approvalRequired) {
		this.approvalRequired = approvalRequired;
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
