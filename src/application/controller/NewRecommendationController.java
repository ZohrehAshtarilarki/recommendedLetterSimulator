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
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.scene.control.TextArea;
import javafx.scene.control.TableView;

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
	@FXML Button facultyDashboardButton;
	@FXML Button compileButton;
	private CommonDAOs commDAOs = CommonDAOs.getInstance();
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

	@FXML public void homePageOp() throws IOException {
		
		Stage stage = (Stage) homePageButton.getScene().getWindow();
		stage.close();
		Stage primaryStage = new Stage();
		Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("view/Home.fxml"));
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
		this.rec = CommonObjs.getInstance().getActiveRecommendation();
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
					this.datePicker.getValue().format(DateTimeFormatter.ofPattern("MM-d-yyyy")),
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
		this.rec.setCurrentDate(this.datePicker.getValue().format(DateTimeFormatter.ofPattern("MM-d-yyyy")));
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

	@FXML public void facultyDashboardOp() throws IOException {
		
		Stage stage = (Stage) facultyDashboardButton.getScene().getWindow();
		stage.close();
		Stage primaryStage = new Stage();
		Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("view/FacultyDashboard.fxml"));
		primaryStage.setScene(new Scene(root));
		primaryStage.show();
	}

	@FXML public void compileOp() throws IOException {
		this.onCompile();
		Stage stage = (Stage) facultyDashboardButton.getScene().getWindow();
		stage.close();
		Stage primaryStage = new Stage();
		Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("view/compilePage.fxml"));
		primaryStage.setScene(new Scene(root));
		primaryStage.show();
	}

}
