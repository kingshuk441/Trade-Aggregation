package com.osttra.capstone.tradeaggregation.entity;

public class PartyBody {
	private int id;
	private String partyName;
	private int institution;

	public PartyBody() {

	}

	public PartyBody(int id, String partyName, int institution) {
		super();
		this.id = id;
		this.partyName = partyName;
		this.institution = institution;
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

	@Override
	public String toString() {
		return "PartyBody [id=" + id + ", partyName=" + partyName + ", institution=" + institution + "]";
	}

}
