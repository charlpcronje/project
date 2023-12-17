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
@Table(schema = "ig_db", name = "ProjectExpenseFile")
public class ProjectExpenseFile implements Serializable{

	private static final long serialVersionUID = -5211039781169666312L;

	public Long getProjectExpenseFileId() {
		return projectExpenseFileId;
	}

	public void setProjectExpenseFileId(Long projectExpenseFileId) {
		this.projectExpenseFileId = projectExpenseFileId;
	}

	public Long getProjectExpenseId() {
		return projectExpenseId;
	}

	public void setProjectExpenseId(Long projectExpenseId) {
		this.projectExpenseId = projectExpenseId;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getOriginalFileName() {
		return originalFileName;
	}

	public void setOriginalFileName(String originalFileName) {
		this.originalFileName = originalFileName;
	}

	public String getFileType() {
		return fileType;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}

	public Long getFileSize() {
		return fileSize;
	}

	public void setFileSize(Long fileSize) {
		this.fileSize = fileSize;
	}

	public Date getUploadDate() {
		return uploadDate;
	}

	public void setUploadDate(Date uploadDate) {
		this.uploadDate = uploadDate;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getLastUpdateUserName() {
		return lastUpdateUserName;
	}

	public void setLastUpdateUserName(String lastUpdateUserName) {
		this.lastUpdateUserName = lastUpdateUserName;
	}

	public Date getLastUpdateTimestamp() {
		return lastUpdateTimestamp;
	}

	public void setLastUpdateTimestamp(Date lastUpdateTimestamp) {
		this.lastUpdateTimestamp = lastUpdateTimestamp;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ProjectExpenseFileId")
	private Long projectExpenseFileId;

	@Column(name = "ProjectExpenseId")
	private Long projectExpenseId;

	@Column(name = "FileName")
	private String fileName;

	@Column(name = "OriginalFileName")
	private String originalFileName;

	@Column(name = "FileType")
	private String fileType;

	@Column(name = "FileSize")
	private Long fileSize;

	@JsonSerialize(using = JsonDateSerializer.class)
	@Column(name = "UploadDate")
	private Date uploadDate;

	@Column(name = "Description")
	private String description;

	@Column(name = "LastUpdateUserName")
	private String lastUpdateUserName;

	@JsonSerialize(using = JsonDateSerializer.class)
	@Column(name = "LastUpdateTimestamp")
	private Date lastUpdateTimestamp;
}
