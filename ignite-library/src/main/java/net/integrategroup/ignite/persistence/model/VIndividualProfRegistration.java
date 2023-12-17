package net.integrategroup.ignite.persistence.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/** @author Generated by Johannes Marais (JohannesSQL v7.7) **/
/** ******* ********* ** 2023-10-17 17:18:04 ******** *ipr* **/

@Entity
@Table(schema = "ig_db", name = "vIndividualProfRegistration")
public class VIndividualProfRegistration implements Serializable {


    private static final long serialVersionUID = 684995381357036674L; /** ID was Generated by Johannes **/


    @Id
	@Column(name = "IndividualProfRegistrationId")
	private Long individualProfRegistrationId;

	@Column(name = "IndividualId")
	private Long individualId;

/***	@OneToOne(targetEntity = Individual.class)
	@JoinColumn(name = "IndividualId", referencedColumnName = "IndividualId", nullable = true, insertable = false, updatable = false)
	private Individual individual; ***/

	@Column(name = "IndividualId_FirstName")
	private String individualId_FirstName;

	@Column(name = "IndividualId_LastName")
	private String individualId_LastName;

	@Column(name = "ParticipantId")
	private Long participantId;

/***	@OneToOne(targetEntity = Participant.class)
	@JoinColumn(name = "ParticipantId", referencedColumnName = "ParticipantId", nullable = true, insertable = false, updatable = false)
	private Participant participant_LastName; ***/

	@Column(name = "ProfessionalInstituteId")
	private Long professionalInstituteId;

/***	@OneToOne(targetEntity = ProfessionalInstitute.class)
	@JoinColumn(name = "ProfessionalInstituteId", referencedColumnName = "ProfessionalInstituteId", nullable = true, insertable = false, updatable = false)
	private ProfessionalInstitute professionalInstitute_LastName; ***/

	@Column(name = "ProfessionalInstituteId_Name")
	private String professionalInstituteId_Name;

	@Column(name = "YearAccepted")
	private Long yearAccepted;

	@Column(name = "ProfNumber")
	private String profNumber;

	@Column(name = "Description")
	private String description;




	public Long getIndividualProfRegistrationId() {
		return individualProfRegistrationId;
	}

