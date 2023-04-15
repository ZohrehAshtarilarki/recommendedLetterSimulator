package application.dal;

import java.sql.SQLException;
import java.util.List;

import application.model.Course;

public interface CourseDAOInt {
	public List<Course> getAllCourses() throws SQLException;
	/**
	 * 
	 * @param name
	 * @param prefix
	 * @param prefixNumber
	 * @return Course or null if retrieving ID from DB after insertion failed
	 * @throws SQLException
	 */
	public Course addCourses(String name, String prefix, int prefixNumber) throws SQLException;
	public void updateCourses(Course course) throws SQLException;
	public void deleteCourses(int courseId) throws SQLException;

}
