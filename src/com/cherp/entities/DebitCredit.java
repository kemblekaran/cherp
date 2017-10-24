package com.cherp.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "debitcredit")
public class DebitCredit {

	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@Column(name = "debitCredit")
	private String debitCredit;
	@Column(name = "debitCreditDate")
	private String debitCreditDate;
	@Column(name = "noteNo")
	private int noteNo;
	@Column(name = "customerCompany")
	private String customerCompany;
	@Column(name = "selectCustCmp")
	private String selectCustCmp;
	@Column(name = "amount")
	private int amount ;
	@Column(name = "remarks")
	private String remarks ;
	@Column(name = "status")
	private int status;
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	
	public String getDebitCredit() {
		return debitCredit;
	}
	public void setDebitCredit(String debitCredit) {
		this.debitCredit = debitCredit;
	}
	public String getDebitCreditDate() {
		return debitCreditDate;
	}
	public void setDebitCreditDate(String debitCreditDate) {
		this.debitCreditDate = debitCreditDate;
	}
	
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public int getNoteNo() {
		return noteNo;
	}
	public void setNoteNo(int noteNo) {
		this.noteNo = noteNo;
	}
	
	public String getCustomerCompany() {
		return customerCompany;
	}
	public void setCustomerCompany(String customerCompany) {
		this.customerCompany = customerCompany;
	}
	public String getSelectCustCmp() {
		return selectCustCmp;
	}
	public void setSelectCustCmp(String selectCustCmp) {
		this.selectCustCmp = selectCustCmp;
	}
	public int getAmount() {
		return amount;
	}
	public void setAmount(int amount) {
		this.amount = amount;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	
	
	
	
	
	
}
