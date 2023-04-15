package application;
	
import java.sql.SQLException;

import application.dal.AcademicCharacteristicDAOInt;
import application.dal.AcademicProgramDAOInt;
import application.dal.CommonDAOs;
import application.dal.CourseDAOInt;
import application.dal.DbConnectionInt;
import application.dal.DbSqlite;
import application.dal.FacultyDAOInt;
import application.model.Course;
import application.model.Faculty;
import application.model.AcademicCharacteristic;
import application.model.AcademicProgram;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class Main extends Application {
	
	
	@Override
	public void start(Stage primaryStage) {
		initializeApp();
				
		try {
			
			Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("view/Main.fxml"));
			primaryStage.setScene(new Scene(root));
			root.getStylesheets().add(getClass().getClassLoader().getResource("css/application.css").toExternalForm());
			primaryStage.show();
			
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	private void initializeApp() {
		String uri = "jdbc:sqlite:test.db";
		DbConnectionInt dataBaseObj = new DbSqlite(uri);
		dataBaseObj.initializeDb();
		
		try {
			FacultyDAOInt facDAOs = CommonDAOs.getInstance().getFacultyDAO();
			Faculty fac = facDAOs.getFaculty();
			fac.setFullName("Jose Velasco");
			fac.setTitle("CS Student");
			fac.setSchoolName("SJSU");
			fac.setDepartment("Comp Sci");
			fac.setEmail("jose.velasco@gmail.com");
			fac.setPhoneNumber("987-456-1234");
			facDAOs.updateFaculty(fac);
			
			CourseDAOInt courseDAOs = CommonDAOs.getInstance().getCourseDAO();
			Course newCourse1 = courseDAOs.addCourses("Python", "CS", 9876);
			Course newCourse2 = courseDAOs.addCourses("Python II", "SWE", 1329);
			Course newCourse3 = courseDAOs.addCourses("C+++++++ II", "SCI", 999);
			
			newCourse1.setName("NEW Py course");
			newCourse1.setPrefix("NEW");
			newCourse1.setPrefixNumber(-1);
			courseDAOs.updateCourses(newCourse2);
			
			courseDAOs.deleteCourses(newCourse1.getCourseId());
			courseDAOs.deleteCourses(newCourse2.getCourseId());
			courseDAOs.deleteCourses(newCourse3.getCourseId());
			
			AcademicCharacteristicDAOInt acadCharDAO = CommonDAOs.getInstance().getAcademicCharacteristicDAO();
			AcademicCharacteristic acadChar1 = acadCharDAO.addAcademicCharacteristic("SUper Fast Learner");
			AcademicCharacteristic acadChar2 = acadCharDAO.addAcademicCharacteristic("SLOW Fast Learner");
			AcademicCharacteristic acadChar3 = acadCharDAO.addAcademicCharacteristic("LAZY!!");
			
			acadChar1.setCharacteristic("SUper Fast Learner III");
			acadCharDAO.updateAcademicCharacteristic(acadChar1);
			
			acadCharDAO.deleteAcademicCharacteristic(acadChar1.getAcademicCharacteristicId());
			acadCharDAO.deleteAcademicCharacteristic(acadChar2.getAcademicCharacteristicId());
			acadCharDAO.deleteAcademicCharacteristic(acadChar3.getAcademicCharacteristicId());
			
			AcademicProgramDAOInt acadProgramDAO = CommonDAOs.getInstance().getAcademicaProgramDAO();
			AcademicProgram acadProgram1 = acadProgramDAO.addAcademicProgram("English Program");
			AcademicProgram acadProgram2 = acadProgramDAO.addAcademicProgram("Math Program");
			AcademicProgram acadProgram3 = acadProgramDAO.addAcademicProgram("Master of Sleep");
			
			acadProgram1.setName("MASTER of ART");
			acadProgramDAO.updateAcademicProgram(acadProgram1);
			
			acadProgramDAO.deleteAcademicProgram(acadProgram1.getAcademicProgramId());
			acadProgramDAO.deleteAcademicProgram(acadProgram2.getAcademicProgramId());
			acadProgramDAO.deleteAcademicProgram(acadProgram3.getAcademicProgramId());
			
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}

	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
