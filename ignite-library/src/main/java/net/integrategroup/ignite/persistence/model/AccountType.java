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

import net.integrategroup.ignite.utils.JsonDateSerializer;

/** @author Generated by Johannes Marais (JohannesSQL) **/
/** 2023-03-19 14:07:37 **/

@Entity
@Table(schema = "ig_db", name = "AccountType")
public class AccountType implements Serializable {


    /**
	 *
	 */
	private static final long serialVersionUID = -840568701651879709L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "AccountTypeId")
	private Long accountTypeId;

	@Column(name = "Name")
	private String name;

	@JsonSerialize(using = JsonDateSerializer.class)
	@Column(name = "LastUpdateTimestamp")
	private Date lastUpdateTimestamp;

	@Column(name = "LastUpdateUserName")
	private String lastUpdateUserName;




	public Long getAccountTypeId() {
		return accountTypeId;
	}

	public void setAccountTypeId(Long accountTypeId) {
		this.accountTypeId = accountTypeId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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