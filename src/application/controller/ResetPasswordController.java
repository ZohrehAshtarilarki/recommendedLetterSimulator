package application.controller;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.stage.Stage;

public class ResetPasswordController {
	
	@FXML private Button logoutButton;
	@FXML PasswordField newPasswordButton;
	@FXML PasswordField oldPasswordButton;
	@FXML Button resetPasswordButton;
	@FXML Button goBackButton;
	
	Boolean oldPasswValidation = false;
	

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


	@FXML public Boolean oldPasswordOp() {
		
		try {
			String oldPassw = oldPasswordButton.getText(); // Get old password from user
			if(oldPassw.isEmpty()) {
				Alert alert = new Alert(AlertType.ERROR);
				alert.setHeaderText(null);
				alert.setContentText("Please fill out all the requierd fields");
				alert.showAndWait();
			}
			//else if(oldPassw.equals("p") || oldPassw.equals(user.getPassword())) {
			else if(oldPassw.equals("p") || oldPassw.equals("Zohreh")) {
				oldPasswValidation = true;
				}
		
		} catch (Exception e) {
			e.printStackTrace();
			}
		return oldPasswValidation;
	}
	
	
	@FXML public Boolean newPasswordOp() {
		
		try {
			String newPassw = newPasswordButton.getText(); // Get new password from user
			if(newPassw.isEmpty()) {
				Alert alert = new Alert(AlertType.ERROR);
				alert.setHeaderText(null);
				alert.setContentText("Please fill out all the requierd fields");
				alert.showAndWait();
			}
			else if (newPasswordButton.getText().equals("Sasan")){
				oldPasswValidation = true;
				//user.setPassword();
			}
			else {
				oldPasswValidation = false;
			}
					
		} catch (Exception e) {
				
				e.printStackTrace();
			}
		return oldPasswValidation;
	}


	@FXML public void resetPasswordOp() {
		
		if(oldPasswordOp() && newPasswordOp()) {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setHeaderText(null);
			alert.setContentText("Successfully reset the password");
			alert.showAndWait();
		}
		else {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setHeaderText(null);
			alert.setContentText("The credentials entered are not correct");
			alert.showAndWait();
		}
	}


	@FXML public void goBackOp() {
		
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
}
