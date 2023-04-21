package application.dal;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import application.model.AcademicCharacteristic;
import application.model.AcademicProgram;
import application.model.RecommendationCourse;
import application.model.Gender;
import application.model.PersonalCharacteristic;
import application.model.Recommendation;
import application.model.Semester;

public interface RecommendationDAOInt {
	/**
	 * 
	 * @param recommendationId
	 * @return Recommendation with Id of recommendationId else null if no Recommendation found
	 * @throws SQLException
	 */
	public Recommendation getRecommendation(int recommendationId) throws SQLException;
//	adjust parameters once model an DB stuff figure out
	public Recommendation addRecommendation(String firstName, String lastName, String targetSchoolName,
			String currentDate, String firstSemesterYear, Gender gender, Semester semester, AcademicProgram academicProgram,
			ArrayList<AcademicCharacteristic> academicCharacteristics, ArrayList<PersonalCharacteristic> personalCharacteristics,
			ArrayList<RecommendationCourse> coursesTaken) throws SQLException;
	public void deleteRecommendation(int recommendationId) throws SQLException;
	public void updateRecommendation(Recommendation recommendation) throws SQLException;
	public List<Recommendation> searchRecommendationByLastName(String lastName) throws SQLException;
	
}
