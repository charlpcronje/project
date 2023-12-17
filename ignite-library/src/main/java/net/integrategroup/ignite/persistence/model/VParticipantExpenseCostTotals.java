package net.integrategroup.ignite.persistence.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import net.integrategroup.ignite.utils.JsonDateSerializer;

/** @author Ingrid Marais */

@Entity
@Table(schema = "ig_db", name = "vParticipantExpenseCostTotals")
public class VParticipantExpenseCostTotals implements Serializable {

	private static final long serialVersionUID = 4796133497220492996L;

	@Id
	@Column(name = "RowNumber")
	private Long rowNumber;

	@Column(name = "ParticipantIdContracting")
	private Long participantIdContracting;

	@Column(name = "ParticipantInAgreementContracting")
	private String participantInAgreementContracting;

	@Column(name = "ParticipantIdContracted")
	private Long participantIdContracted;

	@Column(name = "ParticipantInAgreementContracted")
	private String participantInAgreementContracted;

	@JsonSerialize(using = JsonDateSerializer.class)
	@Column(name = "PurchaseDate")
	private Date purchaseDate;

	@Column(name = "SumNrOfUnits")
	private Double sumNrOfUnits;

	@Column(name = "LineAmount")
	private Double lineAmount;

	@Column(name = "RatesMissing")
	private Integer ratesMissing;

	public Long getRowNumber() {
		return rowNumber;
	}

	public void setRowNumber(Long rowNumber) {
		this.rowNumber = rowNumber;
	}

	public Long getParticipantIdContracting() {
		return participantIdContracting;
	}

	public void setParticipantIdContracting(Long participantIdContracting) {
		this.participantIdContracting = participantIdContracting;
	}

	public String getParticipantInAgreementContracting() {
		return participantInAgreementContracting;
	}

	public void setParticipantInAgreementContracting(String participantInAgreementContracting) {
		this.participantInAgreementContracting = participantInAgreementContracting;
	}

	public Long getParticipantIdContracted() {
		return participantIdContracted;
	}

	public void setParticipantIdContracted(Long participantIdContracted) {
		this.participantIdContracted = participantIdContracted;
	}

	public String getParticipantInAgreementContracted() {
		return participantInAgreementContracted;
	}

	public void setParticipantInAgreementContracted(String participantInAgreementContracted) {
		this.participantInAgreementContracted = participantInAgreementContracted;
	}

	public Date getPurchaseDate() {
		return purchaseDate;
	}

	public void setPurchaseDate(Date purchaseDate) {
		this.purchaseDate = purchaseDate;
	}

	public Double getSumNrOfUnits() {
		return sumNrOfUnits;
	}

	public void setSumNrOfUnits(Double sumNrOfUnits) {
		this.sumNrOfUnits = sumNrOfUnits;
	}

	public Double getLineAmount() {
		return lineAmount;
	}

	public void setLineAmount(Double lineAmount) {
		this.lineAmount = lineAmount;
	}

	public Integer getRatesMissing() {
		return ratesMissing;
	}

	public void setRatesMissing(Integer ratesMissing) {
		this.ratesMissing = ratesMissing;
	}

}