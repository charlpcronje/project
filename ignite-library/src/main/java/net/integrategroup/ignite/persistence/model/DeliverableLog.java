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
@Table(schema = "ig_db", name = "DeliverableLog")
public class DeliverableLog implements Serializable {

	private static final long serialVersionUID = 834073103987627591L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "DeliverableLogId")
	private Long deliverableLogId;
	
	@Column(name = "DeliverableId")
	private Long deliverableId;
	
	@JsonSerialize(using = JsonDateSerializer.class)
	@Column(name = "LogDate")
	private Date logDate;
	
	@Column(name = "DeliverableLogActionCode")
	private String deliverableLogActionCode;
	
	@Column(name = "ProjectTeamMemberIdLogger")
	private Long projectTeamMemberIdLogger;
	
	@Column(name = "DeliverableStatusCodeBefore")
	private String deliverableStatusCodeBefore;
	
	@Column(name = "DeliverableStatusCodeAfter")
	private String deliverableStatusCodeAfter;
	
	@Column(name = "Description")
	private String description;

	@JsonSerialize(using = JsonDateSerializer.class)
	@Column(name = "LastUpdateTimestamp")
	private Date lastUpdateTimestamp;

	@Column(name = "LastUpdateUserName")
	private String lastUpdateUserName;

	public Long getDeliverableLogId() {
		return deliverableLogId;
	}

	public void setDeliverableLogId(Long deliverableLogId) {
		this.deliverableLogId = deliverableLogId;
	}
	
	public Date getLogDate() {
		return logDate;
	}

	public void setLogDate(Date logDate) {
		this.logDate = logDate;
	}
	
	public String getDeliverableLogActionCode() {
		return deliverableLogActionCode;
	}

	public void setDeliverableLogActionCode(String deliverableLogActionCode) {
		this.deliverableLogActionCode = deliverableLogActionCode;
	}
	
	public String getDeliverableStatusCodeBefore() {
		return deliverableStatusCodeBefore;
	}

	public void setDeliverableStatusCodeBefore(String deliverableStatusCodeBefore) {
		this.deliverableStatusCodeBefore = deliverableStatusCodeBefore;
	}
	
	public String getDeliverableStatusCodeAfter() {
		return deliverableStatusCodeAfter;
	}

	public void setDeliverableStatusCodeAfter(String deliverableStatusCodeAfter) {
		this.deliverableStatusCodeAfter = deliverableStatusCodeAfter;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
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
