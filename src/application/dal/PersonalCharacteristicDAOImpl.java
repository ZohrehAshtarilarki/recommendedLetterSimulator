package application.dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import application.model.AcademicProgram;
import application.model.PersonalCharacteristic;
import application.utils.DbUtils;

public class PersonalCharacteristicDAOImpl implements PersonalCharacteristicDAOInt {
	private Connection connection;
	private String tableName;
	
	/**
	 * Use this constructor for interacting with DB
	 * @param connection
	 */
	public PersonalCharacteristicDAOImpl(Connection connection) {
		this.connection = connection;
		this.tableName = "personalCharacteristic";
	}

	public PersonalCharacteristicDAOImpl(Connection connection, String tableName) {
		this.connection = connection;
		this.tableName = tableName;
		initPersonalCharacteristicDAO(new DbUtils());
	}
	
	@Override
	public List<PersonalCharacteristic> getAllPersonalCharacteristics() throws SQLException {
		List<PersonalCharacteristic> personalCharacteristics = new ArrayList<>();
		
		String sqlSelectAllPersonalCharacteristics = String.format("SELECT * FROM %s", tableName);
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(sqlSelectAllPersonalCharacteristics);
        
        while (resultSet.next()) {
        	PersonalCharacteristic personalCharacteristic = new PersonalCharacteristic();
        	personalCharacteristic.setPersonalCharacteristicId(resultSet.getInt("id"));
        	personalCharacteristic.setCharacteristic(resultSet.getString("characteristic"));
        	personalCharacteristics.add(personalCharacteristic);
        }
        resultSet.close();
        statement.close();
		return personalCharacteristics;
	}

	@Override
	public PersonalCharacteristic addPersonalCharacteristic(String characteristic) throws SQLException {
        String sqlInsertDataQuery = String.format("INSERT INTO %s (characteristic) VALUES (?)", tableName);
        PreparedStatement preparedStatement = connection.prepareStatement(sqlInsertDataQuery, Statement.RETURN_GENERATED_KEYS);
    	preparedStatement.setString(1, characteristic);
    	
    	preparedStatement.executeUpdate();
    	
    	PersonalCharacteristic newPersonalCharacteristic = new PersonalCharacteristic();
    	newPersonalCharacteristic.setCharacteristic(characteristic);;
//    	gets the Id of new inserted course from DB
    	try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
    		if (generatedKeys.next()) {
    			newPersonalCharacteristic.setPersonalCharacteristicId(generatedKeys.getInt(1));
    		} else {
    			throw new SQLException("Failed to create new PersonalCharacteristic, no ID found.");
    		}
    	}
    	
    	preparedStatement.close();
    	
    	return newPersonalCharacteristic.getPersonalCharacteristicId() == -1 ? null : newPersonalCharacteristic;
	}

	@Override
	public void updatePersonalCharacteristic(PersonalCharacteristic personalCharacteristic) throws SQLException {
		String sqlUpdateUser = String.format("UPDATE %s SET characteristic=? WHERE id=?", tableName);
		PreparedStatement preparedStatement = connection.prepareStatement(sqlUpdateUser);
    	preparedStatement.setString(1, personalCharacteristic.getCharacteristic());
    	preparedStatement.setInt(2, personalCharacteristic.getPersonalCharacteristicId());
    	
    	int numberOfUpdatedRows = preparedStatement.executeUpdate();
    	
    	if (numberOfUpdatedRows == 0) {
    		throw new SQLException("Updating PersonalCharacteristic failed, no rows affected.");
    	}
		preparedStatement.close();
	}

	@Override
	public void deletePersonalCharacteristic(int personalCharacteristicId) throws SQLException {
		new DbUtils().deleteRowById(personalCharacteristicId, this.tableName, this.connection);
	}
	
	private void initPersonalCharacteristicDAO(DbUtils dbUtils) {
		try {
			if (!dbUtils.tableExists(connection, tableName)) {
//				Creating Table column definitions
				ArrayList<String> tableColumnsAndTypes = new ArrayList<>();
				tableColumnsAndTypes.add("id INTEGER PRIMARY KEY");
				tableColumnsAndTypes.add("characteristic TEXT");
				
				dbUtils.createTable(connection, tableName, tableColumnsAndTypes);
				
//				setting up SQL query
				String sqlInitalQuery = String.format("INSERT INTO %s (characteristic) VALUES (?)", tableName);
				
//				each nested list is a row
				ArrayList<ArrayList<String>> rowsEnteries = new ArrayList<>();
				rowsEnteries.add(new ArrayList<>());
				rowsEnteries.add(new ArrayList<>());
				rowsEnteries.add(new ArrayList<>());
				rowsEnteries.add(new ArrayList<>());
				rowsEnteries.add(new ArrayList<>());
				
//				Values to insert into table
				rowsEnteries.get(0).add("very passionate");
				rowsEnteries.get(1).add("very enthusiastic");
				rowsEnteries.get(2).add("punctual");
				rowsEnteries.get(3).add("attentive");
				rowsEnteries.get(4).add("polite");
				

//				inserting initial Data
				PreparedStatement preparedStatement = connection.prepareStatement(sqlInitalQuery);
				dbUtils.insertDefaultStringOnlyGivenTableData(preparedStatement, rowsEnteries);
				
				preparedStatement.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
	}

}
