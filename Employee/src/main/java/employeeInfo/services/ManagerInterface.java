package employeeInfo.services;

import java.util.List;

import employeeInfo.entities.Manager;

public interface ManagerInterface {
	
	List<Manager> getManagers();

	Manager addNewManager(Manager manager);

	Manager updateManager(Integer managerId, Manager manager);

	void deleteManager(int id);

}
