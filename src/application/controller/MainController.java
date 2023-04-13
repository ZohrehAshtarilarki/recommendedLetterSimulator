package application.controller;

import java.sql.SQLException;

import application.dal.UserDAOImpl;
import application.dal.UserDAOInt;
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
	
	@FXML private Button resetPasswordButton;
	@FXML private Button login;
	@FXML PasswordField passEnter;
	@FXML Label showMessage;
	
	Boolean loginValidation = false;
	
	
	@SuppressWarnings("unlikely-arg-type")
	@FXML public Boolean passwordEnterOp(){
		
		try {
			CommonObjs commonObjs = CommonObjs.getInstance(); 
			UserDAOInt userDAO = new UserDAOImpl(commonObjs.getDataBaseObj().getConnection());
			User user = userDAO.getUser();
			
			String passw = passEnter.getText(); // Get password from the user

			//p is default password, it is entered, user will be redirected to reset password
			if (passw.equals("p") && passw.equals(user.isFirstLogin())){
				user.setIsFirstLogin(false);
				userDAO.updateUser(user);
				
				loginValidation = true;
				
			}
			else if(passw.equals(user.getPassword())){
			
				loginValidation = true;
			}
		
		} catch(Exception e) {
			e.printStackTrace();
			}
		
		return loginValidation;
	}
	
	@FXML public void loginClicked() throws SQLException {
		
		CommonObjs commonObjs = CommonObjs.getInstance(); 
		UserDAOInt userDAO = new UserDAOImpl(commonObjs.getDataBaseObj().getConnection());
		User user = userDAO.getUser();
		
		String passw = passEnter.getText();
		
		try {
			if(passw.isEmpty()) {
				showMessage.setText("Please fill out the required field!");
				showMessage.setTextFill(Color.web("red"));
			}
			else if((passwordEnterOp()) && (user.isFirstLogin())) {
				showMessage.setText("Successfully logged into the application");
				showMessage.setTextFill(Color.web("red"));
				
				Stage stage = (Stage) login.getScene().getWindow();
				stage.close();
				Stage primaryStage = new Stage();
				Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("view/ResetPassword.fxml"));
				primaryStage.setScene(new Scene(root));
				primaryStage.show();
			}
			else if(passwordEnterOp()) {
				showMessage.setText("Successfully logged into the application");
				showMessage.setTextFill(Color.web("red"));
				
				Stage stage = (Stage) login.getScene().getWindow();
				stage.close();
				Stage primaryStage = new Stage();
				Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("view/ResetPassword.fxml"));
				primaryStage.setScene(new Scene(root));
				primaryStage.show();
			}
			else {
				showMessage.setText("The password is incorrect");
				showMessage.setTextFill(Color.web("red"));
			}
		} catch(Exception e) {
			e.printStackTrace();
			}
	}
	
}
