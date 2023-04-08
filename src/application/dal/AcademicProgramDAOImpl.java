package application.dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import application.model.AcademicProgram;
import application.utils.DbUtils;

public class AcademicProgramDAOImpl implements AcademicProgramDAOInt {
	private Connection connection;
	private String tableName;
	
	public AcademicProgramDAOImpl(Connection connection, String tableName) {
		this.connection = connection;
		this.tableName = tableName;
		initAcademicProgramDAO(new DbUtils());
	}

	@Override
	public List<AcademicProgram> getAllAcademicPrograms() throws SQLException {
		List<AcademicProgram> courses = new ArrayList<>();
		
		String sqlSelectAllCourse = String.format("SELECT * FROM %s", tableName);
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(sqlSelectAllCourse);
        
        while (resultSet.next()) {
        	AcademicProgram course = new AcademicProgram();
        	course.setAcademicProgramId(resultSet.getInt("id"));
        	course.setName(resultSet.getString("name"));
        	courses.add(course);
        }
        resultSet.close();
        statement.close();
		return courses;
	}

	@Override
	public void addAcademicProgram(AcademicProgram academicProgram) throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateAcademicProgram(AcademicProgram academicProgram) throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public void deleteAcademicProgram(int academicProgramId) throws SQLException {
		// TODO Auto-generated method stub

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