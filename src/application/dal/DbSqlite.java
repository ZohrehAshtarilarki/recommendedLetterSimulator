package application.dal;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbSqlite implements DbConnectionInt {
	
	private Connection connection;
	
	public DbSqlite(String urlStr) {
		try {
//			should create .db file if it has not be already created
			this.connection = DriverManager.getConnection(urlStr);
			
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
	
	@Override
	public void initializeDb() {
//		default table names in db for each model
		String facultyDBTableName = "faculty";
		String courseDBTableName = "course";
		String academicProgramDBTableName = "academicProgram";
		String academicCharacteristicDBTableName = "academicCharacteristic";
		String personalCharacteristicDBTableName = "personalCharacteristic";
		String userDBTableName = "user";
		
		CommonDAOs commonDAOs = CommonDAOs.getInstance();
		commonDAOs.setDataBaseObj(this);
		
//		each model's table is created and default data inserted if the db has not been already created
//		add each DAO to the commonDAO
		commonDAOs.setUserDAO(new UserDAOImpl(connection, userDBTableName));
		commonDAOs.setFacultyDAO(new FacultyDAOImpl(connection, facultyDBTableName));
		commonDAOs.setCourseDAO(new CourseDAOImpl(connection, courseDBTableName));
		commonDAOs.setAcademicaProgramDAO(new AcademicProgramDAOImpl(connection, academicProgramDBTableName));
		commonDAOs.setPersonalCharacteristicDAO(new PersonalCharacteristicDAOImpl(connection, personalCharacteristicDBTableName));
		commonDAOs.setAcademicCharacteristicDAO(new AcademicCharacteristicDAOImpl(connection, academicCharacteristicDBTableName));
	}

}
