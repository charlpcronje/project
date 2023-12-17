package net.integrategroup.ignite.persistence.model;

import java.io.Serializable;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.Base64;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import net.integrategroup.ignite.utils.JsonDateSerializer;

// Don't overwrite this ------  It has special code for logos --------------------------------------


@Entity
@Table(schema = "ig_db", name = "vParticipant")
public class VParticipant implements Serializable {

	private static final long serialVersionUID = -4955922410935804577L;

	@Id
	@Column(name = "ParticipantId")
	private Long participantId;

	@Column(name = "ProjectIdSustenance")
	private Long projectIdSustenance;

	@Column(name = "ParticipantIdBUParent")
	private Long participantIdBUParent;

	@Column(name = "ParticipantTypeCode")
	private String participantTypeCode;

	@Column(name = "TapSubscriptionCode")
	private String tapSubscriptionCode;

	@Column(name = "RepresentativeIndividualId")
	private Long representativeIndividualId;

	@Column(name = "Representative")
	private String representative;

	@Column(name = "MarketingIndividualId")
	private Long marketingIndividualId;

	@Column(name = "Marketer")
	private String marketer;

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

	@JsonSerialize(using = JsonDateSerializer.class)
	@Column(name = "StartDate")
	private Date startDate;

	@JsonSerialize(using = JsonDateSerializer.class)
	@Column(name = "EndDate")
	private Date endDate;

	@Column(name = "IsIndividual")
	private String isIndividual;

	@Column(name = "TapAdministered")
	private String tapAdministered;

	@Column(name = "RegistrationNumber")
	private String registrationNumber;

	@Column(name = "VatNumber")
	private String vatNumber;

	@Column(name = "DefaultInvoiceDay")
	private Long defaultInvoiceDay;

	@Column(name = "IndividualId")
	private Long individualId;

	@Column(name = "FirstName")
	private String firstName;

	@Column(name = "SecondName")
	private String secondName;

	@Column(name = "ThirdName")
	private String thirdName;

	@Column(name = "NickName")
	private String nickName;

	@Column(name = "LastName")
	private String lastName;

	@Column(name = "Initials")
	private String initials;

	@Column(name = "IdNumber")
	private String idNumber;

	@Column(name = "PassportNumber")
	private String passportNumber;

	@Column(name = "CountryId")
	private Long countryId;

	@Column(name = "CountryName")
	private String countryName;

	@Column(name = "IsActiveMember")
	private String isActiveMember;

	@Column(name = "AllowLoginFlag")
	private String allowLoginFlag;

	@Column(name = "IncomeTaxNumber")
	private String incomeTaxNumber;

	@Column(name = "Pass")
	private String pass;

	@Column(name = "UserName")
	private String userName;

	@Column(name = "PasswordResetToken")
	private String passwordResetToken;

	@JsonSerialize(using = JsonDateSerializer.class)
	@Column(name = "PasswordResetExpiryDate")
	private Date passwordResetExpiryDate;

	@JsonSerialize(using = JsonDateSerializer.class)
	@Column(name = "LastLoginTimestamp")
	private Date lastLoginTimestamp;

	@Column(name = "SystemMemberId")
	private Long systemMemberId;

	@JsonSerialize(using = JsonDateSerializer.class)
	@Column(name = "SystemMemberStartDate")
	private Date systemMemberStartDate;

	@JsonSerialize(using = JsonDateSerializer.class)
	@Column(name = "SystemMemberEndDate")
	private Date systemMemberEndDate;

	@Column(name = "RoleCode")
	private String roleCode;

	@Column(name = "RoleName")
	private String roleName;

	@Column(name = "MedicalName")
	private String medicalName;

	@Column(name = "MedicalNumber")
	private String medicalNumber;

	@Column(name = "TapSubscriptionName")
	private String tapSubscriptionName;

	@Column(name = "ParticipantTypeName")
	private String participantTypeName;

	@Column(name = "ParticipantOfficeId")
	private Long participantOfficeId;

	@Column(name = "ContactPointIdDefault")
	private Long contactPointIdDefault;

	@Column(name = "ParticipantOfficeName")
	private String participantOfficeName;

	@Column(name = "ParticipantOfficeDescription")
	private String participantOfficeDescription;

	@Column(name = "ContactPointName")
	private String contactPointName;

	@Column(name = "ContactPointCityId")
	private Long contactPointCityId;

	@Column(name = "ContactPointCityName")
	private String contactPointCityName;

	@Column(name = "ContactPointSuburbId")
	private Long contactPointSuburbId;

	@Column(name = "ContactPointSuburbName")
	private String contactPointSuburbName;

	@Column(name = "ContactPointProvinceId")
	private Long contactPointProvinceId;

	@Column(name = "ContactPointProvinceName")
	private String contactPointProvinceName;

	@Column(name = "ContactPointCountryId")
	private Long contactPointCountryId;

	@Column(name = "ContactPointCountryName")
	private String contactPointCountryName;

	@Column(name = "EmailAddress")
	private String emailAddress;

	@Column(name = "AddressLine1")
	private String addressLine1;

	@Column(name = "AddressLine2")
	private String addressLine2;

	@Column(name = "AddressLine3")
	private String addressLine3;

	@Column(name = "PhoneNumber")
	private String phoneNumber;

	@Column(name = "BankDetails")
	private String bankDetails;
	
	@Column(name = "ProjectSustenanceName")
	private String projectSustenanceName;
	
	@Column(name = "InvoicePrefix")
	private String invoicePrefix;

	@Column(name = "LatestInvoiceNumber")
	private Long latestInvoiceNumber;

	@Column(name = "InvoiceNumberFormat")
	private String invoiceNumberFormat;

	@Column(name = "EmailAddressAccounts")
	private String emailAddressAccounts;

	@Lob
	@Column(name = "ParticipantLogo")
	@JsonIgnore
	private Blob participantLogo;

