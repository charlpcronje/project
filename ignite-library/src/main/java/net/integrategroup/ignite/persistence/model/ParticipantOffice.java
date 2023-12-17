package net.integrategroup.ignite.persistence.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import net.integrategroup.ignite.utils.JsonDateSerializer;

/**
 * @author Ingrid Marais
 *
 */
@Entity
@Table(schema = "ig_db", name = "ParticipantOffice")
public class ParticipantOffice implements Serializable {

	private static final long serialVersionUID = -6691417801787624328L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ParticipantOfficeId")
	private Long participantOfficeId;

	@Column(name = "ParticipantId")
	private Long participantId;

	@Column(name = "ContactPointIdDefault")
	private Long contactPointIdDefault;

	@OneToOne(targetEntity = ContactPoint.class, fetch = FetchType.EAGER)
	@JoinColumn(name = "ContactPointIdDefault", referencedColumnName = "ContactPointId", updatable = false, insertable = false)
	private ContactPoint contactPointDefault;

	@Column(name = "Name")
	private String name;

	@Column(name = "Description")
	private String description;

	@Column(name = "CostPerSeat")
	private Double costPerSeat;

	@JsonSerialize(using = JsonDateSerializer.class)
	@Column(name = "DateFrom")
	private Date dateFrom;

	@JsonSerialize(using = JsonDateSerializer.class)
	@Column(name = "LastUpdateTimestamp")
	private Date lastUpdateTimestamp;

	@Column(name = "LastUpdateUserName")
	private String lastUpdateUserName;

	public Long getParticipantOfficeId() {
		return participantOfficeId;
	}

	public void setParticipantOfficeId(Long participantOfficeId) {
		this.participantOfficeId = participantOfficeId;
	}

	public Long getParticipantId() {
		return participantId;
	}

	public void setParticipantId(Long participantId) {
		this.participantId = participantId;
	}

	public Long getContactPointIdDefault() {
		return contactPointIdDefault;
	}

	public void setContactPointIdDefault(Long contactPointIdDefault) {
		this.contactPointIdDefault = contactPointIdDefault;
	}

	public ContactPoint getContactPointDefault() {
		return contactPointDefault;
	}

	public void setContactPointDefault(ContactPoint contactPointDefault) {
		this.contactPointDefault = contactPointDefault;
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

	public Double getCostPerSeat() {
		return costPerSeat;
	}

	public void setCostPerSeat(Double costPerSeat) {
		this.costPerSeat = costPerSeat;
	}

	public Date getDateFrom() {
		return dateFrom;
	}

	public void setDateFrom(Date dateFrom) {
		this.dateFrom = dateFrom;
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
