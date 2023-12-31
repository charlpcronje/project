package net.integrategroup.ignite.persistence.model;

import java.io.Serializable;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.Base64;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import net.integrategroup.ignite.utils.JsonDateSerializer;

@Entity
@Table(schema = "ig_db", name = "Participant")
public class Participant implements Serializable {

    private static final long serialVersionUID = 158563266367081268L; /** ID was Generated by Johannes **/

    @Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ParticipantId")
	private Long participantId;

	@Column(name = "ProjectIdSustenance")
	private Long projectIdSustenance;

	@Column(name = "ParticipantIdBuParent")
	private Long participantIdBuParent;

	@Column(name = "ParticipantTypeCode")
	private String participantTypeCode;

	@Column(name = "TapSubscriptionCode")
	private String tapSubscriptionCode;

	@Column(name = "RepresentativeIndividualId")
	private Long representativeIndividualId;

	@Column(name = "MarketingIndividualId")
	private Long marketingIndividualId;

	@Column(name = "ParticipantOfficeIdDefault")
	private Long participantOfficeIdDefault;

	@Column(name = "ParticipantBankDetailsIdDefault")
	private Long participantBankDetailsIdDefault;

	@Column(name = "SystemName")
	private String systemName;

	@Column(name = "RegisteredName")
	private String registeredName;

	@Column(name = "TradingName")
	private String tradingName;

	@Column(name = "ProjectPrefix")
	private String projectPrefix;

	@Column(name = "LatestProjectNumber")
	private Long latestProjectNumber;

	@Column(name = "ProjectPostfix")
	private String projectPostfix;

	@Column(name = "IsIndividual")
	private String isIndividual;

	@Column(name = "TapAdministered")
	private String tapAdministered;

	@Column(name = "RegistrationNumber")
	private String registrationNumber;

	@Column(name = "VatNumber")
	private String vatNumber;

	@Column(name = "DefaultInvoiceDay")
	private Integer defaultInvoiceDay;

	@JsonSerialize(using = JsonDateSerializer.class)
	@Column(name = "StartDate")
	private Date startDate;

	@JsonSerialize(using = JsonDateSerializer.class)
	@Column(name = "EndDate")
	private Date endDate;

	@Column(name = "InvoiceNumberFormat")
	private String invoiceNumberFormat;

	@Column(name = "InvoicePrefix")
	private String invoicePrefix;

	@Column(name = "LatestInvoiceNumber")
	private Long latestInvoiceNumber;

	@Column(name = "EmailAddressAccounts")
	private String emailAddressAccounts;

	@JsonSerialize(using = JsonDateSerializer.class)
	@Column(name = "LastUpdateTimestamp")
	private Date lastUpdateTimestamp;

	@Column(name = "LastUpdateUserName")
	private String lastUpdateUserName;

	@Lob
	@Column(name = "ParticipantLogo")
	private Blob participantLogo;

