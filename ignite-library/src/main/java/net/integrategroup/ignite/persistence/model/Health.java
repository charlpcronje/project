package net.integrategroup.ignite.persistence.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import net.integrategroup.ignite.utils.JsonDateSerializer;

@Entity
@Table(schema="ig_db", name="Health")
public class Health implements Serializable {

	private static final long serialVersionUID = -6586535905654761089L;

	@Id
	@Column(name="ComponentName")
	private String componentName;

	@Column(name="Description")
	private String description;

	@Column(name="SuggestedAction")
	private String suggestedAction;

	@Column(name="RagStatus")
	private String ragStatus;

	@JsonSerialize(using=JsonDateSerializer.class)
	@Column(name="LastUpdateTimestamp")
	private Date lastUpdateTimestamp;

	@Column(name="LastUpdateUserName")
	private String lastUpdateUserName;

	public String getComponentName() {
		return componentName;
	}

	public void setComponentName(String componentName) {
		this.componentName = componentName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getSuggestedAction() {
		return suggestedAction;
	}

	public void setSuggestedAction(String suggestedAction) {
		this.suggestedAction = suggestedAction;
	}

	public String getRagStatus() {
		return ragStatus;
	}

	public void setRagStatus(String ragStatus) {
		this.ragStatus = ragStatus;
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
