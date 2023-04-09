package application.dal;

import java.sql.SQLException;
import java.util.List;

import application.model.PersonalCharacteristic;

public interface PersonalCharacteristicDAOInt {
	public List<PersonalCharacteristic> getAllPersonalCharacteristics() throws SQLException;
	public void addPersonalCharacteristic(PersonalCharacteristic personalCharacteristic) throws SQLException;
	public void updatePersonalCharacteristic(PersonalCharacteristic personalCharacteristic) throws SQLException;
	public void deletePersonalCharacteristic(int personalCharacteristicId) throws SQLException;
}
