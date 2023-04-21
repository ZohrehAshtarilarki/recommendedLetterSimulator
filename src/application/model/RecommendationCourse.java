package application.model;

import javafx.beans.property.SimpleStringProperty;

public class RecommendationCourse extends Course {
	private SimpleStringProperty grade;

	public RecommendationCourse() {
		super();
		this.grade = new SimpleStringProperty();
	}

	public String getGrade() {
		return grade.get();
	}

	public void setGrade(String grade) {
		this.grade.set(grade);
	}
}
