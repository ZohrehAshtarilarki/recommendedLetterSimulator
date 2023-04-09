package application.dal;

import java.sql.SQLException;
import java.util.List;

import application.model.AcademicProgram;

public interface AcademicProgramDAOInt {
	public List<AcademicProgram> getAllAcademicPrograms() throws SQLException;
	public void addAcademicProgram(AcademicProgram academicProgram) throws SQLException;
	public void updateAcademicProgram(AcademicProgram academicProgram) throws SQLException;
	public void deleteAcademicProgram(int academicProgramId) throws SQLException;

}
