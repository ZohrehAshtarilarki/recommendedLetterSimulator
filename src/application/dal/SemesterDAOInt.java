package application.dal;

import java.sql.SQLException;
import java.util.List;

import application.model.Semester;

public interface SemesterDAOInt {
	List<Semester> getAllSemesters() throws SQLException;
	Semester getSemesterById(int semesterId)throws SQLException;

	/**
	 * 
	 * @param Semester
	 * @return Semester or null if retrieving ID from DB after insertion failed
	 * @throws SQLException
	 */
	Semester addSemester(String semesterName) throws SQLException;
	void updateSemester(Semester semester) throws SQLException;
	void deleteSemester(int semesterId) throws SQLException;
}
