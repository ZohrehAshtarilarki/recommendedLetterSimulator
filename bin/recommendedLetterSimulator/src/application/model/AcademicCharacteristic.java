package application.model;

import javafx.beans.property.SimpleStringProperty;

public class AcademicCharacteristic {
	private int academicCharacteristicId;
	private SimpleStringProperty characteristic;
	
	public AcademicCharacteristic() {
		this.academicCharacteristicId = -1;
		this.characteristic = new SimpleStringProperty();
	}
	
	@Override
	public String toString() {
		return "AcademicCharacteristic [academicCharacteristicId=" + academicCharacteristicId + ", characteristic="
				+ characteristic + "]";
	}

	public int getAcademicCharacteristicId() {
		return academicCharacteristicId;
	}

	public void setAcademicCharacteristicId(int academicCharacteristicId) {
		this.academicCharacteristicId = academicCharacteristicId;
	}

	public String getCharacteristic() {
		return characteristic.get();
	}

	public void setCharacteristic(String characteristic) {
		this.characteristic.set(characteristic);
	}

}
