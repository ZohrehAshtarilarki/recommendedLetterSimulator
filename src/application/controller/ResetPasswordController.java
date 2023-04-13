package application.controller;

import java.io.IOException;
import java.sql.SQLException;

import application.dal.UserDAOImpl;
import application.dal.UserDAOInt;
import application.model.User;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class ResetPasswordController {
	
	@FXML private Button logoutButton;
	@FXML PasswordField newPasswordButton;
	@FXML PasswordField confirmPasswordButton;
	@FXML Button resetPasswordButton;
	@FXML Button newRecommendationButton;
	@FXML Button goBackButton;
	@FXML Label showMessage;
	
	
	
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
			CommonObjs commonObjs = CommonObjs.getInstance(); 
			UserDAOInt userDAO = new UserDAOImpl(commonObjs.getDataBaseObj().getConnection());
			User user = userDAO.getUser();
			
			String newPassw = newPasswordButton.getText();
			String confirmPassw = confirmPasswordButton.getText();
			user.setPassword(newPassw);
			
			userDAO.updateUser(user);
			
			
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


	@FXML public void newRecommendationOp() throws IOException {
		
		Stage stage = (Stage) logoutButton.getScene().getWindow();
		stage.close();
		Stage primaryStage = new Stage();
		primaryStage = RecommendationLetterSimulator.show(primaryStage);


		primaryStage.show();
	}
}
