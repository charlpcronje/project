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

@Entity
@Table(schema = "ig_db", name = "MedicalDependant")
public class MedicalDependant implements Serializable {

	private static final long serialVersionUID = -6077305689681690118L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "MedicalDependantId")
	private Long medicalDependantId;

    @Column(name="MainMemberMedicalInsuranceId")
    private Long mainMemberMedicalInsuranceId;

    @Column(name="IndividualIdDependant")
    private Long individualIdDependant;

	@OneToOne(targetEntity = Individual.class)
	@JoinColumn(name = "IndividualIdDependant", referencedColumnName = "IndividualId", nullable = true, insertable = false, updatable = false)
	private Individual individual;

    @Column(name="Description")
    private String description;

	@JsonSerialize(using = JsonDateSerializer.class)
	@Column(name = "LastUpdateTimestamp")
	private Date lastUpdateTimestamp;

	@Column(name = "LastUpdateUserName")
	private String lastUpdateUserName;

	public Long getMedicalDependantId() {
		return medicalDependantId;
	}

	public void setMedicalDependantId(Long medicalDependantId) {
		this.medicalDependantId = medicalDependantId;
	}

	public Long getMainMemberMedicalInsuranceId() {
		return mainMemberMedicalInsuranceId;
	}

	public void setMainMemberMedicalInsuranceId(Long mainMemberMedicalInsuranceId) {
		this.mainMemberMedicalInsuranceId = mainMemberMedicalInsuranceId;
	}

	public Long getIndividualIdDependant() {
		return individualIdDependant;
	}

	public void setIndividualIdDependant(Long individualIdDependant) {
		this.individualIdDependant = individualIdDependant;
	}

	public Individual getIndividual() {
		return individual;
	}

	public void setIndividual(Individual individual) {
		this.individual = individual;
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
