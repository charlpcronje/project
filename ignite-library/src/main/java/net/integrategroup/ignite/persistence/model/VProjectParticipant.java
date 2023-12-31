package net.integrategroup.ignite.persistence.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/** @author Generated by Johannes Marais (JohannesSQL v4.5) **/
/** ******* ********* ** 2023-04-24 19:01:56 ******** ***** **/

@Entity
@Table(schema = "ig_db", name = "vProjectParticipant")
public class VProjectParticipant implements Serializable {

	private static final long serialVersionUID = 4647992896422693538L;

	@Id
	@Column(name = "ProjectParticipantId")
	private Long projectParticipantId;

	@Column(name = "ProjectId")
	private Long projectId;

	@Column(name = "ProjectParticipantIdLevel1")
	private Long projectParticipantIdHost;

	@Column(name = "ParticipantNameLevel1")
	private String participantNameLevel1;

	@Column(name = "ParticipantIdHost")
	private Long participantIdHost;

	@Column(name = "ParticipantNameHost")
	private String participantNameHost;

	@Column(name = "ProjectNumberBigInt")
	private Long projectNumberBigInt;

	@Column(name = "ProjectNumberText")
	private String projectNumberText;

	@Column(name = "ProjectTitle")
	private String projectTitle;

	@Column(name = "ProjectName")
	private String projectName;

	@Column(name = "SubProjNumber")
	private String subProjNumber;

	@Column(name = "ProjectParticipantIdAboveMe")
	private Long projectParticipantIdAboveMe;

	@Column(name = "ProjPartAboveMeSystemName")
	private String projPartAboveMeSystemName;

	@Column(name = "ParticipantIdPayer")
	private Long participantIdPayer;

	@Column(name = "SystemNamePayer")
	private String systemNamePayer;

	@Column(name = "IsIndividual")
	private String isIndividual;

	@Column(name = "RepresentativeIndividualId")
	private Long representativeIndividualId;

	@Column(name = "FirstName")
	private String firstName;

	@Column(name = "LastName")
	private String lastName;

	@Column(name = "IndividualId")
	private Long individualId;

	@Column(name = "FirstName2")
	private String firstName2;

	@Column(name = "LastName2")
	private String lastName2;

	public String getParticipantNameLevel1() {
		return participantNameLevel1;
	}

	public void setParticipantNameLevel1(String participantNameLevel1) {
		this.participantNameLevel1 = participantNameLevel1;
	}

	public Long getProjectParticipantId() {
		return projectParticipantId;
	}

	public void setProjectParticipantId(Long projectParticipantId) {
		this.projectParticipantId = projectParticipantId;
	}

	public Long getProjectId() {
		return projectId;
	}

	public void setProjectId(Long projectId) {
		this.projectId = projectId;
	}

	public Long getProjectParticipantIdHost() {
		return projectParticipantIdHost;
	}

	public void setProjectParticipantIdHost(Long projectParticipantIdHost) {
		this.projectParticipantIdHost = projectParticipantIdHost;
	}

	public Long getParticipantIdHost() {
		return participantIdHost;
	}

	public void setParticipantIdHost(Long participantIdHost) {
		this.participantIdHost = participantIdHost;
	}

	public String getParticipantNameHost() {
		return participantNameHost;
	}

	public void setParticipantNameHost(String participantNameHost) {
		this.participantNameHost = participantNameHost;
	}

	public Long getProjectNumberBigInt() {
		return projectNumberBigInt;
	}

	public void setProjectNumberBigInt(Long projectNumberBigInt) {
		this.projectNumberBigInt = projectNumberBigInt;
	}

	public String getProjectNumberText() {
		return projectNumberText;
	}

	public void setProjectNumberText(String projectNumberText) {
		this.projectNumberText = projectNumberText;
	}

	public String getProjectTitle() {
		return projectTitle;
	}

