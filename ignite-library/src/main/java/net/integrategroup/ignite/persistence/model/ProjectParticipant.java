package net.integrategroup.ignite.persistence.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import net.integrategroup.ignite.utils.JsonDateSerializer;

/**
 * @author Ingrid Marais
 *
 */
@Entity
@Table(schema = "ig_db", name = "ProjectParticipant")
public class ProjectParticipant implements Serializable {

	private static final long serialVersionUID = 3510185492037756280L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ProjectParticipantId")
	private Long projectParticipantId;

	@Column(name = "ProjectId")
	private Long projectId;

	@OneToOne(targetEntity = Project.class)
	@JoinColumn(name = "ProjectId", referencedColumnName = "ProjectId", insertable = false, updatable = false)
	private Project project;

	@Column(name = "ParticipantId")
	private Long participantId;

	@OneToOne(targetEntity = Participant.class)
	@JoinColumn(name = "ParticipantId", referencedColumnName = "ParticipantId", nullable = true, insertable = false, updatable = false)
	private Participant participant;

	@Column(name = "ProjectParticipantIdAboveMe")
	private Long projectParticipantIdAboveMe;

	@OneToOne(targetEntity = ProjectParticipant.class)
	@JoinColumn(name = "ProjectParticipantIdAboveMe", referencedColumnName = "ProjectParticipantId", nullable = true, insertable = false, updatable = false)
	private ProjectParticipant projectParticipantAboveMe;

	@Column(name = "Description")
	private String description;

	@JsonSerialize(using = JsonDateSerializer.class)
	@Column(name = "StartDate")
	private Date startDate;

	@JsonSerialize(using = JsonDateSerializer.class)
	@Column(name = "EndDate")
	private Date endDate;

	@JsonSerialize(using = JsonDateSerializer.class)
	@Column(name = "LastUpdateTimestamp")
	private Date lastUpdateTimestamp;

	@Column(name = "LastUpdateUserName")
	private String lastUpdateUserName;

	public Long getProjectParticipantId() {
		return projectParticipantId;
	}

	public void setProjectParticipantId(Long projectParticipantId) {
		this.projectParticipantId = projectParticipantId;
	}

	public Long getProjectId() {
		return projectId;
	}

	public void setProjectId(Long projectId) {
		this.projectId = projectId;
	}

	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}

	public Long getParticipantId() {
		return participantId;
	}

	public void setParticipantId(Long participantId) {
		this.participantId = participantId;
	}

	public Participant getParticipant() {
		return participant;
	}

	public void setParticipant(Participant participant) {
		this.participant = participant;
	}

	public Long getProjectParticipantIdAboveMe() {
		return projectParticipantIdAboveMe;
	}

	public void setProjectParticipantIdAboveMe(Long projectParticipantIdAboveMe) {
		this.projectParticipantIdAboveMe = projectParticipantIdAboveMe;
	}

	public ProjectParticipant getProjectParticipantAboveMe() {
		return projectParticipantAboveMe;
	}

	public void setProjectParticipantAboveMe(ProjectParticipant projectParticipantAboveMe) {
		this.projectParticipantAboveMe = projectParticipantAboveMe;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
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
