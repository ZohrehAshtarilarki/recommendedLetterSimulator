package application;

import java.io.IOException;

import application.dal.DbConnectionInt;
import application.dal.DbSqlite;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class Main extends Application {
	
	
	@Override
	public void start(Stage primaryStage) throws IOException {
		initializeApp();
			
		Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("view/Login.fxml"));
		primaryStage.setScene(new Scene(root));
		root.getStylesheets().add(getClass().getClassLoader().getResource("css/application.css").toExternalForm());
		primaryStage.show();
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
