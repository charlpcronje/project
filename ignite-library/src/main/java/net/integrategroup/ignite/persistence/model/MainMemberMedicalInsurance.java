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
@Table(schema = "ig_db", name = "MainMemberMedicalInsurance")
public class MainMemberMedicalInsurance implements Serializable {

	private static final long serialVersionUID = -4657678570706225155L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "MainMemberMedicalInsuranceId")
	private Long mainMemberMedicalInsuranceId;

    @Column(name="IndividualId")
    private Long individualId;

    @Column(name="MedicalInsuranceCompanyId")
    private Long medicalInsuranceCompanyId;

    @Column(name="MedicalInsurancePlanId")
    private Long medicalInsurancePlanId;

    @Column(name="InsuranceNumber")
    private String insuranceNumber;

    public String getInsuranceNumber() {
		return insuranceNumber;
	}

	public void setInsuranceNumber(String insuranceNumber) {
		this.insuranceNumber = insuranceNumber;
	}

	@Column(name="Description")
    private String description;

	@JsonSerialize(using = JsonDateSerializer.class)
	@Column(name = "LastUpdateTimestamp")
	private Date lastUpdateTimestamp;

	@Column(name = "LastUpdateUserName")
	private String lastUpdateUserName;

	public Long getMainMemberMedicalInsuranceId() {
		return mainMemberMedicalInsuranceId;
	}

	public void setMainMemberMedicalInsuranceId(Long mainMemberMedicalInsuranceId) {
		this.mainMemberMedicalInsuranceId = mainMemberMedicalInsuranceId;
	}

	public Long getIndividualId() {
		return individualId;
	}

	public void setIndividualId(Long individualId) {
		this.individualId = individualId;
	}

	public Long getMedicalInsuranceCompanyId() {
		return medicalInsuranceCompanyId;
	}

	public void setMedicalInsuranceCompanyId(Long medicalInsuranceCompanyId) {
		this.medicalInsuranceCompanyId = medicalInsuranceCompanyId;
	}

	public Long getMedicalInsurancePlanId() {
		return medicalInsurancePlanId;
	}

	public void setMedicalInsurancePlanId(Long medicalInsurancePlanId) {
		this.medicalInsurancePlanId = medicalInsurancePlanId;
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
