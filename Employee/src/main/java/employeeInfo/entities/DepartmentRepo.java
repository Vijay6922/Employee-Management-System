package employeeInfo.entities;



import org.springframework.data.jpa.repository.JpaRepository;

public interface DepartmentRepo extends JpaRepository<Department,String>{
	
	Department findBymanagerId(int id);

}
