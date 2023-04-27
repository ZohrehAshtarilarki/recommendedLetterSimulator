package application.utils;

import java.util.ArrayList;

import application.model.AcademicCharacteristic;
import application.model.Faculty;
import application.model.Gender;
import application.model.PersonalCharacteristic;
import application.model.Recommendation;
import application.model.RecommendationCourse;

public class TxtCompiler implements CompilerInt {
	private final String letterTitle = "Letter of Recommendation";
	private String genderPronoun;
	
	private String createHeader(String studentFullName, String todayDate) {
		return String.format
				( "Letter of Recommendation\n"
				+ "For: %s\n"
				+ "Date: %s"
				, studentFullName, todayDate);
	}
	
	private String reasonOfWrittingLetter(String studentFullName, String programName) {
		return String.format(
				"I am writing this letter to recommend my former student %s who is applying "
				+ "for the %s in your school. "
						, studentFullName, programName);
	}
	
	private String metStudent(String studentFirstName, String firstSemester, String firstCourseTaken) {
		return String.format(
				"I met %s in %s when he enrolled in my course %s.",
				studentFirstName, firstSemester, firstCourseTaken);
	}
	
	private String toughCourse(String studentFirstName, String letterGrade) {
		return String.format(
				"%s earned %s from this tough course, and this shows how "
				+ "knowledgeable and hard worker <he/she> is. ",
				studentFirstName, letterGrade, this.genderPronoun);
	}
	
	private String moreCoursesWithProfessor(ArrayList<RecommendationCourse> courses) {
		return "";
	}
	
	private String acadCharSection(ArrayList<AcademicCharacteristic> acadChars) {
		return "";
	}
	
	private String perCharSection(ArrayList<PersonalCharacteristic> perChars) {
		return "";
	}
	
	private String termProjectSection() {
		return String.format(
				"Furthermore, I noticed from the term project result, %s developed leadership, time "
				+ "management, and problem-solving skills. %s worked effectively with the team members and "
				+ "delegated tasks appropriately. They were able to deliver a successful project in a timely fashion.",
				this.genderPronoun, this.genderPronoun);
	}
	
	private String excelHigerEducationSection(String studentFirstName) {
		return String.format(
				"I believe that %s has the capacity to excel at higher education program and this "
				+ "is my pleasure to highly recommend him.\n"
				+ "Please do not hesitate to contact me with further questions.",
				studentFirstName);
	}
	
	private String facultySignature(String facultyFullName, String facultyTitle, String schoolName,
			String departmentName, String facutlyEmail, String facultyPhoneNumber) {
		return String.format(
				"Very Respectfully,\n"
				+ "%s\n"
				+ "%s\n"
				+ "%s, %s\n"
				+ "%s\n"
				+ "%s",
				facultyFullName, facultyTitle, schoolName, departmentName, facutlyEmail, facultyPhoneNumber);
	}
	
	private void setGenderPronoun(Gender gender) {
		switch (gender) {
		case MALE:
			this.genderPronoun = "he";
			break;
		case FEMALE:
			this.genderPronoun = "she";
			break;
		default:
			this.genderPronoun = "they";
			break;
		}
	}

	@Override
	public String compile(Faculty faculty, Recommendation recommendation) {
		String compiledRecommendationTxt = "";
		this.setGenderPronoun(recommendation.getGender());
		String studentFullName = recommendation.getStudentFirstName() + recommendation.getStudentLastName();
		String todayDate = "04/26/2023";
		compiledRecommendationTxt += createHeader(studentFullName, todayDate);
		compiledRecommendationTxt += reasonOfWrittingLetter(studentFullName, recommendation.getProgram().getName());
		compiledRecommendationTxt += metStudent(recommendation.getStudentFirstName(),
				recommendation.getSemester().getName() + recommendation.getFirstSemesterYear(),
				recommendation.getCoursesTaken().size() > 0 ? recommendation.getCoursesTaken().get(0).getName() : "No first Course Found");
		compiledRecommendationTxt += toughCourse(recommendation.getStudentFirstName(),
				recommendation.getCoursesTaken().size() > 0 ? recommendation.getCoursesTaken().get(0).getGrade() : "No Grade Found");
		compiledRecommendationTxt += moreCoursesWithProfessor(new ArrayList<>(recommendation.getCoursesTaken()));
		compiledRecommendationTxt += acadCharSection(new ArrayList<>(recommendation.getAcademicCharacteristics()));
		compiledRecommendationTxt += perCharSection(new ArrayList<>(recommendation.getPersonalCharacteristics()));
		compiledRecommendationTxt += termProjectSection();
		compiledRecommendationTxt += excelHigerEducationSection(recommendation.getStudentFirstName());
		compiledRecommendationTxt += facultySignature(faculty.getFullName(), faculty.getTitle(), faculty.getSchoolName(),
				faculty.getDepartment(), faculty.getEmail(), faculty.getPhoneNumber());
		return compiledRecommendationTxt;
	}

}
