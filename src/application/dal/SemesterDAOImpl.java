package application.dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import application.model.Semester;
import application.utils.DbUtils;

public class SemesterDAOImpl implements SemesterDAOInt {
	private Connection connection;
	private String tableName;
	
	/**
	 * Use this constructor for interacting with DB
	 * @param connection
	 */
	public SemesterDAOImpl(Connection connection) {
		this.connection = connection;
		this.tableName = "semester";
	}
	
	/**
	 * Use this one for initial DB table set up
	 * @param connection
	 * @param tableName name of table to create for this model
	 */
	public SemesterDAOImpl(Connection connection, String tableName) {
		this.connection = connection;
		this.tableName = tableName;
		initSemesterDAO(new DbUtils());
	}

	@Override
	public List<Semester> getAllSemesters() throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Semester addSemester(String semesterName) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void updateSemester(Semester semester) throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public void deleteSemester(int semesterId) throws SQLException {
		// TODO Auto-generated method stub

	}
	
	private void initSemesterDAO(DbUtils dbUtils) {
		try {
			if (!dbUtils.tableExists(connection, tableName)) {
//				Creating Table column definitions
				ArrayList<String> tableColumnsAndTypes = new ArrayList<>();
				tableColumnsAndTypes.add("id INTEGER PRIMARY KEY");
				tableColumnsAndTypes.add("name TEXT");
				
				dbUtils.createTable(connection, tableName, tableColumnsAndTypes);
				
//				setting up SQL query
				String sqlInitalAcademicProgramQuery = String.format("INSERT INTO %s (name) VALUES (?)", tableName);
				
//				each nested list is a row
				ArrayList<ArrayList<String>> rowsEnteries = new ArrayList<>();
				rowsEnteries.add(new ArrayList<>());
				rowsEnteries.add(new ArrayList<>());
				rowsEnteries.add(new ArrayList<>());
				rowsEnteries.add(new ArrayList<>());
				
//				Values to insert into table
				rowsEnteries.get(0).add("Fall");
				rowsEnteries.get(1).add("Winter");
				rowsEnteries.get(2).add("Spring");
				rowsEnteries.get(3).add("Summer");
				
//				inserting initial Data
				PreparedStatement preparedStatement = connection.prepareStatement(sqlInitalAcademicProgramQuery);
				dbUtils.insertDefaultStringOnlyGivenTableData(preparedStatement, rowsEnteries);
				
				preparedStatement.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
	}

}
