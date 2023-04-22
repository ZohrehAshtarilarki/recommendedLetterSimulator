package application.dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

import application.model.AcademicCharacteristic;
import application.model.AcademicProgram;
import application.model.RecommendationCourse;
import application.model.Gender;
import application.model.PersonalCharacteristic;
import application.model.Recommendation;
import application.model.Semester;
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
		try (ResultSet resultSet = new DbUtils().getRowbyId(connection, recommendationId, tableName)) {
			List<Recommendation> extractedRecommendations = extractRecommendationsFromResultSet(resultSet);
			return extractedRecommendations.size() > 0 ? extractedRecommendations.get(0) : null ;
		}
	}

	/**
	 * Returns null if valid Recommendation Id not found
	 */
	@Override
	public Recommendation addRecommendation(String firstName, String lastName, String targetSchoolName,
			String currentDate, String firstSemesterYear, Gender gender, Semester semester, AcademicProgram academicProgram,
			ArrayList<AcademicCharacteristic> academicCharacteristics, ArrayList<PersonalCharacteristic> personalCharacteristics,
			ArrayList<RecommendationCourse> coursesTaken) throws SQLException {
		
//		Start Actual logic
        String sqlAddRecommendation = String.format("INSERT INTO %s (studentFirstName, studentLastName, targetSchoolName, currentDate, firstSemesterYear, gender, semester_id, academicProgram_id) VALUES (?, ?, ?, ?, ?, ?, ?, ?)", tableName);
        PreparedStatement preparedStatement = connection.prepareStatement(sqlAddRecommendation, Statement.RETURN_GENERATED_KEYS);
        preparedStatement.setString(1, firstName);
        preparedStatement.setString(2, lastName);
        preparedStatement.setString(3, targetSchoolName);
        preparedStatement.setString(4, currentDate);
        preparedStatement.setString(5, firstSemesterYear);
        preparedStatement.setString(6, gender.name());
        preparedStatement.setInt(7, semester.getSemsterId());
        preparedStatement.setInt(8, academicProgram.getAcademicProgramId());
        
    	int numberOfUpdatedRows = preparedStatement.executeUpdate();
    	
    	if (numberOfUpdatedRows == 0) {
    		throw new SQLException("Adding Recommendation failed, no rows affected.");
    	}
    	
    	int newRowId = new DbUtils().getIdfromResultSet(preparedStatement);
    	
//    	Id not found
    	if(newRowId == -1) {
    		return null;
    	}
    	
    	Recommendation newRecommendation = new Recommendation();
    	newRecommendation.setRecommendationId(newRowId);
    	newRecommendation.setStudentFirstName(firstName);
    	newRecommendation.setStudentLastName(lastName);
    	newRecommendation.setTargetSchoolName(targetSchoolName);
    	newRecommendation.setCurrentDate(currentDate);
    	newRecommendation.setFirstSemesterYear(firstSemesterYear);
    	newRecommendation.setGender(gender);
    	newRecommendation.setSemester(semester);
    	newRecommendation.setProgram(academicProgram);
    	
    	
//    	insert new recommendation many-to-many stuff into DB
    	insertManyToManyRecommendation_academicCharacteristics(newRecommendation.getRecommendationId(), academicCharacteristics);
    	insertManyToManyRecommendation_personalCharacteristic(newRecommendation.getRecommendationId(), personalCharacteristics);    	
    	insertManyToManyRecommendation_course(newRecommendation.getRecommendationId(), coursesTaken);
    	
    	newRecommendation.setAcademicCharacteristics(academicCharacteristics);
    	newRecommendation.setPersonalCharacteristics(personalCharacteristics);
    	newRecommendation.setCoursesTaken(coursesTaken);
    	
    	preparedStatement.close();
		
		return newRecommendation;
	}

	@Override
	public void deleteRecommendation(int recommendationId) throws SQLException {
//		might not delete the junction row entries associated with the deleted recommendation
		new DbUtils().deleteRowById(recommendationId, this.tableName, this.connection);
	}

	@Override
	public void updateRecommendation(Recommendation recommendation) throws SQLException {
		String sqlUpdateUser = String.format("UPDATE %s SET "
				+ "studentFirstName=?, studentLastName=?, targetSchoolName=?, "
				+ "currentDate=?, firstSemesterYear=?, gender=?, "
				+ "semester_id=?, academicProgram_id=? "
				+ "WHERE id=?",
				tableName);		
		PreparedStatement preparedStatement = connection.prepareStatement(sqlUpdateUser);
        preparedStatement.setString(1, recommendation.getStudentFirstName());
        preparedStatement.setString(2, recommendation.getStudentLastName());
        preparedStatement.setString(3, recommendation.getTargetSchoolName());
        preparedStatement.setString(4, recommendation.getCurrentDate());
        preparedStatement.setString(5, recommendation.getFirstSemesterYear());
        preparedStatement.setString(6, recommendation.getGender().name());
        preparedStatement.setInt(7, recommendation.getSemester().getSemsterId());
        preparedStatement.setInt(8, recommendation.getProgram().getAcademicProgramId());
        preparedStatement.setInt(9, recommendation.getRecommendationId());
		
    	int numberOfUpdatedRows = preparedStatement.executeUpdate();
    	
    	if (numberOfUpdatedRows == 0) {
    		throw new SQLException("Adding Recommendation failed, no rows affected.");
    	}
    	

    	updateRecommendation_academicCharacteristicsInJunctionTable(
    			recommendation.getRecommendationId(),
    			"recommendation_academicCharacteristics",
    			"academicCharacteristic_id",
    			recommendation.getAcademicCharacteristics());
    	
    	updateRecommendation_personalCharacteristicInJunctionTable(
    			recommendation.getRecommendationId(),
    			"recommendation_personalCharacteristic",
    			"personalCharacteristic_id",
    			recommendation.getPersonalCharacteristics()
    			);
    	
    	updateRecommendation_CourseInJunctionTable(
    			recommendation.getRecommendationId(),
    			"recommendation_course",
    			"course_id",
    			recommendation.getCoursesTaken());
    	
		preparedStatement.close();
	}

	@Override
	public List<Recommendation> searchRecommendationByLastName(String lastName) throws SQLException {
		String columnName = "studentLastName";
		String sqlSearchByLName = String.format("SELECT * FROM %s WHERE %s = ?", this.tableName, columnName);
		PreparedStatement preparedStatement = connection.prepareStatement(sqlSearchByLName);
		preparedStatement.setString(1, lastName);
		
		try (ResultSet resultSet = preparedStatement.executeQuery()) {
			return extractRecommendationsFromResultSet(resultSet);
		}
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
    	String sqlJunctionCustomRecommendationCourse = "CREATE TABLE %s (%s_id INTEGER, %s_id INTEGER, grade Text, FOREIGN KEY (%s_id) REFERENCES %s(%s), FOREIGN KEY (%s_id) REFERENCES %s(%s))";
		String courseTableName = "course";
		String courseColumnIdName = "id";
		dbUtils.createManyToManyJunctionTabelBetweenModels(connection, courseJunctionTableName, recommendationColumnIdName, this.tableName, courseColumnIdName, courseTableName, sqlJunctionCustomRecommendationCourse);
	
	}
	
	private void insertManyToManyRecommendation_academicCharacteristics(int newRecommendationId, List<AcademicCharacteristic> academicCharacteristics) throws SQLException {
    	String academicCharacteristicsJunctionTableName = "recommendation_academicCharacteristics";
    	String academicCharacteristicsTableName = "academicCharacteristic";
    	for (AcademicCharacteristic academicCharacteristic : academicCharacteristics) {
    		if (academicCharacteristic.getAcademicCharacteristicId() == -1) {
    			System.out.println("Warning: academicCharacteristics with id -1 during addRecommendation()");
    		}
        	String sqlManyToManyInsert = String.format("INSERT INTO %s (%s_id, %s_id) VALUES (?, ?)", academicCharacteristicsJunctionTableName, this.tableName, academicCharacteristicsTableName);
        	try (PreparedStatement preparedStatementManyToMany = connection.prepareStatement(sqlManyToManyInsert)) {
            	preparedStatementManyToMany.setInt(1, newRecommendationId);
            	preparedStatementManyToMany.setInt(2, academicCharacteristic.getAcademicCharacteristicId());
            	int numberOfUpdatedRows = preparedStatementManyToMany.executeUpdate();
            	if (numberOfUpdatedRows == 0) {
            		throw new SQLException("Adding to Recommendation_academicCharacteristics failed, no rows affected.");
            	}
        	}
		}
	}
	
	private void insertManyToManyRecommendation_personalCharacteristic(int newRecommendationId, List<PersonalCharacteristic> personalCharacteristics) throws SQLException {
		String personalCharacteristicsJunctionTableName = "recommendation_personalCharacteristic";
		String personalCharacteristicsTableName = "personalCharacteristic";
    	for (PersonalCharacteristic personalCharacteristic : personalCharacteristics) {
    		if (personalCharacteristic.getPersonalCharacteristicId() == -1) {
    			System.out.println("Warning: personalCharacteristic with id -1 during addRecommendation()");
    		}
        	String sqlManyToManyInsert = String.format("INSERT INTO %s (%s_id, %s_id) VALUES (?, ?)", personalCharacteristicsJunctionTableName, this.tableName, personalCharacteristicsTableName);
        	try (PreparedStatement preparedStatementManyToMany = connection.prepareStatement(sqlManyToManyInsert)) {
            	preparedStatementManyToMany.setInt(1, newRecommendationId);
            	preparedStatementManyToMany.setInt(2, personalCharacteristic.getPersonalCharacteristicId());
            	int numberOfUpdatedRows = preparedStatementManyToMany.executeUpdate();
            	if (numberOfUpdatedRows == 0) {
            		throw new SQLException("Adding to recommendation_personalCharacteristic failed, no rows affected.");
            	}
        	}
		}
	}
	
	private void insertManyToManyRecommendation_course(int newRecommendationId, List<RecommendationCourse> courses) throws SQLException {
		String courseJunctionTableName = "recommendation_course";
		String courseTableName = "course";
    	for (RecommendationCourse course : courses) {
    		if (course.getCourseId() == -1) {
    			System.out.println("Warning: course with id -1 during addRecommendation()");
    		}
        	String sqlManyToManyInsert = String.format("INSERT INTO %s (%s_id, %s_id, grade) VALUES (?, ?, ?)", courseJunctionTableName, this.tableName, courseTableName);
        	try (PreparedStatement preparedStatementManyToMany = connection.prepareStatement(sqlManyToManyInsert)) {
            	preparedStatementManyToMany.setInt(1, newRecommendationId);
            	preparedStatementManyToMany.setInt(2, course.getCourseId());
            	preparedStatementManyToMany.setString(3, course.getGrade());
            	int numberOfUpdatedRows = preparedStatementManyToMany.executeUpdate();
            	if (numberOfUpdatedRows == 0) {
            		throw new SQLException("Adding to recommendation_course failed, no rows affected.");
            	}
        	}
		}
	}
	
	private List<AcademicCharacteristic> getAllRecommendation_academicCharacteristicsInJunctionTable(int recommendationId) throws SQLException {
		ArrayList<AcademicCharacteristic> academicCharacteristics = new ArrayList<>();
    	String academicCharacteristicsJunctionTableName = "recommendation_academicCharacteristics";
    	String academicCharacteristicsTableName = "academicCharacteristic";
		String sqlJunctionJoinAcadChar = String.format("SELECT %s.id, %s.characteristic " 
				+ "FROM %s "
				+ "INNER JOIN %s ON %s.id = %s.%s_id "
				+ "WHERE %s.recommendation_id = ?", 
				academicCharacteristicsTableName, academicCharacteristicsTableName, academicCharacteristicsTableName,
				academicCharacteristicsJunctionTableName, academicCharacteristicsTableName, academicCharacteristicsJunctionTableName, academicCharacteristicsTableName,
				academicCharacteristicsJunctionTableName);
		
		try (PreparedStatement preparedStatement = connection.prepareStatement(sqlJunctionJoinAcadChar)) {
			preparedStatement.setInt(1, recommendationId);
			ResultSet resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
	        	AcademicCharacteristic academicCharacteristic = new AcademicCharacteristic();
	        	academicCharacteristic.setAcademicCharacteristicId(resultSet.getInt("id"));
	        	academicCharacteristic.setCharacteristic(resultSet.getString("characteristic"));
	        	academicCharacteristics.add(academicCharacteristic);
			}
		}
		return academicCharacteristics;
	}
	
	
	private List<PersonalCharacteristic> getAllRecommendation_personalCharacteristicInJunctionTable(int recommendationId) throws SQLException {
		ArrayList<PersonalCharacteristic> personalCharacteristics = new ArrayList<>();
		String personalCharacteristicsJunctionTableName = "recommendation_personalCharacteristic";
		String personalCharacteristicsTableName = "personalCharacteristic";
		String sqlJunctionJoinPerChar = String.format("SELECT %s.id, %s.characteristic " 
				+ "FROM %s "
				+ "INNER JOIN %s ON %s.id = %s.%s_id "
				+ "WHERE %s.recommendation_id = ?", 
				personalCharacteristicsTableName, personalCharacteristicsTableName, personalCharacteristicsTableName,
				personalCharacteristicsJunctionTableName, personalCharacteristicsTableName, personalCharacteristicsJunctionTableName, personalCharacteristicsTableName,
				personalCharacteristicsJunctionTableName);
		
		try (PreparedStatement preparedStatement = connection.prepareStatement(sqlJunctionJoinPerChar)) {
			preparedStatement.setInt(1, recommendationId);
			ResultSet resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
	        	PersonalCharacteristic personalCharacteristic = new PersonalCharacteristic();
	        	personalCharacteristic.setPersonalCharacteristicId(resultSet.getInt("id"));
	        	personalCharacteristic.setCharacteristic(resultSet.getString("characteristic"));
	        	personalCharacteristics.add(personalCharacteristic);
			}
		}
		return personalCharacteristics;
	}
	
	private List<RecommendationCourse> getAllRecommendation_courseInJunctionTable(int recommendationId) throws SQLException {
		ArrayList<RecommendationCourse> courses = new ArrayList<>();
		String courseJunctionTableName = "recommendation_course";
		String courseTableName = "course";
		String sqlJunctionJoin = String.format("SELECT %s.id, %s.name, %s.prefix, %s.prefixNumber, %s.grade " 
				+ "FROM %s "
				+ "INNER JOIN %s ON %s.id = %s.%s_id "
				+ "WHERE %s.recommendation_id = ?", 
				courseTableName, courseTableName, courseTableName, courseTableName, courseJunctionTableName, courseTableName,
				courseJunctionTableName, courseTableName, courseJunctionTableName, courseTableName,
				courseJunctionTableName);
		try (PreparedStatement preparedStatement = connection.prepareStatement(sqlJunctionJoin)) {
			preparedStatement.setInt(1, recommendationId);
			ResultSet resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				RecommendationCourse course = new RecommendationCourse();
	        	course.setCourseId(resultSet.getInt("id"));
	        	course.setName(resultSet.getString("name"));
	        	course.setPrefix(resultSet.getString("prefix"));
	        	course.setPrefixNumber(resultSet.getInt("prefixNumber"));
	        	course.setGrade(resultSet.getString("grade"));
	        	courses.add(course);
			}
		}
		return courses;
	}
	
	private List<Recommendation> extractRecommendationsFromResultSet(ResultSet rs) {
		ArrayList<Recommendation> recommendations = new ArrayList<>();
		
		try {
			while (rs.next()) {
				Recommendation recommendation = new Recommendation();
				recommendation.setRecommendationId(rs.getInt("id"));
				recommendation.setStudentFirstName(rs.getString("studentFirstName"));
				recommendation.setStudentLastName(rs.getString("studentLastName"));
				recommendation.setTargetSchoolName(rs.getString("targetSchoolName"));
				recommendation.setCurrentDate(rs.getString("currentDate"));
				recommendation.setFirstSemesterYear(rs.getString("firstSemesterYear"));
				recommendation.setGender(Gender.valueOf(rs.getString("gender")));
//				might be a better ways using join statement and instantiating each model and setting attributes manually
				recommendation.setSemester(CommonDAOs.getInstance().getSemesterDAO().getSemesterById(rs.getInt("semester_id")));
				recommendation.setProgram(CommonDAOs.getInstance().getAcademicaProgramDAO().getAcademicProgramById(rs.getInt("academicProgram_id")));
//				get the many-to-many data and set
				recommendation.setAcademicCharacteristics(getAllRecommendation_academicCharacteristicsInJunctionTable(recommendation.getRecommendationId()));
				recommendation.setPersonalCharacteristics(getAllRecommendation_personalCharacteristicInJunctionTable(recommendation.getRecommendationId()));
				recommendation.setCoursesTaken(getAllRecommendation_courseInJunctionTable(recommendation.getRecommendationId()));
				recommendations.add(recommendation);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
			System.out.println("Failed to Extract query result from ResultSet in extractRecommendationsFromResultSet");
		}
		return recommendations;
	}
	
	private void updateRecommendation_academicCharacteristicsInJunctionTable(int recId, String JuncTableName, String otherForeignKeyColumnName, List<AcademicCharacteristic> newAcademicCharacteristics) throws SQLException {
    	List<AcademicCharacteristic> oldAcadChar = getAllRecommendation_academicCharacteristicsInJunctionTable(recId);
    	HashMap<Integer, AcademicCharacteristic> oldAcadCharToDel = new HashMap<>();
//    	convert to hash map, will contain the many-to-many objects to remove from DB
    	for (AcademicCharacteristic academicCharacteristic : oldAcadChar) {
    		oldAcadCharToDel.put(academicCharacteristic.getAcademicCharacteristicId(), academicCharacteristic);
		}
//    	will contain the new object to add to a Recommendation many-to-many table
    	LinkedList<AcademicCharacteristic> newAcadCharLL = new LinkedList<>(newAcademicCharacteristics);
    	ListIterator<AcademicCharacteristic> iter = newAcadCharLL.listIterator();
//    	Remove intersection from both hashMap and LL
    	while (iter.hasNext() && oldAcadCharToDel.size() != 0) {
    		AcademicCharacteristic academicCharacteristic = iter.next();
    		if (oldAcadCharToDel.containsKey(academicCharacteristic.getAcademicCharacteristicId())) {
    			oldAcadCharToDel.remove(academicCharacteristic.getAcademicCharacteristicId());
    			iter.remove();
    		}
    	}
//    	delete objects no longer in many-to-many updatedRecommendation object in DB
    	String sqlSelectManyToMany = String.format(
    			"DELETE "
    			+ "FROM %s WHERE "
    			+ "recommendation_id = %s AND %s = ?", JuncTableName, recId, otherForeignKeyColumnName);
    	
    	try (PreparedStatement ps = connection.prepareStatement(sqlSelectManyToMany)) {
    		for (Integer rowId : oldAcadCharToDel.keySet()) {
    			ps.setInt(1, rowId);
    			ps.executeUpdate();
    		}
		}
//    	add new many-to-many row objects to DB
    	insertManyToManyRecommendation_academicCharacteristics(recId, newAcadCharLL);
	}
	
	private void updateRecommendation_personalCharacteristicInJunctionTable(int recId, String JuncTableName, String otherForeignKeyColumnName, List<PersonalCharacteristic> newPersonalCharacteristics) throws SQLException {
    	List<PersonalCharacteristic> oldEntries = getAllRecommendation_personalCharacteristicInJunctionTable(recId);
    	HashMap<Integer, PersonalCharacteristic> oldEntriesToDel = new HashMap<>();
//    	convert to hash map, will contain the many-to-many objects to remove from DB
    	for (PersonalCharacteristic personalCharacteristic : oldEntries) {
    		oldEntriesToDel.put(personalCharacteristic.getPersonalCharacteristicId(), personalCharacteristic);
		}
//    	will contain the new object to add to a Recommendation many-to-many table
    	LinkedList<PersonalCharacteristic> newEntiresLL= new LinkedList<>(newPersonalCharacteristics);
    	ListIterator<PersonalCharacteristic> iter = newEntiresLL.listIterator();
//    	Remove intersection from both hashMap and LL
    	while (iter.hasNext() && oldEntriesToDel.size() != 0) {
    		PersonalCharacteristic personalCharacteristic = iter.next();
    		if (oldEntriesToDel.containsKey(personalCharacteristic.getPersonalCharacteristicId())) {
    			oldEntriesToDel.remove(personalCharacteristic.getPersonalCharacteristicId());
    			iter.remove();
    		}
    	}
//    	delete objects no longer in many-to-many updatedRecommendation object in DB
    	String sqlSelectManyToMany = String.format(
    			"DELETE "
    			+ "FROM %s WHERE "
    			+ "recommendation_id = %s AND %s = ?", JuncTableName, recId, otherForeignKeyColumnName);
    	
    	try (PreparedStatement ps = connection.prepareStatement(sqlSelectManyToMany)) {
    		for (Integer rowId : oldEntriesToDel.keySet()) {
    			ps.setInt(1, rowId);
    			ps.executeUpdate();
    		}
		}
//    	add new many-to-many row objects to DB
    	insertManyToManyRecommendation_personalCharacteristic(recId, newEntiresLL);
	}
	
	private void updateRecommendation_CourseInJunctionTable(int recId, String JuncTableName, String otherForeignKeyColumnName, List<RecommendationCourse> newCourses) throws SQLException {
    	List<RecommendationCourse> oldEntries = getAllRecommendation_courseInJunctionTable(recId);
    	HashMap<Integer, RecommendationCourse> oldEntriesToDel = new HashMap<>();
//    	convert to hash map, will contain the many-to-many objects to remove from DB
    	for (RecommendationCourse entry : oldEntries) {
    		oldEntriesToDel.put(entry.getCourseId(), entry);
		}
//    	will contain the new object to add to a Recommendation many-to-many table
    	LinkedList<RecommendationCourse> newEntiresLL= new LinkedList<>(newCourses);
    	ListIterator<RecommendationCourse> iter = newEntiresLL.listIterator();
//    	Remove intersection from both hashMap and LL
    	while (iter.hasNext() && oldEntriesToDel.size() != 0) {
    		RecommendationCourse course = iter.next();
    		if (oldEntriesToDel.containsKey(course.getCourseId())) {
    			oldEntriesToDel.remove(course.getCourseId());
    			iter.remove();
    		}
    	}
//    	delete objects no longer in many-to-many updatedRecommendation object in DB
    	String sqlSelectManyToMany = String.format(
    			"DELETE "
    			+ "FROM %s WHERE "
    			+ "recommendation_id = %s AND %s = ?", JuncTableName, recId, otherForeignKeyColumnName);
    	
    	try (PreparedStatement ps = connection.prepareStatement(sqlSelectManyToMany)) {
    		for (Integer rowId : oldEntriesToDel.keySet()) {
    			ps.setInt(1, rowId);
    			ps.executeUpdate();
    		}
		}
//    	add new many-to-many row objects to DB
    	insertManyToManyRecommendation_course(recId, newEntiresLL);
	}

}
