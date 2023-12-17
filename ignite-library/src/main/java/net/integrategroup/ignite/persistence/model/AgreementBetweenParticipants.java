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

// @author Ingrid Marais

@Entity
@Table(schema = "ig_db", name = "AgreementBetweenParticipants")
public class AgreementBetweenParticipants implements Serializable {

	private static final long serialVersionUID = 2658851607947251674L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "AgreementBetweenParticipantsId")
	private Long agreementBetweenParticipantsId;

	@Column(name = "RemunerationModelCode")
	private String remunerationModelCode;

	@OneToOne(targetEntity = RemunerationModel.class)
	@JoinColumn(name = "RemunerationModelCode", referencedColumnName = "RemunerationModelCode", nullable = true, insertable = false, updatable = false)
	private RemunerationModel remunerationModel;

	@Column(name = "ProjectParticipantId")
	private Long projectParticipantId;

	@OneToOne(targetEntity = ProjectParticipant.class)
	@JoinColumn(name = "ProjectParticipantId", referencedColumnName = "ProjectParticipantId", nullable = true, insertable = false, updatable = false)
	private ProjectParticipant projectParticipant;

	@Column(name = "AgreementBudget")
	private Double agreementBudget;

	@Column(name = "Description")
	private String description;

	@Column(name = "FSPreSplitContractingExpDeduct")
	private String fsPreSplitContractingExpDeduct;

	@Column(name = "FSPreSplitContractingThirdPDeduct")
	private String fsPreSplitContractingThirdPDeduct;

	@Column(name = "FSContractedExpensesAdded")
	private String fsContractedExpensesAdded;

	@Column(name = "FSPreSplitOtherPartInvoices")
	private String fsPreSplitOtherPartInvoices;

	@JsonSerialize(using = JsonDateSerializer.class)
	@Column(name = "LastUpdateTimestamp")
	private Date lastUpdateTimestamp;

	@Column(name = "LastUpdateUserName")
	private String lastUpdateUserName;

	public Long getAgreementBetweenParticipantsId() {
		return agreementBetweenParticipantsId;
	}

	public void setAgreementBetweenParticipantsId(Long agreementBetweenParticipantsId) {
		this.agreementBetweenParticipantsId = agreementBetweenParticipantsId;
	}

	public String getRemunerationModelCode() {
		return remunerationModelCode;
	}
	public Double getAgreementBudget() {
		return agreementBudget;
	}

	public void setAgreementBudget(Double agreementBudget) {
		this.agreementBudget = agreementBudget;
	}

	public void setRemunerationModelCode(String remunerationModelCode) {
		this.remunerationModelCode = remunerationModelCode;
	}

	public RemunerationModel getRemunerationModel() {
		return remunerationModel;
	}

	public void setRemunerationModel(RemunerationModel remunerationModel) {
		this.remunerationModel = remunerationModel;
	}

	public Long getProjectParticipantId() {
		return projectParticipantId;
	}

	public void setProjectParticipantId(Long projectParticipantId) {
		this.projectParticipantId = projectParticipantId;
	}

	public ProjectParticipant getProjectParticipant() {
		return projectParticipant;
	}

	public void setProjectParticipant(ProjectParticipant projectParticipant) {
		this.projectParticipant = projectParticipant;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getFsPreSplitContractingExpDeduct() {
		return fsPreSplitContractingExpDeduct;
	}

	public void setFsPreSplitContractingExpDeduct(String fsPreSplitContractingExpDeduct) {
		this.fsPreSplitContractingExpDeduct = fsPreSplitContractingExpDeduct;
	}

	public String getFsPreSplitContractingThirdPDeduct() {
		return fsPreSplitContractingThirdPDeduct;
	}

	public void setFsPreSplitContractingThirdPDeduct(String fsPreSplitContractingThirdPDeduct) {
		this.fsPreSplitContractingThirdPDeduct = fsPreSplitContractingThirdPDeduct;
	}

	public String getFsContractedExpensesAdded() {
		return fsContractedExpensesAdded;
	}

	public void setFsContractedExpensesAdded(String fsContractedExpensesAdded) {
		this.fsContractedExpensesAdded = fsContractedExpensesAdded;
	}

	public String getFsPreSplitOtherPartInvoices() {
		return fsPreSplitOtherPartInvoices;
	}

	public void setFsPreSplitOtherPartInvoices(String fsPreSplitOtherPartInvoices) {
		this.fsPreSplitOtherPartInvoices = fsPreSplitOtherPartInvoices;
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
