package com.klinnovations.restcontroller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.klinnovations.exception.ResourceNotFoundException;
import com.klinnovations.model.Employee;
import com.klinnovations.repository.EmployeeRepository;

@CrossOrigin(origins = "http://localhost:4200/")
@RestController


public class EmployeeRestController {

	 private final EmployeeRepository employeeRepo;
	
	@Autowired
	public EmployeeRestController(EmployeeRepository employeeRepo) {
        this.employeeRepo = employeeRepo;
    }
	@GetMapping("/employees")
	public List<Employee> getAllEmployees() {
		return employeeRepo.findAll();
	}

	@PostMapping("/employees")
	public ResponseEntity<String> addEmployee(@RequestBody Employee employee) {
		Employee savedEmployee = employeeRepo.save(employee);

		if (savedEmployee != null) {
			return ResponseEntity.status(HttpStatus.CREATED).body("Employee created successfully");
		} else {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to create employee");
		}
	}

	@GetMapping("/employees/{id}")
	public ResponseEntity<Employee> getEmployeeById(@PathVariable Integer id) {
		Employee employee = employeeRepo.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Employee not Exist with id:" + id));
		return ResponseEntity.ok(employee);
	}

	@PutMapping("/employees/{id}")
	public ResponseEntity<Employee> updateEmployee(@PathVariable Integer id, @RequestBody Employee employeeDetails) {
		
		
		Employee employee = employeeRepo.findById(id).orElseThrow(()->new ResourceNotFoundException("Employee Not Exist with id :"+id));
            
		employee.setFirstName(employeeDetails.getFirstName());
		employee.setLastName(employeeDetails.getLastName());
		employee.setEmailId(employeeDetails.getEmailId());
		
		Employee updateEmployee = employeeRepo.save(employee);
		return ResponseEntity.ok(updateEmployee);
				
	}

	@DeleteMapping("employees/{id}")
	public ResponseEntity<Map<String, Boolean>> deleteEmployee(@PathVariable Integer id) {
		Employee employee = employeeRepo.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Employee not Exist with id:" + id));
		employeeRepo.delete(employee);
  Map<String, Boolean> response = new HashMap<>();
  response.put("Deleted", Boolean.TRUE);
  return ResponseEntity.ok(response);

	}

}
