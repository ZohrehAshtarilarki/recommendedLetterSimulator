package application.dal;

import java.sql.Connection;
import java.sql.SQLException;

public interface DbConnectionInt {
	
	public Connection getConnection();
	
	public void closeConnection() throws SQLException;
	
	public void startTransaction() throws SQLException;
	
	public void endTransaction() throws SQLException;
	
	public void initializeDb();
}
