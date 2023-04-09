package application.model;

import javafx.beans.property.SimpleStringProperty;

public class AcademicProgram {
	private int academicProgramId;
	private SimpleStringProperty name;
	
	public AcademicProgram() {
		this.academicProgramId = -1;
		name = new SimpleStringProperty();
	}

	public int getAcademicProgramId() {
		return academicProgramId;
	}

	public void setAcademicProgramId(int academicProgramId) {
		this.academicProgramId = academicProgramId;
	}

	public String getName() {
		return name.get();
	}

	public void setName(String name) {
		this.name.set(name);
	}

	@Override
	public String toString() {
		return "AcademicProgram [academicProgramId=" + academicProgramId + ", name=" + name + "]";
	}	
}