	@JsonProperty("logo")
	public String getLogo() {
		String result = null;

		if (participantLogo != null) {
			try {
				result = new String(Base64.getEncoder().encode(participantLogo.getBytes(1, (int) participantLogo.length())));
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return result;
	}

	public String getProjectSustenanceName() {
		return projectSustenanceName;
	}

	public void setProjectSustenanceName(String projectSustenanceName) {
		this.projectSustenanceName = projectSustenanceName;
	}

	public String getContactPointProvinceName() {
		return contactPointProvinceName;
	}

	public Long getContactPointProvinceId() {
		return contactPointProvinceId;
	}

	public void setContactPointProvinceId(Long contactPointProvinceId) {
		this.contactPointProvinceId = contactPointProvinceId;
	}
	public Long getContactPointCountryId() {
		return contactPointCountryId;
	}



	public void setContactPointCountryId(Long contactPointCountryId) {
		this.contactPointCountryId = contactPointCountryId;
	}



	public void setContactPointProvinceName(String contactPointProvinceName) {
		this.contactPointProvinceName = contactPointProvinceName;
	}



	public String getContactPointCountryName() {
		return contactPointCountryName;
	}



	public void setContactPointCountryName(String contactPointCountryName) {
		this.contactPointCountryName = contactPointCountryName;
	}



	public Blob getParticipantLogo() {
		return participantLogo;
	}

	public void setParticipantLogo(Blob participantLogo) {
		this.participantLogo = participantLogo;
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

/***	public Project getProjectIdSustenance() {
		return projectIdSustenance;
	}

	public void setProjectIdSustenance(Project projectIdSustenance) {
		this.projectIdSustenance = projectIdSustenance;
	} ***/

	public Long getParticipantIdBUParent() {
		return participantIdBUParent;
	}

	public void setParticipantIdBUParent(Long participantIdBUParent) {
		this.participantIdBUParent = participantIdBUParent;
	}

/***	public Participant getParticipantBUParent() {
		return participantBUParent;
	}

	public void setParticipantBUParent(Participant participantBUParent) {
		this.participantBUParent = participantBUParent;
	} ***/

	public String getParticipantTypeCode() {
		return participantTypeCode;
	}

	public void setParticipantTypeCode(String participantTypeCode) {
		this.participantTypeCode = participantTypeCode;
	}

/***	public ParticipantType getParticipantTypeBUParent() {
		return participantTypeBUParent;
	}

	public void setParticipantTypeBUParent(ParticipantType participantTypeBUParent) {
		this.participantTypeBUParent = participantTypeBUParent;
	} ***/

	public String getTapSubscriptionCode() {
		return tapSubscriptionCode;
	}

	public void setTapSubscriptionCode(String tapSubscriptionCode) {
		this.tapSubscriptionCode = tapSubscriptionCode;
	}

/***	public TapSubscription getTapSubscriptionBUParent() {
		return tapSubscriptionBUParent;
	}

	public void setTapSubscriptionBUParent(TapSubscription tapSubscriptionBUParent) {
		this.tapSubscriptionBUParent = tapSubscriptionBUParent;
	} ***/

	public Long getRepresentativeIndividualId() {
		return representativeIndividualId;
	}

	public void setRepresentativeIndividualId(Long representativeIndividualId) {
		this.representativeIndividualId = representativeIndividualId;
	}

/***	public RepresentativeIndividual getRepresentativeIndividualBUParent() {
		return representativeIndividualBUParent;
	}

	public void setRepresentativeIndividualBUParent(RepresentativeIndividual representativeIndividualBUParent) {
		this.representativeIndividualBUParent = representativeIndividualBUParent;
	} ***/

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

/***	public MarketingIndividual getMarketingIndividual() {
		return marketingIndividual;
	}

	public void setMarketingIndividual(MarketingIndividual marketingIndividual) {
		this.marketingIndividual = marketingIndividual;
	} ***/

	public String getMarketer() {
		return marketer;
	}

	public void setMarketer(String marketer) {
		this.marketer = marketer;
	}

	public Long getParticipantOfficeIdDefault() {
		return participantOfficeIdDefault;
	}

	public void setParticipantOfficeIdDefault(Long participantOfficeIdDefault) {
		this.participantOfficeIdDefault = participantOfficeIdDefault;
	}

/***	public ParticipantOffice getParticipantOfficeDefault() {
		return participantOfficeDefault;
	}

	public void setParticipantOfficeDefault(ParticipantOffice participantOfficeDefault) {
		this.participantOfficeDefault = participantOfficeDefault;
	} ***/

	public Long getParticipantBankDetailsIdDefault() {
		return participantBankDetailsIdDefault;
	}

	public void setParticipantBankDetailsIdDefault(Long participantBankDetailsIdDefault) {
		this.participantBankDetailsIdDefault = participantBankDetailsIdDefault;
	}

/***	public ParticipantBankDetails getParticipantBankDetailsDefault() {
		return participantBankDetailsDefault;
	}

	public void setParticipantBankDetailsDefault(ParticipantBankDetails participantBankDetailsDefault) {
		this.participantBankDetailsDefault = participantBankDetailsDefault;
	} ***/

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

	public Long getDefaultInvoiceDay() {
		return defaultInvoiceDay;
	}

	public void setDefaultInvoiceDay(Long defaultInvoiceDay) {
		this.defaultInvoiceDay = defaultInvoiceDay;
	}

	public Long getIndividualId() {
		return individualId;
	}

	public void setIndividualId(Long individualId) {
		this.individualId = individualId;
	}

/***	public Individual getIndividual() {
		return individual;
	}

	public void setIndividual(Individual individual) {
		this.individual = individual;
	} ***/

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getSecondName() {
		return secondName;
	}

	public void setSecondName(String secondName) {
		this.secondName = secondName;
	}

	public String getThirdName() {
		return thirdName;
	}

	public void setThirdName(String thirdName) {
		this.thirdName = thirdName;
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

	public String getPassportNumber() {
		return passportNumber;
	}

	public void setPassportNumber(String passportNumber) {
		this.passportNumber = passportNumber;
	}

	public Long getCountryId() {
		return countryId;
	}

	public void setCountryId(Long countryId) {
		this.countryId = countryId;
	}

/***	public Country getCountry() {
		return country;
	}

	public void setCountry(Country country) {
		this.country = country;
	} ***/

	public String getCountryName() {
		return countryName;
	}

	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}

	public String getIsActiveMember() {
		return isActiveMember;
	}

	public void setIsActiveMember(String isActiveMember) {
		this.isActiveMember = isActiveMember;
	}

	public String getAllowLoginFlag() {
		return allowLoginFlag;
	}

	public void setAllowLoginFlag(String allowLoginFlag) {
		this.allowLoginFlag = allowLoginFlag;
	}

	public String getIncomeTaxNumber() {
		return incomeTaxNumber;
	}

	public void setIncomeTaxNumber(String incomeTaxNumber) {
		this.incomeTaxNumber = incomeTaxNumber;
	}

	public String getPass() {
		return pass;
	}

	public void setPass(String pass) {
		this.pass = pass;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPasswordResetToken() {
		return passwordResetToken;
	}

	public void setPasswordResetToken(String passwordResetToken) {
		this.passwordResetToken = passwordResetToken;
	}

	public Date getPasswordResetExpiryDate() {
		return passwordResetExpiryDate;
	}

	public void setPasswordResetExpiryDate(Date passwordResetExpiryDate) {
		this.passwordResetExpiryDate = passwordResetExpiryDate;
	}

	public Date getLastLoginTimestamp() {
		return lastLoginTimestamp;
	}

	public void setLastLoginTimestamp(Date lastLoginTimestamp) {
		this.lastLoginTimestamp = lastLoginTimestamp;
	}

	public Long getSystemMemberId() {
		return systemMemberId;
	}

	public void setSystemMemberId(Long systemMemberId) {
		this.systemMemberId = systemMemberId;
	}

/***	public SystemMember getSystemMember() {
		return systemMember;
	}

	public void setSystemMember(SystemMember systemMember) {
		this.systemMember = systemMember;
	} ***/

	public Date getSystemMemberStartDate() {
		return systemMemberStartDate;
	}

	public void setSystemMemberStartDate(Date systemMemberStartDate) {
		this.systemMemberStartDate = systemMemberStartDate;
	}

	public Date getSystemMemberEndDate() {
		return systemMemberEndDate;
	}

	public void setSystemMemberEndDate(Date systemMemberEndDate) {
		this.systemMemberEndDate = systemMemberEndDate;
	}

	public String getRoleCode() {
		return roleCode;
	}

	public void setRoleCode(String roleCode) {
		this.roleCode = roleCode;
	}

/***	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	} ***/

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getMedicalName() {
		return medicalName;
	}

	public void setMedicalName(String medicalName) {
		this.medicalName = medicalName;
	}

	public String getMedicalNumber() {
		return medicalNumber;
	}

	public void setMedicalNumber(String medicalNumber) {
		this.medicalNumber = medicalNumber;
	}

	public String getTapSubscriptionName() {
		return tapSubscriptionName;
	}

	public void setTapSubscriptionName(String tapSubscriptionName) {
		this.tapSubscriptionName = tapSubscriptionName;
	}

	public String getParticipantTypeName() {
		return participantTypeName;
	}

	public void setParticipantTypeName(String participantTypeName) {
		this.participantTypeName = participantTypeName;
	}

	public Long getParticipantOfficeId() {
		return participantOfficeId;
	}

	public void setParticipantOfficeId(Long participantOfficeId) {
		this.participantOfficeId = participantOfficeId;
	}

/***	public ParticipantOffice getParticipantOffice() {
		return participantOffice;
	}

	public void setParticipantOffice(ParticipantOffice participantOffice) {
		this.participantOffice = participantOffice;
	} ***/

	public Long getContactPointIdDefault() {
		return contactPointIdDefault;
	}

	public void setContactPointIdDefault(Long contactPointIdDefault) {
		this.contactPointIdDefault = contactPointIdDefault;
	}

/***	public ContactPoint getContactPointDefault() {
		return contactPointDefault;
	}

	public void setContactPointDefault(ContactPoint contactPointDefault) {
		this.contactPointDefault = contactPointDefault;
	} ***/

	public String getParticipantOfficeName() {
		return participantOfficeName;
	}

	public void setParticipantOfficeName(String participantOfficeName) {
		this.participantOfficeName = participantOfficeName;
	}

	public String getParticipantOfficeDescription() {
		return participantOfficeDescription;
	}

	public void setParticipantOfficeDescription(String participantOfficeDescription) {
		this.participantOfficeDescription = participantOfficeDescription;
	}

	public String getContactPointName() {
		return contactPointName;
	}

	public void setContactPointName(String contactPointName) {
		this.contactPointName = contactPointName;
	}

	public Long getContactPointCityId() {
		return contactPointCityId;
	}

	public void setContactPointCityId(Long contactPointCityId) {
		this.contactPointCityId = contactPointCityId;
	}

/***	public ContactPointCity getContactPointCity() {
		return contactPointCity;
	}

	public void setContactPointCity(ContactPointCity contactPointCity) {
		this.contactPointCity = contactPointCity;
	} ***/

	public String getContactPointCityName() {
		return contactPointCityName;
	}

	public void setContactPointCityName(String contactPointCityName) {
		this.contactPointCityName = contactPointCityName;
	}

	public Long getContactPointSuburbId() {
		return contactPointSuburbId;
	}

	public void setContactPointSuburbId(Long contactPointSuburbId) {
		this.contactPointSuburbId = contactPointSuburbId;
	}

/***	public ContactPointSuburb getContactPointSuburb() {
		return contactPointSuburb;
	}

	public void setContactPointSuburb(ContactPointSuburb contactPointSuburb) {
		this.contactPointSuburb = contactPointSuburb;
	} ***/

	public String getContactPointSuburbName() {
		return contactPointSuburbName;
	}

	public void setContactPointSuburbName(String contactPointSuburbName) {
		this.contactPointSuburbName = contactPointSuburbName;
	}

	public String getEmailAddress() {
		return emailAddress;
	}

	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}

	public String getAddressLine1() {
		return addressLine1;
	}

	public void setAddressLine1(String addressLine1) {
		this.addressLine1 = addressLine1;
	}

	public String getAddressLine2() {
		return addressLine2;
	}

	public void setAddressLine2(String addressLine2) {
		this.addressLine2 = addressLine2;
	}

	public String getAddressLine3() {
		return addressLine3;
	}

	public void setAddressLine3(String addressLine3) {
		this.addressLine3 = addressLine3;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getBankDetails() {
		return bankDetails;
	}

	public void setBankDetails(String bankDetails) {
		this.bankDetails = bankDetails;
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

	public String getInvoiceNumberFormat() {
		return invoiceNumberFormat;
	}

	public void setInvoiceNumberFormat(String invoiceNumberFormat) {
		this.invoiceNumberFormat = invoiceNumberFormat;
	}

	public String getEmailAddressAccounts() {
		return emailAddressAccounts;
	}

	public void setEmailAddressAccounts(String emailAddressAccounts) {
		this.emailAddressAccounts = emailAddressAccounts;
	}


}

/**  javascript      vir grid population        (jsStr)

	var columnsArray = [
		 { data: "participantId" }            //0 MySql-TableName: VParticipant
		,{ data: "projectIdSustenance" }        //1
		,{ data: "participantIdBUParent" }    //2
		,{ data: "participantTypeCode" }      //3
		,{ data: "tapSubscriptionCode" }      //4
		,{ data: "representativeIndividualId" } //5
		,{ data: "representative" }           //6
		,{ data: "marketingIndividualId" }    //7
		,{ data: "marketer" }                 //8
		,{ data: "participantOfficeIdDefault" } //9
		,{ data: "participantBankDetailsIdDefault" } //10
		,{ data: "systemName" }               //11
		,{ data: "registeredName" }           //12
		,{ data: "tradingName" }              //13
		,{ data: "projectPrefix" }            //14
		,{ data: "latestProjectNumber" }      //15
		,{ data: "projectPostfix" }           //16
		,{ data: "startDate" }                //17
		,{ data: "endDate" }                  //18
		,{ data: "isIndividual" }             //19
		,{ data: "tapAdministered" }          //20
		,{ data: "registrationNumber" }       //21
		,{ data: "vatNumber" }                //22
		,{ data: "defaultInvoiceDay" }        //23
		,{ data: "individualId" }             //24
		,{ data: "firstName" }                //25
		,{ data: "secondName" }               //26
		,{ data: "thirdName" }                //27
		,{ data: "nickName" }                 //28
		,{ data: "lastName" }                 //29
		,{ data: "initials" }                 //30
		,{ data: "idNumber" }                 //31
		,{ data: "passportNumber" }           //32
		,{ data: "countryId" }              //33
		,{ data: "countryName" }              //34
		,{ data: "isActiveMember" }           //35
		,{ data: "allowLoginFlag" }           //36
		,{ data: "incomeTaxNumber" }          //37
		,{ data: "pass" }                     //38
		,{ data: "userName" }                 //39
		,{ data: "passwordResetToken" }       //40
		,{ data: "passwordResetExpiryDate" }  //41
		,{ data: "lastLoginTimestamp" }       //42
		,{ data: "systemMemberId" }           //43
		,{ data: "systemMemberStartDate" }    //44
		,{ data: "systemMemberEndDate" }      //45
		,{ data: "roleCode" }                 //46
		,{ data: "roleName" }                 //47
		,{ data: "medicalName" }              //48
		,{ data: "medicalNumber" }            //49
		,{ data: "tapSubscriptionName" }      //50
		,{ data: "participantTypeName" }      //51
		,{ data: "participantOfficeId" }      //52
		,{ data: "contactPointIdDefault" }    //53
		,{ data: "participantOfficeName" }    //54
		,{ data: "participantOfficeDescription" } //55
		,{ data: "contactPointName" }         //56
		,{ data: "contactPointCityId" }       //57
		,{ data: "contactPointCityName" }     //58
		,{ data: "contactPointSuburbId" }     //59
		,{ data: "contactPointSuburbName" }   //60
		,{ data: "emailAddress" }             //61
		,{ data: "addressLine1" }             //62
		,{ data: "addressLine2" }             //63
		,{ data: "addressLine3" }             //64
		,{ data: "phoneNumber" }              //65
		,{ data: "bankDetails" }              //66
		,{ data: "projectIdSustenance" }          //67
		,{ data: "invoicePrefix" }            //68
		,{ data: "latestInvoiceNumber" }      //69
		,{ data: "invoiceNumberFormat" }      //70
		,{ data: "emailAddressAccounts" }     //71
	];

	var columnDefsArray = [
		{
			visible: false,
			targets: [0, 1, 2, 3, 4, 5, 7, 9, 10, 24, 33, 43, 46, 52, 53, 57, 59]
		}
		,{       //for Date columns
			render: function (data, type, row) {
				var html = data;
				if (type == "display") {
					html = timestampToString(data, false);
				}
				return html;
			},
			targets: [17, 18, 41, 42, 44, 45]
		}
		,{        //for checkboxes
			render: function (data, type, row) {
				return "<input type='checkbox' readonly onclick='return false;' " + (row.isIndividual == "Y" ? " checked " : "") + ">";
			},
			className: "dt-center",
			targets:[19]
		}
		,{        //for checkboxes
			render: function (data, type, row) {
				return "<input type='checkbox' readonly onclick='return false;' " + (row.tapAdministered == "Y" ? " checked " : "") + ">";
			},
			className: "dt-center",
			targets:[20]
		}
		,{        //for checkboxes
			render: function (data, type, row) {
				return "<input type='checkbox' readonly onclick='return false;' " + (row.isActiveMember == "Y" ? " checked " : "") + ">";
			},
			className: "dt-center",
			targets:[35]
		}
		,{        //for checkboxes
			render: function (data, type, row) {
				return "<input type='checkbox' readonly onclick='return false;' " + (row.allowLoginFlag == "Y" ? " checked " : "") + ">";
			},
			className: "dt-center",
			targets:[36]
		}

	];







/**  javascript      vir grid SelectFromGridDialog        (js1Str)

	var columns = [
		{ data: "participantId", name: "ParticipantId" }                   //0 MySql-TableName: VParticipant
		,{ data: "projectIdSustenance", name: "ProjectIdSustenance" }           //1
		,{ data: "participantIdBUParent", name: "ParticipantIdBUParent" }   //2
		,{ data: "participantTypeCode", name: "ParticipantTypeCode" }       //3
		,{ data: "tapSubscriptionCode", name: "TapSubscriptionCode" }       //4
		,{ data: "representativeIndividualId", name: "RepresentativeIndividualId" } //5
		,{ data: "representative", name: "Representative" }                 //6
		,{ data: "marketingIndividualId", name: "MarketingIndividualId" }   //7
		,{ data: "marketer", name: "Marketer" }                             //8
		,{ data: "participantOfficeIdDefault", name: "ParticipantOfficeIdDefault" } //9
		,{ data: "participantBankDetailsIdDefault", name: "ParticipantBankDetailsIdDefault" } //10
		,{ data: "systemName", name: "SystemName" }                         //11
		,{ data: "registeredName", name: "RegisteredName" }                 //12
		,{ data: "tradingName", name: "TradingName" }                       //13
		,{ data: "projectPrefix", name: "ProjectPrefix" }                   //14
		,{ data: "latestProjectNumber", name: "LatestProjectNumber" }       //15
		,{ data: "projectPostfix", name: "ProjectPostfix" }                 //16
		,{ data: "startDate", name: "StartDate" }                           //17
		,{ data: "endDate", name: "EndDate" }                               //18
		,{ data: "isIndividual", name: "IsIndividual" }                     //19
		,{ data: "tapAdministered", name: "TapAdministered" }               //20
		,{ data: "registrationNumber", name: "RegistrationNumber" }         //21
		,{ data: "vatNumber", name: "VatNumber" }                           //22
		,{ data: "defaultInvoiceDay", name: "DefaultInvoiceDay" }           //23
		,{ data: "individualId", name: "IndividualId" }                     //24
		,{ data: "firstName", name: "FirstName" }                           //25
		,{ data: "secondName", name: "SecondName" }                         //26
		,{ data: "thirdName", name: "ThirdName" }                           //27
		,{ data: "nickName", name: "NickName" }                             //28
		,{ data: "lastName", name: "LastName" }                             //29
		,{ data: "initials", name: "Initials" }                             //30
		,{ data: "idNumber", name: "IdNumber" }                             //31
		,{ data: "passportNumber", name: "PassportNumber" }                 //32
		,{ data: "countryId", name: "CountryId" }                       //33
		,{ data: "countryName", name: "CountryName" }                       //34
		,{ data: "isActiveMember", name: "IsActiveMember" }                 //35
		,{ data: "allowLoginFlag", name: "AllowLoginFlag" }                 //36
		,{ data: "incomeTaxNumber", name: "IncomeTaxNumber" }               //37
		,{ data: "pass", name: "Pass" }                                     //38
		,{ data: "userName", name: "UserName" }                             //39
		,{ data: "passwordResetToken", name: "PasswordResetToken" }         //40
		,{ data: "passwordResetExpiryDate", name: "PasswordResetExpiryDate" } //41
		,{ data: "lastLoginTimestamp", name: "LastLoginTimestamp" }         //42
		,{ data: "systemMemberId", name: "SystemMemberId" }                 //43
		,{ data: "systemMemberStartDate", name: "SystemMemberStartDate" }   //44
		,{ data: "systemMemberEndDate", name: "SystemMemberEndDate" }       //45
		,{ data: "roleCode", name: "RoleCode" }                             //46
		,{ data: "roleName", name: "RoleName" }                             //47
		,{ data: "medicalName", name: "MedicalName" }                       //48
		,{ data: "medicalNumber", name: "MedicalNumber" }                   //49
		,{ data: "tapSubscriptionName", name: "TapSubscriptionName" }       //50
		,{ data: "participantTypeName", name: "ParticipantTypeName" }       //51
		,{ data: "participantOfficeId", name: "ParticipantOfficeId" }       //52
		,{ data: "contactPointIdDefault", name: "ContactPointIdDefault" }   //53
		,{ data: "participantOfficeName", name: "ParticipantOfficeName" }   //54
		,{ data: "participantOfficeDescription", name: "ParticipantOfficeDescription" } //55
		,{ data: "contactPointName", name: "ContactPointName" }             //56
		,{ data: "contactPointCityId", name: "ContactPointCityId" }         //57
		,{ data: "contactPointCityName", name: "ContactPointCityName" }     //58
		,{ data: "contactPointSuburbId", name: "ContactPointSuburbId" }     //59
		,{ data: "contactPointSuburbName", name: "ContactPointSuburbName" } //60
		,{ data: "emailAddress", name: "EmailAddress" }                     //61
		,{ data: "addressLine1", name: "AddressLine1" }                     //62
		,{ data: "addressLine2", name: "AddressLine2" }                     //63
		,{ data: "addressLine3", name: "AddressLine3" }                     //64
		,{ data: "phoneNumber", name: "PhoneNumber" }                       //65
		,{ data: "bankDetails", name: "BankDetails" }                       //66
		,{ data: "projectIdSustenance", name: "ProjectIdSustenance" }               //67
		,{ data: "invoicePrefix", name: "InvoicePrefix" }                   //68
		,{ data: "latestInvoiceNumber", name: "LatestInvoiceNumber" }       //69
		,{ data: "invoiceNumberFormat", name: "InvoiceNumberFormat" }       //70
		,{ data: "emailAddressAccounts", name: "EmailAddressAccounts" }     //71
	];

	var columnDefs = [
		{
			visible: false,
			targets: [0, 1, 2, 3, 4, 5, 7, 9, 10, 24, 33, 43, 46, 52, 53, 57, 59]
		}
		,{       //for Date columns
			render: function (data, type, row) {
				var html = data;
				if (type == "display") {
					html = timestampToString(data, false);
				}
				return html;
			},
			targets: [17, 18, 41, 42, 44, 45]
		}
		,{        //for checkboxes
			render: function (data, type, row) {
				return "<input type='checkbox' readonly onclick='return false;' " + (row.isIndividual == "Y" ? " checked " : "") + ">";
			},
			className: "dt-center",
			targets:[19]
		}
		,{        //for checkboxes
			render: function (data, type, row) {
				return "<input type='checkbox' readonly onclick='return false;' " + (row.tapAdministered == "Y" ? " checked " : "") + ">";
			},
			className: "dt-center",
			targets:[20]
		}
		,{        //for checkboxes
			render: function (data, type, row) {
				return "<input type='checkbox' readonly onclick='return false;' " + (row.isActiveMember == "Y" ? " checked " : "") + ">";
			},
			className: "dt-center",
			targets:[35]
		}
		,{        //for checkboxes
			render: function (data, type, row) {
				return "<input type='checkbox' readonly onclick='return false;' " + (row.allowLoginFlag == "Y" ? " checked " : "") + ">";
			},
			className: "dt-center",
			targets:[36]
		}


	];







Vir 'n Save Data function sien Participant.java






Vir 'n Populate Data function                                                  (js3Str)

		//  MySql-TableName: VParticipant										   (js3Str)
		$("#cpParticipantId").val(data.participantId);                   //0
		$("#cpProjectIdSustenance").val(data.projectIdSustenance);           //1
		populateSelect("cpProjectIdSustenance",                             //name of html select element that will be populated
				"/rest/ignite/v1/project/find-all",                       //url
				"projectId",                                              //the value that must be saved (ReferencedColumnName)
				"lastUpdateUserName",                                     //shown to user (usually a Name column)
				data.projectIdSustenance,                                   //The selected one, if there is one
				true,                                                     //addEmpty, boolean: should we add empty object at the top
				null                                                      //completeMethod
		),

		$("#cpParticipantIdBUParent").val(data.participantIdBUParent);   //2
		populateSelect("cpParticipantIdBUParent",                         //name of html select element that will be populated
				"/rest/ignite/v1/participant/find-all",                   //url
				"participantId",                                          //the value that must be saved (ReferencedColumnName)
				"systemName",                                             //shown to user (usually a Name column)
				data.participantIdBUParent,                               //The selected one, if there is one
				true,                                                     //addEmpty, boolean: should we add empty object at the top
				null                                                      //completeMethod
		),

		$("#cpParticipantTypeCode").val(data.participantTypeCode);       //3
		populateSelect("cpParticipantTypeCode",                           //name of html select element that will be populated
				"/rest/ignite/v1/participant-type/find-all",              //url
				"participantTypeCode",                                    //the value that must be saved (ReferencedColumnName)
				"typeName",                                               //shown to user (usually a Name column)
				data.participantTypeCode,                                 //The selected one, if there is one
				true,                                                     //addEmpty, boolean: should we add empty object at the top
				null                                                      //completeMethod
		),

		$("#cpTapSubscriptionCode").val(data.tapSubscriptionCode);       //4
		populateSelect("cpTapSubscriptionCode",                           //name of html select element that will be populated
				"/rest/ignite/v1/tap-subscription/find-all",              //url
				"tapSubscriptionCode",                                    //the value that must be saved (ReferencedColumnName)
				"name",                                                   //shown to user (usually a Name column)
				data.tapSubscriptionCode,                                 //The selected one, if there is one
				true,                                                     //addEmpty, boolean: should we add empty object at the top
				null                                                      //completeMethod
		),

		$("#cpRepresentativeIndividualId").val(data.representativeIndividualId); //5
		populateSelect("cpRepresentativeIndividualId",                    //name of html select element that will be populated
				"/rest/ignite/v1/representative-individual/find-all",     //url
				"representativeIndividualId",                             //the value that must be saved (ReferencedColumnName)
				"kryDieKolomNaam",                                        //shown to user (usually a Name column)
				data.representativeIndividualId,                          //The selected one, if there is one
				true,                                                     //addEmpty, boolean: should we add empty object at the top
				null                                                      //completeMethod
		),

		$("#cpRepresentative").val(data.representative);                 //6
		$("#cpMarketingIndividualId").val(data.marketingIndividualId);   //7
		populateSelect("cpMarketingIndividualId",                         //name of html select element that will be populated
				"/rest/ignite/v1/marketing-individual/find-all",          //url
				"marketingIndividualId",                                  //the value that must be saved (ReferencedColumnName)
				"kryDieKolomNaam",                                        //shown to user (usually a Name column)
				data.marketingIndividualId,                               //The selected one, if there is one
				true,                                                     //addEmpty, boolean: should we add empty object at the top
				null                                                      //completeMethod
		),

		$("#cpMarketer").val(data.marketer);                             //8
		$("#cpParticipantOfficeIdDefault").val(data.participantOfficeIdDefault); //9
		populateSelect("cpParticipantOfficeIdDefault",                    //name of html select element that will be populated
				"/rest/ignite/v1/participant-office/find-all",            //url
				"participantOfficeId",                                    //the value that must be saved (ReferencedColumnName)
				"name",                                                   //shown to user (usually a Name column)
				data.participantOfficeIdDefault,                          //The selected one, if there is one
				true,                                                     //addEmpty, boolean: should we add empty object at the top
				null                                                      //completeMethod
		),

		$("#cpParticipantBankDetailsIdDefault").val(data.participantBankDetailsIdDefault); //10
		populateSelect("cpParticipantBankDetailsIdDefault",               //name of html select element that will be populated
				"/rest/ignite/v1/participant-bank-details/find-all",      //url
				"participantBankDetailsId",                               //the value that must be saved (ReferencedColumnName)
				"name",                                                   //shown to user (usually a Name column)
				data.participantBankDetailsIdDefault,                     //The selected one, if there is one
				true,                                                     //addEmpty, boolean: should we add empty object at the top
				null                                                      //completeMethod
		),

		$("#cpSystemName").val(data.systemName);                         //11
		$("#cpRegisteredName").val(data.registeredName);                 //12
		$("#cpTradingName").val(data.tradingName);                       //13
		$("#cpProjectPrefix").val(data.projectPrefix);                   //14
		$("#cpLatestProjectNumber").val(data.latestProjectNumber);       //15
		$("#cpProjectPostfix").val(data.projectPostfix);                 //16
		$("#cpStartDate").datepicker("setDate", data.startDate == null? timestampToString(new Date(), false) : new Date(data.startDate));                           //17
		$("#cpEndDate").datepicker("setDate", data.endDate == null? timestampToString(new Date(), false) : new Date(data.endDate));                               //18
		$("#cpIsIndividual").val(data.isIndividual);                     //19    use one of these
		$("#cpIsIndividual").prop("checked", data.isIndividual == "Y");  //19    use one of these
		$("#cpTapAdministered").val(data.tapAdministered);               //20    use one of these
		$("#cpTapAdministered").prop("checked", data.tapAdministered == "Y"); //20    use one of these
		$("#cpRegistrationNumber").val(data.registrationNumber);         //21
		$("#cpVatNumber").val(data.vatNumber);                           //22
		$("#cpDefaultInvoiceDay").val(data.defaultInvoiceDay);           //23
		$("#cpIndividualId").val(data.individualId);                     //24
		populateSelect("cpIndividualId",                                  //name of html select element that will be populated
				"/rest/ignite/v1/individual/find-all",                    //url
				"individualId",                                           //the value that must be saved (ReferencedColumnName)
				"firstName",                                              //shown to user (usually a Name column)
				data.individualId,                                        //The selected one, if there is one
				true,                                                     //addEmpty, boolean: should we add empty object at the top
				null                                                      //completeMethod
		),

		$("#cpFirstName").val(data.firstName);                           //25
		$("#cpSecondName").val(data.secondName);                         //26
		$("#cpThirdName").val(data.thirdName);                           //27
		$("#cpNickName").val(data.nickName);                             //28
		$("#cpLastName").val(data.lastName);                             //29
		$("#cpInitials").val(data.initials);                             //30
		$("#cpIdNumber").val(data.idNumber);                             //31
		$("#cpPassportNumber").val(data.passportNumber);                 //32
		$("#cpCountryId").val(data.countryId);                       //33
		populateSelect("cpCountryId",                                   //name of html select element that will be populated
				"/rest/ignite/v1/country/find-all",                       //url
				"countryId",                                            //the value that must be saved (ReferencedColumnName)
				"name",                                                   //shown to user (usually a Name column)
				data.countryId,                                         //The selected one, if there is one
				true,                                                     //addEmpty, boolean: should we add empty object at the top
				null                                                      //completeMethod
		),

		$("#cpCountryName").val(data.countryName);                       //34
		$("#cpIsActiveMember").val(data.isActiveMember);                 //35    use one of these
		$("#cpIsActiveMember").prop("checked", data.isActiveMember == "Y"); //35    use one of these
		$("#cpAllowLoginFlag").val(data.allowLoginFlag);                 //36    use one of these
		$("#cpAllowLoginFlag").prop("checked", data.allowLoginFlag == "Y"); //36    use one of these
		$("#cpIncomeTaxNumber").val(data.incomeTaxNumber);               //37
		$("#cpPass").val(data.pass);                                     //38
		$("#cpUserName").val(data.userName);                             //39
		$("#cpPasswordResetToken").val(data.passwordResetToken);         //40
		$("#cpPasswordResetExpiryDate").datepicker("setDate", data.passwordResetExpiryDate == null? timestampToString(new Date(), false) : new Date(data.passwordResetExpiryDate)); //41
		$("#cpLastLoginTimestamp").datepicker("setDate", data.lastLoginTimestamp == null? timestampToString(new Date(), false) : new Date(data.lastLoginTimestamp));         //42
		$("#cpSystemMemberId").val(data.systemMemberId);                 //43
		populateSelect("cpSystemMemberId",                                //name of html select element that will be populated
				"/rest/ignite/v1/system-member/find-all",                 //url
				"systemMemberId",                                         //the value that must be saved (ReferencedColumnName)
				"lastUpdateUserName",                                     //shown to user (usually a Name column)
				data.systemMemberId,                                      //The selected one, if there is one
				true,                                                     //addEmpty, boolean: should we add empty object at the top
				null                                                      //completeMethod
		),

		$("#cpSystemMemberStartDate").datepicker("setDate", data.systemMemberStartDate == null? timestampToString(new Date(), false) : new Date(data.systemMemberStartDate));   //44
		$("#cpSystemMemberEndDate").datepicker("setDate", data.systemMemberEndDate == null? timestampToString(new Date(), false) : new Date(data.systemMemberEndDate));       //45
		$("#cpRoleCode").val(data.roleCode);                             //46
		populateSelect("cpRoleCode",                                      //name of html select element that will be populated
				"/rest/ignite/v1/role/find-all",                          //url
				"roleCode",                                               //the value that must be saved (ReferencedColumnName)
				"name",                                                   //shown to user (usually a Name column)
				data.roleCode,                                            //The selected one, if there is one
				true,                                                     //addEmpty, boolean: should we add empty object at the top
				null                                                      //completeMethod
		),

		$("#cpRoleName").val(data.roleName);                             //47
		$("#cpMedicalName").val(data.medicalName);                       //48
		$("#cpMedicalNumber").val(data.medicalNumber);                   //49
		$("#cpTapSubscriptionName").val(data.tapSubscriptionName);       //50
		$("#cpParticipantTypeName").val(data.participantTypeName);       //51
		$("#cpParticipantOfficeId").val(data.participantOfficeId);       //52
		populateSelect("cpParticipantOfficeId",                           //name of html select element that will be populated
				"/rest/ignite/v1/participant-office/find-all",            //url
				"participantOfficeId",                                    //the value that must be saved (ReferencedColumnName)
				"name",                                                   //shown to user (usually a Name column)
				data.participantOfficeId,                                 //The selected one, if there is one
				true,                                                     //addEmpty, boolean: should we add empty object at the top
				null                                                      //completeMethod
		),

		$("#cpContactPointIdDefault").val(data.contactPointIdDefault);   //53
		populateSelect("cpContactPointIdDefault",                         //name of html select element that will be populated
				"/rest/ignite/v1/contact-point/find-all",                 //url
				"contactPointId",                                         //the value that must be saved (ReferencedColumnName)
				"name",                                                   //shown to user (usually a Name column)
				data.contactPointIdDefault,                               //The selected one, if there is one
				true,                                                     //addEmpty, boolean: should we add empty object at the top
				null                                                      //completeMethod
		),

		$("#cpParticipantOfficeName").val(data.participantOfficeName);   //54
		$("#cpParticipantOfficeDescription").val(data.participantOfficeDescription); //55
		$("#cpContactPointName").val(data.contactPointName);             //56
		$("#cpContactPointCityId").val(data.contactPointCityId);         //57
		populateSelect("cpContactPointCityId",                            //name of html select element that will be populated
				"/rest/ignite/v1/contact-point-city/find-all",            //url
				"contactPointCityId",                                     //the value that must be saved (ReferencedColumnName)
				"kryDieKolomNaam",                                        //shown to user (usually a Name column)
				data.contactPointCityId,                                  //The selected one, if there is one
				true,                                                     //addEmpty, boolean: should we add empty object at the top
				null                                                      //completeMethod
		),

		$("#cpContactPointCityName").val(data.contactPointCityName);     //58
		$("#cpContactPointSuburbId").val(data.contactPointSuburbId);     //59
		populateSelect("cpContactPointSuburbId",                          //name of html select element that will be populated
				"/rest/ignite/v1/contact-point-suburb/find-all",          //url
				"contactPointSuburbId",                                   //the value that must be saved (ReferencedColumnName)
				"kryDieKolomNaam",                                        //shown to user (usually a Name column)
				data.contactPointSuburbId,                                //The selected one, if there is one
				true,                                                     //addEmpty, boolean: should we add empty object at the top
				null                                                      //completeMethod
		),

		$("#cpContactPointSuburbName").val(data.contactPointSuburbName); //60
		$("#cpEmailAddress").val(data.emailAddress);                     //61
		$("#cpAddressLine1").val(data.addressLine1);                     //62
		$("#cpAddressLine2").val(data.addressLine2);                     //63
		$("#cpAddressLine3").val(data.addressLine3);                     //64
		$("#cpPhoneNumber").val(data.phoneNumber);                       //65
		$("#cpBankDetails").val(data.bankDetails);                       //66
		$("#cpProjectIdSustenance").val(data.projectIdSustenance);               //67
		$("#cpInvoicePrefix").val(data.invoicePrefix);                   //68
		$("#cpLatestInvoiceNumber").val(data.latestInvoiceNumber);       //69
		$("#cpInvoiceNumberFormat").val(data.invoiceNumberFormat);       //70
		$("#cpEmailAddressAccounts").val(data.emailAddressAccounts);     //71






/**  HTML  om 'n grid te populate
												<th>ParticipantId</th>               <!--0  MySql-TableName: VParticipant-->
												<th>Project Id Fin Admin</th>        <!--1  ProjectIdSustenance-->
												<th>Participant Id B U Parent</th>   <!--2  ParticipantIdBUParent-->
												<th>ParticipantTypeCode</th>         <!--3  ParticipantTypeCode-->
												<th>TapSubscriptionCode</th>         <!--4  TapSubscriptionCode-->
												<th>RepresentativeIndividualId</th>  <!--5  RepresentativeIndividualId-->
												<th>Representative</th>              <!--6  Representative-->
												<th>MarketingIndividualId</th>       <!--7  MarketingIndividualId-->
												<th>Marketer</th>                    <!--8  Marketer-->
												<th>Participant Office Id Default</th> <!--9  ParticipantOfficeIdDefault-->
												<th>Participant Bank Details Id Default</th> <!--10  ParticipantBankDetailsIdDefault-->
												<th>System Name</th>                 <!--11  SystemName-->
												<th>Registered Name</th>             <!--12  RegisteredName-->
												<th>Trading Name</th>                <!--13  TradingName-->
												<th>Project Prefix</th>              <!--14  ProjectPrefix-->
												<th>Latest Project Number</th>       <!--15  LatestProjectNumber-->
												<th>Project Postfix</th>             <!--16  ProjectPostfix-->
												<th>Start Date</th>                  <!--17  StartDate-->
												<th>End Date</th>                    <!--18  EndDate-->
												<th>Is Individual</th>               <!--19  IsIndividual-->
												<th>Tap Administered</th>            <!--20  TapAdministered-->
												<th>Registration Number</th>         <!--21  RegistrationNumber-->
												<th>V A T Number</th>                <!--22  VatNumber-->
												<th>Default Invoice Day</th>         <!--23  DefaultInvoiceDay-->
												<th>IndividualId</th>                <!--24  IndividualId-->
												<th>First Name</th>                  <!--25  FirstName-->
												<th>Second Name</th>                 <!--26  SecondName-->
												<th>Third Name</th>                  <!--27  ThirdName-->
												<th>Nick Name</th>                   <!--28  NickName-->
												<th>Last Name</th>                   <!--29  LastName-->
												<th>Initials</th>                    <!--30  Initials-->
												<th>Id Number</th>                   <!--31  IdNumber-->
												<th>Passport Number</th>             <!--32  PassportNumber-->
												<th>CountryId</th>                 <!--33  CountryId-->
												<th>Country Name</th>                <!--34  CountryName-->
												<th>Is Active Member</th>            <!--35  IsActiveMember-->
												<th>Allow Login</th>                 <!--36  AllowLoginFlag-->
												<th>Income Tax Number</th>           <!--37  IncomeTaxNumber-->
												<th>Pass</th>                        <!--38  Pass-->
												<th>User Name</th>                   <!--39  UserName-->
												<th>Password Reset Token</th>        <!--40  PasswordResetToken-->
												<th>Password Reset Expiry Date</th>  <!--41  PasswordResetExpiryDate-->
												<th>Last Login Timestamp</th>        <!--42  LastLoginTimestamp-->
												<th>SystemMemberId</th>              <!--43  SystemMemberId-->
												<th>System Member Start Date</th>    <!--44  SystemMemberStartDate-->
												<th>System Member End Date</th>      <!--45  SystemMemberEndDate-->
												<th>RoleCode</th>                    <!--46  RoleCode-->
												<th>Role Name</th>                   <!--47  RoleName-->
												<th>Medical Name</th>                <!--48  MedicalName-->
												<th>Medical Number</th>              <!--49  MedicalNumber-->
												<th>Tap Subscription Name</th>       <!--50  TapSubscriptionName-->
												<th>Participant Type Name</th>       <!--51  ParticipantTypeName-->
												<th>ParticipantOfficeId</th>         <!--52  ParticipantOfficeId-->
												<th>Contact Point Id Default</th>    <!--53  ContactPointIdDefault-->
												<th>Participant Office Name</th>     <!--54  ParticipantOfficeName-->
												<th>Participant Office Description</th> <!--55  ParticipantOfficeDescription-->
												<th>Contact Point Name</th>          <!--56  ContactPointName-->
												<th>ContactPointCityId</th>          <!--57  ContactPointCityId-->
												<th>Contact Point City Name</th>     <!--58  ContactPointCityName-->
												<th>ContactPointSuburbId</th>        <!--59  ContactPointSuburbId-->
												<th>Contact Point Suburb Name</th>   <!--60  ContactPointSuburbName-->
												<th>Email Address</th>               <!--61  EmailAddress-->
												<th>Address Line1</th>               <!--62  AddressLine1-->
												<th>Address Line2</th>               <!--63  AddressLine2-->
												<th>Address Line3</th>               <!--64  AddressLine3-->
												<th>Phone Number</th>                <!--65  PhoneNumber-->
												<th>Bank Details</th>                <!--66  BankDetails-->
												<th>Project Fin Admin</th>           <!--67  ProjectIdSustenance-->
												<th>Invoice Prefix</th>              <!--68  InvoicePrefix-->
												<th>Latest Invoice Number</th>       <!--69  LatestInvoiceNumber-->
												<th>Invoice Number Format</th>       <!--70  InvoiceNumberFormat-->
												<th>Email Address Accounts</th>      <!--71  EmailAddressAccounts-->

*/