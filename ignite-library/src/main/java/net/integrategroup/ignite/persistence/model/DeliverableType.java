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
@Table(schema = "ig_db", name = "DeliverableType")
public class DeliverableType implements Serializable {

	private static final long serialVersionUID = 1955471648052436521L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "DeliverableTypeId")
	private Long deliverableTypeId;

	@Column(name = "ServiceDisciplineIdIndustry")
	private Long serviceDisciplineIdIndustry;

	@Column(name = "Name")
	private String name;
	
	@Column(name = "FormatType")
	private String formatType;

	@Column(name = "Description")
	private String description;

	@JsonSerialize(using = JsonDateSerializer.class)
	@Column(name = "LastUpdateTimestamp")
	private Date lastUpdateTimestamp;

	@Column(name = "LastUpdateUserName")
	private String lastUpdateUserName;

	public Long getDeliverableTypeId() {
		return deliverableTypeId;
	}

	public void setDeliverableTypeId(Long deliverableTypeId) {
		this.deliverableTypeId = deliverableTypeId;
	}

	public Long getServiceDisciplineIdIndustry() {
		return serviceDisciplineIdIndustry;
	}

	public void setServiceDisciplineIdIndustry(Long serviceDisciplineIdIndustry) {
		this.serviceDisciplineIdIndustry = serviceDisciplineIdIndustry;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getFormatType() {
		return formatType;
	}

	public void setFormatType(String formatType) {
		this.formatType = formatType;
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
