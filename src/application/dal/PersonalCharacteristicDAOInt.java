package application.dal;

import java.sql.SQLException;
import java.util.List;

import application.model.PersonalCharacteristic;

public interface PersonalCharacteristicDAOInt {
	public List<PersonalCharacteristic> getAllPersonalCharacteristics() throws SQLException;
	
	/**
	 * 
	 * @param characteristic
	 * @return PersonalCharacteristic or null if retrieving ID from DB after insertion failed
	 * @throws SQLException
	 */
	public PersonalCharacteristic addPersonalCharacteristic(String characteristic) throws SQLException;
	public void updatePersonalCharacteristic(PersonalCharacteristic personalCharacteristic) throws SQLException;
	public void deletePersonalCharacteristic(int personalCharacteristicId) throws SQLException;
}
