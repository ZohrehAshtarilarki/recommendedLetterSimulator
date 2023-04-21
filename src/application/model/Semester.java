package application.model;

import javafx.beans.property.SimpleStringProperty;

public class Semester {
	private int semsterId;
	private SimpleStringProperty name;
	
	public Semester() {
		this.semsterId = -1;
		name = new SimpleStringProperty();
	}

	public int getSemsterId() {
		return semsterId;
	}

	public void setSemesterId(int SemesterId) {
		this.semsterId = SemesterId;
	}

	public String getName() {
		return name.get();
	}

	public void setName(String name) {
		this.name.set(name);
	}

	@Override
	public String toString() {
		return "Semester [semsterId=" + semsterId + ", name=" + name + "]";
	}
}
