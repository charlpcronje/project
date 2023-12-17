package net.integrategroup.ignite.persistence.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import net.integrategroup.ignite.utils.JsonDateSerializer;

/** @author Generated by Johannes Marais (JohannesSQL v7.7) **/
/** ******* ********* ** 2023-09-22 10:27:39 ******** *ind* **/

@Entity
@Table(schema = "ig_db", name = "vIndividual")
public class VIndividual implements Serializable {


    private static final long serialVersionUID = 291800905235867000L; /** ID was Generated by Johannes **/


    @Id
	@Column(name = "IndividualId")
	private Long individualId;

	@Column(name = "ParticipantId")
	private Long participantId;

/***	@OneToOne(targetEntity = Participant.class)
	@JoinColumn(name = "ParticipantId", referencedColumnName = "ParticipantId", nullable = true, insertable = false, updatable = false)
	private Participant participant; ***/

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

/***	@OneToOne(targetEntity = Country.class)
	@JoinColumn(name = "CountryId", referencedColumnName = "CountryId", nullable = true, insertable = false, updatable = false)
	private Country country; ***/

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

	@Column(name = "MedicalName")
	private String medicalName;

	@Column(name = "MedicalNumber")
	private String medicalNumber;

	@JsonSerialize(using = JsonDateSerializer.class)
	@Column(name = "LastUpdateTimestamp")
	private Date lastUpdateTimestamp;

	@Column(name = "LastUpdateUserName")
	private String lastUpdateUserName;

	@Column(name = "SystemMemberId")
	private Long systemMemberId;

/***	@OneToOne(targetEntity = SystemMember.class)
	@JoinColumn(name = "SystemMemberId", referencedColumnName = "SystemMemberId", nullable = true, insertable = false, updatable = false)
	private SystemMember systemMember; ***/

	@JsonSerialize(using = JsonDateSerializer.class)
	@Column(name = "SystemMemberStartDate")
	private Date systemMemberStartDate;

	@JsonSerialize(using = JsonDateSerializer.class)
	@Column(name = "SystemMemberEndDate")
	private Date systemMemberEndDate;

	@Column(name = "RoleCode")
	private String roleCode;

/***	@OneToOne(targetEntity = Role.class)
	@JoinColumn(name = "RoleCode", referencedColumnName = "RoleCode", nullable = true, insertable = false, updatable = false)
	private Role role; ***/

	@Column(name = "RoleName")
	private String roleName;

	@Column(name = "Name")
	private String name;

	@Column(name = "SystemName")
	private String systemName;




	public Long getIndividualId() {
		return individualId;
	}

	public void setIndividualId(Long individualId) {
		this.individualId = individualId;
	}

	public Long getParticipantId() {
		return participantId;
	}

