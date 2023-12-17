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
@Table(schema = "ig_db", name = "SoqItem")
public class SoqItem implements Serializable {

	private static final long serialVersionUID = 2614250349034738832L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "SoqItemId")
	private Long SoqItemId;
	
	@Column(name = "SoqSubScheduleId")
	private Long SoqSubScheduleId;
	
	@Column(name = "SoqItemIdParent")
	private Long SoqItemIdParent;

	@Column(name = "SoqUnitCode")
	private String SoqUnitCode;

	@Column(name = "Col1ItemNumber")
	private Long Col1ItemNumber;

	@Column(name = "Col2ItemNumber")
	private Long Col2ItemNumber;

	@Column(name = "Col3ItemNumber")
	private Long Col3ItemNumber;

	@Column(name = "ItemDescription")
	private Long ItemDescription;

	@Column(name = "OrderNumber")
	private Long OrderNumber;

	@Column(name = "FlagDefineSubItems")
	private Long FlagDefineSubItems;

	@Column(name = "FlagTenderRateAllowed")
	private Long FlagTenderRateAllowed;

	@Column(name = "NonTenderAmount")
	private Long NonTenderAmount;
	
	@Column(name = "LastUpdateTimestamp")
	private Long lastUpdateTimestamp;

	@Column(name = "LastUpdateUserName")
	private Long lastUpdateUserName;

	public Long getSoqItemId() {
		return SoqItemId;
	}

	public void setSoqItemId(Long soqItemId) {
		SoqItemId = soqItemId;
	}

	public Long getSoqSubScheduleId() {
		return SoqSubScheduleId;
	}

	public void setSoqSubScheduleId(Long soqSubScheduleId) {
		SoqSubScheduleId = soqSubScheduleId;
	}

	public Long getSoqItemIdParent() {
		return SoqItemIdParent;
	}

	public void setSoqItemIdParent(Long soqItemIdParent) {
		SoqItemIdParent = soqItemIdParent;
	}

	public String getSoqUnitCode() {
		return SoqUnitCode;
	}

	public void setSoqUnitCode(String soqUnitCode) {
		SoqUnitCode = soqUnitCode;
	}

	public Long getCol1ItemNumber() {
		return Col1ItemNumber;
	}

	public void setCol1ItemNumber(Long col1ItemNumber) {
		Col1ItemNumber = col1ItemNumber;
	}

	public Long getCol2ItemNumber() {
		return Col2ItemNumber;
	}

	public void setCol2ItemNumber(Long col2ItemNumber) {
		Col2ItemNumber = col2ItemNumber;
	}

	public Long getCol3ItemNumber() {
		return Col3ItemNumber;
	}

	public void setCol3ItemNumber(Long col3ItemNumber) {
		Col3ItemNumber = col3ItemNumber;
	}

	public Long getItemDescription() {
		return ItemDescription;
	}

	public void setItemDescription(Long itemDescription) {
		ItemDescription = itemDescription;
	}

	public Long getOrderNumber() {
		return OrderNumber;
	}

	public void setOrderNumber(Long orderNumber) {
		OrderNumber = orderNumber;
	}

	public Long getFlagDefineSubItems() {
		return FlagDefineSubItems;
	}

	public void setFlagDefineSubItems(Long flagDefineSubItems) {
		FlagDefineSubItems = flagDefineSubItems;
	}

	public Long getFlagTenderRateAllowed() {
		return FlagTenderRateAllowed;
	}

	public void setFlagTenderRateAllowed(Long flagTenderRateAllowed) {
		FlagTenderRateAllowed = flagTenderRateAllowed;
	}

	public Long getNonTenderAmount() {
		return NonTenderAmount;
	}

	public void setNonTenderAmount(Long nonTenderAmount) {
		NonTenderAmount = nonTenderAmount;
	}

	public Long getLastUpdateTimestamp() {
		return lastUpdateTimestamp;
	}

	public void setLastUpdateTimestamp(Long lastUpdateTimestamp) {
		this.lastUpdateTimestamp = lastUpdateTimestamp;
	}

	public Long getLastUpdateUserName() {
		return lastUpdateUserName;
	}

	public void setLastUpdateUserName(Long lastUpdateUserName) {
		this.lastUpdateUserName = lastUpdateUserName;
	}

	
}