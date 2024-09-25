package com.mindex.challenge.data;

import java.math.BigDecimal;
import java.time.*;

import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "Compensation")
public class Compensation{
	//Big decimal used for accuracy and prevention of loss of data 
	//Widely accepted as best data type for currency as it allows for a large amount
	//of precision to be represented
	BigDecimal compensation;
	LocalDate effectiveDate;

	@DBRef
	Employee employee;
	public Compensation() {
	}
	
	public Compensation(Employee employee, BigDecimal compensation, LocalDate effectiveDate) {
		this.employee = employee;
		this.compensation = compensation;
		this.effectiveDate = effectiveDate;
	}
	
	public Employee getEmployee() {
		return this.employee;
	}
	
	public void setEmployee(Employee employee) {
		this.employee = employee;
	}
	
	public BigDecimal getCompensation() {
		return this.compensation;
	}
	
	public void setCompenstation(BigDecimal compensation) {
		this.compensation = compensation;
	}
	
	public LocalDate getEffectiveDate() {
		return this.effectiveDate;
	}
	
	public void setEffectiveDate(LocalDate effectiveDate) {
		this.effectiveDate = effectiveDate;
	}
	
	public String toString() {
		return "Employee with id " + employee.getEmployeeId() + " has a salary of $" + compensation;
	}
	
}