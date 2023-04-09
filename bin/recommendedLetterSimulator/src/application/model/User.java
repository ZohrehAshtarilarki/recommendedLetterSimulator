package application.model;

import javafx.beans.property.SimpleStringProperty;

public class User {
	private int UserId;
	private SimpleStringProperty password;
	private boolean isFirstLogin;
	private boolean isAuthenticated;
	
	public User() {
		this.UserId = -1;
		this.password = new SimpleStringProperty();
		this.isFirstLogin = true;
		this.isAuthenticated = false;
	}

	public int getUserId() {
		return UserId;
	}

	public void setUserId(int userId) {
		UserId = userId;
	}

	public String getPassword() {
		return password.get();
	}

	public void setPassword(String password) {
		this.password.set(password);;
	}

	public boolean isFirstLogin() {
		return isFirstLogin;
	}

	public void setIsFirstLogin(boolean isFristLogin) {
		this.isFirstLogin = isFristLogin;
	}

	public boolean isAuthenticated() {
		return isAuthenticated;
	}

	public void setAuthenticated(boolean isAuthenticated) {
		this.isAuthenticated = isAuthenticated;
	}
	
	@Override
	public String toString() {
		return "User [UserId=" + UserId + ", password=" + password + ", isFristLogin=" + isFirstLogin
				+ ", isAuthenticated=" + isAuthenticated + "]";
	}
	
}
