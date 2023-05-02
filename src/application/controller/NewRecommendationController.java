package application.controller;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import application.dal.CommonDAOs;
import application.model.AcademicCharacteristic;
import application.model.AcademicProgram;
import application.model.Course;
import application.model.Gender;
import application.model.PersonalCharacteristic;
import application.model.Recommendation;
import application.model.RecommendationCourse;
import application.model.Semester;
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
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.MultipleSelectionModel;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextField;
import javafx.scene.control.TableView.TableViewSelectionModel;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.scene.control.TextArea;
import javafx.scene.control.TableView;
import javafx.scene.control.Label;

public class NewRecommendationController implements Initializable{
	private Recommendation rec;
	private boolean isCreating;
	private boolean isUpdating;

	@FXML Button homePageButton;
	@FXML TextField stuFirstName;
	@FXML TextField stuLastName;
	@FXML Button logoutButton;
	@FXML TextField schoolName;
	@FXML DatePicker datePicker;
	@FXML Button cancelButton;
	@FXML Label showMessage;
	@FXML Button homeButton;
	@FXML Button saveButton;
	
	
	@FXML TableView<RecommendationCourse> coursesTaken;
	@FXML TableView<PersonalCharacteristic> personalCharacteristics;
	@FXML TableView<AcademicCharacteristic> academicCharacteristics;
	@FXML ComboBox<Semester> firstSemester;
	@FXML TextField firstYear;
	@FXML ComboBox<Gender> genderCombo;
	@FXML ComboBox<AcademicProgram> programCombo;
	@FXML TableColumn<RecommendationCourse, String> courseNameCol;
	@FXML TableColumn<RecommendationCourse, String> courseGradeCol;
	@FXML TableColumn<PersonalCharacteristic, String> personalCharacteristicsCol;
	@FXML TableColumn<AcademicCharacteristic, String> academicCharacteristicsCol;
	
	private CommonDAOs commDAOs = CommonDAOs.getInstance();

