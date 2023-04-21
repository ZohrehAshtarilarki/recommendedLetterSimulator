package application.dal;

public class CommonDAOs {
	private DbConnectionInt dataBaseObj;
	private final static CommonDAOs commonDAO = new CommonDAOs();
	private AcademicCharacteristicDAOInt academicCharacteristicDAO;
	private AcademicProgramDAOInt academicaProgramDAO;
	private CourseDAOInt courseDAO;
	private FacultyDAOInt facultyDAO;
	private PersonalCharacteristicDAOInt personalCharacteristicDAO;
	private UserDAOInt userDAO;
	private SemesterDAOInt semesterDAO;
	private RecommendationDAOInt recommendationDAO;
	
	private CommonDAOs() { }
	
	public static CommonDAOs getInstance() {
		return commonDAO;
	}
	
	public DbConnectionInt getDataBaseObj() {
		return dataBaseObj;
	}
	
	public void setDataBaseObj(DbConnectionInt newDbConnectionInt) {
		this.dataBaseObj = newDbConnectionInt;
	}
	
	public AcademicCharacteristicDAOInt getAcademicCharacteristicDAO() {
		return academicCharacteristicDAO;
	}

	public void setAcademicCharacteristicDAO(AcademicCharacteristicDAOInt academicCharacteristicDAO) {
		this.academicCharacteristicDAO = academicCharacteristicDAO;
	}

	public AcademicProgramDAOInt getAcademicaProgramDAO() {
		return academicaProgramDAO;
	}

	public void setAcademicaProgramDAO(AcademicProgramDAOInt academicaProgramDAO) {
		this.academicaProgramDAO = academicaProgramDAO;
	}

	public CourseDAOInt getCourseDAO() {
		return courseDAO;
	}

	public void setCourseDAO(CourseDAOInt courseDAO) {
		this.courseDAO = courseDAO;
	}

	public FacultyDAOInt getFacultyDAO() {
		return facultyDAO;
	}

	public void setFacultyDAO(FacultyDAOInt facultyDAO) {
		this.facultyDAO = facultyDAO;
	}

	public PersonalCharacteristicDAOInt getPersonalCharacteristicDAO() {
		return personalCharacteristicDAO;
	}

	public void setPersonalCharacteristicDAO(PersonalCharacteristicDAOInt personalCharacteristicDAO) {
		this.personalCharacteristicDAO = personalCharacteristicDAO;
	}

	public UserDAOInt getUserDAO() {
		return userDAO;
	}

	public void setUserDAO(UserDAOInt userDAO) {
		this.userDAO = userDAO;
	}
	
	public SemesterDAOInt getSemesterDAO() {
		return this.semesterDAO;
	}

	public void setSemesterDAO(SemesterDAOInt semesterDAO) {
		this.semesterDAO = semesterDAO;
	}

	public RecommendationDAOInt getRecommendationDAO() {
		return recommendationDAO;
	}

	public void setRecommendationDAO(RecommendationDAOInt recommendationDAO) {
		this.recommendationDAO = recommendationDAO;
	}
}
