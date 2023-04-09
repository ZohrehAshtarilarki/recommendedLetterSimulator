package application.dal;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbSqlite implements DbConnectionInt {
	
	private Connection connection;
	
	public DbSqlite(String urlStr) {
		try {
//			String url = "jdbc:sqlite:test.db";
			
//			should create .db file if it has not be already created
			this.connection = DriverManager.getConnection(urlStr);
			createDefaultDBData();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public Connection getConnection() {
		return this.connection;
	}

	@Override
	public void closeConnection() throws SQLException {
		this.connection.close();
	}

	@Override
	public void startTransaction() throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public void endTransaction() throws SQLException {
		// TODO Auto-generated method stub

	}
	
	private void createDefaultDBData() {
		String facultyDBTableName = "faculty";
		String courseDBTableName = "course";
		String academicProgramDBTableName = "academicProgram";
		String academicCharacteristicDBTableName = "academicCharacteristic";
		String personalCharacteristicDBTableName = "personalCharacteristic";
		String userDBTableName = "user";
		
		new UserDAOImpl(connection, userDBTableName);
		new FacultyDAOImpl(connection, facultyDBTableName);
		new CourseDAOImpl(connection, courseDBTableName);
		new AcademicProgramDAOImpl(connection, academicProgramDBTableName);
		new PersonalCharacteristicDAOImpl(connection, personalCharacteristicDBTableName);
		new AcademicCharacteristicDAOImpl(connection, academicCharacteristicDBTableName);
	}

}
