package application.dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import application.model.AcademicCharacteristic;
import application.utils.DbUtils;

public class AcademicCharacteristicDAOImpl implements AcademicCharacteristicDAOInt {
	private Connection connection;
	private String tableName;
	
	
	/**
	 * Use this constructor one for database interactions
	 * 
	 * @param connection Db connection
	 */
	public AcademicCharacteristicDAOImpl(Connection connection) {
		this.connection = connection;
		this.tableName = "academicCharacteristic";
	}
	
	/**
	 * Not need to be called after DB has been initialized,
	 * most likely the one you need to use the 1 parameter constructor
	 * @param connection
	 * @param tableName name of table in DB
	 */
	public AcademicCharacteristicDAOImpl(Connection connection, String tableName) {
		this.connection = connection;
		this.tableName = tableName;
		initAcademicCharacteristicDAO(new DbUtils());
	}

	@Override
	public List<AcademicCharacteristic> getAllAcademicCharacteristics() throws SQLException {
		List<AcademicCharacteristic> academicCharacteristics = new ArrayList<>();
		
		String sqlSelectAllAcademicCharacteristics = String.format("SELECT * FROM %s", tableName);
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(sqlSelectAllAcademicCharacteristics);
        
        while (resultSet.next()) {
        	AcademicCharacteristic personalCharacteristic = new AcademicCharacteristic();
        	personalCharacteristic.setAcademicCharacteristicId(resultSet.getInt("id"));
        	personalCharacteristic.setCharacteristic(resultSet.getString("characteristic"));
        	academicCharacteristics.add(personalCharacteristic);
        }
        resultSet.close();
        statement.close();
		return academicCharacteristics;
	}

	@Override
	public AcademicCharacteristic addAcademicCharacteristic(String characteristic) throws SQLException {
        String sqlDataQuery = String.format("INSERT INTO %s (characteristic) VALUES (?)", tableName);
        PreparedStatement preparedStatement = connection.prepareStatement(sqlDataQuery, Statement.RETURN_GENERATED_KEYS);
    	preparedStatement.setString(1, characteristic);
    	
    	preparedStatement.executeUpdate();
    	
    	AcademicCharacteristic newAcademicCharacteristic = new AcademicCharacteristic();
    	newAcademicCharacteristic.setCharacteristic(characteristic);
//    	gets the Id of new inserted course from DB
    	try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
    		if (generatedKeys.next()) {
    			newAcademicCharacteristic.setAcademicCharacteristicId(generatedKeys.getInt(1));
    		} else {
    			throw new SQLException("Failed to create new AcademicCharacteristic, no ID found.");
    		}
    	}
    	
    	preparedStatement.close();
    	
    	return newAcademicCharacteristic.getAcademicCharacteristicId() == -1 ? null : newAcademicCharacteristic;
	}

	@Override
	public void updateAcademicCharacteristic(AcademicCharacteristic academicCharacteristic) throws SQLException {
		String sqlUpdateUser = String.format("UPDATE %s SET characteristic=? WHERE id=?", tableName);
		PreparedStatement preparedStatement = connection.prepareStatement(sqlUpdateUser);
    	preparedStatement.setString(1, academicCharacteristic.getCharacteristic());
    	preparedStatement.setInt(2, academicCharacteristic.getAcademicCharacteristicId());
    	
		preparedStatement.executeUpdate();
		preparedStatement.close();
	}

	@Override
	public void deleteAcademicCharacteristic(int academicCharacteristicId) throws SQLException {
		new DbUtils().deleteRowById(academicCharacteristicId, this.tableName, this.connection);
	}
	
	private void initAcademicCharacteristicDAO(DbUtils dbUtils) {
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
				rowsEnteries.add(new ArrayList<>());
				
//				Values to insert into table
				rowsEnteries.get(0).add("submitted well-written assignments");
				rowsEnteries.get(1).add("participated in all of my class activities");
				rowsEnteries.get(2).add("worked hard");
				rowsEnteries.get(3).add("was very well prepared for every exam and assignment");
				rowsEnteries.get(4).add("picked up new skills quickly");
				rowsEnteries.get(5).add("was able to excel academically at the top of my class");
				

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
