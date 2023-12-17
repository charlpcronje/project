package net.integrategroup.ignite.persistence.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(schema = "ig_db", name = "vParticipantResource")
public class VParticipantResource implements Serializable {

	private static final long serialVersionUID = 2225476936417428783L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ResourceId")
	private Long resourceId;

	@Column(name = "ParticipantId")
	private Long participantId;

	@Column(name = "IndividualId")
	private Long individualId;

	@Column(name = "FirstName")
	private String firstName;

	@Column(name = "LastName")
	private String lastName;

	@Column(name = "RoleCode")
	private String roleCode;

	@Column(name = "IsActiveMember")
	private String isActiveMember;

	public Long getResourceId() {
		return resourceId;
	}

	public void setResourceId(Long resourceId) {
		this.resourceId = resourceId;
	}

	public Long getParticipantId() {
		return participantId;
	}

	public void setParticipantId(Long participantId) {
		this.participantId = participantId;
	}

	public Long getindividualId() {
		return individualId;
	}

	public void setindividualId(Long individualId) {
		this.individualId = individualId;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getRoleCode() {
		return roleCode;
	}

	public void setRoleCode(String roleCode) {
		this.roleCode = roleCode;
	}

	public String getIsActiveMember() {
		return isActiveMember;
	}

	public void setIsActiveMember(String isActiveMember) {
		this.isActiveMember = isActiveMember;
	}

}
