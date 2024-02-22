package EmployeeInfo;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import employeeInfo.entities.Employee;
import employeeInfo.entities.EmployeeRepo;
import employeeInfo.services.EmployeeService;

@ExtendWith(MockitoExtension.class)

class EmployeeApplicationTests {

	@Mock
	private EmployeeRepo employeeRepo;

	@InjectMocks
	private EmployeeService employeeService;

	@BeforeEach
	public void init() {
		employeeService = new EmployeeService(employeeRepo);
	}

	@Test
    void testGetEmployees() {
        
        when(employeeRepo.findAll()).thenReturn(List.of(
                new Employee(1, "Ramya", "Menda", "F", "Srikakulam", "ramyamenda369@gmail.com", 630334054, "CSM22")
        ));

        List<Employee> employees = employeeService.getEmployees();

        
        assertEquals(1, employees.size());
       
        assertEquals(1, employees.get(0).getEmployeeId());
        assertEquals("Ramya", employees.get(0).getFirstName());
        assertEquals("Menda", employees.get(0).getLastName());
    }

	@Test
	void testGetEmployeesByDepartmentId() {
		String departmentId = "CSM22";
		when(employeeRepo.findByDepartmentId(departmentId)).thenReturn(List.of(
				new Employee(1, "Ramya", "Menda", "F", "Srikakulam", "ramyamenda369@gmail.com", 630334054, "CSM22")));

		List<Employee> employees = employeeService.getEmployeesByDepartmentId(departmentId);

		// Assert
		assertEquals(1, employees.size());
		assertEquals(departmentId, employees.get(0).getDepartmentId());

	}

	@Test
	void testAddNewEmployee() {

		Employee newEmployee = new Employee(1, "satya", "challapalli", "F", "Tuni", "satya@gmail.com", 123456789,
				"CSM");

		// Mock the behavior of the repository
		when(employeeRepo.findAll()).thenReturn(new ArrayList<>());
		when(employeeRepo.save(newEmployee)).thenReturn(newEmployee);

		// Act
		Employee addedEmployee = employeeService.addNewEmployee(newEmployee);

		// Assert
		assertEquals(newEmployee, addedEmployee);
		verify(employeeRepo, times(1)).save(newEmployee);
	}

	@Test
	void testUpdateEmployee() {
		// Arrange
		Integer employeeId = 11;
		Employee existingEmployee = new Employee(employeeId, "satya", "challapalli", "F", "Tuni", "satya@gmail.com",
				123456789, "CSM");
		Employee updatedEmployee = new Employee(employeeId, "satya", "ch", "F", "Tuni", "satya@gmail.com", 123456789,
				"CSM");

		// Mock the behavior of the repository
		when(employeeRepo.findById(employeeId)).thenReturn(Optional.of(existingEmployee));
		when(employeeRepo.save(updatedEmployee)).thenReturn(updatedEmployee);

		// Act
		Employee result = employeeService.updateEmployee(employeeId, updatedEmployee);
		// System.out.println(result);

		// Assert
		assertEquals(updatedEmployee, result);

		verify(employeeRepo, times(1)).save(updatedEmployee);
	}

	@Test
	void testDeleteEmployee() {

		int employeeId = 1;
		Employee existingEmployee = new Employee(employeeId, "satya", "challapalli", "F", "Tuni", "satya@gmail.com",
				123456789, "CSM");

		// Mock the behavior of the repository
		when(employeeRepo.findById(employeeId)).thenReturn(Optional.of(existingEmployee));

		// Act
		assertDoesNotThrow(() -> employeeService.deleteEmployee(employeeId));

		// Assert
		verify(employeeRepo, times(1)).deleteById(employeeId);
	}
}
