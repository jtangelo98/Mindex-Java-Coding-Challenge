package com.mindex.challenge.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import java.math.BigDecimal;
import java.time.LocalDate;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import com.mindex.challenge.service.EmployeeService;
import com.mindex.challenge.data.Compensation;
import com.mindex.challenge.data.Employee;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CompensationServiceImplTest{
	
	private String compensationUrl;
	private String compensationUrlWithInvalidId;
	
	Compensation compensation;
	
	@Autowired
	CompensationServiceImpl compensationServiceImpl;
	
	@Autowired
	EmployeeService employeeService;
	
    @LocalServerPort
    private int port;
	
    @Autowired
    private TestRestTemplate restTemplate;
    
	@Before
    public void setup() {
        compensationUrl = "http://localhost:" + port + "employee/{id}/compensation";
        compensationUrlWithInvalidId = "http://localhost:" + port + "employee/123456789/compensation";
    }
		
	@Test 
	public void testCreateReadCompensation() {
		
		//ARRANGE
		Employee testEmployee = new Employee();
		String expectedId = "16a596ae-edd3-4847-99fe-c4518e82c86f";
		BigDecimal expectedCompensation = new BigDecimal("70000");
		LocalDate expectedEffectiveDate = LocalDate.of(2024, 9, 21);
		testEmployee.setEmployeeId(expectedId);
		testEmployee.setFirstName("John");
		testEmployee.setLastName("Lennon");
		testEmployee.setPosition("Development Manager");
		testEmployee.setDepartment("Engineering");
		
		Compensation testCompensation = new Compensation();
		testCompensation.setCompensation(expectedCompensation);
		testCompensation.setEmployee(testEmployee);
		testCompensation.setEffectiveDate(expectedEffectiveDate);
		
		
		//ACT
		Compensation createdCompensation = restTemplate.postForEntity(compensationUrl.replace("{id}", expectedId), testCompensation, Compensation.class).getBody();
		Compensation readCompensation = restTemplate.getForEntity(compensationUrl, Compensation.class, createdCompensation.getEmployee().getEmployeeId()).getBody();
		
		//ASSERT
		//create tests
		assertNotNull(createdCompensation);
		assertEquals(expectedId, createdCompensation.getEmployee().getEmployeeId());
		assertEquals(expectedCompensation, createdCompensation.getCompensation());
		assertEquals(expectedEffectiveDate, createdCompensation.getEffectiveDate());
		
		//read tests
		assertNotNull(readCompensation);
		assertEquals(expectedId, readCompensation.getEmployee().getEmployeeId());
		assertEquals(expectedCompensation, readCompensation.getCompensation());
		assertEquals(expectedEffectiveDate, readCompensation.getEffectiveDate());	
	
	}
	
	@Test
	public void testCreateReadCompensationInvalidId() {
		//ARRANGE
		
		//ACT
		ResponseEntity<Compensation> readCompensation = restTemplate.getForEntity(compensationUrlWithInvalidId, Compensation.class);
		//ASSERT
		assertEquals(HttpStatus.NOT_FOUND, readCompensation.getStatusCode());
	}

	
}