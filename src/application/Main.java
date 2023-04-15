package application;

import application.dal.DbConnectionInt;
import application.dal.DbSqlite;
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
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
