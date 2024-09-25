package com.mindex.challenge.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;
import com.mindex.challenge.data.ReportingStructure;
import com.mindex.challenge.service.EmployeeService;
import com.mindex.challenge.data.Employee;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ReportingStructureServiceImplTest{
	
	private String reportUrl;
	
	ReportingStructure reportingStructure;
	
	@Autowired
	ReportingStructureServiceImpl reportingStructureServiceImpl;
	
	@Autowired
	EmployeeService employeeService;
	
    @LocalServerPort
    private int port;
	
    @Autowired
    private TestRestTemplate restTemplate;
    
	@Before
    public void setup() {
        reportUrl = "http://localhost:" + port + "employee/{id}/reports";
    }
		
	@Test 
	public void testReportTreeSearchWithTwoDirectReports() {
		//ARRANGE
		Employee testEmployee = new Employee() ;
		
		Employee directReportEmployee1 = new Employee();
		directReportEmployee1.setFirstName("Miles");
		directReportEmployee1.setLastName("Morales");
		directReportEmployee1.setDepartment("Engineering");
		directReportEmployee1.setPosition("Developer");
		directReportEmployee1.setEmployeeId("2");
		
		
		Employee directReportEmployee2 = new Employee();
		directReportEmployee2.setFirstName("Harry");
		directReportEmployee2.setLastName("Osbourne");
		directReportEmployee2.setDepartment("Engineering");
		directReportEmployee2.setPosition("Developer");
		directReportEmployee2.setEmployeeId("3");
		
		List <Employee> directReports = new ArrayList <Employee>()
		{{
			add(directReportEmployee1);	
			add(directReportEmployee2);
		}};
		testEmployee.setDirectReports(directReports);
		int expectedNumReports = 2;
		
		//ACT
		ReportingStructure report = reportingStructureServiceImpl.reportTreeSearch(testEmployee);
		
		//ASSERT
		assertEquals(testEmployee, report.getEmployee());
		assertEquals(expectedNumReports,report.getNumberOfReports());
	}
	
	@Test 
	public void testReportTreeSearchWithNestedDirectReports() {
		//ARRANGE
		Employee testEmployee = new Employee() ;
		
		Employee directReportEmployee1 = new Employee();
		directReportEmployee1.setFirstName("Miles");
		directReportEmployee1.setLastName("Morales");
		directReportEmployee1.setDepartment("Engineering");
		directReportEmployee1.setPosition("Developer");
		directReportEmployee1.setEmployeeId("2");
		
		Employee directReportEmployee2 = new Employee();
		directReportEmployee2.setFirstName("Harry");
		directReportEmployee2.setLastName("Osbourne");
		directReportEmployee2.setDepartment("Engineering");
		directReportEmployee2.setPosition("Developer");
		directReportEmployee2.setEmployeeId("3");
		
		Employee nestedReportEmployee1 = new Employee();
		directReportEmployee1.setFirstName("Eddie");
		directReportEmployee1.setLastName("Brock");
		directReportEmployee1.setDepartment("Engineering");
		directReportEmployee1.setPosition("Developer");
		directReportEmployee1.setEmployeeId("4");
		
		Employee nestedReportEmployee2 = new Employee();
		directReportEmployee1.setFirstName("Gwen");
		directReportEmployee1.setLastName("Stacy");
		directReportEmployee1.setDepartment("Engineering");
		directReportEmployee1.setPosition("Developer");
		directReportEmployee1.setEmployeeId("5");
		
		
		List <Employee> directReports = new ArrayList <Employee>()
		{{
			add(directReportEmployee1);	
			add(directReportEmployee2);
		}};
		
		List <Employee> nestedDirectReports = new ArrayList <Employee>()
		{{
			add(nestedReportEmployee1);	
			add(nestedReportEmployee2);
		}};
		
		testEmployee.setDirectReports(directReports);
		directReportEmployee1.setDirectReports(nestedDirectReports);
		int expectedNumReports = 4;
		
		//ACT
		ReportingStructure report = reportingStructureServiceImpl.reportTreeSearch(testEmployee);
		
		//ASSERT
		assertEquals(testEmployee, report.getEmployee());
		assertEquals(expectedNumReports,report.getNumberOfReports());
	}

	@Test 
	public void testReportTreeSearchWithNoDirectReports() {
		//ARRANGE
		Employee testEmployee = new Employee() ;

		int expectedNumReports = 0;
		
		//ACT
		ReportingStructure report = reportingStructureServiceImpl.reportTreeSearch(testEmployee);
		
		//ASSERT
		assertEquals(testEmployee, report.getEmployee());
		assertEquals(expectedNumReports,report.getNumberOfReports());
	}
	
	@Test
	public void testReportStructureServiceFromEndpoint() {
		//ARRANGE
		String expectedId = "16a596ae-edd3-4847-99fe-c4518e82c86f"; //id value for John Lennon
		ReportingStructure createdReport = reportingStructureServiceImpl.reportTreeSearch(employeeService.read(expectedId));
		int expectedNumReports = createdReport.getNumberOfReports();
		
		
		//ACT
		ReportingStructure readReport = restTemplate.getForEntity(reportUrl, ReportingStructure.class, createdReport.getEmployee().getEmployeeId()).getBody();
		
		//ASSERT
		assertNotNull(readReport);
		assertEquals(expectedId, readReport.getEmployee().getEmployeeId());
		assertEquals(expectedNumReports, readReport.getNumberOfReports());
	
	}
	
}