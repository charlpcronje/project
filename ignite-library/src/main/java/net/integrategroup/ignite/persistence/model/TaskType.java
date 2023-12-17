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
@Table(schema = "ig_db", name = "TaskType")
public class TaskType implements Serializable {


	private static final long serialVersionUID = 1143746099593575774L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "TaskTypeId")
	private Long taskTypeId;

	@Column(name = "AssignmentTypeId")
	private Long assignmentTypeId;

	@Column(name = "TaskOrderNumber")
	private String taskOrderNumber;

	@Column(name = "Name")
	private String name;

	@Column(name = "Description")
	private String description;

	@Column(name = "DurationDays")
	private Long durationDays;

	@Column(name = "DurationHours")
	private Long durationHours;

	@Column(name = "TimeOfDay")
	private Date timeOfDay;


	@JsonSerialize(using = JsonDateSerializer.class)
	@Column(name = "LastUpdateTimestamp")
	private Date lastUpdateTimestamp;

	@Column(name = "LastUpdateUserName")
	private String lastUpdateUserName;

	public Long getTaskTypeId() {
		return taskTypeId;
	}

	public void setTaskTypeId(Long taskTypeId) {
		this.taskTypeId = taskTypeId;
	}

	public Long getAssignmentTypeId() {
		return assignmentTypeId;
	}

	public void setAssignmentTypeId(Long assignmentTypeId) {
		this.assignmentTypeId = assignmentTypeId;
	}

	public String getTaskOrderNumber() {
		return taskOrderNumber;
	}

	public void setTaskOrderNumber(String taskOrderNumber) {
		this.taskOrderNumber = taskOrderNumber;
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

	public Long getDurationDays() {
		return durationDays;
	}

	public void setDurationDays(Long durationDays) {
		this.durationDays = durationDays;
	}

	public Long getDurationHours() {
		return durationHours;
	}

	public void setDurationHours(Long durationHours) {
		this.durationHours = durationHours;
	}

	public Date getTimeOfDay() {
		return timeOfDay;
	}

	public void setTimeOfDay(Date timeOfDay) {
		this.timeOfDay = timeOfDay;
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
