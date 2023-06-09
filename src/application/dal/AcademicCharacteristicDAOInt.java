package application.dal;

import java.sql.SQLException;
import java.util.List;

import application.model.AcademicCharacteristic;

public interface AcademicCharacteristicDAOInt {
	public List<AcademicCharacteristic> getAllAcademicCharacteristics() throws SQLException;
	
	/**
	 * 
	 * @param characteristic
	 * @return AcademicCharacteristic or null if retrieving ID from DB after insertion failed
	 * @throws SQLException
	 */
	public AcademicCharacteristic addAcademicCharacteristic(String characteristic) throws SQLException;
	public void updateAcademicCharacteristic(AcademicCharacteristic academicCharacteristic) throws SQLException;
	public void deleteAcademicCharacteristic(int academicCharacteristicId) throws SQLException;
}
