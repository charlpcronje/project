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

/** @author Generated by Johannes Marais (JohannesSQL v7.7) **/
/** ******* ********* ** 2023-10-25 18:43:39 ******** *c* **/

@Entity
@Table(schema = "ig_db", name = "City")
public class City implements Serializable {


    private static final long serialVersionUID = 213023282988508171L; /** ID was Generated by Johannes **/


    @Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "CityId")
	private Long cityId;

	@Column(name = "CountryId")
	private Long countryId;

/***	@OneToOne(targetEntity = Country.class)
	@JoinColumn(name = "CountryId", referencedColumnName = "CountryId", nullable = true, insertable = false, updatable = false)
	private Country country; ***/

	@Column(name = "ProvinceId")
	private Long provinceId;

/***	@OneToOne(targetEntity = Province.class)
	@JoinColumn(name = "ProvinceId", referencedColumnName = "ProvinceId", nullable = true, insertable = false, updatable = false)
	private Province province; ***/

	@Column(name = "ProvinceCode")
	private String provinceCode;

/***	@OneToOne(targetEntity = Province.class)
	@JoinColumn(name = "ProvinceCode", referencedColumnName = "ProvinceCode", nullable = true, insertable = false, updatable = false)
	private Province province; ***/

	@Column(name = "Name")
	private String name;

	@Column(name = "Latitude")
	private Double latitude;

	@Column(name = "Longitude")
	private Double longitude;

	@JsonSerialize(using = JsonDateSerializer.class)
	@Column(name = "LastUpdateTimestamp")
	private Date lastUpdateTimestamp;

	@Column(name = "LastUpdateUserName")
	private String lastUpdateUserName;




	public Long getCityId() {
		return cityId;
	}

	public void setCityId(Long cityId) {
		this.cityId = cityId;
	}

	public Long getCountryId() {
		return countryId;
	}

	public void setCountryId(Long countryId) {
		this.countryId = countryId;
	}

/***	public Country getCountry() {
		return country;
	}

	public void setCountry(Country country) {
		this.country = country;
	} ***/

	public Long getProvinceId() {
		return provinceId;
	}

	public void setProvinceId(Long provinceId) {
		this.provinceId = provinceId;
	}

/***	public Province getProvince() {
		return province;
	}

	public void setProvince(Province province) {
		this.province = province;
	} ***/

	public String getProvinceCode() {
		return provinceCode;
	}

	public void setProvinceCode(String provinceCode) {
		this.provinceCode = provinceCode;
	}

/***	public Province getProvince() {
		return province;
	}

	public void setProvince(Province province) {
		this.province = province;
	} ***/

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

