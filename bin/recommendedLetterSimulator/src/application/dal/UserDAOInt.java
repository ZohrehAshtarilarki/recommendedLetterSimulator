package application.dal;

import java.sql.SQLException;

import application.model.User;

public interface UserDAOInt {
	public User getUser() throws SQLException;
	public void updateUser(User user) throws SQLException;
}