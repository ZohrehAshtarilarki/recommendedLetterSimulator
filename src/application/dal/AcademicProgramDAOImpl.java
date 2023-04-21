package application.dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import application.model.AcademicProgram;
import application.model.Semester;
import application.utils.DbUtils;

public class AcademicProgramDAOImpl implements AcademicProgramDAOInt {
	private Connection connection;
	private String tableName;
	
	/**
	 * Use this constructor for interacting with DB
	 * @param connection
	 */
	public AcademicProgramDAOImpl(Connection connection) {
		this.connection = connection;
		this.tableName = "academicProgram";
	}
	
	/**
	 * Use this one for initial DB table set up
	 * @param connection
	 * @param tableName name of table to create for this model
	 */
	public AcademicProgramDAOImpl(Connection connection, String tableName) {
		this.connection = connection;
		this.tableName = tableName;
		initAcademicProgramDAO(new DbUtils());
	}

	@Override
	public List<AcademicProgram> getAllAcademicPrograms() throws SQLException {
		List<AcademicProgram> academicPrograms = new ArrayList<>();
		
		String sqlSelectAllCourse = String.format("SELECT * FROM %s", tableName);
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(sqlSelectAllCourse);
        
        while (resultSet.next()) {
        	AcademicProgram academicProgram = new AcademicProgram();
        	academicProgram.setAcademicProgramId(resultSet.getInt("id"));
        	academicProgram.setName(resultSet.getString("name"));
        	academicPrograms.add(academicProgram);
        }
        resultSet.close();
        statement.close();
		return academicPrograms;
	}
	
	@Override
	public AcademicProgram getAcademicProgramById(int academicProgramId) throws SQLException {
		AcademicProgram academicProgram = null;
		try (ResultSet resultSet = new DbUtils().getRowbyId(connection, academicProgramId, tableName)) {
			while (resultSet.next()) {
				academicProgram = new AcademicProgram();
				academicProgram.setAcademicProgramId(resultSet.getInt("id"));
				academicProgram.setName(resultSet.getString("name"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
			System.out.println("Failed DbUtils().getRowbyId in getAcademicProgramById in AcademicProgram Object");
		}
		return academicProgram;
	}

	@Override
	public AcademicProgram addAcademicProgram(String academicProgram) throws SQLException {
        String sqlInsertDataQuery = String.format("INSERT INTO %s (name) VALUES (?)", tableName);
        PreparedStatement preparedStatement = connection.prepareStatement(sqlInsertDataQuery, Statement.RETURN_GENERATED_KEYS);
    	preparedStatement.setString(1, academicProgram);
    	
    	preparedStatement.executeUpdate();
    	
    	AcademicProgram newAcademicProgram = new AcademicProgram();
    	newAcademicProgram.setName(academicProgram);
//    	gets the Id of new inserted course from DB
    	try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
    		if (generatedKeys.next()) {
    			newAcademicProgram.setAcademicProgramId(generatedKeys.getInt(1));
    		} else {
    			throw new SQLException("Failed to create new AcademicProgram, no ID found.");
    		}
    	}
    	
    	preparedStatement.close();
    	
    	return newAcademicProgram.getAcademicProgramId() == -1 ? null : newAcademicProgram;
	}

	@Override
	public void updateAcademicProgram(AcademicProgram academicProgram) throws SQLException {
		String sqlUpdateUser = String.format("UPDATE %s SET name=? WHERE id=?", tableName);
		PreparedStatement preparedStatement = connection.prepareStatement(sqlUpdateUser);
    	preparedStatement.setString(1, academicProgram.getName());
    	preparedStatement.setInt(2, academicProgram.getAcademicProgramId());
    	
    	int numberOfUpdatedRows = preparedStatement.executeUpdate();
    	
    	if (numberOfUpdatedRows == 0) {
    		throw new SQLException("Updating AcademicProgram failed, no rows affected.");
    	}
		preparedStatement.close();
	}

	@Override
	public void deleteAcademicProgram(int academicProgramId) throws SQLException {
		new DbUtils().deleteRowById(academicProgramId, this.tableName, this.connection);
	}
	
	private void initAcademicProgramDAO(DbUtils dbUtils) {
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
				
//				Values to insert into table
				rowsEnteries.get(0).add("Master of science (MS)");
				rowsEnteries.get(1).add("Master of business administration (MBA)");
				rowsEnteries.get(2).add("Doctor of philosophy (PhD)");
				
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
