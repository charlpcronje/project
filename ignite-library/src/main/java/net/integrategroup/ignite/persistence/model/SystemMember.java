package net.integrategroup.ignite.persistence.model;

import java.io.Serializable;
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

@Entity
@Table(schema = "ig_db", name = "SystemMember")
public class SystemMember implements Serializable {

	private static final long serialVersionUID = -6664983638391161140L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "SystemMemberId")
	private Long systemMemberId;

	@Column(name = "IndividualId")
	private Long individualId;

	@OneToOne(targetEntity = Individual.class)
	@JoinColumn(name = "IndividualId", referencedColumnName = "IndividualId", nullable = true, insertable = false, updatable = false)
	private Individual individual;

	@Column(name = "RoleCode")
	private String roleCode;

	@OneToOne(targetEntity = Role.class)
	@JoinColumn(name = "RoleCode", referencedColumnName = "RoleCode", nullable = true, insertable = false, updatable = false)
	private Role Role;

	@JsonSerialize(using = JsonDateSerializer.class)
	@Column(name = "StartDate")
	private Date startDate;

	@JsonSerialize(using = JsonDateSerializer.class)
	@Column(name = "EndDate")
	private Date endDate;

	@JsonSerialize(using = JsonDateSerializer.class)
	@Column(name = "LastUpdateTimestamp")
	private Date lastUpdateTimestamp;

	@Column(name = "LastUpdateUserName")
	private String lastUpdateUserName;

	public Long getSystemMemberId() {
		return systemMemberId;
	}

	public void setSystemMemberId(Long systemMemberId) {
		this.systemMemberId = systemMemberId;
	}

	public Long getIndividualId() {
		return individualId;
	}

	public void setIndividualId(Long individualId) {
		this.individualId = individualId;
	}

	public Individual getIndividual() {
		return individual;
	}

	public void setIndividual(Individual individual) {
		this.individual = individual;
	}

	public String getRoleCode() {
		return roleCode;
	}

	public void setRoleCode(String roleCode) {
		this.roleCode = roleCode;
	}

	public Role getRole() {
		return Role;
	}

	public void setRole(Role role) {
		Role = role;
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
