package net.integrategroup.ignite.persistence.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(schema = "ig_db", name = "vProjectSdStage")
public class VProjectSdStage implements Serializable {

	private static final long serialVersionUID = -2709580392205309056L;

	@Id
	@Column(name = "ProjectSdStageId")
	private Long projectSdStageId;

	@Column(name = "ProjectId")
	private Long projectId;

	@Column(name = "ProjectNameText")
	private String projectNameText;

	@Column(name = "ProjectSdId")
	private Long projectSdId;

	@Column(name = "IndustrySdName")
	private String industrySdName;

	@Column(name = "ServiceDisciplineId")
	private Long serviceDisciplineId;

	@Column(name = "ServiceDisciplineCode")
	private String serviceDisciplineCode;

	@Column(name = "SdName")
	private String sdName;

	@Column(name = "ParentName")
	private String parentName;

	@Column(name = "StageName")
	private String stageName;

	@Column(name = "ProjectStageId")
	private Long projectStageId;

	@Column(name = "OrderNumber")
	private Long orderNumber;

	public Long getProjectSdStageId() {
		return projectSdStageId;
	}

	public void setProjectSdStageId(Long projectSdStageId) {
		this.projectSdStageId = projectSdStageId;
	}

	public Long getProjectId() {
		return projectId;
	}

	public void setProjectId(Long projectId) {
		this.projectId = projectId;
	}

	public String getProjectNameText() {
		return projectNameText;
	}

	public void setProjectNameText(String projectNameText) {
		this.projectNameText = projectNameText;
	}

	public Long getProjectSdId() {
		return projectSdId;
	}

	public void setProjectSdId(Long projectSdId) {
		this.projectSdId = projectSdId;
	}

	public String getIndustrySdName() {
		return industrySdName;
	}

	public void setIndustrySdName(String industrySdName) {
		this.industrySdName = industrySdName;
	}

	public Long getServiceDisciplineId() {
		return serviceDisciplineId;
	}

	public void setServiceDisciplineId(Long serviceDisciplineId) {
		this.serviceDisciplineId = serviceDisciplineId;
	}

	public String getServiceDisciplineCode() {
		return serviceDisciplineCode;
	}

	public void setServiceDisciplineCode(String serviceDisciplineCode) {
		this.serviceDisciplineCode = serviceDisciplineCode;
	}

	public String getSdName() {
		return sdName;
	}

	public void setSdName(String sdName) {
		this.sdName = sdName;
	}

	public String getParentName() {
		return parentName;
	}

	public void setParentName(String parentName) {
		this.parentName = parentName;
	}

	public String getStageName() {
		return stageName;
	}

	public void setStageName(String stageName) {
		this.stageName = stageName;
	}

	public Long getProjectStageId() {
		return projectStageId;
	}

	public void setProjectStageId(Long projectStageId) {
		this.projectStageId = projectStageId;
	}

	public Long getOrderNumber() {
		return orderNumber;
	}

	public void setOrderNumber(Long orderNumber) {
		this.orderNumber = orderNumber;
	}
	
}