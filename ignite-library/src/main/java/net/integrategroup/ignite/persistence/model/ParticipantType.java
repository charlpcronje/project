package net.integrategroup.ignite.persistence.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import net.integrategroup.ignite.utils.JsonDateSerializer;

/**
 * @author Ingrid Marais
 *
 */
@Entity
@Table(schema = "ig_db", name = "ParticipantType")
public class ParticipantType implements Serializable {

	private static final long serialVersionUID = 574301788506331537L;

	@Id
	@Column(name = "ParticipantTypeCode")
	private String participantTypeCode;

	@Column(name = "TypeName")
	private String typeName;

	@Column(name = "TypeDescription")
	private String typeDescription;

	@Column(name = "MemberCode")
	private String memberCode;

	@Column(name = "MemberName")
	private String memberName;

	@Column(name = "MemberDescription")
	private String memberDescription;

	@JsonSerialize(using = JsonDateSerializer.class)
	@Column(name = "LastUpdateTimestamp")
	private Date lastUpdateTimestamp;

	@Column(name = "LastUpdateUserName")
	private String lastUpdateUserName;

	public String getParticipantTypeCode() {
		return participantTypeCode;
	}

	public void setParticipantTypeCode(String participantTypeCode) {
		this.participantTypeCode = participantTypeCode;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public String getTypeDescription() {
		return typeDescription;
	}

	public void setTypeDescription(String typeDescription) {
		this.typeDescription = typeDescription;
	}

	public String getMemberCode() {
		return memberCode;
	}

	public void setMemberCode(String memberCode) {
		this.memberCode = memberCode;
	}

	public String getMemberName() {
		return memberName;
	}

	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}

	public String getMemberDescription() {
		return memberDescription;
	}

	public void setMemberDescription(String memberDescription) {
		this.memberDescription = memberDescription;
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
