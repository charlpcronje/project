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

/*
 * Representation of the LibraryFile database table, note that we do not include the fileContent column here
 * as it could cause the server to run out of memory storing all the blobs in memory.  We will need to stream
 * this data out with an as needed approach
 */

@Entity
@Table(schema="ig_db", name="LibraryFile")
public class LibraryFile implements Serializable {

	private static final long serialVersionUID = 6404520587257107797L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="libraryFileId")
	private Long libraryFileId;

	@Column(name="libraryFolderId")
	private Long libraryFolderId;

	@Column(name="fileName")
	private String fileName;

	@Column(name="filesize")
	private Long filesize;

	@Column(name="description")
	private String description;

	@JsonSerialize(using = JsonDateSerializer.class)
	@Column(name="lastUpdateTimestamp")
	private Date lastUpdateTimestamp;

	@Column(name="lastUpdateUserName")
	private String lastUpdateUserName;

	public Long getLibraryFileId() {
		return libraryFileId;
	}

	public void setLibraryFileId(Long libraryFileId) {
		this.libraryFileId = libraryFileId;
	}

	public Long getLibraryFolderId() {
		return libraryFolderId;
	}

	public void setLibraryFolderId(Long libraryFolderId) {
		this.libraryFolderId = libraryFolderId;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public Long getFilesize() {
		return filesize;
	}

	public void setFilesize(Long filesize) {
		this.filesize = filesize;
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
