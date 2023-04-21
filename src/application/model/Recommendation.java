package application.model;

import java.util.ArrayList;
import java.util.List;

import javafx.beans.property.SimpleStringProperty;

public class Recommendation {
	private int recommendationId;
	private SimpleStringProperty studentFirstName;
	private SimpleStringProperty studentLastName;
	private SimpleStringProperty targetSchoolName;
	private SimpleStringProperty currentDate;
	private SimpleStringProperty firstSemesterYear;
	private Gender gender;

//	maybe one-to-many,
	private Semester semester;
//	maybe one-to-many, one program can belong to many recommendation but a recommendation can only have one program
	private AcademicProgram program;
//	many-to-many DB relationship
	private List<AcademicCharacteristic> academicCharacteristics;
//	many-to-many DB relationship
	private List<PersonalCharacteristic> personalCharacteristics;
//	many-to-many DB relationship
	private List<Course> coursesTaken;
	
	public Recommendation() {
		studentFirstName = new SimpleStringProperty();
		studentLastName = new SimpleStringProperty();
		targetSchoolName = new SimpleStringProperty();
		currentDate = new SimpleStringProperty();
		academicCharacteristics = new ArrayList<>();
		personalCharacteristics = new ArrayList<>();
		coursesTaken = new ArrayList<>();
	}
	
	public int getRecommendationId() {
		return recommendationId;
	}
	public void setRecommendationId(int recommendationId) {
		this.recommendationId = recommendationId;
	}
	public String getStudentFirstName() {
		return studentFirstName.get();
	}
	public void setStudentFirstName(String studentFirstName) {
		this.studentFirstName.set(studentFirstName);
	}
	public String getStudentLastName() {
		return studentLastName.get();
	}
	public void setStudentLastName(String studentLastName) {
		this.studentLastName.set(studentLastName);
	}
	public String getTargetSchoolName() {
		return targetSchoolName.get();
	}
	public void setTargetSchoolName(String targetSchoolName) {
		this.targetSchoolName.set(targetSchoolName);
	}
	public Gender getGender() {
		return gender;
	}
	public void setGender(Gender gender) {
		this.gender = gender;
	}
	public Semester getSemester() {
		return semester;
	}
	public void setSemester(Semester semester) {
		this.semester = semester;
	}
	public AcademicProgram getProgram() {
		return program;
	}
	public void setProgram(AcademicProgram program) {
		this.program = program;
	}
	public List<AcademicCharacteristic> getAcademicCharacteristics() {
		return academicCharacteristics;
	}
	public void setAcademicCharacteristics(List<AcademicCharacteristic> academicCharacteristics) {
		this.academicCharacteristics = academicCharacteristics;
	}
	public void addAcademicCharacteristics(AcademicCharacteristic academicCharacteristics) {
		this.academicCharacteristics.add(academicCharacteristics);
	}
	public List<PersonalCharacteristic> getPersonalCharacteristics() {
		return personalCharacteristics;
	}
	public void setPersonalCharacteristics(List<PersonalCharacteristic> personalCharacteristics) {
		this.personalCharacteristics = personalCharacteristics;
	}
	public void addPersonalCharacteristics(PersonalCharacteristic personalCharacteristics) {
		this.personalCharacteristics.add(personalCharacteristics);
	}
	public List<Course> getCoursesTaken() {
		return coursesTaken;
	}
	public void setCoursesTaken(List<Course> coursesTaken) {
		this.coursesTaken = coursesTaken;
	}
	public void addCourse(Course course) {
		this.coursesTaken.add(course);
	}
	public String getCurrentDate() {
		return currentDate.get();
	}
	public void setCurrentDate(String date) {
		this.currentDate.set(date);
	}

	public String getFirstSemesterYear() {
		return firstSemesterYear.get();
	}

	public void setFirstSemesterYear(String firstSemesterYear) {
		this.firstSemesterYear.set(firstSemesterYear);
	}
}
