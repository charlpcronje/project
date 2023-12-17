package net.integrategroup.ignite.persistence.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/** @author Generated by Johannes Marais (JohannesSQL v7.7) **/
/** ******* ********* ** 2023-10-19 13:37:08 ******** *sdr* **/

@Entity
@Table(schema = "ig_db", name = "vSdRole")
public class VSdRole implements Serializable {


    private static final long serialVersionUID = 223717530150089723L; /** ID was Generated by Johannes **/


    @Id
	@Column(name = "sdRoleId")
	private Long sdRoleId;

	@Column(name = "serviceDisciplineId")
	private Long serviceDisciplineId;

/***	@OneToOne(targetEntity = serviceDiscipline.class)
	@JoinColumn(name = "serviceDisciplineId", referencedColumnName = "serviceDisciplineId", nullable = true, insertable = false, updatable = false)
	private serviceDiscipline serviceDiscipline; ***/

	@Column(name = "roleOnAProjectId")
	private Long roleOnAProjectId;

/***	@OneToOne(targetEntity = roleOnAProject.class)
	@JoinColumn(name = "roleOnAProjectId", referencedColumnName = "roleOnAProjectId", nullable = true, insertable = false, updatable = false)
	private roleOnAProject roleOnAProject; ***/

	@Column(name = "serviceDisciplineCode")
	private String serviceDisciplineCode;

/***	@OneToOne(targetEntity = serviceDiscipline.class)
	@JoinColumn(name = "serviceDisciplineCode", referencedColumnName = "serviceDisciplineCode", nullable = true, insertable = false, updatable = false)
	private serviceDiscipline serviceDiscipline; ***/

	@Column(name = "serviceDisciplineName")
	private String serviceDisciplineName;

	@Column(name = "roleOnAProjectName")
	private String roleOnAProjectName;

	@Column(name = "combinedName")
	private String combinedName;

	@Column(name = "serviceDisciplineIdIndustry")
	private Long serviceDisciplineIdIndustry;

/***	@OneToOne(targetEntity = serviceDiscipline.class)
	@JoinColumn(name = "serviceDisciplineIdIndustry", referencedColumnName = "serviceDisciplineId", nullable = true, insertable = false, updatable = false)
	private serviceDiscipline serviceDisciplineIndustry; ***/

	@Column(name = "orderNumber")
	private Long orderNumber;

	@Column(name = "sdParentName")
	private String sdParentName;

	@Column(name = "sdGrandParentName")
	private String sdGrandParentName;

	@Column(name = "sdIndustryName")
	private String sdIndustryName;




	public Long getsdRoleId() {
		return sdRoleId;
	}

	public void setsdRoleId(Long sdRoleId) {
		this.sdRoleId = sdRoleId;
	}

	public Long getserviceDisciplineId() {
		return serviceDisciplineId;
	}

	public void setserviceDisciplineId(Long serviceDisciplineId) {
		this.serviceDisciplineId = serviceDisciplineId;
	}

/***	public serviceDiscipline getserviceDiscipline() {
		return serviceDiscipline;
	}

	public void setserviceDiscipline(serviceDiscipline serviceDiscipline) {
		this.serviceDiscipline = serviceDiscipline;
	} ***/

	public Long getroleOnAProjectId() {
		return roleOnAProjectId;
	}

	public void setroleOnAProjectId(Long roleOnAProjectId) {
		this.roleOnAProjectId = roleOnAProjectId;
	}

/***	public roleOnAProject getroleOnAProject() {
		return roleOnAProject;
	}

	public void setroleOnAProject(roleOnAProject roleOnAProject) {
		this.roleOnAProject = roleOnAProject;
	} ***/

	public String getserviceDisciplineCode() {
		return serviceDisciplineCode;
	}

	public void setserviceDisciplineCode(String serviceDisciplineCode) {
		this.serviceDisciplineCode = serviceDisciplineCode;
	}

/***	public serviceDiscipline getserviceDiscipline() {
		return serviceDiscipline;
	}

	public void setserviceDiscipline(serviceDiscipline serviceDiscipline) {
		this.serviceDiscipline = serviceDiscipline;
	} ***/

