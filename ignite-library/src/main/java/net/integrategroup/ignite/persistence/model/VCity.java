package net.integrategroup.ignite.persistence.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(schema = "ig_db", name = "vCity")
public class VCity implements Serializable {

	private static final long serialVersionUID = -2826565663885197989L;

	@Id
	@Column(name = "CityId")
	private Long cityId;

	@Column(name = "Name")
	private String name;

	@Column(name = "Latitude")
	private Double latitude;

	@Column(name = "Longitude")
	private Double longitude;

	@Column(name = "ProvinceId")
	private Long provinceId;

	@Column(name = "ProvinceCode")
	private String provinceCode;

	@Column(name = "ProvinceId_Name")
	private String provinceId_Name;

	@Column(name = "ProvinceCodeAndName")
	private String provinceCodeAndName;

	@Column(name = "ProvinceNameAndCode")
	private String provinceNameAndCode;

	@Column(name = "CountryId")
	private Long countryId;

	@Column(name = "CountryCode")
	private String countryCode;

	@Column(name = "CountryId_Name")
	private String countryId_Name;

	@Column(name = "CountryCodeAndName")
	private String countryCodeAndName;

	@Column(name = "CountryNameAndCode")
	private String countryNameAndCode;

	public Long getCityId() {
		return cityId;
	}

	public void setCityId(Long cityId) {
		this.cityId = cityId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Double getLatitude() {
		return latitude;
	}

	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}

	public Double getLongitude() {
		return longitude;
	}

	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}

	public Long getProvinceId() {
		return provinceId;
	}

	public void setProvinceId(Long provinceId) {
		this.provinceId = provinceId;
	}

	public String getProvinceCode() {
		return provinceCode;
	}

	public void setProvinceCode(String provinceCode) {
		this.provinceCode = provinceCode;
	}

	public String getProvinceId_Name() {
		return provinceId_Name;
	}

	public void setProvinceId_Name(String provinceId_Name) {
		this.provinceId_Name = provinceId_Name;
	}

	public String getProvinceCodeAndName() {
		return provinceCodeAndName;
	}

	public void setProvinceCodeAndName(String provinceCodeAndName) {
		this.provinceCodeAndName = provinceCodeAndName;
	}

	public String getProvinceNameAndCode() {
		return provinceNameAndCode;
	}

	public void setProvinceNameAndCode(String provinceNameAndCode) {
		this.provinceNameAndCode = provinceNameAndCode;
	}

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

	public String getCountryId_Name() {
		return countryId_Name;
	}

	public void setCountryId_Name(String countryId_Name) {
		this.countryId_Name = countryId_Name;
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