/**  javascript      vir grid population        (jsStr)

	var columnsArray = [
		 { data: "cityId" }                   //0 MySql-TableName: City
		,{ data: "countryId" }                //1
		,{ data: "provinceId" }               //2
		,{ data: "provinceCode" }             //3
		,{ data: "name" }                     //4
		,{ data: "latitude" }                 //5
		,{ data: "longitude" }                //6
		,{ data: "lastUpdateTimestamp" }      //7
		,{ data: "lastUpdateUserName" }       //8
	];

	var columnDefsArray = [
		{
			visible: false,
			targets: [0, 1, 2, 3, 4, 8]
		}
		,{       //for Date columns
			render: function (data, type, row) {
				var html = data;
				if (type == "display") {
					html = timestampToString(data, false);
				}
				return html;
			},
			targets: [7]
		}
		,{        //for Amounts
			render: function (data, type, row) {
				var html = data;
				if (type == "display") {
					html = valueToRand(data);
				}
				return html;
			},
			className: "dt-right",
			targets: [5, 6]
		}
	];







/**  javascript      vir grid SelectFromGridDialog        (js1Str)

	var columns = [
		{ data: "cityId", name: "CityId" }                                 //0 MySql-TableName: City
		,{ data: "countryId", name: "CountryId" }                           //1
		,{ data: "provinceId", name: "ProvinceId" }                         //2
		,{ data: "provinceCode", name: "ProvinceCode" }                     //3
		,{ data: "name", name: "Name" }                                     //4
		,{ data: "latitude", name: "Latitude" }                             //5
		,{ data: "longitude", name: "Longitude" }                           //6
		,{ data: "lastUpdateTimestamp", name: "LastUpdateTimestamp" }       //7
		,{ data: "lastUpdateUserName", name: "LastUpdateUserName" }         //8
	];

	var columnDefs = [
		{
			visible: false,
			targets: [0, 1, 2, 3, 4, 8]
		}
		,{       //for Date columns
			render: function (data, type, row) {
				var html = data;
				if (type == "display") {
					html = timestampToString(data, false);
				}
				return html;
			},
			targets: [7]
		}
		,{        //for Amounts
			render: function (data, type, row) {
				var html = data;
				if (type == "display") {
					html = valueToRand(data);
				}
				return html;
			},
			className: "dt-right",
			targets: [5, 6]
		}
	];







Vir 'n Save Data function                        (js2Str)

	$("#cLatitude").val($("#cLatitude").val().replace('R', '').replace(/ /g, ''));  //remove spaces (thousand separator) and R-symbol
	$("#cLongitude").val($("#cLongitude").val().replace('R', '').replace(/ /g, ''));  //remove spaces (thousand separator) and R-symbol

	var postUrl = "/rest/ignite/v1/city/new";
	var postData = {
		cityId : $("#cCityId").val()                   //0 MySql-TableName: City
		,countryId : $("#cCountryId").val()                                        //1
		,provinceId : $("#cProvinceId").val()                                      //2
		,provinceCode : $("#cProvinceCode").val()                                  //3
		,name : $("#cName").val()                                                  //4
		,latitude : $("#cLatitude").val()                                          //5
		,longitude : $("#cLongitude").val()                                        //6
		,lastUpdateTimestamp : getMsFromDatePicker("cLastUpdateTimestamp")         //7
	};

	var errors = "";






Vir 'n Populate Data function                                                  (js3Str)

		//  MySql-TableName: City										   (js3Str)
		$("#cCityId").val(data.cityId);                                 //0
		$("#cCountryId").val(data.countryId);                           //1
		populateSelect("cCountryId",                                      //name of html select element that will be populated
				"/rest/ignite/v1/country/find-all",                       //url
				"countryId",                                              //the value that must be saved (ReferencedColumnName)
				"name",                                                   //shown to user (usually a Name column)
				data.countryId,                                           //The selected one, if there is one
				true,                                                     //addEmpty, boolean: should we add empty object at the top
				null                                                      //completeMethod
		);

		$("#cProvinceId").val(data.provinceId);                         //2
		populateSelect("cProvinceId",                                     //name of html select element that will be populated
				"/rest/ignite/v1/province/find-all",                      //url
				"provinceId",                                             //the value that must be saved (ReferencedColumnName)
				"name",                                                   //shown to user (usually a Name column)
				data.provinceId,                                          //The selected one, if there is one
				true,                                                     //addEmpty, boolean: should we add empty object at the top
				null                                                      //completeMethod
		);

		$("#cProvinceCode").val(data.provinceCode);                     //3
		populateSelect("cProvinceCode",                                   //name of html select element that will be populated
				"/rest/ignite/v1/province/find-all",                      //url
				"provinceCode",                                           //the value that must be saved (ReferencedColumnName)
				"name",                                                   //shown to user (usually a Name column)
				data.provinceCode,                                        //The selected one, if there is one
				true,                                                     //addEmpty, boolean: should we add empty object at the top
				null                                                      //completeMethod
		);

		$("#cName").val(data.name);                                     //4
		$("#cLatitude").val((data.latitude != null) ? "R " + (data.latitude).toFixed(2).replace(/\d(?=(\d{3})+\.)/g, '$& ') : "");  // Sit spasie in as thousand separator en 2 desimale plekke                             //5
		$("#cLongitude").val((data.longitude != null) ? "R " + (data.longitude).toFixed(2).replace(/\d(?=(\d{3})+\.)/g, '$& ') : "");  // Sit spasie in as thousand separator en 2 desimale plekke                           //6
		$("#cLastUpdateTimestamp").datepicker("setDate", data.lastUpdateTimestamp == null? timestampToString(new Date(), false) : new Date(data.lastUpdateTimestamp));       //7






/**  HTML  om 'n grid te populate
												<th>CityId</th>                      <!--0  MySql-TableName: City-->
												<th>CountryId</th>                   <!--1  CountryId-->
												<th>ProvinceId</th>                  <!--2  ProvinceId-->
												<th>ProvinceCode</th>                <!--3  ProvinceCode-->
												<th>Name</th>                        <!--4  Name-->
												<th>Latitude</th>                    <!--5  Latitude-->
												<th>Longitude</th>                   <!--6  Longitude-->
												<th>Last Update Timestamp</th>       <!--7  LastUpdateTimestamp-->
												<th>LastUpdateUserName</th>          <!--8  LastUpdateUserName-->

*/