	@FXML public void logOutOp() throws IOException {
		
		Stage stage = (Stage) logoutButton.getScene().getWindow();
		stage.close();
		Stage primaryStage = new Stage();
		Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("view/Login.fxml"));
		primaryStage.setScene(new Scene(root));
		primaryStage.show();
	}
	
	@FXML public void homeOp() throws IOException {
		
		Stage stage = (Stage) homeButton.getScene().getWindow();
		stage.close();
		Stage primaryStage = new Stage();
		Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("view/Home.fxml"));
		primaryStage.setScene(new Scene(root));
		primaryStage.show();
	}
	
	@FXML public void datePickerOp() {
		
		datePicker.getValue().toString();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		this.rec = CommonObjs.getInstance().getActiveRecommendation();
		CommonObjs.getInstance().setActiveRecommendation(null);
		isUpdating = this.rec != null;
		isCreating = this.rec == null;
		this.datePicker.setValue(LocalDate.now());
		
		try {
			firstSemester.setItems(FXCollections.observableArrayList(commDAOs.getSemesterDAO().getAllSemesters()));
			Callback<ListView<Semester>, ListCell<Semester>> semesterFactory = lv -> new ListCell<Semester>() {
				@Override
			    protected void updateItem(Semester semster, boolean empty) {
			        super.updateItem(semster, empty);
			        setText(empty ? "" : semster.getName());
			    }
			};
			firstSemester.setCellFactory(semesterFactory);
			firstSemester.setButtonCell(semesterFactory.call(null));
			
			programCombo.setItems(FXCollections.observableArrayList(commDAOs.getAcademicaProgramDAO().getAllAcademicPrograms()));
			Callback<ListView<AcademicProgram>, ListCell<AcademicProgram>> academicProgramFactory = lv -> new ListCell<AcademicProgram>() {
				@Override
			    protected void updateItem(AcademicProgram academicProgram, boolean empty) {
			        super.updateItem(academicProgram, empty);
			        setText(empty ? "" : academicProgram.getName());
			    }
			};
			programCombo.setCellFactory(academicProgramFactory);
			programCombo.setButtonCell(academicProgramFactory.call(null));
			
			genderCombo.getItems().setAll(Gender.values());
			
			ObservableList<RecommendationCourse> list1 = FXCollections.observableArrayList();
			RecommendationCourse recCourse;
			for (Course course : commDAOs.getCourseDAO().getAllCourses()) {
				recCourse = new RecommendationCourse(course);
				list1.add(recCourse);
			}
			
			coursesTaken.setItems(list1);
			coursesTaken.setEditable(true);
			coursesTaken.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
			courseNameCol.setCellValueFactory(new PropertyValueFactory<RecommendationCourse, String> ("name"));
			courseGradeCol.setCellValueFactory(cellData -> cellData.getValue().gradeProperty());
			courseGradeCol.setCellFactory(TextFieldTableCell.forTableColumn());
			coursesTaken.getColumns().setAll(courseNameCol, courseGradeCol);
			
			personalCharacteristics.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
			personalCharacteristics.setItems(FXCollections.observableArrayList(commDAOs.getPersonalCharacteristicDAO().getAllPersonalCharacteristics()));
			personalCharacteristicsCol.setCellValueFactory(new PropertyValueFactory<PersonalCharacteristic, String> ("characteristic"));
			
			academicCharacteristics.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
			academicCharacteristics.setItems(FXCollections.observableArrayList(commDAOs.getAcademicCharacteristicDAO().getAllAcademicCharacteristics()));
			academicCharacteristicsCol.setCellValueFactory(new PropertyValueFactory<AcademicCharacteristic, String>("characteristic"));
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		this.setExisitngRecSelection();
	}
	
	/**
	 * sets the GUI input to get entities from Recommendation if that Recommendation is being edited not created
	 */
	private void setExisitngRecSelection() {
		if (isCreating && !isUpdating) {
			return;
		}
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
		
		stuFirstName.setText(rec.getStudentFirstName());
		stuLastName.setText(rec.getStudentLastName());
		schoolName.setText(rec.getTargetSchoolName());
		this.datePicker.setValue(LocalDate.parse(rec.getCurrentDate(), formatter));
		firstYear.setText(rec.getFirstSemesterYear());
		genderCombo.setValue(rec.getGender());
		programCombo.setValue(rec.getProgram());
		firstSemester.setValue(rec.getSemester());
		
//		select updating Recommendation's course already selected
//		still testing
		MultipleSelectionModel<PersonalCharacteristic> selectionModel = personalCharacteristics.getSelectionModel();
		selectionModel.clearSelection();
		for (PersonalCharacteristic perChar : rec.getPersonalCharacteristics()) {
			int index = personalCharacteristics.getItems().indexOf(perChar);
			if (index >= 0) {
				selectionModel.select(index);
			}
		}
	}
	
	private void onCompile() {
		Recommendation workingRecommendation = null;
		if (isCreating) {
			workingRecommendation = createNewRecommendation();
		} else if (isUpdating) {
			updateExstingRecommendation();
			workingRecommendation = this.rec;
		}
		CommonObjs.getInstance().setActiveRecommendation(workingRecommendation);
	}
	
	private Recommendation createNewRecommendation() {
		Recommendation newRec = null;
		try {
			newRec = commDAOs.getRecommendationDAO().addRecommendation(
					this.stuFirstName.getText(),
					this.stuLastName.getText(),
					this.schoolName.getText(),
					this.datePicker.getValue().format(DateTimeFormatter.ofPattern("MM/dd/yyyy")),
					this.firstYear.getText(),
					this.genderCombo.getValue(),
					this.firstSemester.getValue(),
					this.programCombo.getValue(),
					new ArrayList<>(this.academicCharacteristics.getSelectionModel().getSelectedItems()),
					new ArrayList<>(this.personalCharacteristics.getSelectionModel().getSelectedItems()),
					new ArrayList<>(this.coursesTaken.getSelectionModel().getSelectedItems()));
			
		}catch (SQLException e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
		return newRec;
	}
	
	private void updateExstingRecommendation() {
		this.rec.setStudentFirstName(this.stuFirstName.getText());
		this.rec.setStudentLastName(this.stuLastName.getText());
		this.rec.setTargetSchoolName(this.schoolName.getText());
		this.rec.setCurrentDate(this.datePicker.getValue().format(DateTimeFormatter.ofPattern("MM/dd/yyyy")));
		this.rec.setFirstSemesterYear(this.firstYear.getText());
		this.rec.setGender(this.genderCombo.getValue());
		this.rec.setSemester(this.firstSemester.getValue());
		this.rec.setProgram(this.programCombo.getValue());
		this.rec.setAcademicCharacteristics(this.academicCharacteristics.getSelectionModel().getSelectedItems());
		this.rec.setPersonalCharacteristics(this.personalCharacteristics.getSelectionModel().getSelectedItems());
		this.rec.setCoursesTaken(this.coursesTaken.getSelectionModel().getSelectedItems());
		try {
			commDAOs.getRecommendationDAO().updateRecommendation(rec);
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
	}

	@FXML public void cancelButtonOp() throws IOException {
		
		Stage stage = (Stage) cancelButton.getScene().getWindow();
		stage.close();
		Stage primaryStage = new Stage();
		Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("view/Home.fxml"));
		primaryStage.setScene(new Scene(root));
		primaryStage.show();
	}


	@FXML public void saveOp() throws IOException {
		
		String lastName = stuLastName.getText();
		String firstName = stuFirstName.getText();
		String schName = schoolName.getText();
		String year = firstYear.getText();
		
		TableViewSelectionModel<RecommendationCourse> selectionModel1 = coursesTaken.getSelectionModel();
		TableViewSelectionModel<PersonalCharacteristic> selectionModel2 = personalCharacteristics.getSelectionModel();
		TableViewSelectionModel<AcademicCharacteristic> selectionModel3 = academicCharacteristics.getSelectionModel();
		
		if(lastName.isEmpty() || firstName.isEmpty() || schName.isEmpty() || year.isEmpty()) {
			showMessage.setText("Please fill out all the required fields!");
			showMessage.setTextFill(Color.web("red"));
		} else if (genderCombo.getSelectionModel().isEmpty() || programCombo.getSelectionModel().isEmpty()
				|| firstSemester.getSelectionModel().isEmpty()) {
			showMessage.setText("Please fill out all the required fields!");
			showMessage.setTextFill(Color.web("red"));
		} else if (datePicker.getValue() == null) {
			showMessage.setText("Please fill out all the required fields!");
			showMessage.setTextFill(Color.web("red"));
		} else if (selectionModel1.getSelectedCells().isEmpty() || selectionModel2.getSelectedCells().isEmpty() || 
				selectionModel3.getSelectedCells().isEmpty()) {
			showMessage.setText("Please fill out all the required fields!");
			showMessage.setTextFill(Color.web("red"));
		}
		else {
			this.onCompile();
			Stage stage = (Stage) saveButton.getScene().getWindow();
			stage.close();
			Stage primaryStage = new Stage();
			Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("view/compilePage.fxml"));
			primaryStage.setScene(new Scene(root));
			primaryStage.show();
		}
	}

}
