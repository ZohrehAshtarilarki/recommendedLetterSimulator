package application;
	
import application.controller.CommonObjs;
import application.dal.DbSqlite;
import application.dal.FacultyDAOImpl;
import application.dal.UserDAOImpl;
import application.model.PersonalCharacteristic;
import application.dal.CommonDAOs;
import application.dal.CourseDAOImpl;
import application.dal.DbConnectionInt;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;


public class Main extends Application {
	
	
	@Override
	public void start(Stage primaryStage) {
		
		String uri = "jdbc:sqlite:test.db";
		DbConnectionInt dataBaseObj = new DbSqlite(uri);
		CommonObjs commonObjs = CommonObjs.getInstance();
		commonObjs.setDataBaseObj(dataBaseObj);
		
		CommonDAOs commonDAOs = CommonDAOs.getInstance();
		commonDAOs.setDataBaseObj(dataBaseObj);
		
		commonDAOs.setUserDAO(new UserDAOImpl(dataBaseObj.getConnection()));
		commonDAOs.setFacultyDAO(new FacultyDAOImpl(dataBaseObj.getConnection()));
		commonDAOs.setCourseDAO(new CourseDAOImpl(dataBaseObj.getConnection()));
		
		
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
		launch(args);
	}
}
