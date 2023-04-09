package application.dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import application.model.Course;
import application.model.Faculty;

public class FacultyDAOImpl implements FacultyDAOInt {
	private Connection connection;
	private String tableName;
	
	/**
	 * Use this constructor for interacting with DB
	 * @param connection
	 */
	public FacultyDAOImpl(Connection connection) {
		this.connection = connection;
		this.tableName = "faculty";
	}
	
//	make DB connection a singleton?
	public FacultyDAOImpl(Connection connection, String tableName) {
		this.connection = connection;
		this.tableName = tableName;
		initFacultyDAO();
	}

	@Override
	public void updateFaculty() throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public Faculty getFaculty() throws SQLException {
		Faculty faculty = null;
		String sqlSelectFirstFacultyRow = String.format("SELECT * FROM %s LIMIT 1", tableName);
		Statement statement = connection.createStatement();
		ResultSet resultSet = statement.executeQuery(sqlSelectFirstFacultyRow);
		
		while (resultSet.next()) {
			faculty = new Faculty();
			faculty.setFullName(resultSet.getString("fullName"));
			faculty.setTitle(resultSet.getString("title"));
			faculty.setSchoolName(resultSet.getString("schoolName"));
			faculty.setDepartment(resultSet.getString("department"));
			faculty.setEmail(resultSet.getString("email"));
			faculty.setPhoneNumber(resultSet.getString("phonenumber"));
			faculty.setCoursesTaught(new ArrayList<Course>());
		}
		
		return faculty;
	}
	
	private void initFacultyDAO() {
		try {
			
			if (!tableExists(tableName)) {
				createFacultyTable();
				createInitalFacultyData();
				System.out.println("Faculty Table created successfully initialized.");
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
		
	}
	
	private boolean tableExists(String tableName) throws SQLException  {
		String sqlCheckIfFacultyTableEmpty = String.format("SELECT name FROM sqlite_master WHERE type='table' AND name='%s'", tableName);
		Statement statement = connection.createStatement();
		ResultSet resultSet = statement.executeQuery(sqlCheckIfFacultyTableEmpty);
		
		return resultSet.next();
	}
	
	private void createFacultyTable() throws SQLException {
		Statement statement = connection.createStatement();
		String sqlCreateFacultyTable = "CREATE TABLE faculty (id INTEGER PRIMARY KEY, fullName TEXT, title TEXT, schoolName TEXT, department TEXT, email TEXT, phoneNumber TEXT)";
		statement.executeUpdate(sqlCreateFacultyTable);
		statement.close();
	}
	
	private void createInitalFacultyData() throws SQLException {
		String defaultFullName = " Ahmad Yazdankhah";
		String defaultTitle = "Lecturer";
		String defaultSchoolName = "SJSU";
		String defaultDepartment = "CS Department";
		String defaultEmail = "ahmad.yazdankhah@sjsu.edu";
		String defaultPhoneNumber = "(123) 456-7890";
		
        // insert data into the table
        String sqlInitalFacultyData = String.format("INSERT INTO %s (fullName, title, schoolName, department, email, phoneNumber) VALUES (?, ?, ?, ?, ?, ?)", tableName);
        
        PreparedStatement preparedStatement = connection.prepareStatement(sqlInitalFacultyData);
        // inject values into query string
        preparedStatement.setString(1, defaultFullName);
        preparedStatement.setString(2, defaultTitle);
        preparedStatement.setString(3, defaultSchoolName);
        preparedStatement.setString(4, defaultDepartment);
        preparedStatement.setString(5, defaultEmail);
        preparedStatement.setString(6, defaultPhoneNumber);
        
        preparedStatement.executeUpdate();
        preparedStatement.close();

        System.out.println("inital/default Faculty Data inserted successfully.");
	}

}
