package net.integrategroup.ignite.persistence.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(schema = "ig_db", name = "vParticipantMin")
public class VParticipantMin implements Serializable {

	private static final long serialVersionUID = -8799506249074171966L;

	@Id
	@Column(name = "ParticipantId")
	private Long participantId;

	@Column(name = "SystemName")
	private String systemName;
	
	@Column(name = "LatestProjectNumber")
	private String latestProjectNumber;

	@Column(name = "ProjectPrefix")
	private String projectPrefix;
	
	@Column(name = "ProjectPostfix")
	private String projectPostfix;

	@Column(name = "RegisteredName")
	private String registeredName;

	@Column(name = "TradingName")
	private String tradingName;

	@Column(name = "IndividualId")
	private Long individualId;

	@Column(name = "FirstName")
	private String firstName;

	@Column(name = "NickName")
	private String nickName;

	@Column(name = "LastName")
	private String lastName;

	@Column(name = "Initials")
	private String initials;

	@Column(name = "IdNumber")
	private String idNumber;

	@Column(name = "IndividualName")
	private String individualName;
	
	@Column(name = "NonIndivName")
	private String nonIndivName;
    
	@Column(name = "RepresentativeIndividualId")
	private Long representativeIndividualId;

	@Column(name = "Representative")
	private String representative;

	@Column(name = "MarketingIndividualId")
	private Long marketingIndividualId;

	@Column(name = "Marketer")
	private String marketer;

	@Column(name = "ParticipantTypeCode")
	private String participantTypeCode;

	@Column(name = "ParticipantTypeName")
	private String participantTypeName;

	@Column(name = "IsIndividual")
	private String isIndividual;

	@Column(name = "TapAdministered")
	private String tapAdministered;

	@Column(name = "TapSubscriptionCode")
	private String tapSubscriptionCode;

	@Column(name = "TapSubscriptionName")
	private String tapSubscriptionName;

	@Column(name = "EmailAddressAccounts")
	private String emailAddressAccounts;

	@Column(name = "IsActiveMember")
	private String isActiveMember;

	@Column(name = "ContactPointCityName")
	private String contactPointCityName;

	@Column(name = "ContactPointCountryName")
	private String ContactPointCountryName;
    
	@Column(name = "EmailAddress")
	private String emailAddress;

	@Column(name = "PhoneNumber")
	private String phoneNumber;

	@Column(name = "VatNumber")
	private String vatNumber;
	
	
	public String getVatNumber() {
		return vatNumber;
	}

	public void setVatNumber(String vatNumber) {
		this.vatNumber = vatNumber;
	}

	public String getEmailAddress() {
		return emailAddress;
	}

	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public Long getParticipantId() {
		return participantId;
	}

	public void setParticipantId(Long participantId) {
		this.participantId = participantId;
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

	public Long getIndividualId() {
		return individualId;
	}

	public void setIndividualId(Long individualId) {
		this.individualId = individualId;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getInitials() {
		return initials;
	}

	public void setInitials(String initials) {
		this.initials = initials;
	}

	public String getIdNumber() {
		return idNumber;
	}

	public void setIdNumber(String idNumber) {
		this.idNumber = idNumber;
	}

	public Long getRepresentativeIndividualId() {
		return representativeIndividualId;
	}

	public void setRepresentativeIndividualId(Long representativeIndividualId) {
		this.representativeIndividualId = representativeIndividualId;
	}

	public String getRepresentative() {
		return representative;
	}

	public void setRepresentative(String representative) {
		this.representative = representative;
	}

	public Long getMarketingIndividualId() {
		return marketingIndividualId;
	}

	public void setMarketingIndividualId(Long marketingIndividualId) {
		this.marketingIndividualId = marketingIndividualId;
	}

	public String getMarketer() {
		return marketer;
	}

	public void setMarketer(String marketer) {
		this.marketer = marketer;
	}

	public String getParticipantTypeCode() {
		return participantTypeCode;
	}

	public void setParticipantTypeCode(String participantTypeCode) {
		this.participantTypeCode = participantTypeCode;
	}

	public String getParticipantTypeName() {
		return participantTypeName;
	}

	public void setParticipantTypeName(String participantTypeName) {
		this.participantTypeName = participantTypeName;
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

	public String getTapSubscriptionCode() {
		return tapSubscriptionCode;
	}

	public void setTapSubscriptionCode(String tapSubscriptionCode) {
		this.tapSubscriptionCode = tapSubscriptionCode;
	}

	public String getTapSubscriptionName() {
		return tapSubscriptionName;
	}

	public void setTapSubscriptionName(String tapSubscriptionName) {
		this.tapSubscriptionName = tapSubscriptionName;
	}

	public String getEmailAddressAccounts() {
		return emailAddressAccounts;
	}

	public void setEmailAddressAccounts(String emailAddressAccounts) {
		this.emailAddressAccounts = emailAddressAccounts;
	}

	public String getIsActiveMember() {
		return isActiveMember;
	}

	public void setIsActiveMember(String isActiveMember) {
		this.isActiveMember = isActiveMember;
	}

	public String getContactPointCityName() {
		return contactPointCityName;
	}

	public void setContactPointCityName(String contactPointCityName) {
		this.contactPointCityName = contactPointCityName;
	}

	public String getContactPointCountryName() {
		return ContactPointCountryName;
	}

	public void setContactPointCountryName(String contactPointCountryName) {
		ContactPointCountryName = contactPointCountryName;
	}

	public String getLatestProjectNumber() {
		return latestProjectNumber;
	}

	public void setLatestProjectNumber(String latestProjectNumber) {
		this.latestProjectNumber = latestProjectNumber;
	}

	public String getProjectPrefix() {
		return projectPrefix;
	}

	public void setProjectPrefix(String projectPrefix) {
		this.projectPrefix = projectPrefix;
	}

	public String getProjectPostfix() {
		return projectPostfix;
	}

	public void setProjectPostfix(String projectPostfix) {
		this.projectPostfix = projectPostfix;
	}

	public String getIndividualName() {
		return individualName;
	}

	public void setIndividualName(String individualName) {
		this.individualName = individualName;
	}

	public String getNonIndivName() {
		return nonIndivName;
	}

	public void setNonIndivName(String nonIndivName) {
		this.nonIndivName = nonIndivName;
	}

	
}