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

import employeeInfo.entities.Department;
import employeeInfo.services.DepartmentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;

@RestController
public class DepartmentController {

	@Autowired
	private DepartmentService departmentService;
	

	@GetMapping("/departments") // This endpoint handles GET requests for "/api/departments"
	@Operation(summary = "Get a list of departments", description = "Returns a list of all departments.")
	@ApiResponses(value = {
			// 200 OK response indicates successful retrieval of the list of departments
			@ApiResponse(responseCode = "200", description = "Successfully retrieved the list of departments"),

			// 404 NOT FOUND response indicates the list of the department is not present.
			@ApiResponse(responseCode = "404", description = "department NOT FOUND"),

			// 500 Internal Server Error response indicates an issue on the server side
			@ApiResponse(responseCode = "500", description = "Internal server error") })
	public List<Department> getDepartments() {
		return departmentService.getDepartments();
	}

	@GetMapping("/departments/{managerId}") // This endpoint handles GET requests for "/api/departments"
	@Operation(summary = "Get a list of departments by manager Id", description = "Returns a list of all departments of managers Id.") // Operation

	@ApiResponses(value = {

			@ApiResponse(responseCode = "200", description = "Successfully retrieved the list of departments"),

			// 404 NOT FOUND response indicates the ID of the manager is not present.
			@ApiResponse(responseCode = "404", description = "manager Id NOT FOUND"),

			// 500 Internal Server Error response indicates an issue on the server side
			@ApiResponse(responseCode = "500", description = "Internal server error") })
	public Department findDepartmentByManagerId(@PathVariable("managerId") Integer id) {
		// Call the departmentService to get the list of departments
		return departmentService.findDepartmentByManagerId(id);
	}

	@PostMapping("/addDepartment") // This endpoint handles POST requests for "/api/departments"
	@Operation(summary = "Adds a new department", description = "To create and add a new department to the system.")
	// Operation annotation provides metadata for the Swagger documentation
	@ApiResponses(value = {
			// 200 OK response indicates successful creation of the manager
			@ApiResponse(responseCode = "200", description = "department created successfully"),
			// 400 Bad request Error response indicates an Invalid input data
			@ApiResponse(responseCode = "400", description = "Bad request. Invalid input data"),
			// 500 Internal Server Error response indicates an issue on the server side
			@ApiResponse(responseCode = "500", description = "Internal server error") })
	public Department addNewDepartment(@Valid @RequestBody Department department) {
		return departmentService.addNewDepartment(department);
	}

	@PutMapping("/updateDepartment/{departmentId}") // This endpoint handles PUT requests for "/api/departments"
	@Operation(summary = "Updates an existing department", description = "To modify the details of an existing department")
	@ApiResponses(value = {
			// 200 OK response indicates successful updation of the department
			@ApiResponse(responseCode = "200", description = "Department updated successfully"),
			// 400 Bad request Error response indicates an Invalid input data
			@ApiResponse(responseCode = "400", description = "Bad request. Invalid input data"),
			// 404 NOT found Error response indicates that Department not found
			@ApiResponse(responseCode = "404", description = "Department not found"),
			// 500 Internal Server Error response indicates an issue on the server side
			@ApiResponse(responseCode = "500", description = "Internal server error") })
	public Department updateDepartment(@PathVariable("departmentId") String departmentId,
			@Valid @RequestBody Department department) {
		return departmentService.updateDepartment(departmentId, department);
	}

	@DeleteMapping("/department/delete/{departmentId}") // This endpoint handles DELETE requests for "/api/department"
	@Operation(summary = "Delete department", description = "Deletes department of giving Id")
	@ApiResponses(value = {
			// 200 OK response indicates successful deletion of the department.
			@ApiResponse(responseCode = "200", description = "Successfully Deleted"),
			// 404 NOT FOUND response indicates the ID of the department is not present.
			@ApiResponse(responseCode = "404", description = "department Id NOT FOUND"),
			// 500 Internal Server Error response indicates an issue on the server side
			@ApiResponse(responseCode = "500", description = "Internal server error") })
	// ApiResponses annotation provides information about possible responses
	public void deleteDepartment(
			@Parameter(description = "Department Id that is to be deleted", allowEmptyValue = false) @PathVariable("departmentId") String id) {
		departmentService.deleteDepartment(id);

	}
}
