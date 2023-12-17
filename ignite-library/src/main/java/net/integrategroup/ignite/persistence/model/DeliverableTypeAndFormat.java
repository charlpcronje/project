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
@Table(schema = "ig_db", name = "DeliverableTypeAndFormat")
public class DeliverableTypeAndFormat implements Serializable {

	private static final long serialVersionUID = -6907009695700308950L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "DeliverableTypeAndFormatId")
	private Long deliverableTypeAndFormatId;
	
	@Column(name = "DeliverableTypeId")
	private Long deliverableTypeId;
	
	@Column(name = "DeliverableFormatCode")
	private String deliverableFormatCode;
	
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

	public void setDeliverableTypeId(long deliverableTypeId) {
		this.deliverableTypeId = deliverableTypeId;
	}
	
	public Long getDeliverableTypeAndFormatId() {
		return deliverableTypeAndFormatId;
	}

	public void setDeliverableTypeAndFormatId(long deliverableTypeAndFormatId) {
		this.deliverableTypeAndFormatId = deliverableTypeAndFormatId;
	}
	
	public String getDeliverableFormatCode() {
		return deliverableFormatCode;
	}

	public void setDeliverableFormatCode(String deliverableFormatCode) {
		this.deliverableFormatCode = deliverableFormatCode;
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