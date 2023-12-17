package net.integrategroup.ignite.persistence.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(schema = "ig_db", name = "vBankCard")
public class VBankCard implements Serializable {

	private static final long serialVersionUID = 520332750363064457L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "BankCardId")
	private Long bankCardId;

	@Column(name = "ParticipantBankDetailsId")
	private Long participantBankDetailsId;

	@Column(name = "IndividualIdMainCardUser")
	private Long individualIdMainCardUser;

	@Column(name = "CardTypeId")
	private Long cardTypeId;

	@Column(name = "CardNumber")
	private String cardNumber;

	@Column(name = "NameOnCard")
	private String nameOnCard;

	@Column(name = "PersonalCard")
	private String personalCard;

	@Column(name = "Description")
	private String description;

	@Column(name = "MainCardUser")
	private String mainCardUser;

	@Column(name = "CardTypeId_Name")
	private String cardTypeId_Name;

	public Long getBankCardId() {
		return bankCardId;
	}

	public void setBankCardId(Long bankCardId) {
		this.bankCardId = bankCardId;
	}

	public Long getParticipantBankDetailsId() {
		return participantBankDetailsId;
	}

	public void setParticipantBankDetailsId(Long participantBankDetailsId) {
		this.participantBankDetailsId = participantBankDetailsId;
	}

	public Long getIndividualIdMainCardUser() {
		return individualIdMainCardUser;
	}

	public void setIndividualIdMainCardUser(Long individualIdMainCardUser) {
		this.individualIdMainCardUser = individualIdMainCardUser;
	}

	public Long getCardTypeId() {
		return cardTypeId;
	}

	public void setCardTypeId(Long cardTypeId) {
		this.cardTypeId = cardTypeId;
	}

	public String getCardNumber() {
		return cardNumber;
	}

	public void setCardNumber(String cardNumber) {
		this.cardNumber = cardNumber;
	}

	public String getNameOnCard() {
		return nameOnCard;
	}

	public void setNameOnCard(String nameOnCard) {
		this.nameOnCard = nameOnCard;
	}

	public String getPersonalCard() {
		return personalCard;
	}

	public void setPersonalCard(String personalCard) {
		this.personalCard = personalCard;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getMainCardUser() {
		return mainCardUser;
	}

	public void setMainCardUser(String mainCardUser) {
		this.mainCardUser = mainCardUser;
	}

	public String getCardTypeId_Name() {
		return cardTypeId_Name;
	}

	public void setCardTypeId_Name(String cardTypeId_Name) {
		this.cardTypeId_Name = cardTypeId_Name;
	}

}