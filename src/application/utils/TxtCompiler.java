package application.utils;

import java.util.ArrayList;

import application.model.AcademicCharacteristic;
import application.model.Faculty;
import application.model.Gender;
import application.model.PersonalCharacteristic;
import application.model.Recommendation;
import application.model.RecommendationCourse;

public class TxtCompiler implements CompilerInt {
	private String genderPronoun;
	
	private String createHeader(String studentFullName, String todayDate) {
		String dateEmptySpaceAlignment = "                                                                                                                                                           ";
		return String.format
				( "Letter of Recommendation\n\n"
				+ "For: %s\n"
				+ "%sDate: %s\n"
				+ "To: Graduate Admissions Committee\n\n"
				, studentFullName, dateEmptySpaceAlignment, todayDate);
	}
	
	private String reasonOfWrittingLetter(String studentFullName, String programName) {
		return String.format(
				"I am writing this letter to recommend my former student %s who is applying \n"
				+ "for the %s in your school. \n\n"
						, studentFullName, programName);
	}
	
	private String metStudent(String studentFirstName, String firstSemester, String firstCourseTaken) {
		return String.format(
				"I met %s in %s when he enrolled in my course %s. \n\n",
				studentFirstName, firstSemester, firstCourseTaken);
	}
	
	private String toughCourse(String studentFirstName, String letterGrade) {
		return String.format(
				"%s earned %s from this tough course, and this shows how \n"
				+ "knowledgeable and hard worker %s is. \n\n",
				studentFirstName, letterGrade, this.genderPronoun);
	}
	
	private String moreCoursesWithProfessor(ArrayList<RecommendationCourse> courses) {
		String outputString = String.format("%s also earned ", this.genderPronoun);
		int newLinePerElement = 4;
		for (int i = 0; i < courses.size(); i++) {
			RecommendationCourse course = courses.get(i);
			outputString += String.format("\"%s\" from my \"%s\"", course.getGrade(), course.getName());
			outputString += addSentanceListRules(i, courses.size(), newLinePerElement);
		}
		outputString += courses.size() > 1 ? " courses.\n\n" : " course.\n\n";
		
		return outputString;
	}
	
	private String acadCharSection(String studentFirstName, ArrayList<AcademicCharacteristic> acadChars) {
		String outputString = String.format("%s ", studentFirstName);
		int newLinePerElement = 3;
		for (int i = 0; i < acadChars.size(); i++) {
			AcademicCharacteristic acadChar = acadChars.get(i);
			outputString += String.format("%s", acadChar.getCharacteristic());
			outputString += addSentanceListRules(i, acadChars.size(), newLinePerElement);
		}
		outputString += ".\n\n";
		
		return outputString;
	}
	
	private String perCharSection(ArrayList<PersonalCharacteristic> persChars) {
		String outputString = String.format("%s was always ", this.genderPronoun);
		int newLinePerElement = 3;
		for (int i = 0; i < persChars.size(); i++) {
			PersonalCharacteristic persChar = persChars.get(i);
			outputString += String.format("%s", persChar.getCharacteristic());
			outputString += addSentanceListRules(i, persChars.size(), newLinePerElement);
		}
		outputString += ".\n\n";
		return outputString;
	}
	
	private String termProjectSection() {
		return String.format(
				"Furthermore, I noticed from the term project result, %s developed leadership, time \n"
				+ "management, and problem-solving skills. %s worked effectively with the team members and \n"
				+ "delegated tasks appropriately. They were able to deliver a successful project in a timely fashion. \n\n",
				this.genderPronoun, this.genderPronoun);
	}
	
	private String excelHigerEducationSection(String studentFirstName) {
		return String.format(
				"I believe that %s has the capacity to excel at higher education program and this \n"
				+ "is my pleasure to highly recommend him. \n\n"
				+ "Please do not hesitate to contact me with further questions. \n\n\n",
				studentFirstName);
	}
	
	private String facultySignature(String facultyFullName, String facultyTitle, String schoolName,
			String departmentName, String facutlyEmail, String facultyPhoneNumber) {
		return String.format(
				"Very Respectfully,\n\n"
				+ "%s\n\n"
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
	
	private String addSentanceListRules(int currentElement, int maxElements, int newLinePerElement) {
		String outputString = "";
		if (currentElement != maxElements - 1) {
			outputString += ", ";
		}
		if (maxElements > 1 && currentElement == maxElements - 2) {
			outputString += "and ";
		}
		if ((currentElement+1) % newLinePerElement == 0 && currentElement < maxElements - 1) {
			outputString += "\n";
		}
		return outputString;
	}

	@Override
	public String compile(Faculty faculty, Recommendation recommendation) {
		String compiledRecommendationTxt = "";
		this.setGenderPronoun(recommendation.getGender());
		String studentFullName = recommendation.getStudentFirstName() + " " +recommendation.getStudentLastName();
		String todayDate = recommendation.getCurrentDate();
		compiledRecommendationTxt += createHeader(studentFullName, todayDate);
		compiledRecommendationTxt += reasonOfWrittingLetter(studentFullName, recommendation.getProgram().getName());
		compiledRecommendationTxt += metStudent(recommendation.getStudentFirstName(),
				recommendation.getSemester().getName()+ " " + recommendation.getFirstSemesterYear(),
				recommendation.getCoursesTaken().size() > 0 ? recommendation.getCoursesTaken().get(0).getName() : "No first Course Found");
		compiledRecommendationTxt += toughCourse(recommendation.getStudentFirstName(),
				recommendation.getCoursesTaken().size() > 0 ? recommendation.getCoursesTaken().get(0).getGrade() : "No Grade Found");
		compiledRecommendationTxt += moreCoursesWithProfessor(new ArrayList<>(recommendation.getCoursesTaken()));
		compiledRecommendationTxt += acadCharSection(recommendation.getStudentFirstName(), new ArrayList<>(recommendation.getAcademicCharacteristics()));
		compiledRecommendationTxt += perCharSection(new ArrayList<>(recommendation.getPersonalCharacteristics()));
		compiledRecommendationTxt += termProjectSection();
		compiledRecommendationTxt += excelHigerEducationSection(recommendation.getStudentFirstName());
		compiledRecommendationTxt += facultySignature(faculty.getFullName(), faculty.getTitle(), faculty.getSchoolName(),
				faculty.getDepartment(), faculty.getEmail(), faculty.getPhoneNumber());
		return compiledRecommendationTxt;
	}

}
