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
@Table(schema = "ig_db", name = "DeliverableDependency")
public class DeliverableDependency implements Serializable {
	private static final long serialVersionUID = -359126238320912883L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "DeliverableDependencyId")
	private Long deliverableDependencyId;
	
	@Column(name = "DeliverableId")
	private Long deliverableId;
	
	@Column(name = "DeliverableIdDependentOn")
	private Long deliverableIdDependentOn;
	
	@Column(name = "Description")
	private String description;

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

