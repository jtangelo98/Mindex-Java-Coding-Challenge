package com.mindex.challenge.controller;

import com.mindex.challenge.data.Employee;
import com.mindex.challenge.data.ReportingStructure;
import com.mindex.challenge.service.EmployeeService;
import com.mindex.challenge.service.ReportingStructureService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class ReportController {
   private static final Logger LOG = LoggerFactory.getLogger(EmployeeController.class);

   @Autowired
   	private EmployeeService employeeService;
   @Autowired
    private ReportingStructureService reportingStructureService;
    
   //REST endpoint for performing an HTTP request of an employees reporting structure
    @GetMapping("/employee/{id}/reports")
    public ReportingStructure read(@PathVariable String id) {
        LOG.debug("Received employee create request for id [{}]", id);

        //utilize existing employeeService read to fetch employee to pass to search
        Employee temp = employeeService.read(id);
        
        //check for valid employee id
        if(temp == null) {
        	throw new RuntimeException("Employee with id: " + id + "was not found");
        }
        //return reporting structure of employee
        return reportingStructureService.reportTreeSearch(temp);

    }

}