package application.controller;

import java.io.IOException;

import application.dal.Authentication;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class FacultyDashboardController {

	@FXML Button resetPasswordButton;
	@FXML Button newRecommendationButton;
	@FXML Button facultySignatureButton;
	@FXML Button logoutButton;
	@FXML Button searchButton;
	
	private Authentication auth = Authentication.getInstance();
	

	@FXML public void resetPasswordOp() throws IOException {
		
		Stage stage = (Stage) resetPasswordButton.getScene().getWindow();
		stage.close();
		Stage primaryStage = new Stage();
		Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("view/ResetPassword.fxml"));
		primaryStage.setScene(new Scene(root));
		primaryStage.show();
	}

	@FXML public void newRecommendationOp() throws IOException {
		
		Stage stage = (Stage) newRecommendationButton.getScene().getWindow();
		stage.close();
		Stage primaryStage = new Stage();
		Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("view/NewRecommendation.fxml"));
		primaryStage.setScene(new Scene(root));
		primaryStage.show();
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

	@FXML public void facultySignatureOp() throws IOException {
		
		Stage stage = (Stage) facultySignatureButton.getScene().getWindow();
		stage.close();
		Stage primaryStage = new Stage();
		Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("view/FacultySignature.fxml"));
		primaryStage.setScene(new Scene(root));
		primaryStage.show();
		auth.logout();
	}

	@FXML public void searchRecommendationOp() throws IOException {
		
		Stage stage = (Stage) searchButton.getScene().getWindow();
		stage.close();
		Stage primaryStage = new Stage();
		Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("view/SearchPage.fxml"));
		primaryStage.setScene(new Scene(root));
		primaryStage.show();
		auth.logout();
	}

}
