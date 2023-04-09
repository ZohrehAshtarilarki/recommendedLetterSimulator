package application.model;

import javafx.beans.property.SimpleStringProperty;

public class PersonalCharacteristic {
	private int personalCharacteristicId;
	private SimpleStringProperty characteristic;
	
	public PersonalCharacteristic() {
		this.personalCharacteristicId = -1;
		this.characteristic = new SimpleStringProperty();
	}

	public int getPersonalCharacteristicId() {
		return personalCharacteristicId;
	}

	public void setPersonalCharacteristicId(int courseId) {
		this.personalCharacteristicId = courseId;
	}

	public String getCharacteristic() {
		return characteristic.get();
	}

	public void setCharacteristic(String characteristic) {
		this.characteristic.set(characteristic);
	}

	@Override
	public String toString() {
		return "PersonalCharacteristic [courseId=" + personalCharacteristicId + ", characteristic=" + characteristic + "]";
	}
	
}
