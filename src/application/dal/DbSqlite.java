package application.dal;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbSqlite implements DbConnectionInt {
	
	private Connection connection;
	
	public DbSqlite(String urlStr) {
		try {
			String url = "jdbc:sqlite:test.db";
//			should create .db file if it has not be already created
			this.connection = DriverManager.getConnection(url);
			
//			this.connection = DriverManager.getConnection(urlStr);
			
//			String sqlCreateDB = "CREATE DATABASE recommendationLetterSimulator";
//			Statement stmt = this.connection.createStatement();
//			stmt.executeUpdate(sqlCreateDB);
//			System.out.println("Database 'recommendationLetterSimulator' created successfully");
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public Connection getConnection() {
		// TODO Auto-generated method stub
		return this.connection;
	}

	@Override
	public void closeConnection() throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public void startTransaction() throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public void endTransaction() throws SQLException {
		// TODO Auto-generated method stub

	}

}
