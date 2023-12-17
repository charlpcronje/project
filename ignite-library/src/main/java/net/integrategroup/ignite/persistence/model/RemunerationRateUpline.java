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

//  @author Ingrid Marais

@Entity
@Table(schema = "ig_db", name = "RemunerationRateUpline")
public class RemunerationRateUpline implements Serializable {

	private static final long serialVersionUID = 2393378595891588754L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "RemunerationRateUplineId")
	private Long remunerationRateUplineId;

	@Column(name = "AgreementBetweenParticipantsId")
	private Long agreementBetweenParticipantsId;

	@OneToOne(targetEntity = AgreementBetweenParticipants.class)
	@JoinColumn(name = "AgreementBetweenParticipantsId", referencedColumnName = "AgreementBetweenParticipantsId", nullable = true, insertable = false, updatable = false)
	private AgreementBetweenParticipants agreementBetweenParticipants;

	@Column(name = "ProjectParticipantSdRoleIdForRate")
	private Long projectParticipantSdRoleIdForRate;

	@OneToOne(targetEntity = ProjectParticipantSdRole.class)
	@JoinColumn(name = "ProjectParticipantSdRoleIdForRate", referencedColumnName = "ProjectParticipantSdRoleId", nullable = true, insertable = false, updatable = false)
	private ProjectParticipantSdRole projectParticipantSd;

	@Column(name = "ParticipantIdIndividual")
	private Long participantIdIndividual;

	@OneToOne(targetEntity = Participant.class)
	@JoinColumn(name = "ParticipantIdIndividual", referencedColumnName = "ParticipantId", nullable = true, insertable = false, updatable = false)
	private Participant participantIndividual;

	@Column(name = "ProjBasedRemunTypeId")
	private Long projBasedRemunTypeId;

	@OneToOne(targetEntity = ProjBasedRemunType.class)
	@JoinColumn(name = "ProjBasedRemunTypeId", referencedColumnName = "ProjBasedRemunTypeId", nullable = true, insertable = false, updatable = false)
	private ProjBasedRemunType projBasedRemunType;

	@Column(name = "RatePerUnit")
	private Double ratePerUnit;

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

	public Long getRemunerationRateUplineId() {
		return remunerationRateUplineId;
	}

	public void setRemunerationRateUplineId(Long remunerationRateUplineId) {
		this.remunerationRateUplineId = remunerationRateUplineId;
	}

	public Long getAgreementBetweenParticipantsId() {
		return agreementBetweenParticipantsId;
	}

	public void setAgreementBetweenParticipantsId(Long agreementBetweenParticipantsId) {
		this.agreementBetweenParticipantsId = agreementBetweenParticipantsId;
	}

	public AgreementBetweenParticipants getAgreementBetweenParticipants() {
		return agreementBetweenParticipants;
	}

	public void setAgreementBetweenParticipants(AgreementBetweenParticipants agreementBetweenParticipants) {
		this.agreementBetweenParticipants = agreementBetweenParticipants;
	}

	public Long getProjectParticipantSdRoleIdForRate() {
		return projectParticipantSdRoleIdForRate;
	}

	public void setProjectParticipantSdRoleIdForRate(Long projectParticipantSdRoleIdForRate) {
		this.projectParticipantSdRoleIdForRate = projectParticipantSdRoleIdForRate;
	}

	public ProjectParticipantSdRole getProjectParticipantSd() {
		return projectParticipantSd;
	}

	public void setProjectParticipantSd(ProjectParticipantSdRole projectParticipantSd) {
		this.projectParticipantSd = projectParticipantSd;
	}

	public Long getParticipantIdIndividual() {
		return participantIdIndividual;
	}

	public void setParticipantIdIndividual(Long participantIdIndividual) {
		this.participantIdIndividual = participantIdIndividual;
	}

	public Participant getParticipantIndividual() {
		return participantIndividual;
	}

	public void setParticipantIndividual(Participant participantIndividual) {
		this.participantIndividual = participantIndividual;
	}

	public Long getProjBasedRemunTypeId() {
		return projBasedRemunTypeId;
	}

	public void setProjBasedRemunTypeId(Long projBasedRemunTypeId) {
		this.projBasedRemunTypeId = projBasedRemunTypeId;
	}

	public ProjBasedRemunType getProjBasedRemunType() {
		return projBasedRemunType;
	}

	public void setProjBasedRemunType(ProjBasedRemunType projBasedRemunType) {
		this.projBasedRemunType = projBasedRemunType;
	}

	public Double getRatePerUnit() {
		return ratePerUnit;
	}

	public void setRatePerUnit(Double ratePerUnit) {
		this.ratePerUnit = ratePerUnit;
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