	public void setParticipantId(Long participantId) {
		this.participantId = participantId;
	}

/***	public Participant getParticipant() {
		return participant;
	}

	public void setParticipant(Participant participant) {
		this.participant = participant;
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSystemName() {
		return systemName;
	}

	public void setSystemName(String systemName) {
		this.systemName = systemName;
	}


}

/**  javascript      vir grid population        (jsStr)

	var columnsArray = [                      //Om 'n grid vol te maak.
		 { data: "individualId" }             //0 MySql-TableName: VIndividual
		,{ data: "participantId" }            //1
		,{ data: "firstName" }                //2
		,{ data: "secondName" }               //3
		,{ data: "thirdName" }                //4
		,{ data: "nickName" }                 //5
		,{ data: "lastName" }                 //6
		,{ data: "initials" }                 //7
		,{ data: "idNumber" }                 //8
		,{ data: "passportNumber" }           //9
		,{ data: "countryId" }              //10
		,{ data: "isActiveMember" }           //11
		,{ data: "allowLoginFlag" }           //12
		,{ data: "incomeTaxNumber" }          //13
		,{ data: "pass" }                     //14
		,{ data: "userName" }                 //15
		,{ data: "passwordResetToken" }       //16
		,{ data: "passwordResetExpiryDate" }  //17
		,{ data: "lastLoginTimestamp" }       //18
		,{ data: "medicalName" }              //19
		,{ data: "medicalNumber" }            //20
		,{ data: "lastUpdateTimestamp" }      //21
		,{ data: "lastUpdateUserName" }       //22
		,{ data: "systemMemberId" }           //23
		,{ data: "systemMemberStartDate" }    //24
		,{ data: "systemMemberEndDate" }      //25
		,{ data: "roleCode" }                 //26
		,{ data: "roleName" }                 //27
		,{ data: "name" }                     //28
		,{ data: "systemName" }               //29
	];

	var columnDefsArray = [
		{
			visible: false,
			targets: [0, 1, 10, 21, 22, 23, 26]
		}
		,{       //for Date columns
			render: function (data, type, row) {
				var html = data;
				if (type == "display") {
					html = timestampToString(data, false);
				}
				return html;
			},
			targets: [17, 18, 24, 25]
		}
		,{        //for checkboxes
			render: function (data, type, row) {
				return "<input type='checkbox' readonly onclick='return false;' " + (row.isActiveMember == "Y" ? " checked " : "") + ">";
			},
			className: "dt-center",
			targets:[11]
		}
		,{        //for checkboxes
			render: function (data, type, row) {
				return "<input type='checkbox' readonly onclick='return false;' " + (row.allowLoginFlag == "Y" ? " checked " : "") + ">";
			},
			className: "dt-center",
			targets:[12]
		}

	];







/**  javascript      vir grid SelectFromGridDialog        (js1Str)

	var columns = [                                                         // vir grid SelectFromGridDialog
		{ data: "individualId", name: "IndividualId" }                     //0 MySql-TableName: VIndividual
		,{ data: "participantId", name: "ParticipantId" }                   //1
		,{ data: "firstName", name: "FirstName" }                           //2
		,{ data: "secondName", name: "SecondName" }                         //3
		,{ data: "thirdName", name: "ThirdName" }                           //4
		,{ data: "nickName", name: "NickName" }                             //5
		,{ data: "lastName", name: "LastName" }                             //6
		,{ data: "initials", name: "Initials" }                             //7
		,{ data: "idNumber", name: "IdNumber" }                             //8
		,{ data: "passportNumber", name: "PassportNumber" }                 //9
		,{ data: "countryId", name: "CountryId" }                       //10
		,{ data: "isActiveMember", name: "IsActiveMember" }                 //11
		,{ data: "allowLoginFlag", name: "AllowLoginFlag" }                 //12
		,{ data: "incomeTaxNumber", name: "IncomeTaxNumber" }               //13
		,{ data: "pass", name: "Pass" }                                     //14
		,{ data: "userName", name: "UserName" }                             //15
		,{ data: "passwordResetToken", name: "PasswordResetToken" }         //16
		,{ data: "passwordResetExpiryDate", name: "PasswordResetExpiryDate" } //17
		,{ data: "lastLoginTimestamp", name: "LastLoginTimestamp" }         //18
		,{ data: "medicalName", name: "MedicalName" }                       //19
		,{ data: "medicalNumber", name: "MedicalNumber" }                   //20
		,{ data: "lastUpdateTimestamp", name: "LastUpdateTimestamp" }       //21
		,{ data: "lastUpdateUserName", name: "LastUpdateUserName" }         //22
		,{ data: "systemMemberId", name: "SystemMemberId" }                 //23
		,{ data: "systemMemberStartDate", name: "SystemMemberStartDate" }   //24
		,{ data: "systemMemberEndDate", name: "SystemMemberEndDate" }       //25
		,{ data: "roleCode", name: "RoleCode" }                             //26
		,{ data: "roleName", name: "RoleName" }                             //27
		,{ data: "name", name: "Name" }                                     //28
		,{ data: "systemName", name: "SystemName" }                         //29
	];

	var columnDefs = [
		{
			visible: false,
			targets: [0, 1, 10, 21, 22, 23, 26]
		}
		,{       //for Date columns
			render: function (data, type, row) {
				var html = data;
				if (type == "display") {
					html = timestampToString(data, false);
				}
				return html;
			},
			targets: [17, 18, 24, 25]
		}
		,{        //for checkboxes
			render: function (data, type, row) {
				return "<input type='checkbox' readonly onclick='return false;' " + (row.isActiveMember == "Y" ? " checked " : "") + ">";
			},
			className: "dt-center",
			targets:[11]
		}
		,{        //for checkboxes
			render: function (data, type, row) {
				return "<input type='checkbox' readonly onclick='return false;' " + (row.allowLoginFlag == "Y" ? " checked " : "") + ">";
			},
			className: "dt-center",
			targets:[12]
		}


	];







Vir 'n Save Data function sien Individual.java






Vir 'n Populate Data function                                                  (js3Str)

		//  MySql-TableName: VIndividual										   (js3Str)
		$("#indIndividualId").val(data.individualId);                     //0
		$("#indParticipantId").val(data.participantId);                   //1
		populateSelect("indParticipantId",                                //name of html select element that will be populated
				"/rest/ignite/v1/participant/find-all",                   //url
				"participantId",                                          //the value that must be saved (ReferencedColumnName)
				"systemName",                                             //shown to user (usually a Name column)
				data.participantId,                                       //The selected one, if there is one
				true,                                                     //addEmpty, boolean: should we add empty object at the top
				null                                                      //completeMethod
		),

		$("#indFirstName").val(data.firstName);                           //2
		$("#indSecondName").val(data.secondName);                         //3
		$("#indThirdName").val(data.thirdName);                           //4
		$("#indNickName").val(data.nickName);                             //5
		$("#indLastName").val(data.lastName);                             //6
		$("#indInitials").val(data.initials);                             //7
		$("#indIdNumber").val(data.idNumber);                             //8
		$("#indPassportNumber").val(data.passportNumber);                 //9
		$("#indCountryId").val(data.countryId);                       //10
		populateSelect("indCountryId",                                  //name of html select element that will be populated
				"/rest/ignite/v1/country/find-all",                       //url
				"countryId",                                            //the value that must be saved (ReferencedColumnName)
				"name",                                                   //shown to user (usually a Name column)
				data.countryId,                                         //The selected one, if there is one
				true,                                                     //addEmpty, boolean: should we add empty object at the top
				null                                                      //completeMethod
		),

		$("#indIsActiveMember").val(data.isActiveMember);                 //11    use one of these
		$("#indIsActiveMember").prop("checked", data.isActiveMember == "Y"); //11    use one of these
		$("#indAllowLoginFlag").val(data.allowLoginFlag);                 //12    use one of these
		$("#indAllowLoginFlag").prop("checked", data.allowLoginFlag == "Y"); //12    use one of these
		$("#indIncomeTaxNumber").val(data.incomeTaxNumber);               //13
		$("#indPass").val(data.pass);                                     //14
		$("#indUserName").val(data.userName);                             //15
		$("#indPasswordResetToken").val(data.passwordResetToken);         //16
		$("#indPasswordResetExpiryDate").datepicker("setDate", data.passwordResetExpiryDate == null? timestampToString(new Date(), false) : new Date(data.passwordResetExpiryDate)); //17
		$("#indLastLoginTimestamp").datepicker("setDate", data.lastLoginTimestamp == null? timestampToString(new Date(), false) : new Date(data.lastLoginTimestamp));         //18
		$("#indMedicalName").val(data.medicalName);                       //19
		$("#indMedicalNumber").val(data.medicalNumber);                   //20
		$("#indSystemMemberId").val(data.systemMemberId);                 //23
		populateSelect("indSystemMemberId",                               //name of html select element that will be populated
				"/rest/ignite/v1/system-member/find-all",                 //url
				"systemMemberId",                                         //the value that must be saved (ReferencedColumnName)
				"lastUpdateUserName",                                     //shown to user (usually a Name column)
				data.systemMemberId,                                      //The selected one, if there is one
				true,                                                     //addEmpty, boolean: should we add empty object at the top
				null                                                      //completeMethod
		),

		$("#indSystemMemberStartDate").datepicker("setDate", data.systemMemberStartDate == null? timestampToString(new Date(), false) : new Date(data.systemMemberStartDate));   //24
		$("#indSystemMemberEndDate").datepicker("setDate", data.systemMemberEndDate == null? timestampToString(new Date(), false) : new Date(data.systemMemberEndDate));       //25
		$("#indRoleCode").val(data.roleCode);                             //26
		populateSelect("indRoleCode",                                     //name of html select element that will be populated
				"/rest/ignite/v1/role/find-all",                          //url
				"roleCode",                                               //the value that must be saved (ReferencedColumnName)
				"name",                                                   //shown to user (usually a Name column)
				data.roleCode,                                            //The selected one, if there is one
				true,                                                     //addEmpty, boolean: should we add empty object at the top
				null                                                      //completeMethod
		),

		$("#indRoleName").val(data.roleName);                             //27
		$("#indName").val(data.name);                                     //28
		$("#indSystemName").val(data.systemName);                         //29






/**  HTML  om 'n grid te populate
												<th>IndividualId</th>                <!--0  MySql-TableName: VIndividual-->
												<th>ParticipantId</th>               <!--1  ParticipantId-->
												<th>First Name</th>                  <!--2  FirstName-->
												<th>Second Name</th>                 <!--3  SecondName-->
												<th>Third Name</th>                  <!--4  ThirdName-->
												<th>Nick Name</th>                   <!--5  NickName-->
												<th>Last Name</th>                   <!--6  LastName-->
												<th>Initials</th>                    <!--7  Initials-->
												<th>Id Number</th>                   <!--8  IdNumber-->
												<th>Passport Number</th>             <!--9  PassportNumber-->
												<th>CountryId</th>                 <!--10  CountryId-->
												<th>Is Active Member</th>            <!--11  IsActiveMember-->
												<th>Allow Login</th>                 <!--12  AllowLoginFlag-->
												<th>Income Tax Number</th>           <!--13  IncomeTaxNumber-->
												<th>Pass</th>                        <!--14  Pass-->
												<th>User Name</th>                   <!--15  UserName-->
												<th>Password Reset Token</th>        <!--16  PasswordResetToken-->
												<th>Password Reset Expiry Date</th>  <!--17  PasswordResetExpiryDate-->
												<th>Last Login Timestamp</th>        <!--18  LastLoginTimestamp-->
												<th>Medical Name</th>                <!--19  MedicalName-->
												<th>Medical Number</th>              <!--20  MedicalNumber-->
												<th>LastUpdateTimestamp</th>         <!--21  LastUpdateTimestamp-->
												<th>LastUpdateUserName</th>          <!--22  LastUpdateUserName-->
												<th>SystemMemberId</th>              <!--23  SystemMemberId-->
												<th>System Member Start Date</th>    <!--24  SystemMemberStartDate-->
												<th>System Member End Date</th>      <!--25  SystemMemberEndDate-->
												<th>RoleCode</th>                    <!--26  RoleCode-->
												<th>Role Name</th>                   <!--27  RoleName-->
												<th>Name</th>                        <!--28  Name-->
												<th>System Name</th>                 <!--29  SystemName-->

*/