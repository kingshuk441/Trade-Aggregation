package com.osttra.capstone.tradeaggregation.responsebody;

import java.util.Arrays;

public class InstitutionBody {
	private int id;
	private String institutionName;
	private int[] parties;

	public InstitutionBody() {
	}

	public InstitutionBody(int id, String institutionName, int[] parties) {
		super();
		this.id = id;
		this.institutionName = institutionName;
		this.parties = parties;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getInstitutionName() {
		return institutionName;
	}

	public void setInstitutionName(String institutionName) {
		this.institutionName = institutionName;
	}

	public int[] getParties() {
		return parties;
	}

	public void setParties(int[] parties) {
		this.parties = parties;
	}

	@Override
	public String toString() {
		return "InstitutionBody [id=" + id + ", institutionName=" + institutionName + ", parties="
				+ Arrays.toString(parties) + "]";
	}

}
