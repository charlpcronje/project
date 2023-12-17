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
@Table(schema = "ig_db", name = "vProjectTree")
public class VProjectTree implements Serializable {

	private static final long serialVersionUID = -804723312334187509L;

	public static String NODETYPE_PROJECT = "P";
	public static String NODETYPE_SUBPROJECT = "S";
	public static String NODETYPE_RESOURCES = "R";
	public static String NODETYPE_FILES = "F";

	@Id
	//@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ProjectId")
	private Long projectId;

	@Column(name = "ProjectIdParent")
	private Long projectIdParent;

	@Column(name = "ProjectParticipantIdLevel1")
	private Long projectParticipantIdLevel1;

	@Column(name = "ParticipantIdLevel1")
	private Long participantIdLevel1;

	@Column(name = "ParticipantNameLevel1")
	private String participantNameLevel1;

	@Column(name = "ParticipantIdHost")
	private Long participantIdHost;

	@Column(name = "participantNameHost")
	private String participantNameHost;

	@Column(name = "ProjectIdSustenance")
	private Long projectIdSustenance;

	@Column(name = "IsSustenanceProject")
	private String isSustenanceProject;

	@Column(name = "IndividualIdProjectAdmin")
	private Long individualIdProjectAdmin;

	@Column(name = "ProjectNumberBigInt")
	private Long projectNumberBigInt;

	@Column(name = "ProjectNumberText")
	private String projectNumberText;

	@Column(name = "ProjectNameText")
	private String projectNameText;

	@Column(name = "Title")
	private String title;

	@Column(name = "SubProjNumber")
	private String subProjNumber;

	@Column(name = "Description")
	private String description;

	@Column(name = "IsActive")
	private String isActive;

	@JsonSerialize(using = JsonDateSerializer.class)
	@Column(name = "StartDate")
	private Date startDate;

	@JsonSerialize(using = JsonDateSerializer.class)
	@Column(name = "CompletionDate")
	private Date completionDate;

	@JsonSerialize(using = JsonDateSerializer.class)
	@Column(name = "LastUpdateTimestamp")
	private Date lastUpdateTimestamp;

	@Column(name = "LastUpdateUserName")
	private String lastUpdateUserName;

	@Column(name = "ProjectStage")
	private String projectStage;

	@Column(name = "NodeType")
	private String nodeType;

	@Column(name = "RowOrderNo")
	private Long rowOrderNo;

	@Column(name = "Level")
	private Long level;

	@Column(name = "ProjectName")
	private String projectName;

	@Column(name = "PId")
	private Long pid;

	@Column(name = "SubitemCount")
	private Long subitemCount;

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

	public static String getNODETYPE_PROJECT() {
		return NODETYPE_PROJECT;
	}

	public static void setNODETYPE_PROJECT(String nODETYPE_PROJECT) {
		NODETYPE_PROJECT = nODETYPE_PROJECT;
	}

	public static String getNODETYPE_SUBPROJECT() {
		return NODETYPE_SUBPROJECT;
	}

	public static void setNODETYPE_SUBPROJECT(String nODETYPE_SUBPROJECT) {
		NODETYPE_SUBPROJECT = nODETYPE_SUBPROJECT;
	}

	public static String getNODETYPE_RESOURCES() {
		return NODETYPE_RESOURCES;
	}

	public static void setNODETYPE_RESOURCES(String nODETYPE_RESOURCES) {
		NODETYPE_RESOURCES = nODETYPE_RESOURCES;
	}

	public static String getNODETYPE_FILES() {
		return NODETYPE_FILES;
	}

	public static void setNODETYPE_FILES(String nODETYPE_FILES) {
		NODETYPE_FILES = nODETYPE_FILES;
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

	public Long getProjectParticipantIdLevel1() {
		return projectParticipantIdLevel1;
	}

	public void setProjectParticipantIdLevel1(Long projectParticipantIdLevel1) {
		this.projectParticipantIdLevel1 = projectParticipantIdLevel1;
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

	public Long getProjectIdSustenance() {
		return projectIdSustenance;
	}

	public void setProjectIdSustenance(Long projectIdSustenance) {
		this.projectIdSustenance = projectIdSustenance;
	}

	public String getIsSustenanceProject() {
		return isSustenanceProject;
	}

	public void setIsSustenanceProject(String isSustenanceProject) {
		this.isSustenanceProject = isSustenanceProject;
	}

	public Long getIndividualIdProjectAdmin() {
		return individualIdProjectAdmin;
	}

	public void setIndividualIdProjectAdmin(Long individualIdProjectAdmin) {
		this.individualIdProjectAdmin = individualIdProjectAdmin;
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

	public String getProjectStage() {
		return projectStage;
	}

	public void setProjectStage(String projectStage) {
		this.projectStage = projectStage;
	}

	public String getNodeType() {
		return nodeType;
	}

	public void setNodeType(String nodeType) {
		this.nodeType = nodeType;
	}

	public Long getRowOrderNo() {
		return rowOrderNo;
	}

	public void setRowOrderNo(Long rowOrderNo) {
		this.rowOrderNo = rowOrderNo;
	}

	public Long getLevel() {
		return level;
	}

	public void setLevel(Long level) {
		this.level = level;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public Long getPid() {
		return pid;
	}

	public void setPid(Long pid) {
		this.pid = pid;
	}

	public Long getSubitemCount() {
		return subitemCount;
	}

	public void setSubitemCount(Long subitemCount) {
		this.subitemCount = subitemCount;
	}



}
