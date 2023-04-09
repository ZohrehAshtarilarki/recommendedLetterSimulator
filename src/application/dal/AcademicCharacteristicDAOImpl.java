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
		List<AcademicCharacteristic> AcademicCharacteristics = new ArrayList<>();
		
		String sqlSelectAllAcademicCharacteristics = String.format("SELECT * FROM %s", tableName);
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(sqlSelectAllAcademicCharacteristics);
        
        while (resultSet.next()) {
        	AcademicCharacteristic personalCharacteristic = new AcademicCharacteristic();
        	personalCharacteristic.setAcademicCharacteristicId(resultSet.getInt("id"));
        	personalCharacteristic.setCharacteristic(resultSet.getString("characteristic"));
        	AcademicCharacteristics.add(personalCharacteristic);
        }
        resultSet.close();
        statement.close();
		return AcademicCharacteristics;
	}

	@Override
	public void addAcademicCharacteristic(AcademicCharacteristic academicCharacteristic) throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateAcademicCharacteristic(AcademicCharacteristic academicCharacteristic) throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public void deleteAcademicCharacteristic(int academicCharacteristicId) throws SQLException {
		// TODO Auto-generated method stub

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
