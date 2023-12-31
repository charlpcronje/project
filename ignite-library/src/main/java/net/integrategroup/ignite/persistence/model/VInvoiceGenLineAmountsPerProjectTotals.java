package net.integrategroup.ignite.persistence.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import net.integrategroup.ignite.utils.JsonDateSerializer;

/** @author Generated by Johannes Marais (JohannesSQL v7.5) **/
/** ******* ********* ** 2023-09-04 14:14:27 ******** *inv* **/

@Entity
@Table(schema = "ig_db", name = "vInvoiceGenLineAmountsPerProjectTotals")
public class VInvoiceGenLineAmountsPerProjectTotals implements Serializable {

	private static final long serialVersionUID = 6488727118095897723L;

	@Id
	@Column(name = "ProjectId")
	private Long projectId;

	@Column(name = "ProjectName")
	private String projectName;

		@Column(name = "ParticipantIdContracting")
	private Long participantIdContracting;

	@Column(name = "ParticipantContracting")
	private String participantContracting;

	@Column(name = "ParticipantIdContracted")
	private Long participantIdContracted;

	@Column(name = "ParticipantContracted")
	private String participantContracted;

	@Column(name = "SumNrOfUnits")
	private Double sumNrOfUnits;

	@Column(name = "SumLineAmount")
	private Double sumLineAmount;

	@Column(name = "SumRatesMissing")
	private Integer sumRatesMissing;

	@JsonSerialize(using = JsonDateSerializer.class)
	@Column(name = "ActivityDate")
	private Date activityDate;

	public Date getActivityDate() {
		return activityDate;
	}

	public void setActivityDate(Date activityDate) {
		this.activityDate = activityDate;
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

	public Long getParticipantIdContracting() {
		return participantIdContracting;
	}

	public void setParticipantIdContracting(Long participantIdContracting) {
		this.participantIdContracting = participantIdContracting;
	}

	public String getParticipantContracting() {
		return participantContracting;
	}

	public void setParticipantContracting(String participantContracting) {
		this.participantContracting = participantContracting;
	}

	public Long getParticipantIdContracted() {
		return participantIdContracted;
	}

	public void setParticipantIdContracted(Long participantIdContracted) {
		this.participantIdContracted = participantIdContracted;
	}

	public String getParticipantContracted() {
		return participantContracted;
	}

	public void setParticipantContracted(String participantContracted) {
		this.participantContracted = participantContracted;
	}

	public Double getSumNrOfUnits() {
		return sumNrOfUnits;
	}

	public void setSumNrOfUnits(Double sumNrOfUnits) {
		this.sumNrOfUnits = sumNrOfUnits;
	}

	public Double getSumLineAmount() {
		return sumLineAmount;
	}

	public void setSumLineAmount(Double sumLineAmount) {
		this.sumLineAmount = sumLineAmount;
	}

	public Integer getSumRatesMissing() {
		return sumRatesMissing;
	}

	public void setSumRatesMissing(Integer sumRatesMissing) {
		this.sumRatesMissing = sumRatesMissing;
	}
}
