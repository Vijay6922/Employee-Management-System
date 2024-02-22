package employeeInfo.entities;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepo extends JpaRepository<Employee,Integer> {
	
	// Find employees by departmentId
    List<Employee> findByDepartmentId(String departmentId);

}
