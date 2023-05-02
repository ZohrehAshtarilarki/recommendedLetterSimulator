package application.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import application.dal.Authentication;
import application.dal.CommonDAOs;
import application.model.Recommendation;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.scene.control.Label;

public class HomeController {

	@FXML Button resetPasswordButton;
	@FXML Button newRecommendationButton;
	@FXML Button facultySignatureButton;
	@FXML Button logoutButton;
	@FXML Button searchButton;
	@FXML TextField searchTextField;
	@FXML Label showMessage;
	
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
		List<Recommendation> recommednaitonsFound = null;
		String lastName = searchTextField.getText();
		try {
			recommednaitonsFound = CommonDAOs.getInstance().getRecommendationDAO().searchRecommendationByLastName(searchTextField.getText());
			CommonObjs.getInstance().setSearchedRecommendations(recommednaitonsFound);
			
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
		
		if(lastName.isEmpty()) {
			showMessage.setText("Please enter the student last name");
			showMessage.setTextFill(Color.web("red"));
		} else {
			Stage stage = (Stage) searchButton.getScene().getWindow();
			stage.close();
			Stage primaryStage = new Stage();
			Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("view/SearchPage.fxml"));
			primaryStage.setScene(new Scene(root));
			primaryStage.show();
			auth.logout();
		}
		
	}

}
