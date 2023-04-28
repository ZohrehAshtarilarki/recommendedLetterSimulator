package application.controller;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

import application.dal.Authentication;
import application.dal.CommonDAOs;
import application.model.AcademicProgram;
import application.model.Course;
import application.model.Recommendation;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.TableColumn;

public class SearchController implements Initializable {
	private List<Recommendation> foundRecommendations;

	@FXML Button resetPasswordButton;
	@FXML Button logoutButton;
	@FXML Button facultyDashboardButton;
	@FXML Button editRecmButton;
	@FXML Button deleteRecmButoon;
	
	private CommonDAOs commDAOs = CommonDAOs.getInstance();
	private Authentication auth = Authentication.getInstance();
	
	// Configure the table
	@FXML TableView<Recommendation> searchTableView;
	@FXML TableColumn<Recommendation, String> firstNameColumn;
	@FXML TableColumn<Recommendation, String> lastNameColumn;
	@FXML TableColumn<Recommendation, String> yearColumn;
	@FXML TableColumn<Recommendation, String> targetSchoolColumn;
	@FXML TableColumn<Recommendation, String> programColumn;
	
	
	@Override
	// Whenever scene is loaded, it calls the initialize method first
	public void initialize(URL location, ResourceBundle resources){
		this.foundRecommendations = CommonObjs.getInstance().getSearchedRecommendations();
		searchTableView.setItems(FXCollections.observableArrayList(this.foundRecommendations));

			// Set up the first name column in the table
			firstNameColumn.setCellValueFactory(new PropertyValueFactory<Recommendation, String> ("studentFirstName"));
			
			// Set up the last name column in the table
			lastNameColumn.setCellValueFactory(new PropertyValueFactory<Recommendation, String> ("studentLastName"));
			
			// Set up the year column in the table
			yearColumn.setCellValueFactory(new PropertyValueFactory<Recommendation, String> ("currentDate"));
			
			// Set up the target school in the table
			targetSchoolColumn.setCellValueFactory(new PropertyValueFactory<Recommendation, String> ("targetSchoolName"));
			
			programColumn.setCellValueFactory(cellData -> cellData.getValue().programProperty());
			searchTableView.getColumns().setAll(firstNameColumn, lastNameColumn, yearColumn, targetSchoolColumn, programColumn);
	}

	@FXML public void resetPasswordOp() throws IOException {
		
		Stage stage = (Stage) resetPasswordButton.getScene().getWindow();
		stage.close();
		Stage primaryStage = new Stage();
		Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("view/ResetPassword.fxml"));
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


	@FXML public void facultyDashboardOp() throws IOException {
		
		Stage stage = (Stage) facultyDashboardButton.getScene().getWindow();
		stage.close();
		Stage primaryStage = new Stage();
		Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("view/FacultyDashboard.fxml"));
		primaryStage.setScene(new Scene(root));
		primaryStage.show();
		auth.logout();
	}

	@FXML public void editRecmOp() throws IOException {
		Recommendation recToEdit = searchTableView.getSelectionModel().getSelectedItem();
		CommonObjs.getInstance().setActiveRecommendation(recToEdit);
		
		Stage stage = (Stage) editRecmButton.getScene().getWindow();
		stage.close();
		Stage primaryStage = new Stage();
		Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("view/NewRecommendation.fxml"));
		primaryStage.setScene(new Scene(root));
		primaryStage.show();
	}

	@FXML public void deleteRecmOp() {
		try {
			Recommendation recToDel = searchTableView.getSelectionModel().getSelectedItem();
			commDAOs.getRecommendationDAO().deleteRecommendation(recToDel.getRecommendationId());
			searchTableView.getItems().remove(recToDel);
		}catch (SQLException e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
	}
	
	
}