	@JsonProperty("logo")
	public String getLogo() {
		String result = null;

		if (participantLogo != null) {
			try {
				result = new String(Base64.getEncoder().encode(participantLogo.getBytes(0, (int) participantLogo.length())));
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return result;
	}

	public Long getParticipantId() {
		return participantId;
	}

	public void setParticipantId(Long participantId) {
		this.participantId = participantId;
	}

	public Long getProjectIdSustenance() {
		return projectIdSustenance;
	}

	public void setProjectIdSustenance(Long projectIdSustenance) {
		this.projectIdSustenance = projectIdSustenance;
	}

	public Long getParticipantIdBuParent() {
		return participantIdBuParent;
	}

	public void setParticipantIdBuParent(Long participantIdBuParent) {
		this.participantIdBuParent = participantIdBuParent;
	}

	public String getParticipantTypeCode() {
		return participantTypeCode;
	}

	public void setParticipantTypeCode(String participantTypeCode) {
		this.participantTypeCode = participantTypeCode;
	}

	public String getTapSubscriptionCode() {
		return tapSubscriptionCode;
	}

	public void setTapSubscriptionCode(String tapSubscriptionCode) {
		this.tapSubscriptionCode = tapSubscriptionCode;
	}

	public Long getRepresentativeIndividualId() {
		return representativeIndividualId;
	}

	public void setRepresentativeIndividualId(Long representativeIndividualId) {
		this.representativeIndividualId = representativeIndividualId;
	}

	public Long getMarketingIndividualId() {
		return marketingIndividualId;
	}

	public void setMarketingIndividualId(Long marketingIndividualId) {
		this.marketingIndividualId = marketingIndividualId;
	}

	public Long getParticipantOfficeIdDefault() {
		return participantOfficeIdDefault;
	}

	public void setParticipantOfficeIdDefault(Long participantOfficeIdDefault) {
		this.participantOfficeIdDefault = participantOfficeIdDefault;
	}

	public Long getParticipantBankDetailsIdDefault() {
		return participantBankDetailsIdDefault;
	}

	public void setParticipantBankDetailsIdDefault(Long participantBankDetailsIdDefault) {
		this.participantBankDetailsIdDefault = participantBankDetailsIdDefault;
	}

	public String getSystemName() {
		return systemName;
	}

	public void setSystemName(String systemName) {
		this.systemName = systemName;
	}

	public String getRegisteredName() {
		return registeredName;
	}

	public void setRegisteredName(String registeredName) {
		this.registeredName = registeredName;
	}

	public String getTradingName() {
		return tradingName;
	}

	public void setTradingName(String tradingName) {
		this.tradingName = tradingName;
	}

	public String getProjectPrefix() {
		return projectPrefix;
	}

	public void setProjectPrefix(String projectPrefix) {
		this.projectPrefix = projectPrefix;
	}

	public Long getLatestProjectNumber() {
		return latestProjectNumber;
	}

	public void setLatestProjectNumber(Long latestProjectNumber) {
		this.latestProjectNumber = latestProjectNumber;
	}

	public String getProjectPostfix() {
		return projectPostfix;
	}

	public void setProjectPostfix(String projectPostfix) {
		this.projectPostfix = projectPostfix;
	}

	public String getIsIndividual() {
		return isIndividual;
	}

	public void setIsIndividual(String isIndividual) {
		this.isIndividual = isIndividual;
	}

	public String getTapAdministered() {
		return tapAdministered;
	}

	public void setTapAdministered(String tapAdministered) {
		this.tapAdministered = tapAdministered;
	}

	public String getRegistrationNumber() {
		return registrationNumber;
	}

	public void setRegistrationNumber(String registrationNumber) {
		this.registrationNumber = registrationNumber;
	}

	public String getVatNumber() {
		return vatNumber;
	}

	public void setVatNumber(String vatNumber) {
		this.vatNumber = vatNumber;
	}

	public Integer getDefaultInvoiceDay() {
		return defaultInvoiceDay;
	}

	public void setDefaultInvoiceDay(Integer defaultInvoiceDay) {
		this.defaultInvoiceDay = defaultInvoiceDay;
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

	public String getInvoiceNumberFormat() {
		return invoiceNumberFormat;
	}

	public void setInvoiceNumberFormat(String invoiceNumberFormat) {
		this.invoiceNumberFormat = invoiceNumberFormat;
	}

	public String getInvoicePrefix() {
		return invoicePrefix;
	}

	public void setInvoicePrefix(String invoicePrefix) {
		this.invoicePrefix = invoicePrefix;
	}

	public Long getLatestInvoiceNumber() {
		return latestInvoiceNumber;
	}

	public void setLatestInvoiceNumber(Long latestInvoiceNumber) {
		this.latestInvoiceNumber = latestInvoiceNumber;
	}

	public String getEmailAddressAccounts() {
		return emailAddressAccounts;
	}

	public void setEmailAddressAccounts(String emailAddressAccounts) {
		this.emailAddressAccounts = emailAddressAccounts;
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

	public Blob getParticipantLogo() {
		return participantLogo;
	}

	public void setParticipantLogo(Blob participantLogo) {
		this.participantLogo = participantLogo;
	}


}