package application.dal;

import java.sql.SQLException;
import java.util.List;

import application.model.AcademicProgram;

public interface AcademicProgramDAOInt {
	public List<AcademicProgram> getAllAcademicPrograms() throws SQLException;
	AcademicProgram getAcademicProgramById(int academicProgramId) throws SQLException;
	
	/**
	 * 
	 * @param academicProgram
	 * @return AcademicProgram or null if retrieving ID from DB after insertion failed
	 * @throws SQLException
	 */
	public AcademicProgram addAcademicProgram(String academicProgram) throws SQLException;
	public void updateAcademicProgram(AcademicProgram academicProgram) throws SQLException;
	public void deleteAcademicProgram(int academicProgramId) throws SQLException;

}
