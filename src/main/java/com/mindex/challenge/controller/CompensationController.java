package com.mindex.challenge.controller;

import com.mindex.challenge.data.Compensation;
import com.mindex.challenge.service.CompensationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
public class CompensationController {
    private static final Logger LOG = LoggerFactory.getLogger(CompensationController.class);

    @Autowired
    private CompensationService compensationService;
    
    //REST endpoint for performing an HTTP POST request for creating a compensation
    @PostMapping("/employee/{id}/compensation")
    public Compensation create(@RequestBody Compensation compensation) {
        LOG.debug("Received compensation create request for employee with id [{}]", compensation.getEmployee().getEmployeeId());

        return compensationService.createCompensation(compensation);
    }

    //REST endpoint for performing an HTTP GET request of an employees compensation
    @GetMapping("/employee/{id}/compensation")
    public Compensation read(@PathVariable String id) {
        LOG.debug("Received compensation create request for employee with id [{}]", id);
        
        Compensation readCompensation = compensationService.readCompensation(id);
        
        //return status code for employee not found 
		if(readCompensation == null) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Compensation for employee with id " + id + " not found.");
		}
        return readCompensation;
    }
   
}