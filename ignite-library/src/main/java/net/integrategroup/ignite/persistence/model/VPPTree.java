package net.integrategroup.ignite.persistence.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import net.integrategroup.ignite.utils.JsonDateSerializer;

// @author Ingrid Marais
// class name is ServiceD as the imports clashes with import org.springframework.stereotype.Service; in the ServiceImpl

@Entity
@Table(schema = "ig_db", name = "vPPTree")
public class VPPTree implements Serializable {

	private static final long serialVersionUID = -2106447247423794209L;



	@Id
	@Column(name = "ProjectParticipantIdContracted")
	private Long projectParticipantIdContracted;

	@Column(name = "ProjectId")
	private Long projectId;

	@Column(name = "ProjectName")
	private String projectName;

	@Column(name = "Level")
	private Integer level;

	@Column(name = "ProjectParticipantIdContracting")
	private Long projectParticipantIdContracting;

	@OneToMany(targetEntity = VPPTree.class, fetch = FetchType.EAGER)
	@JoinColumn(name = "ProjectParticipantIdContracting", referencedColumnName = "ProjectParticipantIdContracted", updatable = false, insertable = false, nullable = true)
	private List<VPPTree> children;

	@Column(name = "ParticipantIdContracting")
	private Long participantIdContracting;

	@Column(name = "ParticipantNameContracting")
	private String participantNameContracting;

	@Column(name = "IndividualIdContracting")
	private Long individualIdContracting;

	@Column(name = "IndividualNameContracting")
	private String individualNameContracting;

	@Column(name = "ParticipantIdContracted")
	private Long participantIdContracted;

	@Column(name = "ParticipantNameContracted")
	private String participantNameContracted;

	@Column(name = "IndividualIdContracted")
	private Long individualIdContracted;

	@Column(name = "IndividualNameContracted")
	private String individualNameContracted;

	@JsonSerialize(using = JsonDateSerializer.class)
	@Column(name = "ContractedStartDate")
	private Date contractedStartDate;

	@JsonSerialize(using = JsonDateSerializer.class)
	@Column(name = "ContractedEndDate")
	private Date contractedEndDate;

	public Date getContractedStartDate() {
		return contractedStartDate;
	}

	public void setContractedStartDate(Date contractedStartDate) {
		this.contractedStartDate = contractedStartDate;
	}

	public Date getContractedEndDate() {
		return contractedEndDate;
	}

	public void setContractedEndDate(Date contractedEndDate) {
		this.contractedEndDate = contractedEndDate;
	}

	@Column(name = "AnyChildren")
	private String anyChildren;

	@Column(name = "IsIndividual")
	private String isIndividual;

	public Long getProjectParticipantIdContracted() {
		return projectParticipantIdContracted;
	}

	public void setProjectParticipantIdContracted(Long projectParticipantIdContracted) {
		this.projectParticipantIdContracted = projectParticipantIdContracted;
	}

	public Long getProjectId() {
		return projectId;
	}

	public void setProjectId(Long projectId) {
		this.projectId = projectId;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public Integer getLevel() {
		return level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}

	public Long getProjectParticipantIdContracting() {
		return projectParticipantIdContracting;
	}

	public void setProjectParticipantIdContracting(Long projectParticipantIdContracting) {
		this.projectParticipantIdContracting = projectParticipantIdContracting;
	}

	public List<VPPTree> getChildren() {
		return children;
	}

	public void setChildren(List<VPPTree> children) {
		this.children = children;
	}

	public Long getParticipantIdContracting() {
		return participantIdContracting;
	}

	public void setParticipantIdContracting(Long participantIdContracting) {
		this.participantIdContracting = participantIdContracting;
	}

	public String getParticipantNameContracting() {
		return participantNameContracting;
	}

	public void setParticipantNameContracting(String participantNameContracting) {
		this.participantNameContracting = participantNameContracting;
	}

	public Long getIndividualIdContracting() {
		return individualIdContracting;
	}

	public void setIndividualIdContracting(Long individualIdContracting) {
		this.individualIdContracting = individualIdContracting;
	}

	public String getIndividualNameContracting() {
		return individualNameContracting;
	}

	public void setIndividualNameContracting(String individualNameContracting) {
		this.individualNameContracting = individualNameContracting;
	}

	public Long getParticipantIdContracted() {
		return participantIdContracted;
	}

	public void setParticipantIdContracted(Long participantIdContracted) {
		this.participantIdContracted = participantIdContracted;
	}

	public String getParticipantNameContracted() {
		return participantNameContracted;
	}

	public void setParticipantNameContracted(String participantNameContracted) {
		this.participantNameContracted = participantNameContracted;
	}

	public Long getIndividualIdContracted() {
		return individualIdContracted;
	}

	public void setIndividualIdContracted(Long individualIdContracted) {
		this.individualIdContracted = individualIdContracted;
	}

	public String getIndividualNameContracted() {
		return individualNameContracted;
	}

	public void setIndividualNameContracted(String individualNameContracted) {
		this.individualNameContracted = individualNameContracted;
	}

	public String getAnyChildren() {
		return anyChildren;
	}

	public void setAnyChildren(String anyChildren) {
		this.anyChildren = anyChildren;
	}

	public String getIsIndividual() {
		return isIndividual;
	}

	public void setIsIndividual(String isIndividual) {
		this.isIndividual = isIndividual;
	}


	}
