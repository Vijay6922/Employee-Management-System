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

import employeeInfo.entities.Manager;
import employeeInfo.services.ManagerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;

@RestController
public class ManagerController {

	@Autowired
	private ManagerService managerService;

	@GetMapping("/managers")  // This endpoint handles GET requests for "/api/managers"
	@Operation(summary = "Get a list of managers", description = "Returns a list of all managers.")
	// Operation annotation provides metadata for the Swagger documentation

	@ApiResponses(value = {
			// 200 OK response indicates successful retrieval of the list of departments
			@ApiResponse(responseCode = "200", description = "Successfully retrieved the list of managers"),
            // 500 Internal Server Error response indicates an issue on the server side
			@ApiResponse(responseCode = "500", description = "Internal server error") })
	public List<Manager> getManagers() {
		return managerService.getManagers();
	}

	@PostMapping("/addManager")   // This endpoint handles POST requests for "/api/managers"
	@Operation(summary = "Adds a new manager", description = "To create and add a new manager to the system.")
	// Operation annotation provides metadata for the Swagger documentation
	@ApiResponses(value = {
			// 200 OK response indicates successful creation of the manager
			@ApiResponse(responseCode = "200", description = "Manager created successfully"),
			// 400 Bad request Error response indicates an Invalid input data
			@ApiResponse(responseCode = "400", description = "Bad request. Invalid input data"),
			// 500 Internal Server Error response indicates an issue on the server side
			@ApiResponse(responseCode = "500", description = "Internal server error") })
	public Manager addNewManager(@Valid @RequestBody Manager manager) {
		return managerService.addNewManager(manager);
	}

	@PutMapping("/updateManager/{managerId}")  // This endpoint handles PUT requests for "/api/managers"
	@Operation(summary = "Updates an existing manager", description = "To modify the details of an existing manager")
	// Operation annotation provides metadata for the Swagger documentation
	@ApiResponses(value = {
			// 200 OK response indicates successful updation of the manager
			@ApiResponse(responseCode = "200", description = "Department updated successfully"),
			// 400 Bad request Error response indicates an Invalid input data
			@ApiResponse(responseCode = "400", description = "Bad request. Invalid input data"),
			// 404 NOT found Error response indicates that manager not found
			@ApiResponse(responseCode = "404", description = "Department not found"),
			// 500 Internal Server Error response indicates an issue on the server side
			@ApiResponse(responseCode = "500", description = "Internal server error") })
	public Manager updateManager(@PathVariable("managerId") Integer managerId, @Valid @RequestBody Manager manager) {
		return managerService.updateManager(managerId, manager);
	}

	@DeleteMapping("/managers/delete/{managerId}") // This endpoint handles DELETE requests for "/api/managers"
	@Operation(summary = "Delete managers", description = "Deletes managers of giving Id")
	@ApiResponses(value = {
			// 200 OK response indicates successful deletion of the manager.
			@ApiResponse(responseCode = "200", description = "Successfully Deleted"),
			// 404 NOT FOUND response indicates the ID of the manager is not present.
			@ApiResponse(responseCode = "404", description = "manager Id NOT FOUND"),
			// 500 Internal Server Error response indicates an issue on the server side
			@ApiResponse(responseCode = "500", description = "Internal server error") })
	// ApiResponses annotation provides information about possible responses
	public void deleteManager(
			@Parameter(description = "managerId that is to be deleted", allowEmptyValue = false) @PathVariable("managerId") Integer id) {
		managerService.deleteManager(id);
	}
}
