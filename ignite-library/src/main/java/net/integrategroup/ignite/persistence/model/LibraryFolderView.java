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
@Table(schema="ig_db", name="vLibraryFolders")
public class LibraryFolderView implements Serializable {

	private static final long serialVersionUID = -1862131773863356044L;

	@Id
	@Column(name="libraryFolderId")
	private Long libraryFolderId;

	@Column(name="parentFolderId")
	private Long parentFolderId;

	@Column(name="folderName")
	private String folderName;

	@Column(name="pathName")
	private String pathName;

	@Column(name="description")
	private String description;

	@JsonSerialize(using = JsonDateSerializer.class)
	@Column(name="lastUpdateTimestamp")
	private Date lastUpdateTimestamp;

	@Column(name="lastUpdateUserName")
	private String lastUpdateUserName;

	@Column(name="level")
	private int level;

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public String getRowOrder() {
		return rowOrder;
	}

	public void setRowOrder(String rowOrder) {
		this.rowOrder = rowOrder;
	}

	public int getChildCount() {
		return childCount;
	}

	public void setChildCount(int childCount) {
		this.childCount = childCount;
	}

	@Column(name="rowOrder")
	private String rowOrder;

	@Column(name="childCount")
	private int childCount;

	public LibraryFolderView() {
		// nothing
	}

	public LibraryFolderView(
			Long libraryFolderId, Long parentFolderId, String folderName, String pathName,
			String description, Date lastUpdateTimestamp, String lastUpdateUserName, int level,
			String rowOrder, int childCount) {
		this.libraryFolderId = libraryFolderId;
		this.parentFolderId = parentFolderId;
		this.folderName = folderName;
		this.pathName = pathName;
		this.description = description;
		this.lastUpdateTimestamp = lastUpdateTimestamp;
		this.lastUpdateUserName = lastUpdateUserName;
		this.level = level;
		this.rowOrder = rowOrder;
		this.childCount = childCount;
	}

	public Long getLibraryFolderId() {
		return libraryFolderId;
	}

	public void setLibraryFolderId(Long libraryFolderId) {
		this.libraryFolderId = libraryFolderId;
	}

	public Long getParentFolderId() {
		return parentFolderId;
	}

	public void setParentFolderId(Long parentFolderId) {
		this.parentFolderId = parentFolderId;
	}

	public String getFolderName() {
		return folderName;
	}

	public void setFolderName(String folderName) {
		this.folderName = folderName;
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

	public String getPathName() {
		return pathName;
	}

	public void setPathName(String pathName) {
		this.pathName = pathName;
	}

}
