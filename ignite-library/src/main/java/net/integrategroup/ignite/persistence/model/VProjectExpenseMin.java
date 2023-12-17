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
@Table(schema = "ig_db", name = "vProjectExpenseMin")
public class VProjectExpenseMin implements Serializable {

	private static final long serialVersionUID = -2091239835388451027L;
	
	@Id
	@Column(name = "ProjectExpenseId")
	private Long projectExpenseId;

	@Column(name = "ProjectParticipantIdPayer")
	private Long projectParticipantIdPayer;

	@Column(name = "ParticipantIdPayer")
	private Long participantIdPayer;

	@Column(name = "ParticipantPayerSystemName")
	private String participantPayerSystemName;

	@Column(name = "ProjectId")
	private Long projectId;

	@Column(name = "ProjectName")
	private String projectName;

	@Column(name = "ExpenseTypeId")
	private Long expenseTypeId;

	@Column(name = "ExpenseTypeName")
	private String expenseTypeName;

	@Column(name = "AllowanceFlag")
	private String allowanceFlag;

	@Column(name = "UnitTypeCode")
	private String unitTypeCode;

	@Column(name = "UnitTypeName")
	private String unitTypeName;

	@Column(name = "AssetId")
	private Long assetId;

	@Column(name = "VehicleId")
	private Long vehicleId;

	@Column(name = "VehicleName")
	private String vehicleName;

	@Column(name = "AssetDescription")
	private String assetDescription;

	@Column(name = "PaymentDescription")
	private String paymentDescription;

	@JsonSerialize(using = JsonDateSerializer.class)
	@Column(name = "PurchaseDate")
	private Date purchaseDate;

	@Column(name = "NumberOfUnits")
	private Double numberOfUnits;

	@Column(name = "AmountPerUnit")
	private Double amountPerUnit;

	@Column(name = "LineTotal")
	private Double lineTotal;
	
	public Double getLineTotal() {
		return lineTotal;
	}

	public void setLineTotal(Double lineTotal) {
		this.lineTotal = lineTotal;
	}

	public Long getProjectExpenseId() {
		return projectExpenseId;
	}

	public void setProjectExpenseId(Long projectExpenseId) {
		this.projectExpenseId = projectExpenseId;
	}

	public String getAllowanceFlag() {
		return allowanceFlag;
	}

	public void setAllowanceFlag(String allowanceFlag) {
		this.allowanceFlag = allowanceFlag;
	}

	public Long getProjectParticipantIdPayer() {
		return projectParticipantIdPayer;
	}

	public void setProjectParticipantIdPayer(Long projectParticipantIdPayer) {
		this.projectParticipantIdPayer = projectParticipantIdPayer;
	}

	public Long getParticipantIdPayer() {
		return participantIdPayer;
	}

	public void setParticipantIdPayer(Long participantIdPayer) {
		this.participantIdPayer = participantIdPayer;
	}

	public String getParticipantPayerSystemName() {
		return participantPayerSystemName;
	}

	public void setParticipantPayerSystemName(String participantPayerSystemName) {
		this.participantPayerSystemName = participantPayerSystemName;
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

	public Long getExpenseTypeId() {
		return expenseTypeId;
	}

	public void setExpenseTypeId(Long expenseTypeId) {
		this.expenseTypeId = expenseTypeId;
	}

	public String getExpenseTypeName() {
		return expenseTypeName;
	}

	public void setExpenseTypeName(String expenseTypeName) {
		this.expenseTypeName = expenseTypeName;
	}

	public String getUnitTypeCode() {
		return unitTypeCode;
	}

	public void setUnitTypeCode(String unitTypeCode) {
		this.unitTypeCode = unitTypeCode;
	}

	public String getUnitTypeName() {
		return unitTypeName;
	}

	public void setUnitTypeName(String unitTypeName) {
		this.unitTypeName = unitTypeName;
	}

	public Long getAssetId() {
		return assetId;
	}

	public void setAssetId(Long assetId) {
		this.assetId = assetId;
	}

	public Long getVehicleId() {
		return vehicleId;
	}

	public void setVehicleId(Long vehicleId) {
		this.vehicleId = vehicleId;
	}

	public String getVehicleName() {
		return vehicleName;
	}

	public void setVehicleName(String vehicleName) {
		this.vehicleName = vehicleName;
	}

	public String getAssetDescription() {
		return assetDescription;
	}

	public void setAssetDescription(String assetDescription) {
		this.assetDescription = assetDescription;
	}

	public String getPaymentDescription() {
		return paymentDescription;
	}

	public void setPaymentDescription(String paymentDescription) {
		this.paymentDescription = paymentDescription;
	}

	public Date getPurchaseDate() {
		return purchaseDate;
	}

	public void setPurchaseDate(Date purchaseDate) {
		this.purchaseDate = purchaseDate;
	}

	public Double getNumberOfUnits() {
		return numberOfUnits;
	}

	public void setNumberOfUnits(Double numberOfUnits) {
		this.numberOfUnits = numberOfUnits;
	}

	public Double getAmountPerUnit() {
		return amountPerUnit;
	}

	public void setAmountPerUnit(Double amountPerUnit) {
		this.amountPerUnit = amountPerUnit;
	}
	
}