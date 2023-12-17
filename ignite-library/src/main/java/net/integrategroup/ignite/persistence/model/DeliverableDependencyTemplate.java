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
@Table(schema = "ig_db", name = "DeliverableDependencyTemplate")
public class DeliverableDependencyTemplate implements Serializable {

	private static final long serialVersionUID = -1782310258804517600L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "DeliverableDependencyTemplateId")
	private Long deliverableDependencyTemplateId;
	
	@Column(name = "DeliverableTemplateId")
	private Long deliverableTemplateId;
	
	@Column(name = "DeliverableTemplateIdDependentOn")
	private Long deliverableTemplateIdDependentOn;
	
	@Column(name = "Description")
	private String description;

	@JsonSerialize(using = JsonDateSerializer.class)
	@Column(name = "LastUpdateTimestamp")
	private Date lastUpdateTimestamp;

	@Column(name = "LastUpdateUserName")
	private String lastUpdateUserName;

	public Long getDeliverableTemplateId() {
		return deliverableTemplateId;
	}

	public void setDeliverableTemplateId(Long deliverableTemplateId) {
		this.deliverableTemplateId = deliverableTemplateId;
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
