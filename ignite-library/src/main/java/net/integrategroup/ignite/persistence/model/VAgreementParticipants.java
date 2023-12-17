package net.integrategroup.ignite.persistence.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

 // @author Ingrid Marais

@Entity
@Table(schema = "ig_db", name = "vAgreementParticipants")
public class VAgreementParticipants implements Serializable {

	private static final long serialVersionUID = 8826932215075800946L;

	@Id
	//@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "RowNumber")
	private Long rowNumber;

	@Column(name = "ProjectId")
	private Long projectId;

	@Column(name = "ProjectName")
	private String projectName;

	@Column(name = "AgreementBetweenParticipantsId")
	private Long agreementBetweenParticipantsId;

	@Column(name = "Level")
	private Integer level;

	@Column(name = "RemunerationModelCode")
	private String remunerationModelCode;

	@Column(name = "ProjectParticipantIdContracting")
	private Long projectParticipantIdContracting;

	@Column(name = "ParticipantIdContracting")
	private Long participantIdContracting;

	@Column(name = "ParticipantNameContracting")
	private String participantNameContracting;

	@Column(name = "IndividualIdContracting")
	private Long individualIdContracting;

	@Column(name = "IndividualNameContracting")
	private String individualNameContracting;

	@Column(name = "ProjectParticipantIdContracted")
	private Long projectParticipantIdContracted;

	@Column(name = "ParticipantIdContracted")
	private Long participantIdContracted;

	@Column(name = "ParticipantNameContracted")
	private String participantNameContracted;

	@Column(name = "IndividualIdContracted")
	private Long individualIdContracted;

	@Column(name = "IndividualNameContracted")
	private String individualNameContracted;

	@Column(name = "AgreementDescription")
	private String agreementDescription;

	@Column(name = "anyChildren")
	private String anyChildren;

	@Column(name = "isIndividual")
	private String isIndividual;

	public Long getRowNumber() {
		return rowNumber;
	}

	public void setRowNumber(Long rowNumber) {
		this.rowNumber = rowNumber;
	}

	public Long getProjectId() {
		return projectId;
	}

	public void setProjectId(Long projectId) {
		this.projectId = projectId;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public Long getAgreementBetweenParticipantsId() {
		return agreementBetweenParticipantsId;
	}

	public void setAgreementBetweenParticipantsId(Long agreementBetweenParticipantsId) {
		this.agreementBetweenParticipantsId = agreementBetweenParticipantsId;
	}

	public Integer getLevel() {
		return level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}

	public String getRemunerationModelCode() {
		return remunerationModelCode;
	}

	public void setRemunerationModelCode(String remunerationModelCode) {
		this.remunerationModelCode = remunerationModelCode;
	}

	public Long getProjectParticipantIdContracting() {
		return projectParticipantIdContracting;
	}

	public void setProjectParticipantIdContracting(Long projectParticipantIdContracting) {
		this.projectParticipantIdContracting = projectParticipantIdContracting;
	}

	public Long getParticipantIdContracting() {
		return participantIdContracting;
	}

	public void setParticipantIdContracting(Long participantIdContracting) {
		this.participantIdContracting = participantIdContracting;
	}

	public String getParticipantNameContracting() {
		return participantNameContracting;
	}

	public void setParticipantNameContracting(String participantNameContracting) {
		this.participantNameContracting = participantNameContracting;
	}

	public Long getIndividualIdContracting() {
		return individualIdContracting;
	}

	public void setIndividualIdContracting(Long individualIdContracting) {
		this.individualIdContracting = individualIdContracting;
	}

	public String getIndividualNameContracting() {
		return individualNameContracting;
	}

	public void setIndividualNameContracting(String individualNameContracting) {
		this.individualNameContracting = individualNameContracting;
	}

	public Long getProjectParticipantIdContracted() {
		return projectParticipantIdContracted;
	}

	public void setProjectParticipantIdContracted(Long projectParticipantIdContracted) {
		this.projectParticipantIdContracted = projectParticipantIdContracted;
	}

	public Long getParticipantIdContracted() {
		return participantIdContracted;
	}

	public void setParticipantIdContracted(Long participantIdContracted) {
		this.participantIdContracted = participantIdContracted;
	}

	public String getParticipantNameContracted() {
		return participantNameContracted;
	}

	public void setParticipantNameContracted(String participantNameContracted) {
		this.participantNameContracted = participantNameContracted;
	}

	public Long getIndividualIdContracted() {
		return individualIdContracted;
	}

	public void setIndividualIdContracted(Long individualIdContracted) {
		this.individualIdContracted = individualIdContracted;
	}

	public String getIndividualNameContracted() {
		return individualNameContracted;
	}

	public void setIndividualNameContracted(String individualNameContracted) {
		this.individualNameContracted = individualNameContracted;
	}

	public String getAgreementDescription() {
		return agreementDescription;
	}

	public void setAgreementDescription(String agreementDescription) {
		this.agreementDescription = agreementDescription;
	}

	public String getAnyChildren() {
		return anyChildren;
	}

	public void setAnyChildren(String anyChildren) {
		this.anyChildren = anyChildren;
	}

	public String getIsIndividual() {
		return isIndividual;
	}

	public void setIsIndividual(String isIndividual) {
		this.isIndividual = isIndividual;
	}

}
