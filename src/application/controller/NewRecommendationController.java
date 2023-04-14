package application.controller;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class NewRecommendationController {

	@FXML Button homePageButton;
	@FXML Button resetPasswordButton;
	@FXML TextField stuFirstName;
	@FXML TextField stuLastName;
	@FXML Button logoutButton;
	@FXML ComboBox<String> genderButton;
	@FXML TextField schoolName;
	@FXML DatePicker datePicker;

	@FXML public void homePageOp() {
		
		try {
			Stage stage = (Stage) homePageButton.getScene().getWindow();
			stage.close();
			Stage primaryStage = new Stage();
			Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("view/HomePage.fxml"));
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

	@FXML public void genderButtonOp() {
		
		//ObservableList<String> gender = FXCollections.observableList("Male", "Female");
		
		genderButton.getItems().addAll("Male", "Female");
	}

	@FXML public void datePickerOp() {
		
		datePicker.getValue().toString();
	}

}
