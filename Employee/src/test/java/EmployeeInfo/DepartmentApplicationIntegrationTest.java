package EmployeeInfo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.server.ResponseStatusException;

import employeeInfo.entities.Department;
import employeeInfo.services.DepartmentService;

@SpringBootTest(classes = employeeInfo.EmployeeApplication.class)

public class DepartmentApplicationIntegrationTest {

	@Autowired
	private DepartmentService departmentSerivce;

	@Test
	@Disabled
	void testGetDepartment() {

		// Call the service method that fetches department
		List<Department> department = departmentSerivce.getDepartments();

		// Assert
		// assertEquals("CSM22", department.get(0).getDepartmentId());
		assertEquals("CSM", department.get(0).getDepartmentName());
		assertEquals(101, department.get(0).getManagerId());
		assertEquals(5, department.size());
	}

	@Test
	@Disabled
	void testAddNewDepartment() {
		// Create a new department
		Department newDepartment = new Department();
		newDepartment.setDepartmentId("CSM@");
		newDepartment.setDepartmentName("MAT");
		newDepartment.setManagerId(103);
		// Call the service method to add the new department
		Department addedDepartment = departmentSerivce.addNewDepartment(newDepartment);

		// Call the service method that fetches department after adding a new one
		List<Department> updatedDepartment = departmentSerivce.getDepartments();

		// Assert
		assertEquals(5, updatedDepartment.size()); // Assuming you had 5 department before adding the new one
		assertEquals(newDepartment.getDepartmentId(), addedDepartment.getDepartmentId());
		assertEquals(newDepartment.getDepartmentName(), addedDepartment.getDepartmentName());
		assertEquals(newDepartment.getManagerId(), addedDepartment.getManagerId());

		// Ensure that adding the same department again results in a BAD_REQUEST
		assertThrows(ResponseStatusException.class, () -> departmentSerivce.addNewDepartment(newDepartment));
	}

	@Test
	void testUpdateDepartment() {
		// Call the service method that fetches department
		List<Department> departmentBeforeUpdate = departmentSerivce.getDepartments();

		Department departmentToUpdate = departmentBeforeUpdate.get(3);

		// Create a new department with updated details
		Department updateddepartment = new Department();
		updateddepartment.setDepartmentId("DS44");
		updateddepartment.setDepartmentName("MATCH");
		updateddepartment.setManagerId(103);

		// Call the service method to update the department
		Department result = departmentSerivce.updateDepartment(departmentToUpdate.getDepartmentId(), updateddepartment);

		// Assert
		assertEquals(departmentToUpdate.getDepartmentId(), result.getDepartmentId());
		// assertEquals("CSM@@", result.getDepartmentName());
	}

	@Test
	@Disabled
	void testDeleteDepartment() {
		// Call the service method that fetches department before deletion
		List<Department> departmentBeforeDeletion = departmentSerivce.getDepartments();

		Department employeeToDelete = departmentBeforeDeletion.get(5);

		// Call the service method to delete the department
		departmentSerivce.deleteDepartment(employeeToDelete.getDepartmentId());

		// Call the service method that fetches department after deletion
		List<Department> departmentAfterDeletion = departmentSerivce.getDepartments();

		// Assert
		assertEquals(departmentBeforeDeletion.size() - 1, departmentAfterDeletion.size());

	}

}
