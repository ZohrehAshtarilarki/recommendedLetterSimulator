package application.controller;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class HomePageController {

	@FXML Button newRecommendationButton;
	@FXML Button logoutButton;
	@FXML Button resetPasswordButton;

	@FXML public void newRecommendationOp() {
		
		try {
		Stage stage = (Stage) newRecommendationButton.getScene().getWindow();
		stage.close();
		Stage primaryStage = new Stage();
		Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("view/NewRecommendation.fxml"));
		primaryStage.setScene(new Scene(root));
		primaryStage.show();
		
		} catch (IOException e) {
			
			e.printStackTrace();
		}
	}

	@FXML public void logOutOp() {
		
		try {
			Stage stage = (Stage) logoutButton.getScene().getWindow();
			stage.close();
			Stage primaryStage = new Stage();
			Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("view/Main.fxml"));
			primaryStage.setScene(new Scene(root));
			primaryStage.show();
			
		} catch (IOException e) {
			
			e.printStackTrace();
		}
	}

	@FXML public void resetPasswordOp() {
		
		try {
			Stage stage = (Stage) resetPasswordButton.getScene().getWindow();
			stage.close();
			Stage primaryStage = new Stage();
			Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("view/ResetPassword.fxml"));
			primaryStage.setScene(new Scene(root));
			primaryStage.show();
			
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		
	}

}
