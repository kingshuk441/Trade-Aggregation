package com.osttra.capstone.tradeaggregation.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name = "institution")
public class Institution {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "institution_id")
	private int institutionId;
	@Column(name = "institution_name")
	private String institutionName;

	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "i_id")
	@JsonManagedReference
	private List<Party> allParties;

	public Institution() {

	}

	public Institution(String institutionName) {
		super();
		this.institutionName = institutionName;
	}

	public int getInstitutionId() {
		return institutionId;
	}

	public void setInstitutionId(int institutionId) {
		this.institutionId = institutionId;
	}

	public String getInstitutionName() {
		return institutionName;
	}

	public void setInstitutionName(String institutionName) {
		this.institutionName = institutionName;
	}

	public List<Party> getAllParties() {
		return allParties;
	}

	public void setAllParties(List<Party> allParties) {
		this.allParties = allParties;
	}

	@Override
	public String toString() {
		return "Institution [institutionId=" + institutionId + ", institutionName=" + institutionName + ", allParties="
				+ allParties + "]";
	}

	public void addParty(Party p) {
		if (this.allParties == null) {
			this.allParties = new ArrayList<>();
		}
		this.allParties.add(p);
	}

}
