package net.integrategroup.ignite.persistence.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import net.integrategroup.ignite.utils.JsonDateSerializer;

/**
 * @author Admin
 *
 */
@Entity
@Table(schema = "ig_db", name = "SupportService")
public class SupportService implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "SupportServiceCode")
	private String supportServiceCode;

	@Column(name = "Name")
	private String name;

	@Column(name = "Description")
	private String description;

	@Column(name = "SupportServiceCodeParent")
	private String supportServiceCodeParent;

	@Column(name = "AllowForIndividual")
	private String allowForIndividual;

	@JsonSerialize(using = JsonDateSerializer.class)
	@Column(name = "LastUpdateTimestamp")
	private Date lastUpdateTimestamp;

	@Column(name = "LastUpdateUserName")
	private String lastUpdateUserName;

	@Transient
	private String displayName; // Om mooi display te wys vir die Parent in die dropdown

	public String getDisplayName() {
		return this.supportServiceCode + " - " + this.name;
	}

	public String getSupportServiceCode() {
		return supportServiceCode;
	}

	public void setSupportServiceCode(String supportServiceCode) {
		this.supportServiceCode = supportServiceCode;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAllowForIndividual() {
		return allowForIndividual;
	}

	public void setAllowForIndividual(String allowForIndividual) {
		this.allowForIndividual = allowForIndividual;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getSupportServiceCodeParent() {
		return supportServiceCodeParent;
	}

	public void setSupportServiceCodeParent(String supportServiceCodeParent) {
		this.supportServiceCodeParent = supportServiceCodeParent;
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
