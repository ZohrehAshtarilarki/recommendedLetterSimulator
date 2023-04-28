package application.controller;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
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
	@FXML TableColumn<AcademicProgram, String> programColumn;
	
	
	@Override
	// Whenever scene is loaded, it calls the initialize method first
	public void initialize(URL location, ResourceBundle resources){
		
		try {
			// Set up the first name column in the table
			ObservableList<Recommendation> list1 = FXCollections.observableArrayList(commDAOs.getRecommendationDAO().getRecommendation(0));
			searchTableView.setItems(list1);
			firstNameColumn.setCellValueFactory(new PropertyValueFactory<Recommendation, String> ("firstName"));
			searchTableView.getColumns().setAll(firstNameColumn);
			
			// Set up the last name column in the table
			ObservableList<Recommendation> list2 = FXCollections.observableArrayList(commDAOs.getRecommendationDAO().getRecommendation(0));
			searchTableView.setItems(list2);
			lastNameColumn.setCellValueFactory(new PropertyValueFactory<Recommendation, String> ("lastName"));
			searchTableView.getColumns().setAll(lastNameColumn);
			
			// Set up the year column in the table
			ObservableList<Recommendation> list3 = FXCollections.observableArrayList(commDAOs.getRecommendationDAO().getRecommendation(0));
			searchTableView.setItems(list3);
			yearColumn.setCellValueFactory(new PropertyValueFactory<Recommendation, String> ("year"));
			searchTableView.getColumns().setAll(yearColumn);
			
			// Set up the target school in the table
			ObservableList<Recommendation> list4 = FXCollections.observableArrayList(commDAOs.getRecommendationDAO().getRecommendation(0));
			searchTableView.setItems(list4);
			targetSchoolColumn.setCellValueFactory(new PropertyValueFactory<Recommendation, String> ("targetSchool"));
			searchTableView.getColumns().setAll(targetSchoolColumn);
			
			
			/**
			 * I commented out this part to prevent the application from crashing
			 */
//			// Set up the academic program in the table
//			ObservableList<AcademicProgram> list5 = FXCollections.observableArrayList(commDAOs.getAcademicaProgramDAO().getAllAcademicPrograms());
//			searchTableView.setItems(list5);
//			programColumn.setCellValueFactory(new PropertyValueFactory<AcademicProgram, String> ("n"));
//			searchTableView.getColumns().setAll(programColumn);
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
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

	@FXML public void editRecmOp() {}

	@FXML public void deleteRecmOp() {}
	
	
}