	public void setIndividualProfRegistrationId(Long individualProfRegistrationId) {
		this.individualProfRegistrationId = individualProfRegistrationId;
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

	public String getIndividualId_FirstName() {
		return individualId_FirstName;
	}

	public void setIndividualId_FirstName(String individualId_FirstName) {
		this.individualId_FirstName = individualId_FirstName;
	}

	public String getIndividualId_LastName() {
		return individualId_LastName;
	}

	public void setIndividualId_LastName(String individualId_LastName) {
		this.individualId_LastName = individualId_LastName;
	}

	public Long getParticipantId() {
		return participantId;
	}

	public void setParticipantId(Long participantId) {
		this.participantId = participantId;
	}

/***	public Participant getParticipant_LastName() {
		return participant_LastName;
	}

	public void setParticipant_LastName(Participant participant_LastName) {
		this.participant_LastName = participant_LastName;
	} ***/

	public Long getProfessionalInstituteId() {
		return professionalInstituteId;
	}

	public void setProfessionalInstituteId(Long professionalInstituteId) {
		this.professionalInstituteId = professionalInstituteId;
	}

/***	public ProfessionalInstitute getProfessionalInstitute_LastName() {
		return professionalInstitute_LastName;
	}

	public void setProfessionalInstitute_LastName(ProfessionalInstitute professionalInstitute_LastName) {
		this.professionalInstitute_LastName = professionalInstitute_LastName;
	} ***/

	public String getProfessionalInstituteId_Name() {
		return professionalInstituteId_Name;
	}

	public void setProfessionalInstituteId_Name(String professionalInstituteId_Name) {
		this.professionalInstituteId_Name = professionalInstituteId_Name;
	}

	public Long getYearAccepted() {
		return yearAccepted;
	}

	public void setYearAccepted(Long yearAccepted) {
		this.yearAccepted = yearAccepted;
	}

	public String getProfNumber() {
		return profNumber;
	}

	public void setProfNumber(String profNumber) {
		this.profNumber = profNumber;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}


}

/**  javascript      vir grid population        (jsStr)

	var columnsArray = [
		 { data: "individualProfRegistrationId" } //0 MySql-TableName: VIndividualProfRegistration
		,{ data: "individualId" }             //1
		,{ data: "individualId_FirstName" }   //2
		,{ data: "individualId_LastName" }    //3
		,{ data: "participantId" }            //4
		,{ data: "professionalInstituteId" }  //5
		,{ data: "professionalInstituteId_Name" } //6
		,{ data: "yearAccepted" }             //7
		,{ data: "profNumber" }               //8
		,{ data: "description" }              //9
	];

	var columnDefsArray = [
		{
			visible: false,
			targets: [0, 1, 2, 3, 4, 5]
		}
	];







/**  javascript      vir grid SelectFromGridDialog        (js1Str)

	var columns = [
		{ data: "individualProfRegistrationId", name: "IndividualProfRegistrationId" } //0 MySql-TableName: VIndividualProfRegistration
		,{ data: "individualId", name: "IndividualId" }                     //1
		,{ data: "individualId_FirstName", name: "IndividualId_FirstName" } //2
		,{ data: "individualId_LastName", name: "IndividualId_LastName" }   //3
		,{ data: "participantId", name: "ParticipantId" }                   //4
		,{ data: "professionalInstituteId", name: "ProfessionalInstituteId" } //5
		,{ data: "professionalInstituteId_Name", name: "ProfessionalInstituteId_Name" } //6
		,{ data: "yearAccepted", name: "YearAccepted" }                     //7
		,{ data: "profNumber", name: "ProfNumber" }                         //8
		,{ data: "description", name: "Description" }                       //9
	];

	var columnDefs = [
		{
			visible: false,
			targets: [0, 1, 2, 3, 4, 5]
		}
	];







Vir 'n Save Data function sien IndividualProfRegistration.java






Vir 'n Populate Data function                                                  (js3Str)

		//  MySql-TableName: VIndividualProfRegistration										   (js3Str)
		$("#iprIndividualProfRegistrationId").val(data.individualProfRegistrationId); //0
		$("#iprIndividualId").val(data.individualId);                     //1
		populateSelect("iprIndividualId",                                 //name of html select element that will be populated
				"/rest/ignite/v1/individual/find-all",                    //url
				"individualId",                                           //the value that must be saved (ReferencedColumnName)
				"firstName",                                              //shown to user (usually a Name column)
				data.individualId,                                        //The selected one, if there is one
				true,                                                     //addEmpty, boolean: should we add empty object at the top
				null                                                      //completeMethod
		),

		$("#iprIndividualId_FirstName").val(data.individualId_FirstName); //2
		$("#iprIndividualId_LastName").val(data.individualId_LastName);   //3
		$("#iprParticipantId").val(data.participantId);                   //4
		populateSelect("iprParticipantId",                                //name of html select element that will be populated
				"/rest/ignite/v1/participant/find-all",                   //url
				"participantId",                                          //the value that must be saved (ReferencedColumnName)
				"systemName",                                             //shown to user (usually a Name column)
				data.participantId,                                       //The selected one, if there is one
				true,                                                     //addEmpty, boolean: should we add empty object at the top
				null                                                      //completeMethod
		),

		$("#iprProfessionalInstituteId").val(data.professionalInstituteId); //5
		populateSelect("iprProfessionalInstituteId",                      //name of html select element that will be populated
				"/rest/ignite/v1/professional-institute/find-all",        //url
				"professionalInstituteId",                                //the value that must be saved (ReferencedColumnName)
				"name",                                                   //shown to user (usually a Name column)
				data.professionalInstituteId,                             //The selected one, if there is one
				true,                                                     //addEmpty, boolean: should we add empty object at the top
				null                                                      //completeMethod
		),

		$("#iprProfessionalInstituteId_Name").val(data.professionalInstituteId_Name); //6
		$("#iprYearAccepted").val(data.yearAccepted);                     //7
		$("#iprProfNumber").val(data.profNumber);                         //8
		$("#iprDescription").val(data.description);                       //9






/**  HTML  om 'n grid te populate
												<th>IndividualProfRegistrationId</th> <!--0  MySql-TableName: VIndividualProfRegistration-->
												<th>IndividualId</th>                <!--1  IndividualId-->
												<th>Individual First Name</th>       <!--2  IndividualId_FirstName-->
												<th>Individual Last Name</th>        <!--3  IndividualId_LastName-->
												<th>ParticipantId</th>               <!--4  ParticipantId-->
												<th>ProfessionalInstituteId</th>     <!--5  ProfessionalInstituteId-->
												<th>Professional Institute Name</th> <!--6  ProfessionalInstituteId_Name-->
												<th>Year Accepted</th>               <!--7  YearAccepted-->
												<th>Prof Number</th>                 <!--8  ProfNumber-->
												<th>Description</th>                 <!--9  Description-->

*/