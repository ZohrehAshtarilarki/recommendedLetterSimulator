package application;
	
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.sql.SQLException;
import java.util.List;

import application.model.AcademicCharacteristic;
import application.model.AcademicProgram;
import application.model.Course;
import application.model.Faculty;
import application.model.PersonalCharacteristic;
import application.model.User;

import application.dal.AcademicProgramDAOImpl;
import application.dal.CourseDAOImpl;
import application.dal.DbSqlite;
import application.dal.FacultyDAOImpl;
import application.dal.PersonalCharacteristicDAOImpl;
import application.dal.AcademicCharacteristicDAOImpl;
import application.dal.UserDAOImpl;

public class Main extends Application {
	
	
	@Override
	public void start(Stage primaryStage) {
		
		try {
			
			Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("view/Main.fxml"));
			primaryStage.setScene(new Scene(root));
			root.getStylesheets().add(getClass().getClassLoader().getResource("css/application.css").toExternalForm());
			primaryStage.show();
			
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		
//		##################### Testing DB stuff can delete #####################
		DbSqlite db = new DbSqlite("");
		FacultyDAOImpl facultyDAO = new FacultyDAOImpl(db.getConnection(), "faculty");
		CourseDAOImpl courseDAO = new CourseDAOImpl(db.getConnection(), "course");
		AcademicProgramDAOImpl academicProgramDAO = new AcademicProgramDAOImpl(db.getConnection(), "academicProgram");
		
		try {
			
//			testing faculty stuff
			Faculty fac = facultyDAO.getFaculty();
			System.out.println(fac.toString());
			
			
//			testing Courses stuff
			System.out.println("### COURSES TEST ###");
			List<Course> SavedInDBCourses = courseDAO.getAllCourses();
			for (Course course : SavedInDBCourses) {
				System.out.println(course.toString());
			}
			
//			testing AcademicProgram stuff
			System.out.println("### AcademicProgram TEST ###");
			List<AcademicProgram> SavedInDBAcademicProgram = academicProgramDAO.getAllAcademicPrograms();
			for (AcademicProgram course : SavedInDBAcademicProgram) {
				System.out.println(course.toString());
			}
			
//			testing PersonalCharacteristic stuff
			System.out.println("### PersonalCharacteristic TEST ###");
			PersonalCharacteristicDAOImpl personalCharacteristicDAO = new PersonalCharacteristicDAOImpl(db.getConnection(), "personalCharacteristic");
			List<PersonalCharacteristic> SavedInDBPersonalCharacteristic = personalCharacteristicDAO.getAllPersonalCharacteristics();
			for (PersonalCharacteristic personalCharacteristic : SavedInDBPersonalCharacteristic) {
				System.out.println(personalCharacteristic.toString());
			}
			
//			testing AcademicCharacteristic stuff
			System.out.println("### AcademicCharacteristic TEST ###");
			AcademicCharacteristicDAOImpl academicCharacteristicDAO = new AcademicCharacteristicDAOImpl(db.getConnection(), "academicCharacteristic");
			List<AcademicCharacteristic> SavedInDBAcademicCharacteristic = academicCharacteristicDAO.getAllAcademicCharacteristics();
			for (AcademicCharacteristic academicCharacteristic : SavedInDBAcademicCharacteristic) {
				System.out.println(academicCharacteristic.toString());
			}
			
//			testing User stuff
			System.out.println("### User TEST ###");
			UserDAOImpl userDAO = new UserDAOImpl(db.getConnection(), "user");
			User user = userDAO.getUser();
			System.out.println(user);
			
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
		
//		##################### END DB test #####################

		launch(args);
	}
	
	
	
}
