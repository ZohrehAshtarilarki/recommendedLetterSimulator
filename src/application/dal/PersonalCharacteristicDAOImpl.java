package application.dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import application.model.PersonalCharacteristic;
import application.utils.DbUtils;

public class PersonalCharacteristicDAOImpl implements PersonalCharacteristicDAOInt {
	private Connection connection;
	private String tableName;

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
	public void addPersonalCharacteristic(PersonalCharacteristic personalCharacteristic) throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public void updatePersonalCharacteristic(PersonalCharacteristic personalCharacteristic) throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public void deletePersonalCharacteristic(int personalCharacteristicId) throws SQLException {
		// TODO Auto-generated method stub

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
