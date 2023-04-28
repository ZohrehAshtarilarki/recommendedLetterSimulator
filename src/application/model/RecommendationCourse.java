package application.model;

import java.util.List;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class RecommendationCourse extends Course {
	private SimpleStringProperty grade;

	public RecommendationCourse() {
		super();
		this.grade = new SimpleStringProperty();
	}
	
	public RecommendationCourse(Course coursesWithNoGrades) {
		super(coursesWithNoGrades.getCourseId(), coursesWithNoGrades.getName(), coursesWithNoGrades.getPrefix(), coursesWithNoGrades.getPrefixNumber());
		this.grade = new SimpleStringProperty();
		try {
			if (coursesWithNoGrades.getCourseId() < 0) {
				throw new IllegalArgumentException(String.format("RecommendationCourse pased Course with invalid courseID of %s", coursesWithNoGrades.getCourseId()));
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
	}

	public String getGrade() {
		return grade.get();
	}

	public void setGrade(String grade) {
		this.grade.set(grade);
	}
	
	public final StringProperty gradeProperty() {
	    return grade;
	  }
}
