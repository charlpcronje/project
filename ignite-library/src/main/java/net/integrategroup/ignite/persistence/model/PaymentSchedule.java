package net.integrategroup.ignite.persistence.model;

import java.io.Serializable;
import java.math.BigDecimal;
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

/**
 * @author Ingrid Marais
 *
 */
@Entity
@Table(schema = "ig_db", name = "PaymentSchedule")
public class PaymentSchedule implements Serializable {

	private static final long serialVersionUID = -7036776150808529927L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "PaymentScheduleId")
	private Long paymentScheduleId;

	@Column(name = "AgreementBetweenParticipantsId")
	private Long agreementBetweenParticipantsId;

	@OneToOne(targetEntity = AgreementBetweenParticipants.class)
	@JoinColumn(name = "AgreementBetweenParticipantsId", referencedColumnName = "AgreementBetweenParticipantsId", insertable = false, updatable = false)
	private AgreementBetweenParticipants agreementBetweenParticipants;

	@Column(name = "PaymentTypeId")
	private Long paymentTypeId;

	@OneToOne(targetEntity = PaymentType.class)
	@JoinColumn(name = "PaymentTypeId", referencedColumnName = "PaymentTypeId", nullable = true, insertable = false, updatable = false)
	private PaymentType paymentType;

	@Column(name = "Description")
	private String description;

	@JsonSerialize(using = JsonDateSerializer.class)
	@Column(name = "PaymentDate")
	private Date paymentDate;

	@Column(name = "PercentageOfFee")
	private BigDecimal percentageOfFee;

	@Column(name = "AmountPayable")
	private BigDecimal amountPayable;

	@JsonSerialize(using = JsonDateSerializer.class)
	@Column(name = "LastUpdateTimestamp")
	private Date lastUpdateTimestamp;

	@Column(name = "LastUpdateUserName")
	private String lastUpdateUserName;

	public Long getPaymentScheduleId() {
		return paymentScheduleId;
	}

	public void setPaymentScheduleId(Long paymentScheduleId) {
		this.paymentScheduleId = paymentScheduleId;
	}

	public Long getAgreementBetweenParticipantsId() {
		return agreementBetweenParticipantsId;
	}

	public void setAgreementBetweenParticipantsId(Long agreementBetweenParticipantsId) {
		this.agreementBetweenParticipantsId = agreementBetweenParticipantsId;
	}

	public AgreementBetweenParticipants getAgreementBetweenParticipants() {
		return agreementBetweenParticipants;
	}

	public void setAgreementBetweenParticipants(AgreementBetweenParticipants agreementBetweenParticipants) {
		this.agreementBetweenParticipants = agreementBetweenParticipants;
	}

	public Long getPaymentTypeId() {
		return paymentTypeId;
	}

	public void setPaymentTypeId(Long paymentTypeId) {
		this.paymentTypeId = paymentTypeId;
	}

	public PaymentType getPaymentType() {
		return paymentType;
	}

	public void setPaymentType(PaymentType paymentType) {
		this.paymentType = paymentType;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getPaymentDate() {
		return paymentDate;
	}

	public void setPaymentDate(Date paymentDate) {
		this.paymentDate = paymentDate;
	}

	public BigDecimal getPercentageOfFee() {
		return percentageOfFee;
	}

	public void setPercentageOfFee(BigDecimal percentageOfFee) {
		this.percentageOfFee = percentageOfFee;
	}

	public BigDecimal getAmountPayable() {
		return amountPayable;
	}

	public void setAmountPayable(BigDecimal amountPayable) {
		this.amountPayable = amountPayable;
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
