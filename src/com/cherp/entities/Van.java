package com.cherp.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="van")
public class Van {
	
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@Column(name="vanNumber")
	private String vanNumber;
	@Column(name="companyName")
	private String companyName;
	@Column(name="vanModel")
	private String vanModel;
	@Column(name="ownerName")
	private String ownerName;
	@Column(name="fitness")
	private int fitness;
	@Column(name="capacity")
	private int vanCapacity;
	@Column(name="insuranceNo")
	private int insuranceNo;
	@Column(name="insStartDate")
	private String insStartDate;
	@Column(name="insEndDate")
	private String insEndDate;
	@Column(name="permitNo")
	private int permitNo;
	@Column(name="permitStartDate")
	private String permitStartDate;
	@Column(name="permitEndDate")
	private String permitEndDate;
	@Column(name="status")
	private int status;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getVanNumber() {
		return vanNumber;
	}

	public void setVanNumber(String vanNumber) {
		this.vanNumber = vanNumber;
	}

	public String getVanModel() {
		return vanModel;
	}

	public void setVanModel(String vanModel) {
		this.vanModel = vanModel;
	}

	public String getOwnerName() {
		return ownerName;
	}

	public void setOwnerName(String ownerName) {
		this.ownerName = ownerName;
	}

	public int getFitness() {
		return fitness;
	}

	public void setFitness(int fitness) {
		this.fitness = fitness;
	}

	public int getVanCapacity() {
		return vanCapacity;
	}

	public void setVanCapacity(int vanCapacity) {
		this.vanCapacity = vanCapacity;
	}

	public int getInsuranceNo() {
		return insuranceNo;
	}

	public void setInsuranceNo(int insuranceNo) {
		this.insuranceNo = insuranceNo;
	}

	public String getInsStartDate() {
		return insStartDate;
	}

	public void setInsStartDate(String insStartDate) {
		this.insStartDate = insStartDate;
	}

	public String getInsEndDate() {
		return insEndDate;
	}

	public void setInsEndDate(String insEndDate) {
		this.insEndDate = insEndDate;
	}

	public int getPermitNo() {
		return permitNo;
	}

	public void setPermitNo(int permitNo) {
		this.permitNo = permitNo;
	}

	public String getPermitStartDate() {
		return permitStartDate;
	}

	public void setPermitStartDate(String permitStartDate) {
		this.permitStartDate = permitStartDate;
	}

	public String getPermitEndDate() {
		return permitEndDate;
	}

	public void setPermitEndDate(String permitEndDate) {
		this.permitEndDate = permitEndDate;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

}
