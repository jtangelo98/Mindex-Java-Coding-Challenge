package com.mindex.challenge.service.impl;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import org.springframework.stereotype.Service;
import com.mindex.challenge.data.Employee;
import com.mindex.challenge.service.ReportingStructureService;
import com.mindex.challenge.data.ReportingStructure;

@Service
public class ReportingStructureServiceImpl implements ReportingStructureService {	
	public ReportingStructure reportTreeSearch(Employee employee) {
		
		//HashSet used for allReportingEmployees to avoid possible duplicates for subordinate employees with the same direct reports
		HashSet <Employee> allReportingEmployees = new HashSet <Employee>();
		Queue <Employee> queue = new LinkedList<>();
		ReportingStructure report = new ReportingStructure(employee,0);
			
		//if employee has any direct reports
		if(employee.getDirectReports() != null) {		
			
			//add reports to queue
			queue.addAll(employee.getDirectReports());
		}
		
		//while there are still reports to search
		while(!queue.isEmpty()) {
			
			//add direct reports to working queue
			allReportingEmployees.add(queue.peek());
			
			//if this employee has direct reports
			if(employee.getDirectReports() != null) {
				
				//add direct reports of current employee to queue for further search
				queue.addAll(employee.getDirectReports());
			}
			//get next employee to check
			employee = queue.poll();
		}
		
		//set number of reports in reporting structure 
		report.setNumberOfReports(allReportingEmployees.size());
		
		//return entire reporting structure object
		return report;
		
	}
		
	
}