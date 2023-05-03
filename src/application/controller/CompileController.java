package application.controller;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import application.dal.Authentication;
import application.dal.CommonDAOs;
import application.model.Faculty;
import application.model.Recommendation;
import application.utils.CompilerInt;
import application.utils.TxtCompiler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class CompileController implements Initializable {
	private CompilerInt compiler;

	@FXML Button compileButton;
	@FXML TextArea textArea;
	@FXML AnchorPane mainBox;
	@FXML Button newRecommendationButton;
	@FXML Button logoutButton;
	@FXML Button homeButton;
	
	private Authentication auth = Authentication.getInstance();
	
	public CompileController() {
		this.compiler = new TxtCompiler();
	}
	
	@FXML public void compileOp() {
		String fileName = String.format("Recommendation_%s.txt", LocalDate.now().format(DateTimeFormatter.ISO_LOCAL_DATE));
		String recommednationText = textArea.getText();
        try {
            File file = new File(fileName);
            FileWriter writer = new FileWriter(file);
            
            writer.write(recommednationText);
            writer.close();
            System.out.println("Successfully wrote to the file.");
        } catch (IOException e) {
            System.out.println("An error occurred while writing to the file.");
            e.printStackTrace();
        }
	}
	
	@FXML public void logOutOp() throws IOException {
		
		Stage stage = (Stage) logoutButton.getScene().getWindow();
		stage.close();
		Stage primaryStage = new Stage();
		Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("view/Login.fxml"));
		primaryStage.setScene(new Scene(root));
		primaryStage.show();
		auth.logout();
	}
	
	@FXML public void homeOp() throws IOException {
		
		Stage stage = (Stage) homeButton.getScene().getWindow();
		stage.close();
		Stage primaryStage = new Stage();
		Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("view/Home.fxml"));
		primaryStage.setScene(new Scene(root));
		primaryStage.show();
	}
	

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		CommonObjs commObj = CommonObjs.getInstance();
		CommonDAOs commDAOs = CommonDAOs.getInstance();
		Recommendation currentRecommednation = commObj.getActiveRecommendation();
		Faculty faculty = null;
		try {
			faculty = commDAOs.getFacultyDAO().getFaculty();
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
		String recommednationText = compiler.compile(faculty, currentRecommednation);
		textArea.setText(recommednationText);
		commObj.setActiveRecommendation(null);
	}

}
