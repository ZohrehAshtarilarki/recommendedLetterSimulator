package application.dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import application.model.Course;

public class CourseDAOImpl implements CourseDAOInt {
	private Connection connection;
	private String tableName;
	
	public CourseDAOImpl(Connection connection, String tableName) {
		this.tableName = tableName;
		this.connection = connection;
		initCourseDAO();
	}

	@Override
	public List<Course> getAllCourses() throws SQLException {
		List<Course> courses = new ArrayList<>();
		
		String sqlSelectAllCourse = String.format("SELECT * FROM %s", tableName);
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(sqlSelectAllCourse);
        
        while (resultSet.next()) {
        	Course course = new Course();
        	course.setCourseId(resultSet.getInt("id"));
        	course.setName(resultSet.getString("name"));
        	course.setPrefix(resultSet.getString("prefix"));
        	course.setPrefixNumber(resultSet.getInt("prefixNumber"));
        	courses.add(course);
        }
        resultSet.close();
        statement.close();
		return courses;
	}

	@Override
	public void addCourses(Course course) throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateCourses(Course course) throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public void deleteCourses(Course course) throws SQLException {
		// TODO Auto-generated method stub

	}
	
	private void initCourseDAO() {
		try {
			if (!tableExists(tableName)) {
				createCourseTable();
				createInitialCourseData();
				System.out.println("course Table created successfully initialized.");
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
	}
	
	private boolean tableExists(String tableName) throws SQLException {
		String sqlCheckIfEmpty = String.format("SELECT name FROM sqlite_master WHERE type='table' AND name='%s'", tableName);
		Statement statement = connection.createStatement();
		ResultSet resultSet = statement.executeQuery(sqlCheckIfEmpty);
		
		return resultSet.next();
	}
	
	private void createCourseTable() throws SQLException {
		Statement statement = connection.createStatement();
		String sqlCreateCourseTable = String.format("CREATE TABLE %s (id INTEGER PRIMARY KEY, name TEXT, prefix TEXT, prefixNumber INTEGER)", tableName);
		
		statement.executeUpdate(sqlCreateCourseTable);
		statement.close();
	}
	
	private void createInitialCourseData() throws SQLException {
		ArrayList<Course> defaultCourses = new ArrayList<>();
		defaultCourses.add(new Course(-1,"Object-Oriented Design", "CS", 151));
		defaultCourses.add(new Course(-1,"Information Security", "CS", 166));
		defaultCourses.add(new Course(-1,"Theory of Computation", "CS", 154));
		defaultCourses.add(new Course(-1,"Software Engineering", "CS", 160));
		defaultCourses.add(new Course(-1,"Cryptography", "CS", 256));
		defaultCourses.add(new Course(-1,"Data Structures and Algorithms", "CS", 146));
		defaultCourses.add(new Course(-1,"Programming Languages Paradigm", "CS", 152));
		
        String sqlInitalCourseData = String.format("INSERT INTO %s (name, prefix, prefixNumber) VALUES (?, ?, ?)", tableName);
        PreparedStatement preparedStatement = connection.prepareStatement(sqlInitalCourseData);
        
        for (Course course : defaultCourses) {
        	preparedStatement.setString(1, course.getName());
        	preparedStatement.setString(2, course.getPrefix());
        	preparedStatement.setInt(3, course.getPrefixNumber());
        	preparedStatement.executeUpdate();
        }
        
        preparedStatement.close();
        System.out.println("inital/default Course Data inserted successfully.");

	}

}