	public void setProjectTitle(String projectTitle) {
		this.projectTitle = projectTitle;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public String getSubProjNumber() {
		return subProjNumber;
	}

	public void setSubProjNumber(String subProjNumber) {
		this.subProjNumber = subProjNumber;
	}

	public Long getProjectParticipantIdAboveMe() {
		return projectParticipantIdAboveMe;
	}

	public void setProjectParticipantIdAboveMe(Long projectParticipantIdAboveMe) {
		this.projectParticipantIdAboveMe = projectParticipantIdAboveMe;
	}

	public String getProjPartAboveMeSystemName() {
		return projPartAboveMeSystemName;
	}

	public void setProjPartAboveMeSystemName(String projPartAboveMeSystemName) {
		this.projPartAboveMeSystemName = projPartAboveMeSystemName;
	}

	public Long getParticipantIdPayer() {
		return participantIdPayer;
	}

	public void setParticipantIdPayer(Long participantIdPayer) {
		this.participantIdPayer = participantIdPayer;
	}

	public String getSystemNamePayer() {
		return systemNamePayer;
	}

	public void setSystemNamePayer(String systemNamePayer) {
		this.systemNamePayer = systemNamePayer;
	}

	public String getIsIndividual() {
		return isIndividual;
	}

	public void setIsIndividual(String isIndividual) {
		this.isIndividual = isIndividual;
	}

	public Long getRepresentativeIndividualId() {
		return representativeIndividualId;
	}

	public void setRepresentativeIndividualId(Long representativeIndividualId) {
		this.representativeIndividualId = representativeIndividualId;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public Long getIndividualId() {
		return individualId;
	}

	public void setIndividualId(Long individualId) {
		this.individualId = individualId;
	}

	public String getFirstName2() {
		return firstName2;
	}

	public void setFirstName2(String firstName2) {
		this.firstName2 = firstName2;
	}

	public String getLastName2() {
		return lastName2;
	}

	public void setLastName2(String lastName2) {
		this.lastName2 = lastName2;
	}




}

/**  javascript
		{ data: "projectParticipantId" },     //0 MySql-TableName: VProjectParticipant
		{ data: "projectId" },                //1
		{ data: "participantIdHost" }, //2
		{ data: "participantIdHost" },    //3
		{ data: "participantNameHost" },  //4
		{ data: "projectNumber" },            //5
		{ data: "projectNumberText" },        //6
		{ data: "projectTitle" },             //7
		{ data: "projectName" },              //8
		{ data: "projectParticipantIdAboveMe" }, //9
		{ data: "projPartAboveMeSystemName" }, //10
		{ data: "participantIdPayer" },       //11
		{ data: "systemNamePayer" },          //12
		{ data: "isIndividual" },             //13
		{ data: "representativeIndividualId" }, //14
		{ data: "firstName" },                //15
		{ data: "lastName" },                 //16
		{ data: "individualId" },             //17
		{ data: "firstName2" },               //18
		{ data: "lastName2" },                //19

/**  html
										<th>ProjectParticipantId</th>     <!--0  MySql-TableName: VProjectParticipant-->
										<th>ProjectId</th>                <!--1  ProjectId-->
										<th>ParticipantIdHost</th> <!--2  ParticipantIdHost-->
										<th>ParticipantIdHost</th>    <!--3  ParticipantIdHost-->
										<th>participantNameHost</th>  <!--4  participantNameHost-->
										<th>ProjectNumber</th>            <!--5  ProjectNumber-->
										<th>ProjectNumberText</th>        <!--6  ProjectNumberText-->
										<th>ProjectTitle</th>             <!--7  ProjectTitle-->
										<th>ProjectName</th>              <!--8  ProjectName-->
										<th>ProjectParticipantIdAboveMe</th> <!--9  ProjectParticipantIdAboveMe-->
										<th>ProjPartAboveMeSystemName</th> <!--10  ProjPartAboveMeSystemName-->
										<th>ParticipantIdPayer</th>       <!--11  ParticipantIdPayer-->
										<th>SystemNamePayer</th>          <!--12  SystemNamePayer-->
										<th>IsIndividual</th>             <!--13  IsIndividual-->
										<th>RepresentativeIndividualId</th> <!--14  RepresentativeIndividualId-->
										<th>FirstName</th>                <!--15  FirstName-->
										<th>LastName</th>                 <!--16  LastName-->
										<th>IndividualId</th>             <!--17  IndividualId-->
										<th>FirstName2</th>               <!--18  FirstName2-->
										<th>LastName2</th>                <!--19  LastName2-->

*/