package application.model;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Course {
	private int courseId;
	private SimpleStringProperty name;
	private SimpleStringProperty prefix;
	private SimpleIntegerProperty prefixNumber;
	
	public Course(int courseId, String courseName, String prefix, int prefixNumber) {
		this.courseId = courseId;
		this.name = new SimpleStringProperty(courseName);
		this.prefix = new SimpleStringProperty(prefix);
		this.prefixNumber = new SimpleIntegerProperty(prefixNumber);
	}
	
	public Course() {
		this.courseId = -1;
		this.name = new SimpleStringProperty();
		this.prefix = new SimpleStringProperty();
		this.prefixNumber = new SimpleIntegerProperty();
	}
	
	public String getName() {
		return name.get();
	}
	
	public void setName(String courseName) {
		this.name.set(courseName);
	}
	
	public String getPrefix() {
		return prefix.get();
	}
	
	public void setPrefix(String prefix) {
		this.prefix.set(prefix);
	}
	
	public int getPrefixNumber() {
		return prefixNumber.get();
	}
	
	public void setPrefixNumber(int prefixNumber) {
		this.prefixNumber.set(prefixNumber);
	}

	public int getCourseId() {
		return courseId;
	}
	
	public void setCourseId(int courseId) {
		this.courseId = courseId;
	}
	
	@Override
	public String toString() {
		return "Course [courseId=" + courseId + ", name=" + name + ", prefix=" + prefix + ", prefixNumber="
				+ prefixNumber + "]";
	}
	
	public final StringProperty nameProperty() {
	    return name;
	  }
}
