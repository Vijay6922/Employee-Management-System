package employeeInfo.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import employeeInfo.entities.Department;
import employeeInfo.entities.DepartmentRepo;

@Service
public class DepartmentService implements DepartmentInterface {

	@Autowired
	private DepartmentRepo departmentRepo;

	public DepartmentService() {
		super();
	}

	public DepartmentService(DepartmentRepo departmentRepo) {
		super();
		this.departmentRepo = departmentRepo;
	}

	// GetMapping
	@Override
	public List<Department> getDepartments() {
		try {
			List<Department> departmentList = departmentRepo.findAll();
			if (departmentList.isEmpty()) {
				throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No Departments found");
			}
			return departmentList;
		} catch (Exception ex) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error retrieving departments", ex);
		}
	}
	
	// GetMapping
	
		public Optional<Department> findDepartmentsById(String id) {
			try {
				Optional<Department> departmentList = departmentRepo.findById(id);
				if (departmentList.isEmpty()) {
					throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No Departments found");
				}
				return departmentList;
			} catch (Exception ex) {
				throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error retrieving departments", ex);
			}
		}

	// Implementation of the getDepartments by managers method
	@Override
	public Department findDepartmentByManagerId(int id) {
		try {
			// Attempt to fetch the department from the repository
			Department department = departmentRepo.findBymanagerId(id);

			// Check if the department is not null
			if (department != null) {
				return department;
			} else {
				throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No department found");
			}
		} catch (Exception ex) {
			// Log the error and throw a response status exception with an internal server
			// error status
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "No departments found", ex);
		}
	}

//PostMapping
	@Override
	public Department addNewDepartment(Department department) {
		try {
			List<Department> existingdepartment = departmentRepo.findAll();
			if (existingdepartment.contains(department)) {
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
						"A department with the same details already exists.");
			} else {
				departmentRepo.save(department);
				return department;
			}
		} catch (Exception ex) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
		}
	}

//PutMapping
	@Override
	public Department updateDepartment(String departmentId, Department department) {
		Optional<Department> optDepartment = departmentRepo.findById(departmentId);
		if (!optDepartment.isPresent()) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "DepartmentId Not Found..!");
		}
		try {
			Department updatedDepartment = departmentRepo.save(department);
			return updatedDepartment;
		} catch (Exception ex) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
		}
	}

	// DeleteMapping
	// Implementation of the deleteDepartment method
	@Override
	public void deleteDepartment(String id) {
		try {
			// Attempt to fetch the department id from the repository
			var department = departmentRepo.findById(id);

			// Check if the departmentId is Present
			if (department.isPresent()) {
				// deleting a department
				departmentRepo.deleteById(id);
			} else {
				// If department id is not found throwing an exception
				throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No department found");
			}

		} catch (ResponseStatusException ex) {
			// throw response status error
			throw ex;
		} catch (Exception ex) {
			// Catch any exceptions that might occur during the process
			// Log the error and throw a response status exception with an internal server
			// error status
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
		}
	}

	

}
