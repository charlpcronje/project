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

@Entity
@Table(schema = "ig_db", name = "SupportServicesUsed")
public class SupportServicesUsed implements Serializable {

	private static final long serialVersionUID = 1690532281219916721L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "SupportServicesUsedId")
	private Long supportServicesUsedId;

	@Column(name = "ParticipantId")
	private Long participantId;

	@Column(name = "SupportServiceCode")
	private String supportServiceCode;

	@JsonSerialize(using = JsonDateSerializer.class)
	@Column(name = "LastUpdateTimestamp")
	private Date lastUpdateTimestamp;

	@Column(name = "LastUpdateUserName")
	private String lastUpdateUserName;

	public Long getSupportServicesUsedId() {
		return supportServicesUsedId;
	}

	public void setSupportServicesUsedId(Long supportServicesUsedId) {
		this.supportServicesUsedId = supportServicesUsedId;
	}

	public Long getParticipantId() {
		return participantId;
	}

	public void setParticipantId(Long participantId) {
		this.participantId = participantId;
	}

	public String getSupportServiceCode() {
		return supportServiceCode;
	}

	public void setSupportServiceCode(String supportServiceCode) {
		this.supportServiceCode = supportServiceCode;
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
