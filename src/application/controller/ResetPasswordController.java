package application.controller;

import java.io.IOException;
import java.sql.SQLException;

import application.dal.Authentication;
import application.dal.CommonDAOs;
import application.model.User;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class ResetPasswordController {
	
	@FXML private Button logoutButton;
	@FXML PasswordField newPasswordButton;
	@FXML PasswordField confirmPasswordButton;
	@FXML Button savePasswordButton;
	@FXML Button goBackButton;
	@FXML Label showMessage;
	private CommonDAOs comDAO = CommonDAOs.getInstance();
	private Authentication auth = Authentication.getInstance();
	
	@FXML public void logOutOp() {
		
		try {
			Stage stage = (Stage) logoutButton.getScene().getWindow();
			stage.close();
			Stage primaryStage = new Stage();
			Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("view/Main.fxml"));
			primaryStage.setScene(new Scene(root));
			primaryStage.show();
			auth.logout();
			
		} catch (IOException e) {
			
			e.printStackTrace();
		}
	}


	@FXML public void savePasswordOp() {
		
		try {
			
			String newPassw = newPasswordButton.getText();
			String confirmPassw = confirmPasswordButton.getText();
			User user = comDAO.getUserDAO().getUser();
			
			
			user.setPassword(newPassw);
			
			comDAO.getUserDAO().updateUser(user);
			
			
			if (newPassw.isEmpty() || confirmPassw.isEmpty()){
				showMessage.setText("Please fill out all the required fields!");
				showMessage.setTextFill(Color.web("red"));
			}
			else if (newPassw.isEmpty() && confirmPassw.isEmpty()){
				showMessage.setText("Please fill out all the required fields!");
				showMessage.setTextFill(Color.web("red"));
			}
			else if(newPassw.equals(confirmPassw)) {
					showMessage.setText("Successfully reset the password");
					showMessage.setTextFill(Color.web("red"));	
			} 
	
		} catch (SQLException e) {
				e.printStackTrace();
				System.out.println(e.getMessage());
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
