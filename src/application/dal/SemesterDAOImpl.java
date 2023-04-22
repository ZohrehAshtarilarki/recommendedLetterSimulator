package application.dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import application.model.AcademicCharacteristic;
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
		List<Semester> semesters = new ArrayList<>();
		
		String sqlSelectAllRowsQuery= String.format("SELECT * FROM %s", tableName);
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(sqlSelectAllRowsQuery);
        
        while (resultSet.next()) {
        	Semester semester = new Semester();
        	semester.setSemesterId(resultSet.getInt("id"));;
        	semester.setName(resultSet.getString("name"));;
        	semesters.add(semester);
        }
        resultSet.close();
        statement.close();
		return semesters;
	}
	
	@Override
	public Semester getSemesterById(int semesterId) throws SQLException {
		Semester semester = null;
		
		try (ResultSet resultSet = new DbUtils().getRowbyId(connection, semesterId, tableName)) {
			while (resultSet.next()) {
				semester = new Semester();
				semester.setSemesterId(resultSet.getInt("id"));
				semester.setName(resultSet.getString("name"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
			System.out.println("Failed DbUtils().getRowbyId in getSemesterById in Semster Object");
		}
		return semester;
	}

	@Override
	public Semester addSemester(String semesterName) throws SQLException {
        String sqlAddDataQuery = String.format("INSERT INTO %s (name) VALUES (?)", tableName);
        PreparedStatement preparedStatement = connection.prepareStatement(sqlAddDataQuery, Statement.RETURN_GENERATED_KEYS);
    	preparedStatement.setString(1, semesterName);
    	
    	int numberOfUpdatedRows = preparedStatement.executeUpdate();
    	
    	if (numberOfUpdatedRows == 0) {
    		throw new SQLException("Adding Semester failed, no rows affected.");
    	}
    	
//    	gets the Id of new inserted entry from DB
    	int newRowId = new DbUtils().getIdfromResultSet(preparedStatement);
    	
//    	Id not found
    	if(newRowId == -1) {
    		return null;
    	}
    	
    	Semester semester = new Semester();
    	semester.setName(semesterName);
    	semester.setSemesterId(newRowId);
    	
    	preparedStatement.close();
    	
    	return semester;
	}

	@Override
	public void updateSemester(Semester semester) throws SQLException {
		String sqlUpdateUser = String.format("UPDATE %s SET name=? WHERE id=?", tableName);
		PreparedStatement preparedStatement = connection.prepareStatement(sqlUpdateUser);
    	preparedStatement.setString(1, semester.getName());
    	preparedStatement.setInt(2, semester.getSemsterId());
    	
		preparedStatement.executeUpdate();
		preparedStatement.close();
	}

	@Override
	public void deleteSemester(int semesterId) throws SQLException {
		new DbUtils().deleteRowById(semesterId, this.tableName, this.connection);
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
