package com.osttra.capstone.tradeaggregation.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;

@ApiModel
@Api(value = "Institution")
@Entity
@Table(name = "institution")
public class Institution {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "institution_id")
	private int institutionId;
	@Column(name = "institution_name", unique = true)
	@NotNull(message = "institution Name cant be null")
	private String institutionName;

	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "i_id")
	@JsonManagedReference
	private List<Party> allParties;

	public Institution() {

	}

	public Institution(Institution i) {
		List<Party> p = i.getAllParties();
		List<Party> np = new ArrayList<>(p);
		this.allParties = np;
		this.institutionId = i.getInstitutionId();
		this.institutionName = i.getInstitutionName();
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
