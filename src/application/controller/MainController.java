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

public class MainController {
	
	@FXML PasswordField passEnter;
	@FXML Button loginButton;
	@FXML Label showMessage;
	
	Boolean loginValidation = false;
	private Authentication auth = Authentication.getInstance();
	private CommonDAOs commDAOs = CommonDAOs.getInstance();
	
	@FXML public Boolean passwordEnterOp(){
		
		return loginValidation;
	}
	
	@FXML public void loginOp() {
		
		String passw = passEnter.getText();
		
		if(passw.isEmpty()) {
			showMessage.setText("Please fill out the required field!");
			showMessage.setTextFill(Color.web("red"));
			return;
		} 
		
		try {
			User user;
			if (auth.login(passw)) {
				user = auth.getLoggedInUser();
				user.setIsFirstLogin(false);
				commDAOs.getUserDAO().updateUser(user);
				loginValidation = true;
				if (user.isFirstLogin()) {
					routeToResetPasswordView();
				} else {
					routeToHomePage();					
				}
			}
		} catch (SQLException | IOException e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
	}
	
	private void routeToResetPasswordView() throws IOException {
		Stage stage = (Stage) loginButton.getScene().getWindow();
		stage.close();
		Stage primaryStage = new Stage();
		Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("view/ResetPassword.fxml"));
		primaryStage.setScene(new Scene(root));
		primaryStage.show();
	}
	
	private void routeToHomePage() throws IOException {
		Stage stage = (Stage) loginButton.getScene().getWindow();
		stage.close();
		Stage primaryStage = new Stage();
		Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("view/HomePage.fxml"));
		primaryStage.setScene(new Scene(root));
		primaryStage.show();
	}
}
