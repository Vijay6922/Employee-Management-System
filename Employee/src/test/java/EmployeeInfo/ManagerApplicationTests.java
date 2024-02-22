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

import employeeInfo.entities.Manager;
import employeeInfo.entities.ManagerRepo;
import employeeInfo.services.ManagerService;

@ExtendWith(MockitoExtension.class)

public class ManagerApplicationTests {
	@Mock
	private ManagerRepo managerRepo;
	@InjectMocks
	private ManagerService managerService;

	@BeforeEach
	public void init() {
		managerService = new ManagerService(managerRepo);
	}

	@Test
    void testGetEmployees() {
        
        when(managerRepo.findAll()).thenReturn(List.of(
                new Manager(1,"Venu","venu@gmail.com")
        ));

        List<Manager> managers = managerService.getManagers();

        
        assertEquals(1, managers.size());
       
        assertEquals(1, managers.get(0).getManagerId());
        assertEquals("Venu", managers.get(0).getManagerName());
        assertEquals("venu@gmail.com", managers.get(0).getEmail());
    }

	@Test
	void testAddNewManager() {

		Manager newManager = new Manager(2, "Rajeev", "rajeev@gmail.com");

		// Mock the behavior of the repository
		when(managerRepo.findAll()).thenReturn(new ArrayList<>());
		when(managerRepo.save(newManager)).thenReturn(newManager);

		// Act
		Manager addedManager = managerService.addNewManager(newManager);

		// Assert
		assertEquals(newManager, addedManager);
		verify(managerRepo, times(1)).save(newManager);
	}

	@Test
	void testUpdateManager() {
		// Arrange
		Integer managerId = 3;
		Manager existingManager = new Manager(managerId, "Madhukar", "madhukar@gmail.com");
		Manager updatedManager = new Manager(managerId, "Sasank", "sasank@gmail.com");

		// Mock the behavior of the repository
		when(managerRepo.findById(managerId)).thenReturn(Optional.of(existingManager));
		when(managerRepo.save(updatedManager)).thenReturn(updatedManager);

		// Act
		Manager result = managerService.updateManager(managerId, updatedManager);
		// System.out.println(result);

		// Assert
		assertEquals(updatedManager, result);

		verify(managerRepo, times(1)).save(updatedManager);
	}

	@Test
	void testDeleteManager() {

		int managerId = 1;
		Manager existingManager = new Manager(managerId, "Madhukar", "madhukar@gmail.com");

		// Mock the behavior of the repository
		when(managerRepo.findById(managerId)).thenReturn(Optional.of(existingManager));

		// Act
		assertDoesNotThrow(() -> managerService.deleteManager(managerId));

		// Assert
		verify(managerRepo, times(1)).deleteById(managerId);
	}

}