	public String getserviceDisciplineName() {
		return serviceDisciplineName;
	}

	public void setserviceDisciplineName(String serviceDisciplineName) {
		this.serviceDisciplineName = serviceDisciplineName;
	}

	public String getroleOnAProjectName() {
		return roleOnAProjectName;
	}

	public void setroleOnAProjectName(String roleOnAProjectName) {
		this.roleOnAProjectName = roleOnAProjectName;
	}

	public String getcombinedName() {
		return combinedName;
	}

	public void setcombinedName(String combinedName) {
		this.combinedName = combinedName;
	}

	public Long getserviceDisciplineIdIndustry() {
		return serviceDisciplineIdIndustry;
	}

	public void setserviceDisciplineIdIndustry(Long serviceDisciplineIdIndustry) {
		this.serviceDisciplineIdIndustry = serviceDisciplineIdIndustry;
	}

/***	public serviceDiscipline getserviceDisciplineIndustry() {
		return serviceDisciplineIndustry;
	}

	public void setserviceDisciplineIndustry(serviceDiscipline serviceDisciplineIndustry) {
		this.serviceDisciplineIndustry = serviceDisciplineIndustry;
	} ***/

	public Long getorderNumber() {
		return orderNumber;
	}

	public void setorderNumber(Long orderNumber) {
		this.orderNumber = orderNumber;
	}

	public String getsdParentName() {
		return sdParentName;
	}

	public void setsdParentName(String sdParentName) {
		this.sdParentName = sdParentName;
	}

	public String getsdGrandParentName() {
		return sdGrandParentName;
	}

	public void setsdGrandParentName(String sdGrandParentName) {
		this.sdGrandParentName = sdGrandParentName;
	}

	public String getsdIndustryName() {
		return sdIndustryName;
	}

	public void setsdIndustryName(String sdIndustryName) {
		this.sdIndustryName = sdIndustryName;
	}


}

