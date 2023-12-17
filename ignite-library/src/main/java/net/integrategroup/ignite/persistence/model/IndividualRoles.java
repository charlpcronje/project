package net.integrategroup.ignite.persistence.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(schema="ig_db", name="vIndividualRoles")
public class IndividualRoles implements Serializable {

	private static final long serialVersionUID = 8289304839018615497L;

	@EmbeddedId
	private IndividualRolesPrimaryKey individualRolesPrimaryKey;

	public IndividualRoles() {
		this.individualRolesPrimaryKey = new IndividualRolesPrimaryKey();
	}

	@Column(name="UserName", insertable=false, updatable=false)
	private String userName;

	@Column(name="RoleCode", insertable=false, updatable=false)
	private String roleCode;

	@Column(name="AllowLoginFlag")
	private String allowLoginFlag;

	@Column(name="IsActiveMember")
	private String isActiveMember;

	public IndividualRolesPrimaryKey getIndividualRolesPrimaryKey() {
		return individualRolesPrimaryKey;
	}

	public void setIndividualRolesPrimaryKey(IndividualRolesPrimaryKey individualRolesPrimaryKey) {
		this.individualRolesPrimaryKey = individualRolesPrimaryKey;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getRoleCode() {
		return roleCode;
	}

	public void setRoleCode(String roleCode) {
		this.roleCode = roleCode;
	}

	public String getAllowLoginFlag() {
		return allowLoginFlag;
	}

	public void setAllowLoginFlag(String allowLoginFlag) {
		this.allowLoginFlag = allowLoginFlag;
	}

	public String getIsActiveMember() {
		return isActiveMember;
	}

	public void setIsActiveMember(String isActiveMember) {
		this.isActiveMember = isActiveMember;
	}
}
