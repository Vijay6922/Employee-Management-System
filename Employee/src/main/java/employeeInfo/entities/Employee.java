package employeeInfo.entities;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name = "Employees")
public class Employee {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "Employeeid")
	private int employeeId;

	@NotBlank(message = "First Name cannot be blank")
	@Column(name = "Firstname")
	private String firstName;

	@NotBlank(message = "Last Name cannot be blank")
	@Column(name = "Lastname")
	private String lastName;

	@NotBlank(message = "Gender cannot be blank")
	@Column(name = "Gender")
	private String gender;

	@NotBlank(message = "Address cannot be blank")
	@Column(name = "Address")
	private String address;

	@Email(message = "Invalid email format")
	@Column(name = "Email")
	private String email;

	@Column(name = "Phoneno")
	private int phoneNo;

	@NotBlank(message = "Department Id cannot be blank")
	@Column(name = "Departmentid")
	private String departmentId;

	public Employee() {
		super();
	}

	public Employee(int employeeId, @NotBlank(message = "First Name cannot be blank") String firstName,
			@NotBlank(message = "Last Name cannot be blank") String lastName,
			@NotBlank(message = "Gender cannot be blank") String gender,
			@NotBlank(message = "Address cannot be blank") String address,
			@Email(message = "Invalid email format") String email, int phoneNo,
			@NotBlank(message = "Department Id cannot be blank") String departmentId) {
		super();
		this.employeeId = employeeId;
		this.firstName = firstName;
		this.lastName = lastName;
		this.gender = gender;
		this.address = address;
		this.email = email;
		this.phoneNo = phoneNo;
		this.departmentId = departmentId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JsonIgnore
	@JoinColumn(name = "Departmentid", insertable = false, updatable = false)
	private Department department;

	public int getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(int employeeId) {
		this.employeeId = employeeId;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public int getPhoneNo() {
		return phoneNo;
	}

	public void setPhoneNo(int phoneNo) {
		this.phoneNo = phoneNo;
	}

	public String getDepartmentId() {
		return departmentId;
	}

	public void setDepartmentId(String departmentId) {
		this.departmentId = departmentId;
	}

	public Department getDepartment() {
		return department;
	}

	public void setDepartment(Department department) {
		this.department = department;
	}

	@Override
	public String toString() {
		return "Employee [employeeId=" + employeeId + ", firstName=" + firstName + ", lastName=" + lastName
				+ ", gender=" + gender + ", address=" + address + ", email=" + email + ", phoneNo=" + phoneNo
				+ ", departmentId=" + departmentId + ", department=" + department + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(firstName, lastName, phoneNo);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Employee other = (Employee) obj;
		return Objects.equals(firstName, other.firstName) && Objects.equals(lastName, other.lastName)
				&& phoneNo == other.phoneNo;
	}

}
