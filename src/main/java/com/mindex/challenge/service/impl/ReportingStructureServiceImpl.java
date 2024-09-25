/*
 *	Name		: ReportingStructureServiceImpl 
 *  Description	: A breadth first search algorithm to step through the list of direct reports
 *  			  for an employee (and subsequent direct reports of subordinate employees) and
 *  			  determine the total number of reporting employees. 
 * 	Notes		: List of all reporting employees maintained for testing purposes. 
 */
package com.mindex.challenge.service.impl;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import org.springframework.stereotype.Service;

import com.mindex.challenge.data.Employee;
import com.mindex.challenge.service.ReportingStructureService;
import com.mindex.challenge.data.ReportingStructure;


//local class to create nodes of search tree 
//(mainly used to determine if node has been visited already)
//(allows us to use breadth-first search [BFS] to determine total number of reports. 
class TreeNode {
	
	//instance variables
	Employee node;
	List<Employee> children;
	boolean isVisited = false;
	
	TreeNode(Employee node){
		this.node = node;
	}
	
	void addChild(Employee childNode) {
		children.add(childNode);
	}
	
	void setIsVisited(boolean isVisited) {
		this.isVisited = isVisited;
	}
}


@Service
public class ReportingStructureServiceImpl implements ReportingStructureService {
	
	
	public ReportingStructure reportTreeSearch(Employee employee) {
		List <Employee> allReportingEmployees = new ArrayList<Employee>();
		Queue <Employee> queue = new LinkedList<>();
		ReportingStructure report = new ReportingStructure(employee,0);
			
		//check for valid employee parameter
		if(employee == null) {
			throw new RuntimeException("Employee not found");
		}
		
		//get first employee and add to queue
		queue.add(employee);
		
		//if employee has any direct reports
		if(employee.getDirectReports() != null) {		
			
			//add reports to queue
			queue.addAll(employee.getDirectReports());
		}
		//pop first employee from queue (do not want to count them in reporting employees)
		queue.poll();
		
		//while there are still reports to search
		while(!queue.isEmpty()) {
			
			//create a node object
			TreeNode current = new TreeNode(queue.poll());
			
			//if node has not yet been visited
			if(!current.isVisited) {
				
				//mark it as visited
				current.setIsVisited(true);
				
				//add employee to reporting employees list
				allReportingEmployees.add(current.node);
				
				//if this employee has direct reports
				if(current.node.getDirectReports() != null) {
					
					//add direct reports of current employee (tree node) to queue for further search
					queue.addAll(current.node.getDirectReports());
				}
			}
				
		}
		//report.setAllReports(allReportingEmployees);
		
		//set number of reports in reporting structure 
		report.setNumberOfReports(allReportingEmployees.size());
		
		//return entire reporting structure object
		return report;
		
	}
		
	
}