package application.controller;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import application.dal.Authentication;
import application.dal.CommonDAOs;
import application.model.AcademicCharacteristic;
import application.model.AcademicProgram;
import application.model.Course;
import application.model.Faculty;
import application.model.PersonalCharacteristic;
import application.model.Semester;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.Stage;

public class FacultySignatureController implements Initializable {
	
	// These instance variables are used for the navigation bar
	@FXML Button facultyDashboardButton;
	@FXML Button resetPasswordButton;
	@FXML Button logoutButton;
	
	// These instance variables are used to create new objects in the tables
	@FXML TextField courseTextField;
	@FXML TextField academicCharTextField;
	@FXML TextField programsTextField;
	@FXML TextField personalCharTextField;
	@FXML TextField semesterTextField;
	
	// These instance variables are used to update changes in the faculty's signature
	@FXML Button saveButton;
	@FXML TextField fullNameTextField;
	@FXML TextField titletextField;
	@FXML TextField schoolAndDepartmentTextField;
	@FXML TextField emailAddressTextField;
	@FXML TextField phoneNumberTextField;
	

	private CommonDAOs commDAOs = CommonDAOs.getInstance();
	private Authentication auth = Authentication.getInstance();
	
	// Configure the table
	@FXML TableView<Course> courseTableView;
	@FXML TableColumn<Course, String> courseColumn;
	
	
	@FXML TableView<AcademicCharacteristic> academicCharTableView;
	@FXML TableColumn<AcademicCharacteristic, String> academicCharColumn;
	
	
	@FXML TableView<AcademicProgram> programTableView;
	@FXML TableColumn<AcademicProgram, String> programColumn;
	
	
	@FXML TableView<PersonalCharacteristic> personalCharTableView;
	@FXML TableColumn<PersonalCharacteristic, String> personalCharColumn;
	
	
	@FXML TableView<Semester> semesterTableView;
	@FXML TableColumn<Semester, String> semesterColumn;
	
	
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
	}
	
	
	@Override
	// Whenever scene is loaded, it calls the initialize method first
	public void initialize(URL location, ResourceBundle resources){
		
		try {
			
			// Set up the Course column in the table
			ObservableList<Course> list1 = FXCollections.observableArrayList(commDAOs.getCourseDAO().getAllCourses());
			courseTableView.setItems(list1);
			courseColumn.setCellValueFactory(new PropertyValueFactory<Course, String> ("name"));
			courseTableView.getColumns().setAll(courseColumn);
			
			// Set up Academic Characteristic column in the table
			ObservableList<AcademicCharacteristic> list2 = FXCollections.observableArrayList(commDAOs.getAcademicCharacteristicDAO().getAllAcademicCharacteristics());
			academicCharTableView.setItems(list2);
			academicCharColumn.setCellValueFactory(new PropertyValueFactory<AcademicCharacteristic, String> ("characteristic"));
			academicCharTableView.getColumns().setAll(academicCharColumn);
			
			// Set up Programs Names column in the table
			ObservableList<AcademicProgram> list3 = FXCollections.observableArrayList(commDAOs.getAcademicaProgramDAO().getAllAcademicPrograms());
			programTableView.setItems(list3);
			programColumn.setCellValueFactory(new PropertyValueFactory<AcademicProgram, String> ("name"));
			programTableView.getColumns().setAll(programColumn);
			
			// Set up Personal Characteristic column in the table
			ObservableList<PersonalCharacteristic> list4 = FXCollections.observableArrayList(commDAOs.getPersonalCharacteristicDAO().getAllPersonalCharacteristics());
			personalCharTableView.setItems(list4);
			personalCharColumn.setCellValueFactory(new PropertyValueFactory<PersonalCharacteristic, String> ("characteristic"));
			personalCharTableView.getColumns().setAll(personalCharColumn);
			
			// Set up Personal Semester column in the table
			ObservableList<Semester> list5 = FXCollections.observableArrayList(commDAOs.getSemesterDAO().getAllSemesters());
			semesterTableView.setItems(list5);
			semesterColumn.setCellValueFactory(new PropertyValueFactory<Semester, String> ("name"));
			semesterTableView.getColumns().setAll(semesterColumn);
			
			// Update all the tables to allow for editable fields
			courseTableView.setEditable(true);
			courseColumn.setCellFactory(TextFieldTableCell.forTableColumn());
			
			academicCharTableView.setEditable(true);
			academicCharColumn.setCellFactory(TextFieldTableCell.forTableColumn());
			
			programTableView.setEditable(true);
			programColumn.setCellFactory(TextFieldTableCell.forTableColumn());
			
			personalCharTableView.setEditable(true);
			personalCharColumn.setCellFactory(TextFieldTableCell.forTableColumn());
			
			semesterTableView.setEditable(true);
			semesterColumn.setCellFactory(TextFieldTableCell.forTableColumn());
			
			// The following will allow the table to select rows for deleting
			courseTableView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
			academicCharTableView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
			programTableView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
			personalCharTableView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
			semesterTableView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
			
			// Sets the faculty's full name
			fullNameTextField.setText(commDAOs.getFacultyDAO().getFaculty().getFullName());
			fullNameTextField.setEditable(true); // Makes the faculty's full name editable
			
			// Sets the faculty's title
			titletextField.setText(commDAOs.getFacultyDAO().getFaculty().getTitle());
			titletextField.setEditable(true); // Makes the faculty's title editable
			
			// Sets the faculty's school name and department name
			schoolAndDepartmentTextField.setText(commDAOs.getFacultyDAO().getFaculty().getSchoolName() 
					+ " " + commDAOs.getFacultyDAO().getFaculty().getDepartment());
			schoolAndDepartmentTextField.setEditable(true); 
			
			// Sets the faculty's email address
			emailAddressTextField.setText(commDAOs.getFacultyDAO().getFaculty().getEmail());
			emailAddressTextField.setEditable(true);
			
			// Sets the faculty's phone number
			phoneNumberTextField.setText(commDAOs.getFacultyDAO().getFaculty().getPhoneNumber());
			phoneNumberTextField.setEditable(true);
			
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}

	/**
	 * This method will allow the user to double click on a cell and update
	 * Courses Taught
	 * @throws SQLException 
	 */
	@FXML public void changeCoursesTaughtOp(CellEditEvent edditedCell) throws SQLException {
		
		Course courseSelected = courseTableView.getSelectionModel().getSelectedItem();
		courseSelected.setName(edditedCell.getNewValue().toString());
		commDAOs.getCourseDAO().updateCourses(courseSelected);
	}

	/**
	 * This method will allow the user to double click on a cell and update
	 * the Academic Characteristics
	 * @throws SQLException 
	 */
	@FXML public void changeacademicCharacteristicOp(CellEditEvent edditedCell) throws SQLException {
		
		AcademicCharacteristic academicCharSelected = academicCharTableView.getSelectionModel().getSelectedItem();
		academicCharSelected.setCharacteristic(edditedCell.getNewValue().toString());
		commDAOs.getAcademicCharacteristicDAO().updateAcademicCharacteristic(academicCharSelected);
	}

	/**
	 * This method will allow the user to double click on a cell and update
	 * the Programs Names
	 * @throws SQLException 
	 */
	@FXML public void changeProgramsNamesOp(CellEditEvent edditedCell) throws SQLException {
		
		AcademicProgram academicProSelected = programTableView.getSelectionModel().getSelectedItem();
		academicProSelected.setName(edditedCell.getNewValue().toString());
		commDAOs.getAcademicaProgramDAO().updateAcademicProgram(academicProSelected);
	}

	/**
	 * This method will allow the user to double click on a cell and update
	 * the Personal Characteristics
	 * @throws SQLException 
	 */
	@FXML public void changePersonalCharacteristicsOp(CellEditEvent edditedCell) throws SQLException {
		
		PersonalCharacteristic personalCharSelected = personalCharTableView.getSelectionModel().getSelectedItem();
		personalCharSelected.setCharacteristic(edditedCell.getNewValue().toString());
		commDAOs.getPersonalCharacteristicDAO().updatePersonalCharacteristic(personalCharSelected);
	}

	/**
	 * This method will allow the user to double click on a cell and update
	 * the Semester
	 * @throws SQLException 
	 */
	@FXML public void changeSemestersOp(CellEditEvent edditedCell) throws SQLException {
		
		Semester semesterSelected = semesterTableView.getSelectionModel().getSelectedItem();
		semesterSelected.setName(edditedCell.getNewValue().toString());
		commDAOs.getSemesterDAO().updateSemester(semesterSelected);
	}
	
	
	

	/**
	 * This method will create a new course object and add it to the table
	 * @throws SQLException 
	 */
	@FXML public void newCourseOp() throws SQLException {
		
		Course newCourse = commDAOs.getCourseDAO().addCourses(courseTextField.getText(), "", 0);
		courseTableView.getItems().add(newCourse);
	}

	/**
	 * This method will create a new Academic Characteristic object and 
	 * add it to the table
	 * @throws SQLException 
	 */
	@FXML public void newAcademicCharOp() throws SQLException {
		
		AcademicCharacteristic newAcaChar = commDAOs.getAcademicCharacteristicDAO().addAcademicCharacteristic(academicCharTextField.getText());
		academicCharTableView.getItems().add(newAcaChar);
	}

	/**
	 * This method will create a new Academic Program object and 
	 * add it to the table
	 * @throws SQLException 
	 */
	@FXML public void newProgramNameOp() throws SQLException {
		
		AcademicProgram newProgram = commDAOs.getAcademicaProgramDAO().addAcademicProgram(programsTextField.getText());
		programTableView.getItems().add(newProgram);
	}

	/**
	 * This method will create a new Personal Characteristic object and 
	 * add it to the table
	 * @throws SQLException 
	 */
	@FXML public void newPersonalCharOp() throws SQLException {
		
		PersonalCharacteristic newPrsChar = commDAOs.getPersonalCharacteristicDAO().addPersonalCharacteristic(personalCharTextField.getText());
		personalCharTableView.getItems().add(newPrsChar);
	}

	/**
	 * This method will create a new Semester object and 
	 * add it to the table
	 * @throws SQLException 
	 */
	@FXML public void newSemesterOp() throws SQLException {
		
		Semester newSemester = commDAOs.getSemesterDAO().addSemester(semesterTextField.getText());
		semesterTableView.getItems().add(newSemester);
	}

	/**
	 * This method will remove the selected semester from the table
	 */
	@FXML public void deleteSemesterOp() {
		
		ObservableList<Semester> semesterSelected, semesters;
		semesters = semesterTableView.getItems();
		
		semesterSelected = semesterTableView.getSelectionModel().getSelectedItems();
		semesterSelected.forEach((semester) -> {
			try {
				commDAOs.getSemesterDAO().deleteSemester(semester.getSemsterId());
				semesters.remove(semester);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		});
		
	}

	/**
	 * This method will remove the selected personal characteristic from the table
	 */
	@FXML public void deletePersonalCharOp() {
		
		ObservableList<PersonalCharacteristic> persnCharSelected, persnChars;
		persnChars = personalCharTableView.getItems();
		
		persnCharSelected = personalCharTableView.getSelectionModel().getSelectedItems();
		persnCharSelected.forEach((characteristic) -> {
			try {
				commDAOs.getPersonalCharacteristicDAO().deletePersonalCharacteristic(characteristic.getPersonalCharacteristicId());
				persnChars.remove(characteristic);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		});
		
	}

	/**
	 * This method will remove the selected academic program from the table
	 */
	@FXML public void deleteProgramOp() {
		
		ObservableList<AcademicProgram> acadProgmSelected, acadmProgm;
		acadmProgm = programTableView.getItems();
		
		acadProgmSelected = programTableView.getSelectionModel().getSelectedItems();
		acadProgmSelected.forEach((program) -> {
			try {
				commDAOs.getAcademicaProgramDAO().deleteAcademicProgram(program.getAcademicProgramId());
				acadmProgm.remove(program);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		});
	}

	/**
	 * This method will remove the selected academic characteristic from the table
	 */
	@FXML public void deleteAcademicCharOp() {
		
		ObservableList<AcademicCharacteristic> acadCharSelected, acadmChar;
		acadmChar = academicCharTableView.getItems();
		
		acadCharSelected = academicCharTableView.getSelectionModel().getSelectedItems();
		acadCharSelected.forEach((characteristic) -> {
			try {
				commDAOs.getAcademicCharacteristicDAO().deleteAcademicCharacteristic(characteristic.getAcademicCharacteristicId());
				acadmChar.remove(characteristic);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		});
	}

	/**
	 * This method will remove the selected course from the table
	 */
	@FXML public void deleteCourseOp() {
		
		ObservableList<Course> courseSelected, courses;
		courses = courseTableView.getItems();
		
		courseSelected = courseTableView.getSelectionModel().getSelectedItems();
		courseSelected.forEach((course) -> {
			try {
				commDAOs.getCourseDAO().deleteCourses(course.getCourseId());
				courses.remove(course);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		});
	}

	/**
	 * This method saves the changes for the faculty's signature
	 * @throws SQLException 
	 */
	@FXML public void saveButtonOp(ActionEvent event) throws SQLException {
		
		Faculty update = commDAOs.getFacultyDAO().getFaculty();
		update.setFullName(fullNameTextField.getText());
		update.setTitle(titletextField.getText());
		update.setSchoolName(schoolAndDepartmentTextField.getText());
		update.setEmail(emailAddressTextField.getText());
		update.setPhoneNumber(phoneNumberTextField.getText());
		commDAOs.getFacultyDAO().updateFaculty(update);
	}
}