/**  javascript      vir grid population        (jsStr)

	var columnsArray = [
		 { data: "sdRoleId" }                 //0 MySql-TableName: VSdRole
		,{ data: "serviceDisciplineId" }      //1
		,{ data: "roleOnAProjectId" }       //2
		,{ data: "serviceDisciplineCode" }    //3
		,{ data: "serviceDisciplineName" }    //4
		,{ data: "roleOnAProjectName" }       //5
		,{ data: "combinedName" }             //6
		,{ data: "serviceDisciplineIdIndustry" } //7
		,{ data: "orderNumber" }              //8
		,{ data: "sdParentName" }             //9
		,{ data: "sdGrandParentName" }        //10
		,{ data: "sdIndustryName" }           //11
	];

	var columnDefsArray = [
		{
			visible: false,
			targets: [0, 1, 2, 3, 7]
		}
	];







/**  javascript      vir grid SelectFromGridDialog        (js1Str)

	var columns = [
		{ data: "sdRoleId", name: "sdRoleId" }                             //0 MySql-TableName: VSdRole
		,{ data: "serviceDisciplineId", name: "serviceDisciplineId" }       //1
		,{ data: "roleOnAProjectId", name: "roleOnAProjectId" }         //2
		,{ data: "serviceDisciplineCode", name: "serviceDisciplineCode" }   //3
		,{ data: "serviceDisciplineName", name: "serviceDisciplineName" }   //4
		,{ data: "roleOnAProjectName", name: "roleOnAProjectName" }         //5
		,{ data: "combinedName", name: "combinedName" }                     //6
		,{ data: "serviceDisciplineIdIndustry", name: "serviceDisciplineIdIndustry" } //7
		,{ data: "orderNumber", name: "orderNumber" }                       //8
		,{ data: "sdParentName", name: "sdParentName" }                     //9
		,{ data: "sdGrandParentName", name: "sdGrandParentName" }           //10
		,{ data: "sdIndustryName", name: "sdIndustryName" }                 //11
	];

	var columnDefs = [
		{
			visible: false,
			targets: [0, 1, 2, 3, 7]
		}
	];







Vir 'n Save Data function sien SdRole.java






Vir 'n Populate Data function                                                  (js3Str)

		//  MySql-TableName: VSdRole										   (js3Str)
		$("#sdrsdRoleId").val(data.sdRoleId);                             //0
		$("#sdrserviceDisciplineId").val(data.serviceDisciplineId);       //1
		populateSelect("sdrserviceDisciplineId",                          //name of html select element that will be populated
				"/rest/ignite/v1/service-discipline/find-all",             //url
				"serviceDisciplineId",                                    //the value that must be saved (ReferencedColumnName)
				"name",                                                   //shown to user (usually a Name column)
				data.serviceDisciplineId,                                 //The selected one, if there is one
				true,                                                     //addEmpty, boolean: should we add empty object at the top
				null                                                      //completeMethod
		),

		$("#sdrroleOnAProjectId").val(data.roleOnAProjectId);         //2
		populateSelect("sdrroleOnAProjectId",                           //name of html select element that will be populated
				"/rest/ignite/v1/role-on-a-project/find-all",              //url
				"roleOnAProjectId",                                     //the value that must be saved (ReferencedColumnName)
				"name",                                                   //shown to user (usually a Name column)
				data.roleOnAProjectId,                                  //The selected one, if there is one
				true,                                                     //addEmpty, boolean: should we add empty object at the top
				null                                                      //completeMethod
		),

		$("#sdrserviceDisciplineCode").val(data.serviceDisciplineCode);   //3
		populateSelect("sdrserviceDisciplineCode",                        //name of html select element that will be populated
				"/rest/ignite/v1/service-discipline/find-all",             //url
				"serviceDisciplineCode",                                  //the value that must be saved (ReferencedColumnName)
				"name",                                                   //shown to user (usually a Name column)
				data.serviceDisciplineCode,                               //The selected one, if there is one
				true,                                                     //addEmpty, boolean: should we add empty object at the top
				null                                                      //completeMethod
		),

		$("#sdrserviceDisciplineName").val(data.serviceDisciplineName);   //4
		$("#sdrroleOnAProjectName").val(data.roleOnAProjectName);         //5
		$("#sdrcombinedName").val(data.combinedName);                     //6
		$("#sdrserviceDisciplineIdIndustry").val(data.serviceDisciplineIdIndustry); //7
		populateSelect("sdrserviceDisciplineIdIndustry",                  //name of html select element that will be populated
				"/rest/ignite/v1/service-discipline/find-all",             //url
				"serviceDisciplineId",                                    //the value that must be saved (ReferencedColumnName)
				"name",                                                   //shown to user (usually a Name column)
				data.serviceDisciplineIdIndustry,                         //The selected one, if there is one
				true,                                                     //addEmpty, boolean: should we add empty object at the top
				null                                                      //completeMethod
		),

		$("#sdrorderNumber").val(data.orderNumber);                       //8
		$("#sdrsdParentName").val(data.sdParentName);                     //9
		$("#sdrsdGrandParentName").val(data.sdGrandParentName);           //10
		$("#sdrsdIndustryName").val(data.sdIndustryName);                 //11






/**  HTML  om 'n grid te populate
												<th>sdRoleId</th>                    <!--0  MySql-TableName: VSdRole-->
												<th>serviceDisciplineId</th>         <!--1  serviceDisciplineId-->
												<th>roleOnAProjectId</th>          <!--2  roleOnAProjectId-->
												<th>serviceDisciplineCode</th>       <!--3  serviceDisciplineCode-->
												<th>service Discipline Name</th>     <!--4  serviceDisciplineName-->
												<th>role On A Project Name</th>      <!--5  roleOnAProjectName-->
												<th>combined Name</th>               <!--6  combinedName-->
												<th>service Discipline Id Industry</th> <!--7  serviceDisciplineIdIndustry-->
												<th>order Number</th>                <!--8  orderNumber-->
												<th>sd Parent Name</th>              <!--9  sdParentName-->
												<th>sd Grand Parent Name</th>        <!--10  sdGrandParentName-->
												<th>sd Industry Name</th>            <!--11  sdIndustryName-->

*/