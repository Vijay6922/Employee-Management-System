package employeeInfo.services;

import java.util.List;

import employeeInfo.entities.Employee;

public interface EmployeeInterface {

	List<Employee> getEmployees();

	Employee addNewEmployee(Employee employee);

	Employee updateEmployee(Integer employeeId, Employee employee);

	List<Employee> getEmployeesByDepartmentId(String departmentId);

	void deleteEmployee(int id);
	

}
