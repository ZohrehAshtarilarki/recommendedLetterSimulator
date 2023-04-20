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
	
	@FXML public void logOutOp() throws IOException {
		
			Stage stage = (Stage) logoutButton.getScene().getWindow();
			stage.close();
			Stage primaryStage = new Stage();
			Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("view/Login.fxml"));
			primaryStage.setScene(new Scene(root));
			primaryStage.show();
			auth.logout();
	}


	@FXML public void savePasswordOp() throws IOException, SQLException {
		
		String newPassw = newPasswordButton.getText();
		String confirmPassw = confirmPasswordButton.getText();
		User user = auth.getLoggedInUser();
			
		if (newPassw.isEmpty() || confirmPassw.isEmpty()){
			showMessage.setText("Please fill out all the required fields!");
			showMessage.setTextFill(Color.web("red"));
		}
		else if(newPassw.equals(confirmPassw) && auth.getIsAuthentication()) {
			if (user.isFirstLogin()) {
				user.setIsFirstLogin(false);
			}
			user.setPassword(newPassw);
			comDAO.getUserDAO().updateUser(user);
				
				
			Stage stage = (Stage) savePasswordButton.getScene().getWindow();
			stage.close();
			Stage primaryStage = new Stage();
			Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("view/Login.fxml"));
			primaryStage.setScene(new Scene(root));
			primaryStage.show();
			auth.logout();
		} 
	} 

	@FXML public void goBackOp() throws IOException {
		
		Stage stage = (Stage) logoutButton.getScene().getWindow();
		stage.close();
		Stage primaryStage = new Stage();
		Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("view/Login.fxml"));
		primaryStage.setScene(new Scene(root));
		primaryStage.show();
		auth.logout();
	}
}
