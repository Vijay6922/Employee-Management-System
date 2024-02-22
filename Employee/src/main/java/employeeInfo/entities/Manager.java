package employeeInfo.entities;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name = "Managers")
public class Manager {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "Managerid")
	private int managerId;

	@NotBlank(message = "manager name cannot be blank")
	@Column(name = "Managername")
	private String managerName;

	@Email(message = "Invalid email format")
	@Column(name = "Email")
	private String email;

	public Manager(int managerId, @NotBlank(message = "manager name cannot be blank") String managerName,
			@Email(message = "Invalid email format") String email) {
		super();
		this.managerId = managerId;
		this.managerName = managerName;
		this.email = email;
	}

	public Manager() {
		super();
	}

	@JsonIgnore
	@OneToOne(mappedBy = "manager", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private Department department;

	public int getManagerId() {
		return managerId;
	}

	public void setManagerId(int managerId) {
		this.managerId = managerId;
	}

	public String getManagerName() {
		return managerName;
	}

	public void setManagerName(String managerName) {
		this.managerName = managerName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Department getDepartment() {
		return department;
	}

	public void setDepartment(Department department) {
		this.department = department;
	}

	@Override
	public String toString() {
		return "Manager [managerId=" + managerId + ", managerName=" + managerName + ", email=" + email + ", department="
				+ department + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(email, managerName);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Manager other = (Manager) obj;
		return Objects.equals(email, other.email) && Objects.equals(managerName, other.managerName);
	}

}
