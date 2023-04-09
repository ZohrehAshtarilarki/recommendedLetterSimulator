package application.controller;

import java.io.IOException;

import application.dal.UserDAOImpl;
import application.dal.UserDAOInt;
import application.model.User;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

public class MainController {
	
	@FXML private Button resetPasswordButton;
	@FXML private Button login;
	@FXML PasswordField passEnter;
	
	Boolean loginValidation = false;
	
	
	@FXML public Boolean passwordEnterOp(){
		try {
			CommonObjs commonObjs = CommonObjs.getInstance(); 
			UserDAOInt userDAO = new UserDAOImpl(commonObjs.getDataBaseObj().getConnection());
			User user = userDAO.getUser();
			
			String newP = passEnter.getText(); // Get new password from user
			if(newP.isEmpty()) {
				Alert alert = new Alert(AlertType.ERROR);
				alert.setHeaderText(null);
				alert.setContentText("Please fill out all the requierd fields");
				alert.showAndWait();
			}
			//p is default password, it is entered, user will be redirected to reset password
			else if (newP.equals("p")||newP.equals(user.getPassword())){
				loginValidation = true;
				Stage stage = (Stage) login.getScene().getWindow();
				stage.close();
				Stage primaryStage = new Stage();
				Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("view/ResetPassword.fxml"));
				primaryStage.setScene(new Scene(root));
				primaryStage.show();
				
			}
		
		} catch(Exception e) {
			e.printStackTrace();
			}
		return loginValidation;
	}
	
	@FXML public void loginClicked() {
		if(passwordEnterOp()) {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setHeaderText(null);
			//alert.setContentText("Successfully logged into the application");
			alert.showAndWait();
		}
		else {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setHeaderText(null);
			alert.setContentText("The password is incorrect");
			alert.showAndWait();
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
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
