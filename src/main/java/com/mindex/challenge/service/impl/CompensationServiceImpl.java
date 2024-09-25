package com.mindex.challenge.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.mindex.challenge.dao.CompensationRepository;
import com.mindex.challenge.data.Compensation;
import com.mindex.challenge.service.CompensationService;

@Service
public class CompensationServiceImpl implements CompensationService{
	
    private static final Logger LOG = LoggerFactory.getLogger(CompensationServiceImpl.class);
    
    @Autowired
    private CompensationRepository compensationRepository;
    
    @Override
	public Compensation createCompensation(Compensation compensation) {
		
		String employeeId = compensation.getEmployee().getEmployeeId();
		LOG.debug("Creating compensation for employee [{}]", employeeId);
		
		//send compensation to repository
		compensationRepository.insert(compensation);
		
		return compensation;
	}
    
    @Override
	public Compensation readCompensation(String id) {
		
		//check to see if compensation already exists for employee
		Compensation compensation = compensationRepository.findByEmployee_EmployeeId(id);
		
		//ensure compensation field not null
		if(compensation == null) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Compensation for employee with id " + id + " not found.");
		}
		
		return compensation;
	}
}
