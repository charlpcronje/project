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
@Table(schema = "ig_db", name = "MedicalInsurancePlan")
public class MedicalInsurancePlan implements Serializable {

	private static final long serialVersionUID = 4097803130010792329L;

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "MedicalInsurancePlanId")
	private Long medicalInsurancePlanId;

	@Column(name = "MedicalInsuranceCompanyId")
	private Long medicalInsuranceCompanyId;

	@Column(name = "Name")
	private String name;

	@Column(name = "Description")
	private String description;

	@JsonSerialize(using = JsonDateSerializer.class)
	@Column(name = "LastUpdateTimestamp")
	private Date lastUpdateTimestamp;

	@Column(name = "LastUpdateUserName")
	private String lastUpdateUserName;

	public Long getMedicalInsurancePlanId() {
		return medicalInsurancePlanId;
	}

	public void setMedicalInsurancePlanId(Long medicalInsurancePlanId) {
		this.medicalInsurancePlanId = medicalInsurancePlanId;
	}

	public Long getMedicalInsuranceCompanyId() {
		return medicalInsuranceCompanyId;
	}

	public void setMedicalInsuranceCompanyId(Long medicalInsuranceCompanyId) {
		this.medicalInsuranceCompanyId = medicalInsuranceCompanyId;
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
