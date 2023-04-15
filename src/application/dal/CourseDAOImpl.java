package application.dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import application.model.Course;
import application.utils.DbUtils;

public class CourseDAOImpl implements CourseDAOInt {
	private Connection connection;
	private String tableName;
	
	/**
	 * Use this constructor for interacting with DB
	 * @param connection
	 */
	public CourseDAOImpl(Connection connection) {
		this.tableName = "course";
		this.connection = connection;
	}
	
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
	public Course addCourses(String name, String prefix, int prefixNumber) throws SQLException {
        String sqlInitalCourseData = String.format("INSERT INTO %s (name, prefix, prefixNumber) VALUES (?, ?, ?)", tableName);
        PreparedStatement preparedStatement = connection.prepareStatement(sqlInitalCourseData, Statement.RETURN_GENERATED_KEYS);
    	preparedStatement.setString(1, name);
    	preparedStatement.setString(2, prefix);
    	preparedStatement.setInt(3, prefixNumber);
    	
    	preparedStatement.executeUpdate();
    	
    	Course newCourse = new Course();
    	newCourse.setName(name);
    	newCourse.setPrefix(prefix);
    	newCourse.setPrefixNumber(prefixNumber);
//    	gets the Id of new inserted course from DB
    	try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
    		if (generatedKeys.next()) {
    			newCourse.setCourseId(generatedKeys.getInt(1));
    		} else {
    			throw new SQLException("Failed to create new Coursem, no ID found.");
    		}
    	}
    	
    	preparedStatement.close();
    	
    	return newCourse.getCourseId() == -1 ? null : newCourse;
	}

	@Override
	public void updateCourses(Course course) throws SQLException {
		String sqlUpdateUser = String.format("UPDATE %s SET name=?, prefix=?, prefixNumber=? WHERE id=?", tableName);
		PreparedStatement preparedStatement = connection.prepareStatement(sqlUpdateUser);
    	preparedStatement.setString(1, course.getName());
    	preparedStatement.setString(2, course.getPrefix());
    	preparedStatement.setInt(3, course.getPrefixNumber());
    	preparedStatement.setInt(4, course.getCourseId());
    	
		preparedStatement.executeUpdate();
		preparedStatement.close();
	}

	@Override
	public void deleteCourses(int courseId) throws SQLException {	 
		 new DbUtils().deleteRowById(courseId, this.tableName, this.connection);
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
