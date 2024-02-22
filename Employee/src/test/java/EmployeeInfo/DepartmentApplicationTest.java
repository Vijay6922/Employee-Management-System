package EmployeeInfo;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import employeeInfo.entities.Department;
import employeeInfo.entities.DepartmentRepo;
import employeeInfo.services.DepartmentService;

@ExtendWith(MockitoExtension.class)
@TestMethodOrder(OrderAnnotation.class)
class DepartmentApplicationTest {

	@Mock
	private DepartmentRepo departmentrepo;

	@InjectMocks
	private DepartmentService departmentservice;

	@BeforeEach
	public void init() {
		departmentservice = new DepartmentService(departmentrepo);
	}

	@Test
	@Order(1)
	@Disabled
    void testGetDepartment() {
        
        when(departmentrepo.findAll()).thenReturn(List.of(
                new Department("DSDS","DataStructures",104)));

        List<Department> department = departmentservice.getDepartments();

        
        assertEquals(1, department.size());
       
        assertEquals("DSDS", department.get(0).getDepartmentId());
        assertEquals("DataStructures", department.get(0).getDepartmentName());
        assertEquals(104, department.get(0).getManagerId());
    }

	@Test
	@Order(2)
	// @Disabled
	void testGetDepartmentByManagerId() {
		Integer managerId = 104;
		when(departmentrepo.findBymanagerId(managerId)).thenReturn(new Department("DSDS", "DataStructures", 104));

		Department department = departmentservice.findDepartmentByManagerId(managerId);

		// assertEquals(1, department.size());

		assertEquals("DSDS", department.getDepartmentId());
		assertEquals("DataStructures", department.getDepartmentName());
		assertEquals(104, department.getManagerId());
	}

	@Test
	@Order(3)

	@Disabled
	void testAddNewDepartment() {

		Department department = new Department("DSDS", "DataStructures", 104);

		// Mock the behavior of the repository
		when(departmentrepo.findAll()).thenReturn(new ArrayList<>());
		when(departmentrepo.save(department)).thenReturn(department);

		// Act
		Department addedDepartment = departmentservice.addNewDepartment(department);

		// Assert
		assertEquals(department, addedDepartment);
		verify(departmentrepo, times(1)).save(department);
	}

	@Test
	@Order(4)
	@Disabled
	void testUpdateDepartment() {
		// Arrange
		String departmentId = "DSDS";
		Department existingdepartment = new Department("DSDS", "DataStructures", 104);
		Department updateddepartment = new Department("DS", "DataStructuresNew", 106);
		// Mock the behavior of the repository
		when(departmentrepo.findById(departmentId)).thenReturn(Optional.of(existingdepartment));
		when(departmentrepo.save(updateddepartment)).thenReturn(updateddepartment);

		// Act
		Department result = departmentservice.updateDepartment(departmentId, updateddepartment);
		// System.out.println(result);

		// Assert
		assertEquals(updateddepartment, result);

		verify(departmentrepo, times(1)).save(updateddepartment);
	}

//
	@Test
	@Order(5)
	@Disabled
	void testDeleteDepartment() {

		String departmentId = "DSDS";
		Department existingDepartment = new Department("DSDS", "DataStructures", 104);
		// Mock the behavior of the repository
		when(departmentrepo.findById(departmentId)).thenReturn(Optional.of(existingDepartment));

		// Act
		assertDoesNotThrow(() -> departmentservice.deleteDepartment(departmentId));

		// Assert
		verify(departmentrepo, times(1)).deleteById(departmentId);
	}

//
	@Test
	@Disabled
	void testDeleteDepartmentNotFound() {
		// Arrange
		String departmentId = "DSDS";

		// Mock the behavior of the repository
		when(departmentrepo.findById(departmentId)).thenReturn(Optional.empty());

		// Act and Assert
		ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
			departmentservice.deleteDepartment(departmentId);
		});

		assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
		assertEquals("No department found", exception.getReason());
	}

}
