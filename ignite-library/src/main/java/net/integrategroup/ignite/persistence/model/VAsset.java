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

/** @author Ingrid Marais **/

@Entity
@Table(schema = "ig_db", name = "vAsset")
public class VAsset implements Serializable {

	private static final long serialVersionUID = 8563778858783217153L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "AssetId")
	private Long assetId;

	@Column(name = "AssetTypeId")
	private Long assetTypeId;

	@Column(name = "AssetTypeName")
	private String assetTypeName;

	@Column(name = "AssetConditionId")
	private Long assetConditionId;

	@Column(name = "AssetConditionName")
	private String assetConditionName;

	@Column(name = "AssetStatusId")
	private Long assetStatusId;

	@Column(name = "AssetStatusName")
	private String assetStatusName;

	@Column(name = "ParticipantIdOriginalOwner")
	private Long participantIdOriginalOwner;

	@Column(name = "OriginalOwner")
	private String originalOwner;

	@Column(name = "ParticipantIdCurrentOwner")
	private Long participantIdCurrentOwner;

	@Column(name = "CurrentOwner")
	private String currentOwner;

	@Column(name = "ParticipantIdSponsor")
	private Long participantIdSponsor;

	@Column(name = "Sponsor")
	private String sponsor;

	@Column(name = "ParticipantIdSoldTo")
	private Long participantIdSoldTo;

	@Column(name = "SoldTo")
	private String soldTo;

	@Column(name = "VehicleId")
	private Long vehicleId;

	@Column(name = "VehicleName")
	private String vehicleName;

	@Column(name = "LicenceNumber")
	private String licenceNumber;

	@Column(name = "ParticipantOfficeIdLocation")
	private Long participantOfficeIdLocation;

	@Column(name = "ParticipantOfficeName")
	private String participantOfficeName;

	@Column(name = "AssetNumber")
	private Long assetNumber;

	@Column(name = "Description")
	private String description;

	@Column(name = "BrandOrMake")
	private String brandOrMake;

	@Column(name = "SerialNumber")
	private String serialNumber;

	@Column(name = "ParticipantRightOfUse")
	private String participantRightOfUse;



	@Column(name = "PurchaseAmount")
	private Double purchaseAmount;

	@JsonSerialize(using = JsonDateSerializer.class)
	@Column(name = "GuaranteePeriodEnd")
	private Date guaranteePeriodEnd;



	@JsonSerialize(using = JsonDateSerializer.class)
	@Column(name = "AssetAquiredDate")
	private Date assetAquiredDate;

	@JsonSerialize(using = JsonDateSerializer.class)
	@Column(name = "OwnershipToSponsorDate")
	private Date ownershipToSponsorDate;

	@JsonSerialize(using = JsonDateSerializer.class)
	@Column(name = "AssetRemovedDate")
	private Date assetRemovedDate;

	@Column(name = "AssetSoldAmount")
	private Double assetSoldAmount;

	@JsonSerialize(using = JsonDateSerializer.class)
	@Column(name = "LastUpdateTimestamp")
	private Date lastUpdateTimestamp;

	@Column(name = "LastUpdateUserName")
	private String lastUpdateUserName;

	public Long getAssetId() {
		return assetId;
	}

	public void setAssetId(Long assetId) {
		this.assetId = assetId;
	}

	public Long getAssetTypeId() {
		return assetTypeId;
	}



	public String getSponsor() {
		return sponsor;
	}

	public void setSponsor(String sponsor) {
		this.sponsor = sponsor;
	}

	public void setAssetTypeId(Long assetTypeId) {
		this.assetTypeId = assetTypeId;
	}

	public String getAssetTypeName() {
		return assetTypeName;
	}

	public void setAssetTypeName(String assetTypeName) {
		this.assetTypeName = assetTypeName;
	}

	public Long getAssetConditionId() {
		return assetConditionId;
	}

	public void setAssetConditionId(Long assetConditionId) {
		this.assetConditionId = assetConditionId;
	}

	public String getAssetConditionName() {
		return assetConditionName;
	}

	public void setAssetConditionName(String assetConditionName) {
		this.assetConditionName = assetConditionName;
	}

	public Long getAssetStatusId() {
		return assetStatusId;
	}

	public void setAssetStatusId(Long assetStatusId) {
		this.assetStatusId = assetStatusId;
	}

	public String getAssetStatusName() {
		return assetStatusName;
	}

	public void setAssetStatusName(String assetStatusName) {
		this.assetStatusName = assetStatusName;
	}

	public Long getParticipantIdOriginalOwner() {
		return participantIdOriginalOwner;
	}

	public void setParticipantIdOriginalOwner(Long participantIdOriginalOwner) {
		this.participantIdOriginalOwner = participantIdOriginalOwner;
	}

	public String getOriginalOwner() {
		return originalOwner;
	}

	public void setOriginalOwner(String originalOwner) {
		this.originalOwner = originalOwner;
	}

	public Long getParticipantIdCurrentOwner() {
		return participantIdCurrentOwner;
	}

	public void setParticipantIdCurrentOwner(Long participantIdCurrentOwner) {
		this.participantIdCurrentOwner = participantIdCurrentOwner;
	}

	public String getCurrentOwner() {
		return currentOwner;
	}

	public void setCurrentOwner(String currentOwner) {
		this.currentOwner = currentOwner;
	}

	public Long getParticipantIdSponsor() {
		return participantIdSponsor;
	}

	public void setParticipantIdSponsor(Long participantIdSponsor) {
		this.participantIdSponsor = participantIdSponsor;
	}

	public Long getParticipantIdSoldTo() {
		return participantIdSoldTo;
	}

	public void setParticipantIdSoldTo(Long participantIdSoldTo) {
		this.participantIdSoldTo = participantIdSoldTo;
	}

	public String getSoldTo() {
		return soldTo;
	}

	public void setSoldTo(String soldTo) {
		this.soldTo = soldTo;
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

	public String getLicenceNumber() {
		return licenceNumber;
	}

	public void setLicenceNumber(String licenceNumber) {
		this.licenceNumber = licenceNumber;
	}

	public Long getParticipantOfficeIdLocation() {
		return participantOfficeIdLocation;
	}

	public void setParticipantOfficeIdLocation(Long participantOfficeIdLocation) {
		this.participantOfficeIdLocation = participantOfficeIdLocation;
	}

	public String getParticipantOfficeName() {
		return participantOfficeName;
	}

	public void setParticipantOfficeName(String participantOfficeName) {
		this.participantOfficeName = participantOfficeName;
	}

	public Long getAssetNumber() {
		return assetNumber;
	}

	public void setAssetNumber(Long assetNumber) {
		this.assetNumber = assetNumber;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getBrandOrMake() {
		return brandOrMake;
	}

	public void setBrandOrMake(String brandOrMake) {
		this.brandOrMake = brandOrMake;
	}

	public String getSerialNumber() {
		return serialNumber;
	}

	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}

	public String getParticipantRightOfUse() {
		return participantRightOfUse;
	}

	public void setParticipantRightOfUse(String participantRightOfUse) {
		this.participantRightOfUse = participantRightOfUse;
	}



	public Double getPurchaseAmount() {
		return purchaseAmount;
	}

	public void setPurchaseAmount(Double purchaseAmount) {
		this.purchaseAmount = purchaseAmount;
	}

	public Date getGuaranteePeriodEnd() {
		return guaranteePeriodEnd;
	}

	public void setGuaranteePeriodEnd(Date guaranteePeriodEnd) {
		this.guaranteePeriodEnd = guaranteePeriodEnd;
	}



	public Date getAssetAquiredDate() {
		return assetAquiredDate;
	}

	public void setAssetAquiredDate(Date assetAquiredDate) {
		this.assetAquiredDate = assetAquiredDate;
	}

	public Date getOwnershipToSponsorDate() {
		return ownershipToSponsorDate;
	}

	public void setOwnershipToSponsorDate(Date ownershipToSponsorDate) {
		this.ownershipToSponsorDate = ownershipToSponsorDate;
	}

	public Date getAssetRemovedDate() {
		return assetRemovedDate;
	}

	public void setAssetRemovedDate(Date assetRemovedDate) {
		this.assetRemovedDate = assetRemovedDate;
	}

	public Double getAssetSoldAmount() {
		return assetSoldAmount;
	}

	public void setAssetSoldAmount(Double assetSoldAmount) {
		this.assetSoldAmount = assetSoldAmount;
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