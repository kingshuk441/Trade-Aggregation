package com.osttra.capstone.tradeaggregation.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;

@ApiModel
@Api(value = "Party")
@Entity
@Table(name = "party")
public class Party {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "party_id")
	private int partyId;
	@Column(name = "party_name")
	private String partyName;

	@Column(name = "party_full_name")
	private String partyFullName;

	@OneToOne
	@JoinColumn(name = "i_id")
	@JsonBackReference
	private Institution institution;

	public Party() {

	}

	public Party(String partyName, String partyFullName) {
		super();
		this.partyName = partyName;
		this.partyFullName = partyFullName;
	}

	public int getPartyId() {
		return partyId;
	}

	public void setPartyId(int partyId) {
		this.partyId = partyId;
	}

	public String getPartyName() {
		return partyName;
	}

	public void setPartyName(String partyName) {
		this.partyName = partyName;
	}

	public Institution getInstitution() {
		return institution;
	}

	public void setInstitution(Institution institution) {
		this.institution = institution;
	}

	public String getPartyFullName() {
		return partyFullName;
	}

	public void setPartyFullName(String partyFullName) {
		this.partyFullName = partyFullName;
	}

	@Override
	public String toString() {
		return "Party [partyId=" + partyId + ", partyName=" + partyName + ", institutionName="
				+ institution.getInstitutionName() + ", institutionId=" + institution.getInstitutionId()
				+ ", partyFullName=" + partyFullName + "]";
	}

}