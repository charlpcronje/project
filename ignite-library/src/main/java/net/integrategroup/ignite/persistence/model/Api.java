package net.integrategroup.ignite.persistence.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import net.integrategroup.ignite.utils.IgniteUtils;
import net.integrategroup.ignite.utils.JsonDateSerializer;

@Entity
@Table(schema="ig_db", name="Api")
public class Api implements Serializable {

	private static final long serialVersionUID = -8308787573583691845L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="ApiId")
	private Long apiId;

	@Column(name="ApplicationName")
	private String applicationName;

	@Column(name="ApiKey")
	private String apiKey;

	@Column(name="Secret")
	private String secret;

	@Column(name="ActiveFlag")
	private String activeFlag;

	@JsonSerialize(using=JsonDateSerializer.class)
	@Column(name = "LastUpdateTimestamp")
	private Date lastUpdateTimestamp;

	@Column(name = "LastUpdateUserName")
	private String lastUpdateUserName;

	public Api() {
		//
	}

	public Api(Long apiId, String applicationName, String apiKey, String secret, String activeFlag, Date lastUpdateTimestamp, String lastUpdateUserName) {
		this.apiId = apiId;
		this.applicationName = applicationName;
		this.apiKey = apiKey;
		this.secret = secret;
		this.activeFlag = activeFlag;
		this.lastUpdateTimestamp = lastUpdateTimestamp;
		this.lastUpdateUserName = lastUpdateUserName;
	}

	public Long getApiId() {
		return apiId;
	}

	public void setApiId(Long apiId) {
		this.apiId = apiId;
	}

	public String getApplicationName() {
		return applicationName;
	}

	public void setApplicationName(String applicationName) {
		this.applicationName = applicationName;
	}

	public String getApiKey() {
		return apiKey;
	}

	public void setApiKey(String apiKey) {
		this.apiKey = apiKey;
	}

	public String getSecret() {
		return IgniteUtils.obfuscatePassword(secret);
	}

	public String getNaturalSecret() {
		return secret;
	}

	public void setSecret(String secret) {
		this.secret = secret;
	}

	public String getActiveFlag() {
		return activeFlag;
	}

	public void setActiveFlag(String activeFlag) {
		this.activeFlag = activeFlag;
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
