package application.model;

import java.util.List;
import java.util.ArrayList;

public class Faculty {
	private int facultyId;
	private String fullName;
	private String title;
	private String schoolName;
	private String department;
	private String email;
	private String phoneNumber;
	private List<Course> coursesTaught;
	
	public Faculty(String fullName, String title, String schoolName, String deparment, String email, String phoneNumber) {
		this.setFullName(fullName);
		this.setTitle(title);
		this.setSchoolName(schoolName);
		this.setDepartment(deparment);
		this.setEmail(email);
		this.setPhoneNumber(phoneNumber);
		this.setCoursesTaught(new ArrayList<Course>());
	}
	
	public Faculty() {}
	
	public int getFacultyId() {
		return facultyId;
	}
	
	public void setFacultyId(int id) {
		facultyId = id;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getSchoolName() {
		return schoolName;
	}

	public void setSchoolName(String schoolName) {
		this.schoolName = schoolName;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public List<Course> getCoursesTaught() {
		return coursesTaught;
	}

	public void setCoursesTaught(List<Course> coursesTaught) {
		this.coursesTaught = coursesTaught;
	}
	
	@Override
	public String toString() {
		return "Faculty [fullName=" + fullName + ", title=" + title + ", schoolName=" + schoolName + ", department="
				+ department + ", email=" + email + ", phoneNumber=" + phoneNumber + ", coursesTaught=" + coursesTaught
				+ "]";
	}
	
}
