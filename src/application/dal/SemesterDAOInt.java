package application.dal;

import java.sql.SQLException;
import java.util.List;

import application.model.Semester;

public interface SemesterDAOInt {
	public List<Semester> getAllSemesters() throws SQLException;
	
	/**
	 * 
	 * @param Semester
	 * @return Semester or null if retrieving ID from DB after insertion failed
	 * @throws SQLException
	 */
	public Semester addSemester(String semesterName) throws SQLException;
	public void updateSemester(Semester semester) throws SQLException;
	public void deleteSemester(int semesterId) throws SQLException;
}
