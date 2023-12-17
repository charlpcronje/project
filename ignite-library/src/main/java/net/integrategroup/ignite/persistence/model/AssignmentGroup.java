package net.integrategroup.ignite.persistence.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import net.integrategroup.ignite.utils.JsonDateSerializer;

/** @author Generated by Johannes Marais (JohannesSQL) */

@Entity
@Table(schema = "ig_db", name = "AssignmentGroup")
public class AssignmentGroup implements Serializable {

	private static final long serialVersionUID = -1821014244232594739L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "AssignmentGroupId")
	private Long assignmentGroupId;

	@Column(name = "ProjectId")
	private Long projectId;


	@OneToOne(targetEntity = Project.class)
	@JoinColumn(name = "ProjectId", referencedColumnName = "ProjectId", nullable = true, insertable = false, updatable = false)
	private Project project;


	@Column(name = "AssignmentGroupNumber")
	private String assignmentGroupNumber;

	@Column(name = "Name")
	private String name;

	@Column(name = "Description")
	private String description;

	@JsonSerialize(using = JsonDateSerializer.class)
	@Column(name = "LastUpdateTimestamp")
	private Date lastUpdateTimestamp;

	@Column(name = "LastUpdateUserName")
	private String lastUpdateUserName;

	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}

	public Long getAssignmentGroupId() {
		return assignmentGroupId;
	}

	public void setAssignmentGroupId(Long assignmentGroupId) {
		this.assignmentGroupId = assignmentGroupId;
	}

	public Long getProjectId() {
		return projectId;
	}

	public void setProjectId(Long projectId) {
		this.projectId = projectId;
	}

	public String getAssignmentGroupNumber() {
		return assignmentGroupNumber;
	}

	public void setAssignmentGroupNumber(String assignmentGroupNumber) {
		this.assignmentGroupNumber = assignmentGroupNumber;
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