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

@Entity
@Table(schema="ig_db", name="LibraryFolder")
public class LibraryFolder implements Serializable {

	private static final long serialVersionUID = -5050349895044810474L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="libraryFolderId")
	private Long libraryFolderId;

	@Column(name="parentFolderId")
	private Long parentFolderId;

	@Column(name="folderName")
	private String folderName;

	@Column(name="description")
	private String description;

	@JsonSerialize(using = JsonDateSerializer.class)
	@Column(name="lastUpdateTimestamp")
	private Date lastUpdateTimestamp;

	@Column(name="lastUpdateUserName")
	private String lastUpdateUserName;

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

}
