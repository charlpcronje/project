package net.integrategroup.ignite.persistence.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class RolePermissionPrimaryKey implements Serializable {

	private static final long serialVersionUID = -1408460237263397379L;

	@Column(name="RoleCode")
	private String roleCode;

	@Column(name="PermissionCode")
	private String permissionCode;

	public String getRoleCode() {
		return roleCode;
	}

	public void setRoleCode(String roleCode) {
		this.roleCode = roleCode;
	}

	public String getPermissionCode() {
		return permissionCode;
	}

	public void setPermissionCode(String permissionCode) {
		this.permissionCode = permissionCode;
	}

}
