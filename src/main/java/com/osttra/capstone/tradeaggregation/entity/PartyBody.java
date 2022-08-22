package com.osttra.capstone.tradeaggregation.entity;

public class PartyBody {
	private int id;
	private String partyName;
	private String partyFullName;
	private int institution;

	public PartyBody() {

	}

	public PartyBody(int id, String partyName, String partyFullName, int institution) {
		super();
		this.id = id;
		this.partyName = partyName;
		this.institution = institution;
		this.partyFullName = partyFullName;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getPartyName() {
		return partyName;
	}

	public void setPartyName(String partyName) {
		this.partyName = partyName;
	}

	public int getInstitution() {
		return institution;
	}

	public void setInstitution(int institution) {
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
		return "PartyBody [id=" + id + ", partyName=" + partyName + ", partyFullName=" + partyFullName
				+ ", institution=" + institution + "]";
	}

}
