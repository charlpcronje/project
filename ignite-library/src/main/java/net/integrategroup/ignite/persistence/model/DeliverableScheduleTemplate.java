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
@Table(schema = "ig_db", name = "DeliverableScheduleTemplate")
public class DeliverableScheduleTemplate implements Serializable {

	private static final long serialVersionUID = 8510973969394650818L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "DeliverableScheduleTemplateId")
	private Long deliverableScheduleTemplateId;
	
	@Column(name = "Name")
	private String name;
	
	@Column(name = "Description")
	private String description;
	
	@Column(name = "BufferPeriodDays")
	private int bufferPeriodDays;
	
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

	public Long getDeliverableScheduleTemplateId() {
		return deliverableScheduleTemplateId;
	}

	public void setDeliverableScheduleTemplateId(long deliverableScheduleTemplateId) {
		this.deliverableScheduleTemplateId = deliverableScheduleTemplateId;
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
	
	public int getQueryOrRejectPeriod() {
		return queryOrRejectPeriod;
	}

	public void setQueryOrRejectPeriod(int queryOrRejectPeriod) {
		this.queryOrRejectPeriod = queryOrRejectPeriod;
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