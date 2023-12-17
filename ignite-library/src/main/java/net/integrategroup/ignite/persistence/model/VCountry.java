package net.integrategroup.ignite.persistence.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(schema = "ig_db", name = "vCountry")
public class VCountry implements Serializable {

	private static final long serialVersionUID = -1382772247231875077L;

	@Id
	@Column(name = "CountryId")
	private Long countryId;

	@Column(name = "CountryCode")
	private String countryCode;

	@Column(name = "Name")
	private String name;

	@Column(name = "CountryCodeAndName")
	private String countryCodeAndName;

	@Column(name = "CountryNameAndCode")
	private String countryNameAndCode;

	public Long getCountryId() {
		return countryId;
	}

	public void setCountryId(Long countryId) {
		this.countryId = countryId;
	}

	public String getCountryCode() {
		return countryCode;
	}

	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCountryCodeAndName() {
		return countryCodeAndName;
	}

	public void setCountryCodeAndName(String countryCodeAndName) {
		this.countryCodeAndName = countryCodeAndName;
	}

	public String getCountryNameAndCode() {
		return countryNameAndCode;
	}

	public void setCountryNameAndCode(String countryNameAndCode) {
		this.countryNameAndCode = countryNameAndCode;
	}


}