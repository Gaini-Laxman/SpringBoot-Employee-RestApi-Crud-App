package com.klinnovations.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.klinnovations.model.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, Integer>{

	

}
