package application.dal;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import application.model.Recommendation;
import application.utils.DbUtils;

public class RecommendationDAOImpl implements RecommendationDAOInt {
	private Connection connection;
	private String tableName;
	
	/**
	 * Use this constructor for interacting with DB
	 * @param connection
	 */
	public RecommendationDAOImpl(Connection connection) {
		this.connection = connection;
		this.tableName = "recommendation";
	}
	
	/**
	 * Use this one for initial DB table set up
	 * @param connection
	 * @param tableName name of table to create for this model
	 */
	public RecommendationDAOImpl(Connection connection, String tableName) {
		this.connection = connection;
		this.tableName = tableName;
		initRecommendationDAO(new DbUtils());
	}

	@Override
	public Recommendation getRecommendation(int recommendationId) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Recommendation addRecommendation() throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteRecommendation(int recommendationId) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateRecommendation(Recommendation recommendation) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<Recommendation> searchRecommendationByLastName(String lastName) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}
	
	private void initRecommendationDAO(DbUtils dbUtils) {
		try {
			if (!dbUtils.tableExists(connection, tableName)) {
				createRecommendationTable(dbUtils);
				createJunctionDBTable(dbUtils);
				
			}
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
	}
	
	private void createRecommendationTable(DbUtils dbUtils) throws SQLException {
//		Creating Table column definitions
		ArrayList<String> tableColumnsAndTypes = new ArrayList<>();
		tableColumnsAndTypes.add("id INTEGER PRIMARY KEY");
		tableColumnsAndTypes.add("studentFirstName TEXT");
		tableColumnsAndTypes.add("studentLastName TEXT");
		tableColumnsAndTypes.add("targetSchoolName TEXT");
		tableColumnsAndTypes.add("currentDate TEXT");
		tableColumnsAndTypes.add("firstSemesterYear TEXT");
		tableColumnsAndTypes.add("gender TEXT");
		tableColumnsAndTypes.add("semester_id INTEGER NOT NULL");
		tableColumnsAndTypes.add("academicProgram_id INTEGER NOT NULL");
		tableColumnsAndTypes.add("FOREIGN KEY (semester_id) REFERENCES semester(id)");
		tableColumnsAndTypes.add("FOREIGN KEY (academicProgram_id) REFERENCES academicProgram(id)");
		
		dbUtils.createTable(connection, tableName, tableColumnsAndTypes);
	}
	
	private void createJunctionDBTable(DbUtils dbUtils) throws SQLException {
		String recommendationColumnIdName = "id";
		
		String academicCharacteristicsJunctionTableName = "recommendation_academicCharacteristics";
		String academicCharacteristicsTableName = "academicCharacteristic";
		String academicCharacteristicsColumnIdName = "id";
		dbUtils.createManyToManyJunctionTabelBetweenModels(connection, academicCharacteristicsJunctionTableName, recommendationColumnIdName, this.tableName, academicCharacteristicsColumnIdName, academicCharacteristicsTableName);
		
		String personalCharacteristicsJunctionTableName = "recommendation_personalCharacteristic";
		String personalCharacteristicsTableName = "personalCharacteristic";
		String personalCharacteristicsColumnIdName = "id";
		dbUtils.createManyToManyJunctionTabelBetweenModels(connection, personalCharacteristicsJunctionTableName, recommendationColumnIdName, this.tableName, personalCharacteristicsColumnIdName, personalCharacteristicsTableName);
		
		String courseJunctionTableName = "recommendation_course";
		String courseTableName = "course";
		String courseColumnIdName = "id";
		dbUtils.createManyToManyJunctionTabelBetweenModels(connection, courseJunctionTableName, recommendationColumnIdName, this.tableName, courseColumnIdName, courseTableName);
	
	}

}
