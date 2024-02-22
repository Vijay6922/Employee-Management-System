package employeeInfo.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import employeeInfo.entities.Employee;
import employeeInfo.entities.EmployeeRepo;

@Service
public class EmployeeService implements EmployeeInterface {

	@Autowired
	private EmployeeRepo employeeRepo;

	public EmployeeService(EmployeeRepo employeeRepo) {
		super();
		this.employeeRepo = employeeRepo;
	}

	public EmployeeService() {
		super();
	}

//GetMapping
	@Override
	public List<Employee> getEmployees() {
		try {
			List<Employee> employeeList = employeeRepo.findAll();
			//System.out.println(employeeList);
			if (employeeList.isEmpty()) {
				throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No employees found");
			}
			return employeeList;
		} catch (Exception ex) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error retrieving employees", ex);
		}
	}

	@Override
	public List<Employee> getEmployeesByDepartmentId(String departmentId) {
		// Attempt to fetch the list of employees by departmentId from the repository

		List<Employee> employees = employeeRepo.findByDepartmentId(departmentId);

		// Check if the list is empty and throw an exception if no employees are found
		if (!employees.isEmpty()) {
			return employees;
		} else
			// If the list is empty, return a 404 response
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No employees found for the department");

	}

//PostMapping
	@Override
	public Employee addNewEmployee(Employee employee) {
		try {
			List<Employee> existingemployee = employeeRepo.findAll();
			if (existingemployee.contains(employee)) {
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
						"A employee with the same details already exists.");
			}
			employeeRepo.save(employee);
			return employee;
		} catch (Exception ex) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error retrieving employees", ex);
		}
	}

//PutMapping
	@Override
	public Employee updateEmployee(Integer employeeId, Employee employee) {
		try {
			Optional<Employee> optEmployee = employeeRepo.findById(employeeId);
			if (!optEmployee.isPresent()) {
				throw new ResponseStatusException(HttpStatus.NOT_FOUND, "EmployeeId Not Found..!");
			} else {
				Employee updatedEmployee = employeeRepo.save(employee);
				return updatedEmployee;
			}
		} catch (DataAccessException ex) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
		}
	}

	// Implementation of the deleteEmployee method
	@Override
	public void deleteEmployee(int id) {
		try {
			// Attempt to fetch the employee id from the repository
			var employee = employeeRepo.findById(id);

			// Check if the employeeId is Present
			if (employee.isPresent()) {
				// deleting a employee
				employeeRepo.deleteById(id);
			} else {
				// If employee id is not found throwing an exception
				throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No employees found");
			}

		} catch (ResponseStatusException ex) {
			// throw response status error
			throw ex;

		} catch (Exception ex) {
			// Catch any exceptions that might occur during the process
			// Log the error and throw a response status exception with an internal server
			// error status
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
		}
	}
}
