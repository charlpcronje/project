package net.integrategroup.ignite.persistence.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import net.integrategroup.ignite.utils.JsonDateSerializer;
@Entity
@Table(schema = "ig_db", name = "vProject")
public class VProject implements Serializable {

	private static final long serialVersionUID = -2354718393192523236L;

	@Id
	@Column(name = "ProjectId")
	private Long projectId;

	@Column(name = "ProjectIdParent")
	private Long projectIdParent;

	@Column(name = "ParticipantIdHost")
	private Long participantIdHost;

	@Column(name = "participantNameHost")
	private String participantNameHost;

	@Column(name = "ProjectParticipantIdLevel1")
	private Long projectParticipantIdLevel1;

	@Column(name = "ParticipantIdLevel1")
	private Long participantIdLevel1;

	@Column(name = "ParticipantNameLevel1")
	private String participantNameLevel1;
	
	@Column(name = "IndividualIdProjectAdmin")
	private Long individualIdProjectAdmin;

	@Column(name = "IndividualNameProjectAdmin")
	private String individualNameProjectAdmin;

	@Column(name = "ProjectNumberBigInt")
	private Long projectNumberBigInt;

	@Column(name = "ProjectNumberText")
	private String projectNumberText;

	@Column(name = "ProjectNameText")
	private String projectNameText;

	@Column(name = "ProjectName")
	private String projectName;

	@Column(name = "Title")
	private String title;

	@Column(name = "SubProjNumber")
	private String subProjNumber;

	@Column(name = "Description")
	private String description;

	@Column(name = "IsActive")
	private String isActive;
	
	@Column(name = "FlagSustenanceProject")
	private String flagSustenanceProject;
	


	@JsonSerialize(using = JsonDateSerializer.class)
	@Column(name = "StartDate")
	private Date startDate;

	@JsonSerialize(using = JsonDateSerializer.class)
	@Column(name = "CompletionDate")
	private Date completionDate;
	
	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public Long getProjectId() {
		return projectId;
	}

	public void setProjectId(Long projectId) {
		this.projectId = projectId;
	}

	public Long getProjectIdParent() {
		return projectIdParent;
	}

	public void setProjectIdParent(Long projectIdParent) {
		this.projectIdParent = projectIdParent;
	}

	public Long getParticipantIdHost() {
		return participantIdHost;
	}

	public void setParticipantIdHost(Long participantIdHost) {
		this.participantIdHost = participantIdHost;
	}

	public String getParticipantNameHost() {
		return participantNameHost;
	}

	public void setParticipantNameHost(String participantNameHost) {
		this.participantNameHost = participantNameHost;
	}

	public Long getProjectParticipantIdLevel1() {
		return projectParticipantIdLevel1;
	}

	public void setProjectParticipantIdLevel1(Long projectParticipantIdLevel1) {
		this.projectParticipantIdLevel1 = projectParticipantIdLevel1;
	}

	public Long getParticipantIdLevel1() {
		return participantIdLevel1;
	}

	public void setParticipantIdLevel1(Long participantIdLevel1) {
		this.participantIdLevel1 = participantIdLevel1;
	}

	public String getParticipantNameLevel1() {
		return participantNameLevel1;
	}

	public void setParticipantNameLevel1(String participantNameLevel1) {
		this.participantNameLevel1 = participantNameLevel1;
	}

	public Long getIndividualIdProjectAdmin() {
		return individualIdProjectAdmin;
	}

	public void setIndividualIdProjectAdmin(Long individualIdProjectAdmin) {
		this.individualIdProjectAdmin = individualIdProjectAdmin;
	}

	public String getIndividualNameProjectAdmin() {
		return individualNameProjectAdmin;
	}

	public void setIndividualNameProjectAdmin(String individualNameProjectAdmin) {
		this.individualNameProjectAdmin = individualNameProjectAdmin;
	}

	public Long getProjectNumberBigInt() {
		return projectNumberBigInt;
	}

	public void setProjectNumberBigInt(Long projectNumberBigInt) {
		this.projectNumberBigInt = projectNumberBigInt;
	}

	public String getProjectNumberText() {
		return projectNumberText;
	}

	public void setProjectNumberText(String projectNumberText) {
		this.projectNumberText = projectNumberText;
	}

	public String getProjectNameText() {
		return projectNameText;
	}

	public void setProjectNameText(String projectNameText) {
		this.projectNameText = projectNameText;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getSubProjNumber() {
		return subProjNumber;
	}

	public void setSubProjNumber(String subProjNumber) {
		this.subProjNumber = subProjNumber;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getIsActive() {
		return isActive;
	}

	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getCompletionDate() {
		return completionDate;
	}

	public void setCompletionDate(Date completionDate) {
		this.completionDate = completionDate;
	}

	public String getFlagSustenanceProject() {
		return flagSustenanceProject;
	}

	public void setFlagSustenanceProject(String flagSustenanceProject) {
		this.flagSustenanceProject = flagSustenanceProject;
	}
	
	
}
