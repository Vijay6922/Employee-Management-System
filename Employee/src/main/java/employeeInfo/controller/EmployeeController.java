package employeeInfo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import employeeInfo.entities.Employee;
import employeeInfo.services.EmployeeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;

@RestController
public class EmployeeController {

	@Autowired
	private EmployeeService employeeService;

	// getmapping
	@GetMapping("/employees")  // This endpoint handles get requests for "/api/employee"
	@Operation(summary = "Get a list of employees", description = "Returns a list of all employees.")
	@ApiResponses(value = {
			// 200 OK response indicates successful retrieval of the list of employees
			@ApiResponse(responseCode = "200", description = "Successfully retrieved the list of employees"),
			// 500 Internal Server Error response indicates an issue on the server side
			@ApiResponse(responseCode = "500", description = "Internal server error") }) // ApiResponses annotation
																							// provides information
																							// about possible responses
	public List<Employee> getEmployees() {
		return employeeService.getEmployees();
	}

	@GetMapping("/employeesbydeptid/{departmentId}")
	@Operation(summary = "Get employees by department ID", description = "Returns a list of employees based on the provided department ID.")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Successfully retrieved the list of employees"),
			@ApiResponse(responseCode = "404", description = "No employees found for the department"),
			@ApiResponse(responseCode = "500", description = "Internal server error") })
	public List<Employee> getEmployeesByDepartmentId(@PathVariable("departmentId") String departmentId) {
		// Call the employeeService to get the list of employees by department ID
		return employeeService.getEmployeesByDepartmentId(departmentId);

	}

	// postmapping
	@PostMapping("/addEmployee")  // This endpoint handles post requests for "/api/employee"
	@Operation(summary = "Adds a new employee", description = "To create and add a new employee to the system.")
	// Operation annotation provides metadata for the Swagger documentation
	@ApiResponses(value = {
			// 200 OK response indicates successful creation of the employee
			@ApiResponse(responseCode = "201", description = "Employee created successfully"),
			// 400 Bad request Error response indicates an Invalid input data
			@ApiResponse(responseCode = "400", description = "Bad request. Invalid input data"),
			// 500 Internal Server Error response indicates an issue on the server side
			@ApiResponse(responseCode = "500", description = "Internal server error") })
	// ApiResponses annotation provides information about possible responses

	public Employee addNewEmployee(@Valid @RequestBody Employee employee) {
		return employeeService.addNewEmployee(employee);
	}

	// putmapping
	@PutMapping("/updateEmployee/{employeeId}")  // This endpoint handles put requests for "/api/employee"
	@Operation(summary = "Updates an existing employee", description = "To modify the details of an existing employee")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Employee updated successfully"),
			@ApiResponse(responseCode = "400", description = "Bad request. Invalid input data"),
			@ApiResponse(responseCode = "404", description = "Employee not found"),
			@ApiResponse(responseCode = "500", description = "Internal server error") })
	public Employee updateEmployee(@PathVariable("employeeId") Integer employeeId,
			@Valid @RequestBody Employee employee) {
		return employeeService.updateEmployee(employeeId, employee);
	}

	@DeleteMapping("/employee/delete/{employeeId}") // This endpoint handles DELETE requests for "/api/employee"
	@Operation(summary = "Delete employee", description = "Deletes employees of giving Id")
	@ApiResponses(value = {
			// 200 OK response indicates successful deletion of the employee.
			@ApiResponse(responseCode = "200", description = "Successfully Deleted"),
			// 404 NOT FOUND response indicates the ID of the employee is not present.
			@ApiResponse(responseCode = "404", description = "employee Id NOT FOUND"),
			// 500 Internal Server Error response indicates an issue on the server side
			@ApiResponse(responseCode = "500", description = "Internal server error") })
	// ApiResponses annotation provides information about possible responses
	public void deleteEmployee(
			@Parameter(description = "Employee Id that is to be deleted", allowEmptyValue = false) @PathVariable("employeeId") Integer id) {
		employeeService.deleteEmployee(id);
	}

}
