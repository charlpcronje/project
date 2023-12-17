package net.integrategroup.ignite.persistence.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class IndividualRolesPrimaryKey implements Serializable {

	private static final long serialVersionUID = -8980698489170130736L;

	@Column(name="UserName")
	private String userName;

	@Column(name="RoleCode")
	private String roleCode;

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

}
