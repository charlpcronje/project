package net.integrategroup.ignite.persistence.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import net.integrategroup.ignite.utils.JsonDateSerializer;

@Entity
@Table(schema = "ig_db", name = "IndividualSd")
public class IndividualSd implements Serializable {

	private static final long serialVersionUID = -917511666254758499L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "IndividualSdId")
	private Long individualSdId;

	@Column(name = "IndividualId")
	private Long individualId;

	@OneToOne(targetEntity = Individual.class, fetch = FetchType.EAGER)
	@JoinColumn(name = "IndividualId", referencedColumnName = "IndividualId", updatable = false, insertable = false)
	private Individual individual;

	@Column(name = "ServiceDisciplineCode")
	private String serviceDisciplineCode;

	@OneToOne(targetEntity = ServiceDiscipline.class, fetch = FetchType.EAGER)
	@JoinColumn(name = "ServiceDisciplineCode", referencedColumnName = "ServiceDisciplineCode", updatable = false, insertable = false)
	private ServiceDiscipline serviceDiscipline;

	@JsonSerialize(using = JsonDateSerializer.class)
	@Column(name = "LastUpdateTimestamp")
	private Date lastUpdateTimestamp;

	@Column(name = "LastUpdateUserName")
	private String lastUpdateUserName;

	public Long getIndividualSdId() {
		return individualSdId;
	}

	public void setIndividualSdId(Long individualSdId) {
		this.individualSdId = individualSdId;
	}

	public Long getIndividualId() {
		return individualId;
	}

	public void setIndividualId(Long individualId) {
		this.individualId = individualId;
	}

	public Individual getIndividual() {
		return individual;
	}

	public void setIndividual(Individual individual) {
		this.individual = individual;
	}

	public String getServiceDisciplineCode() {
		return serviceDisciplineCode;
	}

	public void setServiceDisciplineCode(String serviceDisciplineCode) {
		this.serviceDisciplineCode = serviceDisciplineCode;
	}

	public ServiceDiscipline getServiceDiscipline() {
		return serviceDiscipline;
	}

	public void setServiceDiscipline(ServiceDiscipline serviceDiscipline) {
		this.serviceDiscipline = serviceDiscipline;
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
