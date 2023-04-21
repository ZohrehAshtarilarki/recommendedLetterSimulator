package application.dal;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import application.model.AcademicProgram;
import application.model.Course;
import application.model.Gender;
import application.model.Semester;
import application.model.AcademicCharacteristic;
import application.model.PersonalCharacteristic;
import application.model.Recommendation;
import application.model.RecommendationCourse;
import java.util.ArrayList;

public class DbSqlite implements DbConnectionInt {
	
	private Connection connection;
	
	public DbSqlite(String urlStr) {
		try {
//			should create .db file if it has not be already created
			this.connection = DriverManager.getConnection(urlStr);
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public Connection getConnection() {
		return this.connection;
	}

	@Override
	public void closeConnection() throws SQLException {
		this.connection.close();
	}

	@Override
	public void startTransaction() throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public void endTransaction() throws SQLException {
		// TODO Auto-generated method stub

	}
	
	@Override
	public void initializeDb() {
//		default table names in DB for each model
		String facultyDBTableName = "faculty";
		String courseDBTableName = "course";
		String academicProgramDBTableName = "academicProgram";
		String academicCharacteristicDBTableName = "academicCharacteristic";
		String personalCharacteristicDBTableName = "personalCharacteristic";
		String userDBTableName = "user";
		String semsterDBTableName = "semester";
		String recommendationDBTableName = "recommendation";
		
		CommonDAOs commonDAOs = CommonDAOs.getInstance();
		commonDAOs.setDataBaseObj(this);
		
//		each model's table is created and default data inserted if the db has not been already created
//		add each DAO to the commonDAO
		commonDAOs.setUserDAO(new UserDAOImpl(connection, userDBTableName));
		commonDAOs.setFacultyDAO(new FacultyDAOImpl(connection, facultyDBTableName));
		commonDAOs.setCourseDAO(new CourseDAOImpl(connection, courseDBTableName));
		commonDAOs.setAcademicaProgramDAO(new AcademicProgramDAOImpl(connection, academicProgramDBTableName));
		commonDAOs.setPersonalCharacteristicDAO(new PersonalCharacteristicDAOImpl(connection, personalCharacteristicDBTableName));
		commonDAOs.setAcademicCharacteristicDAO(new AcademicCharacteristicDAOImpl(connection, academicCharacteristicDBTableName));
		commonDAOs.setSemesterDAO(new SemesterDAOImpl(connection, semsterDBTableName));
		
//		must be later than the columns recommendation has a relationship with other models in DB
		commonDAOs.setRecommendationDAO(new RecommendationDAOImpl(connection, recommendationDBTableName));
		
		
		
		
		
		
		
//		Testing new DB stuff many-to-many etc
		
		try {
			String firstName = "Jose";
			String lastName = "Velasco";
			String targetSchoolName = "San Jose State University";
			String currentDate = "05/23/2023";
			String firstSemesterYear = "2023";
			Gender gender = Gender.OTHER;
			Semester semester = commonDAOs.getSemesterDAO().getSemesterById(1);
			AcademicProgram academicProgram = commonDAOs.getAcademicaProgramDAO().getAcademicProgramById(1);
	
//			many-to-many relationship
			ArrayList<AcademicCharacteristic> academicCharacteristics = new ArrayList<>(commonDAOs.getAcademicCharacteristicDAO().getAllAcademicCharacteristics());
			ArrayList<PersonalCharacteristic> personalCharacteristics = new ArrayList<>(commonDAOs.getPersonalCharacteristicDAO().getAllPersonalCharacteristics());
			ArrayList<Course> coursesTaken = new ArrayList<>(commonDAOs.getCourseDAO().getAllCourses());
			ArrayList<RecommendationCourse> coursesTakenWithGrade = new ArrayList<>();
			for (Course course : coursesTaken) {
				RecommendationCourse tmpCourseWithGrade = new RecommendationCourse();
				tmpCourseWithGrade.setCourseId(course.getCourseId());
				tmpCourseWithGrade.setName(course.getName());
				tmpCourseWithGrade.setPrefix(course.getPrefix());
				tmpCourseWithGrade.setPrefixNumber(course.getPrefixNumber());
				tmpCourseWithGrade.setGrade("A+");
				coursesTakenWithGrade.add(tmpCourseWithGrade);
			}
			
			commonDAOs.getRecommendationDAO().addRecommendation(
					firstName,
					lastName,
					targetSchoolName,
					currentDate,
					firstSemesterYear,
					gender,
					semester,
					academicProgram,
					academicCharacteristics,
					personalCharacteristics,
					coursesTakenWithGrade);
			Recommendation testRec = commonDAOs.getRecommendationDAO().getRecommendation(2);
			System.out.println("TestRec:!!");
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
	}

}
