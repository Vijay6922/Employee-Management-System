package employeeInfo.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import employeeInfo.entities.Manager;
import employeeInfo.entities.ManagerRepo;

@Service
public class ManagerService implements ManagerInterface {

	@Autowired
	private ManagerRepo managerRepo;

	public ManagerService(ManagerRepo managerRepo) {
		super();
		this.managerRepo = managerRepo;
	}

//GetMapping
	@Override
	public List<Manager> getManagers() {

		List<Manager> managerList = managerRepo.findAll();
		if (managerList.isEmpty()) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No Manager found");
		} else {
			return managerList;
		}
	}

//PostMapping
	@Override
	public Manager addNewManager(Manager manager) {
		try {
			List<Manager> existingManager = managerRepo.findAll();
			if (existingManager.contains(manager)) {
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
						"A manager with the same details already exists.");
			}
			managerRepo.save(manager);
			return manager;
		} catch (Exception ex) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error retrieving managers", ex);
		}
	}

//PutMapping
	@Override
	public Manager updateManager(Integer managerId, Manager manager) {
		try {
			Optional<Manager> optManager = managerRepo.findById(managerId);
			if (!optManager.isPresent()) {
				throw new ResponseStatusException(HttpStatus.NOT_FOUND, "ManagerId Not Found..!");
			} else {

				Manager updatedManager = managerRepo.save(manager);
				return updatedManager;
			}
		} catch (DataAccessException ex) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
		}
	}

	// Implementation of the deleteManagers method
	@Override
	public void deleteManager(int id) {
		try {
			// Attempt to fetch the manager id from the repository
			var manager = managerRepo.findById(id);

			// Check if the managerId is Present
			if (manager.isPresent()) {
				// deleting a manager
				managerRepo.deleteById(id);
			} else {
				// If Manager id is not found throwing an exception
				throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No managers found");
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