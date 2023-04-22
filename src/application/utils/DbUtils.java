package application.utils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class DbUtils {

	public boolean tableExists(Connection connection, String tableName) throws SQLException {
		String sqlCheckIfEmpty = String.format("SELECT name FROM sqlite_master WHERE type='table' AND name='%s'", tableName);
		Statement statement = connection.createStatement();
		ResultSet resultSet = statement.executeQuery(sqlCheckIfEmpty);
		
		return resultSet.next();
	}
	
	/**
	 * Example of what the Array list could looks like
	 *	ArrayList<String> colAndTypes = new ArrayList<>();
	 *	colAndTypes.add("id INTEGER PRIMARY KEY");
	 *	colAndTypes.add("fullName TEXT");
	 *	colAndTypes.add("title TEXT");
	 *	colAndTypes.add("schoolName TEXT");
	 * @param connection
	 * @param tableName
	 * @param tableColumnsAndTypes
	 * @throws SQLException
	 */
	public void createTable(Connection connection, String tableName, List<String> tableColumnsAndTypes) throws SQLException {
		Statement statement = connection.createStatement();
		String sqlCreateCourseTable = String.format("CREATE TABLE %s (", tableName);
		
//		build query from given column and type EX. 'id INTEGER PRIMARY KEY', 'fullName TEXT'
		for (int i = 0; i < tableColumnsAndTypes.size(); i++) {
			if (i != 0) {
				sqlCreateCourseTable = sqlCreateCourseTable + ", ";
			}
			sqlCreateCourseTable = sqlCreateCourseTable + tableColumnsAndTypes.get(i);
		}
		sqlCreateCourseTable = sqlCreateCourseTable + ")";
		
		statement.executeUpdate(sqlCreateCourseTable);
		statement.close();
		
		System.out.println("Created Table with sql query:");
		System.out.println(sqlCreateCourseTable);
	}
	
	/**
	 * Example:
	 * prepare statement: INSERT INTO %s (name, prefix, prefixNumber) VALUES (?, ?, ?)
	 * 
	 * Example:
	 * String sqlInitalAcademicProgramQuery = String.format("INSERT INTO %s (name) VALUES (?)", tableName);
	 * ArrayList<String> rowEntery = new ArrayList<>();
	 * rowEntery.add("Master of science (MS)");
	 * rowEntery.add("Master of business administration (MBA)");
	 * rowEntery.add("Doctor of philosophy (PhD)");
	 * ArrayList<ArrayList<String>> rowsEnteries = new ArrayList<>();
	 * rowsEnteries.add(rowEntery);
	 * 
	 * @param preparedStatement
	 * @param NumOfValuesPerRow number of values in the preparedStatement EX. (?, ?, ?) NumOfValuesPerRow would be 3
	 * @param rowEntries values that will be inserted into row, each nested list is a row
	 * @throws SQLException
	 */
	public void insertDefaultStringOnlyGivenTableData(PreparedStatement preparedStatement, ArrayList<ArrayList<String>> rowEntries) throws SQLException {
		int numOfValuesPerRow = rowEntries.get(0).size();
		for (ArrayList<String>  row : rowEntries) {
			for (int i = 1; i <= numOfValuesPerRow; i++) {
				preparedStatement.setString(i, row.get(i - 1));
				preparedStatement.executeUpdate();
			}
		}
		System.out.println("inital/default Data inserted successfully, NumOfValuesPerRow = " + numOfValuesPerRow);
	}
	
	/**
	 * closes PreparedStatement once method completes
	 * @param rowId
	 * @param tableName
	 * @param connection
	 * @throws SQLException
	 */
	public void deleteRowById(int rowId, String tableName, Connection connection) throws SQLException {
		 String sqlDelete = String.format("DELETE FROM %s WHERE id = ?", tableName);
		 PreparedStatement preparedStatement = connection.prepareStatement(sqlDelete);
		 preparedStatement.setInt(1, rowId);
		 preparedStatement.executeUpdate();
		 preparedStatement.close();
	}
	
	/**
	 * Make sure to closed returned ResultSet to avoid memory leaks
	 * @param rowId
	 * @param tableName
	 * @param connection
	 * @return
	 * @throws SQLException
	 */
	public ResultSet getRowbyId(Connection connection, int rowId, String tableName) throws SQLException {
		String sqlSelectRowById = String.format("SELECT * FROM %s WHERE id = ?", tableName);
		PreparedStatement prepareStatement = connection.prepareStatement(sqlSelectRowById);
		prepareStatement.setInt(1, rowId);
		ResultSet resultSet = prepareStatement.executeQuery();
		return resultSet;
	}
	
	public void createManyToManyJunctionTabelBetweenModels(Connection connection, String junctionTableName, String modelIdColumnName1, String modelTableName1, String modelIdColumnName2, String modelTableName2) throws SQLException {
		Statement statement = connection.createStatement();
		String UnformattedbaseSQLStr = "CREATE TABLE %s (%s_id INTEGER, %s_id INTEGER, FOREIGN KEY (%s_id) REFERENCES %s(%s), FOREIGN KEY (%s_id) REFERENCES %s(%s))";
		String sqlCreateJuctionTable = String.format(UnformattedbaseSQLStr, junctionTableName, modelTableName1, modelTableName2, modelTableName1, modelTableName1, modelIdColumnName1, modelTableName2, modelTableName2, modelIdColumnName2);
		statement.execute(sqlCreateJuctionTable);
		statement.close();
		System.out.println("Junction table created successfully: " + junctionTableName);
	}
	
	public void createManyToManyJunctionTabelBetweenModels(Connection connection, String junctionTableName, String modelIdColumnName1, String modelTableName1, String modelIdColumnName2, String modelTableName2, String UnformattedbaseSQLStr) throws SQLException {
		Statement statement = connection.createStatement();
		String sqlCreateJuctionTable = String.format(UnformattedbaseSQLStr, junctionTableName, modelTableName1, modelTableName2, modelTableName1, modelTableName1, modelIdColumnName1, modelTableName2, modelTableName2, modelIdColumnName2);
		statement.execute(sqlCreateJuctionTable);
		statement.close();
		System.out.println("Junction table created successfully: " + junctionTableName);
	}
	
	/**
	 * Statement.RETURN_GENERATED_KEYS needs to be passed as argument 
	 * during PreparedStatement instantiation from a Connection object
	 * @param rs
	 * @return -1 if no id found else id extracted from ResultSet
	 */
	public int getIdfromResultSet(PreparedStatement ps) throws SQLException {
//    	gets the Id of new inserted course from DB
		int id = -1;
    	try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
    		if (generatedKeys.next()) {
    			id = generatedKeys.getInt(1);
    		} else {
    			throw new SQLException("Failed to get ID in getIdfromResultSet, no ID found.");
    		}
    	}
    	return id;
	}
}
