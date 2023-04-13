package application.dal;

import java.sql.SQLException;

import application.model.User;

public final class Authentication {
	private final static Authentication auth = new Authentication();
	private DbConnectionInt dataBaseObj;
	private UserDAOInt userDAO;
	private User user;
	private boolean isAuthentication;
	
	private Authentication() {
		if (CommonDAOs.getInstance().getUserDAO() == null) {
			this.userDAO = new UserDAOImpl(dataBaseObj.getConnection());
		} else {			
			this.userDAO = CommonDAOs.getInstance().getUserDAO();
		}
	}
	
	/**
	 * On log in success, stores reference to logged in user in user attribute
	 * 
	 * @param password
	 * @return True if password match, false if password did not match the User password in DB 
	 * @throws SQLException
	 */
	public boolean login(String password) throws SQLException {
		User user = userDAO.getUser();
		isAuthentication = isCorrectPassword(password, user);
		if (isAuthentication) {
			this.user = user;
			
		}
		return isAuthentication;
	}
	
	public void logout() {
		this.user = null;
		this.isAuthentication = false;
	}
	
	public boolean getIsAuthentication() {
		return isAuthentication;
	}
	
	/**
	 * Returns null if there is not loged in user
	 * @return
	 */
	public User getLoggedInUser() {
		return user;
	}
	
	private boolean isCorrectPassword(String password, User user) {
		return password.equals(user.getPassword());
	}
}
