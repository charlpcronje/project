package net.integrategroup.ignite.persistence.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(schema="ig_db", name="vUiComponentMenu")
public class UiComponentView implements Serializable {

	private static final long serialVersionUID = 7046638302862688146L;

	@Id
	@Column(name="UiComponentId")
	private Long uiComponentId;

	@Column(name="UiComponentTitle")
	private String uiComponentTitle;

	@Column(name="UiComponentLink")
	private String uiComponentLink;

	@Column(name="OrderNo")
	private Integer orderNo;

	@Column(name="ParentUiComponentId")
	private Long parentUiComponentId;

	@OneToOne(targetEntity = UiComponent.class, fetch = FetchType.EAGER)
	@JoinColumn(name = "ParentUiComponentId", referencedColumnName = "UiComponentId", updatable = false, insertable = false)
	private UiComponent uiComponentParent;

	@Column(name="ActiveInd")
	private String activeInd;

	@Column(name="IconClassName")
	private String iconClassName;

	@Column(name="PermissionCodeRequired")
	private String permissionCodeRequired;

	@Column(name="RowOrderNo")
	private Integer rowOrderNo;

	@Column(name="MenuLevel")
	private Integer menuLevel;

	@Column(name="SubitemCount")
	private Integer subitemCount;




	public UiComponent getUiComponentParent() {
		return uiComponentParent;
	}

	public void setUiComponentParent(UiComponent uiComponentParent) {
		this.uiComponentParent = uiComponentParent;
	}

	public Long getUiComponentId() {
		return uiComponentId;
	}

	public void setUiComponentId(Long uiComponentId) {
		this.uiComponentId = uiComponentId;
	}

	public String getUiComponentTitle() {
		return uiComponentTitle;
	}

	public void setUiComponentTitle(String uiComponentTitle) {
		this.uiComponentTitle = uiComponentTitle;
	}

	public String getUiComponentLink() {
		return uiComponentLink;
	}

	public void setUiComponentLink(String uiComponentLink) {
		this.uiComponentLink = uiComponentLink;
	}

	public Integer getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(Integer orderNo) {
		this.orderNo = orderNo;
	}

	public Long getParentUiComponentId() {
		return parentUiComponentId;
	}

	public void setParentUiComponentId(Long parentUiComponentId) {
		this.parentUiComponentId = parentUiComponentId;
	}

	public String getActiveInd() {
		return activeInd;
	}

	public void setActiveInd(String activeInd) {
		this.activeInd = activeInd;
	}


	public String getPermissionCodeRequired() {
		return permissionCodeRequired;
	}

	public void setPermissionCodeRequired(String permissionCodeRequired) {
		this.permissionCodeRequired = permissionCodeRequired;
	}

	public Integer getRowOrderNo() {
		return rowOrderNo;
	}

	public void setRowOrderNo(Integer rowOrderNo) {
		this.rowOrderNo = rowOrderNo;
	}

	public Integer getMenuLevel() {
		return menuLevel;
	}

	public void setMenuLevel(Integer menuLevel) {
		this.menuLevel = menuLevel;
	}

	public Integer getSubitemCount() {
		return subitemCount;
	}

	public void setSubitemCount(Integer subitemCount) {
		this.subitemCount = subitemCount;
	}

	public String getIconClassName() {
		return iconClassName;
	}

	public void setIconClassName(String iconClassName) {
		this.iconClassName = iconClassName;
	}


}
