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

//  @author Ingrid Marais

@Entity
@Table(schema = "ig_db", name = "ExpenseRate")
public class ExpenseRate implements Serializable {

	private static final long serialVersionUID = 3300056072211962407L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ExpenseRateId")
	private Long expenseRateId;

	@Column(name = "RecoverableExpenseId")
	private Long recoverableExpenseId;

	@Column(name = "ExpenseRatePerUnit")
	private Double expenseRatePerUnit;

	@Column(name = "ExpenseHandlingPerc")
	private Double expenseHandlingPerc;

	@Column(name = "MaxExpenseAmtPerUnit")
	private Double maxExpenseAmtPerUnit;

	@Column(name = "Description")
	private String description;

	@JsonSerialize(using = JsonDateSerializer.class)
	@Column(name = "StartDate")
	private Date startDate;

	@JsonSerialize(using = JsonDateSerializer.class)
	@Column(name = "EndDate")
	private Date endDate;

	@JsonSerialize(using = JsonDateSerializer.class)
	@Column(name = "LastUpdateTimestamp")
	private Date lastUpdateTimestamp;

	@Column(name = "LastUpdateUserName")
	private String lastUpdateUserName;

	public Long getExpenseRateId() {
		return expenseRateId;
	}

	public void setExpenseRateId(Long expenseRateId) {
		this.expenseRateId = expenseRateId;
	}

	public Long getRecoverableExpenseId() {
		return recoverableExpenseId;
	}

	public void setRecoverableExpenseId(Long recoverableExpenseId) {
		this.recoverableExpenseId = recoverableExpenseId;
	}

	public Double getExpenseRatePerUnit() {
		return expenseRatePerUnit;
	}

	public void setExpenseRatePerUnit(Double expenseRatePerUnit) {
		this.expenseRatePerUnit = expenseRatePerUnit;
	}

	public Double getExpenseHandlingPerc() {
		return expenseHandlingPerc;
	}

	public void setExpenseHandlingPerc(Double expenseHandlingPerc) {
		this.expenseHandlingPerc = expenseHandlingPerc;
	}

	public Double getMaxExpenseAmtPerUnit() {
		return maxExpenseAmtPerUnit;
	}

	public void setMaxExpenseAmtPerUnit(Double maxExpenseAmtPerUnit) {
		this.maxExpenseAmtPerUnit = maxExpenseAmtPerUnit;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
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
