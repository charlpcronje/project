package net.integrategroup.ignite.persistence.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

// @author Ingrid Marais

@Entity
@Table(schema = "ig_db", name = "vIndividualCompetency")
public class VIndividualCompetency implements Serializable {

	private static final long serialVersionUID = -7699467004849330553L;

	@Id
	@Column(name = "IndividualCompetencyId")
	private Long individualCompetencyId;

	@Column(name = "IndividualId")
	private Long individualId;

	@Column(name = "ServiceDisciplineCode")
	private String serviceDisciplineCode;

	@Column(name = "RoleOnAProjectId")
	private Long roleOnAProjectId;

	@Column(name = "CompetencyLevelId")
	private Long competencyLevelId;

	@Column(name = "SystemName")
	private String systemName;

	@Column(name = "CompetencyLevelName")
	private String competencyLevelName;

	@Column(name = "RoleOnAProjectName")
	private String roleOnAProjectName;

	public Long getIndividualCompetencyId() {
		return individualCompetencyId;
	}

	public void setIndividualCompetencyId(Long individualCompetencyId) {
		this.individualCompetencyId = individualCompetencyId;
	}

	public Long getIndividualId() {
		return individualId;
	}

	public void setIndividualId(Long individualId) {
		this.individualId = individualId;
	}

	public String getServiceDisciplineCode() {
		return serviceDisciplineCode;
	}

	public void setServiceDisciplineCode(String serviceDisciplineCode) {
		this.serviceDisciplineCode = serviceDisciplineCode;
	}

	public Long getRoleOnAProjectId() {
		return roleOnAProjectId;
	}

	public void setRoleOnAProjectId(Long roleOnAProjectId) {
		this.roleOnAProjectId = roleOnAProjectId;
	}

	public Long getCompetencyLevelId() {
		return competencyLevelId;
	}

	public void setCompetencyLevelId(Long competencyLevelId) {
		this.competencyLevelId = competencyLevelId;
	}

	public String getSystemName() {
		return systemName;
	}

	public void setSystemName(String systemName) {
		this.systemName = systemName;
	}

	public String getCompetencyLevelName() {
		return competencyLevelName;
	}

	public void setCompetencyLevelName(String competencyLevelName) {
		this.competencyLevelName = competencyLevelName;
	}

	public String getRoleOnAProjectName() {
		return roleOnAProjectName;
	}

	public void setRoleOnAProjectName(String roleOnAProjectName) {
		this.roleOnAProjectName = roleOnAProjectName;
	}
}
