package com.mindex.challenge.data;

public class ReportingStructure{
		int numberOfReports;
		Employee employee;
	
	public ReportingStructure() {

	}
		
	public ReportingStructure(Employee employee, int numberOfReports) {
		this.employee = employee;
		this.numberOfReports = numberOfReports;		
	}
	
	//standard getters/setters/toString
	
	public void setEmployee(Employee employee) {
		this.employee = employee;
	}
	
	public Employee getEmployee() {
		return this.employee;
	}
	
	public void setNumberOfReports(int numberOfReports) {
		this.numberOfReports = numberOfReports;
	}
	
	public int getNumberOfReports() {
		return this.numberOfReports;
	}

	public String toString() {
		return "Employee with id " + employee.getEmployeeId() + " has a total of " + numberOfReports + " reporting employees.";
	}
	
}