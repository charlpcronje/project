package net.integrategroup.ignite.persistence.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


// @author Ingrid Marais

@Entity
@Table(schema = "ig_db", name = "vProjectParticipantPayerBen")
public class VProjectParticipantPayerBen implements Serializable {

	private static final long serialVersionUID = 8637460215475739782L;

	@Id
	//@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ProjectParticipantId")
	private Long projectParticipantId;

	@Column(name = "ProjectParticipantIdAboveMe")
	private Long projectParticipantIdAboveMe;

	@Column(name = "ProjectId")
	private Long projectId;

	@Column(name = "ParticipantIdHost")
	private Long participantIdHost;

	@Column(name = "ProjectNumberText")
	private String projectNumberText;

	@Column(name = "ProjectTitle")
	private String projectTitle;

	@Column(name = "ParticipantIdPayer")
	private Long participantIdPayer;

	@Column(name = "SystemNamePayer")
	private String systemNamePayer;

	@Column(name = "ParticipantIdBeneficiary")
	private Long participantIdBeneficiary;

	@Column(name = "SystemNameBeneficiary")
	private String systemNameBeneficiary;

	public Long getProjectParticipantId() {
		return projectParticipantId;
	}

	public void setProjectParticipantId(Long projectParticipantId) {
		this.projectParticipantId = projectParticipantId;
	}

	public Long getProjectParticipantIdAboveMe() {
		return projectParticipantIdAboveMe;
	}

	public void setProjectParticipantIdAboveMe(Long projectParticipantIdAboveMe) {
		this.projectParticipantIdAboveMe = projectParticipantIdAboveMe;
	}

	public Long getProjectId() {
		return projectId;
	}

	public void setProjectId(Long projectId) {
		this.projectId = projectId;
	}

	public Long getParticipantIdHost() {
		return participantIdHost;
	}

	public void setParticipantIdHost(Long participantIdHost) {
		this.participantIdHost = participantIdHost;
	}

	public String getProjectNumberText() {
		return projectNumberText;
	}

	public void setProjectNumberText(String projectNumberText) {
		this.projectNumberText = projectNumberText;
	}

	public String getProjectTitle() {
		return projectTitle;
	}

	public void setProjectTitle(String projectTitle) {
		this.projectTitle = projectTitle;
	}

	public Long getParticipantIdPayer() {
		return participantIdPayer;
	}

	public void setParticipantIdPayer(Long participantIdPayer) {
		this.participantIdPayer = participantIdPayer;
	}

	public String getSystemNamePayer() {
		return systemNamePayer;
	}

	public void setSystemNamePayer(String systemNamePayer) {
		this.systemNamePayer = systemNamePayer;
	}

	public Long getParticipantIdBeneficiary() {
		return participantIdBeneficiary;
	}

	public void setParticipantIdBeneficiary(Long participantIdBeneficiary) {
		this.participantIdBeneficiary = participantIdBeneficiary;
	}

	public String getSystemNameBeneficiary() {
		return systemNameBeneficiary;
	}

	public void setSystemNameBeneficiary(String systemNameBeneficiary) {
		this.systemNameBeneficiary = systemNameBeneficiary;
	}


}
