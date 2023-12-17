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

// @author Ingrid Marais

@Entity
@Table(schema = "ig_db", name = "ProjectParticipantSdRole")
public class ProjectParticipantSdRole implements Serializable {

	private static final long serialVersionUID = -7439879013675615935L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ProjectParticipantSdRoleId")
	private Long projectParticipantSdRoleId;

	@Column(name = "ProjectParticipantId")
	private Long projectParticipantId;

//	@OneToOne(targetEntity = ProjectParticipant.class)
//	@JoinColumn(name = "ProjectParticipantId", referencedColumnName = "ProjectParticipantId", insertable = false, updatable = false)
//	private ProjectParticipant projectParticipant;

	@Column(name = "ProjectSdId")
	private Long projectSdId;

//	@OneToOne(targetEntity = ProjectSd.class)
//	@JoinColumn(name = "ProjectSdId", referencedColumnName = "ProjectSdId", insertable = false, updatable = false)
//	private ProjectSd projectSd;

	@Column(name = "SdRoleId")
	private Long sdRoleId;

//	@OneToOne(targetEntity = SdRole.class)
//	@JoinColumn(name = "SdRoleId", referencedColumnName = "SdRoleId", insertable = false, updatable = false)
//	private SdRole sdRole;

	@JsonSerialize(using = JsonDateSerializer.class)
	@Column(name = "LastUpdateTimestamp")
	private Date lastUpdateTimestamp;

	@Column(name = "LastUpdateUserName")
	private String lastUpdateUserName;

	public Long getProjectParticipantSdRoleId() {
		return projectParticipantSdRoleId;
	}

	public void setProjectParticipantSdRoleId(Long projectParticipantSdRoleId) {
		this.projectParticipantSdRoleId = projectParticipantSdRoleId;
	}

	public Long getProjectParticipantId() {
		return projectParticipantId;
	}

	public void setProjectParticipantId(Long projectParticipantId) {
		this.projectParticipantId = projectParticipantId;
	}

	public Long getProjectSdId() {
		return projectSdId;
	}

	public void setProjectSdId(Long projectSdId) {
		this.projectSdId = projectSdId;
	}

	public Long getSdRoleId() {
		return sdRoleId;
	}

	public void setSdRoleId(Long sdRoleId) {
		this.sdRoleId = sdRoleId;
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