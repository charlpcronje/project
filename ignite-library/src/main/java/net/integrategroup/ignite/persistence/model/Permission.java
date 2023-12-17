package net.integrategroup.ignite.persistence.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import net.integrategroup.ignite.utils.JsonDateSerializer;

/**
 * @author Tony De Buys
 *
 */
@Entity
@Table(schema="ig_db", name="Permission")
public class Permission implements Serializable {

	private static final long serialVersionUID = -1194924991779147211L;

	@Id
	@Column(name="PermissionCode")
	private String permissionCode;

	@Column(name="Name")
	private String name;

	@Column(name="Description")
	private String description;

	@JsonSerialize(using=JsonDateSerializer.class)
	@Column(name="LastUpdateTimestamp")
	private Date lastUpdateTimestamp;

	@Column(name="LastUpdateUserName")
	private String lastUpdateUserName;

	public String getPermissionCode() {
		return permissionCode;
	}

	public void setPermissionCode(String permissionCode) {
		this.permissionCode = permissionCode;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}


	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
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