package application.dal;

import java.sql.SQLException;

import application.model.Faculty;

public interface FacultyDAOInt {
	public void updateFaculty() throws SQLException;
	public Faculty getFaculty() throws SQLException;

}
