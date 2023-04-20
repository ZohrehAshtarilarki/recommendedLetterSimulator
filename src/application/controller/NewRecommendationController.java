package application.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.scene.control.TextArea;

public class NewRecommendationController implements Initializable{

	@FXML Button homePageButton;
	@FXML Button resetPasswordButton;
	@FXML TextField stuFirstName;
	@FXML TextField stuLastName;
	@FXML Button logoutButton;
	@FXML TextField schoolName;
	@FXML DatePicker datePicker;
	@FXML ComboBox<String> genderButton;
	@FXML ComboBox<String> programButton;
	@FXML ComboBox<String> semesterTaken;
	@FXML ComboBox<String> courseTaken;
	@FXML ComboBox<String> personalChar;
	@FXML ComboBox<String> academicChar;
	@FXML TextArea courseTextArea;
	@FXML Button addCourseButton;
	@FXML TextField gradeField;
	@FXML Button addPersonalChar;
	@FXML TextArea personalArea;
	@FXML Button achademicArea;
	@FXML TextArea achademicField;

	@FXML public void homePageOp() throws IOException {
		
		Stage stage = (Stage) homePageButton.getScene().getWindow();
		stage.close();
		Stage primaryStage = new Stage();
		Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("view/HomePage.fxml"));
		primaryStage.setScene(new Scene(root));
		primaryStage.show();
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
	}
	
	@FXML public void datePickerOp() {
		
		datePicker.getValue().toString();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		ObservableList<String> list1 = FXCollections.observableArrayList("Male", "Female");
		genderButton.setItems(list1);
		
		ObservableList<String> list2 = FXCollections.observableArrayList("Master of science (MS)", 
				"Master of business administration (MBA)", "Doctor of philosophy (PhD)");
		programButton.setItems(list2);
		
		ObservableList<String> list3 = FXCollections.observableArrayList("Fall", "Spring", 
				"Summer", "Winter");
		semesterTaken.setItems(list3);
		
		ObservableList<String> list4 = FXCollections.observableArrayList("CS151: Object-Oriented Design", 
				"CS166: Information Security", "CS154: Theory of Computation", 
				"CS160: Software Engineering", "CS256: Cryptography", 
				"CS146: Data Structures and Algorithms", "CS152: Programming Languages Paradigm");
		courseTaken.setItems(list4);
		
		ObservableList<String> list5 = FXCollections.observableArrayList("very passionate", 
				"very enthusiastic", "punctual", "attentive", "polite");
		personalChar.setItems(list5);
		
		ObservableList<String> list6 = FXCollections.observableArrayList("submitted well-written assignments", 
				"participated in all of my class activities", "worked hard", 
				"was very well prepared for every exam and assignment", "picked up new skills quickly"
				, "was able to excel academically at the top of my class");
		academicChar.setItems(list6);
	
	}

	@FXML public void courseTextAreaOp() {
		
	}

	@FXML public void addCourseOp() {
		
		String course = courseTaken.getSelectionModel().getSelectedItem();
		
		courseTextArea.appendText(course + ", ");
		courseTextArea.appendText(gradeField.getText());
		courseTextArea.appendText("\n");
		
	}

	@FXML public void addPersonalCharOp() {
		
		String personalCh = personalChar.getSelectionModel().getSelectedItem();
		
		personalArea.appendText(personalCh);
		personalArea.appendText("\n");
	}

	@FXML public void achademicAreaOp() {
		
		String academicCh = academicChar.getSelectionModel().getSelectedItem();
		
		achademicField.appendText(academicCh);
		achademicField.appendText("\n");
	}

}
