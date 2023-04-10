package application.dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import application.model.User;
import application.utils.DbUtils;

public class UserDAOImpl implements UserDAOInt {
	private Connection connection;
	private String tableName;
	
	/**
	 * Use this constructor for interacting with DB
	 * @param connection
	 */
	public UserDAOImpl(Connection connection) {
		this.connection = connection;
		this.tableName = "user";
	}
	public UserDAOImpl(Connection connection, String tableName) {
		this.connection = connection;
		this.tableName = tableName;
		initUserDAO(new DbUtils());
	}
	
	@Override
	public User getUser() throws SQLException {
		User user = null;
		String sqlSelectFirstUserRow = String.format("SELECT * FROM %s LIMIT 1", tableName);
		Statement statement = connection.createStatement();
		ResultSet resultSet = statement.executeQuery(sqlSelectFirstUserRow);
		
		while(resultSet.next()) {
			user = new User();
			user.setUserId(resultSet.getInt("id"));
			user.setPassword(resultSet.getString("password"));
			user.setIsFirstLogin(resultSet.getInt("isfirstLogin") == 1);
		}
		return user;
	}

	@Override
	public void updateUser(User user) throws SQLException {
		String sqlUpdateUser = String.format("UPDATE %s SET password=?, isfirstLogin=? WHERE id=?", tableName);
		PreparedStatement preparedStatement = connection.prepareStatement(sqlUpdateUser);
		preparedStatement.setString(1, user.getPassword());
		preparedStatement.setInt(2, user.isFirstLogin() ? 1 : 0);
		preparedStatement.setInt(3, user.getUserId());
		
        preparedStatement.executeUpdate();
        preparedStatement.close();

        System.out.println("Updated User successfully.");
	}
	
	private void initUserDAO(DbUtils dbUtils) {
		try {
			if (!dbUtils.tableExists(connection, tableName)) {
//				Creating Table column definitions
				ArrayList<String> tableColumnsAndTypes = new ArrayList<>();
				tableColumnsAndTypes.add("id INTEGER PRIMARY KEY");
				tableColumnsAndTypes.add("password TEXT");
//				SQlite does not have boolean type so 1 == true and 0 == false 
				tableColumnsAndTypes.add("isfirstLogin INTEGER");
				dbUtils.createTable(connection, tableName, tableColumnsAndTypes);
				
				insertDefaultStringOnlyGivenTableData();
			}
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
	}
	
	public void insertDefaultStringOnlyGivenTableData() throws SQLException {
		String defaultPassword = "p";
		
//		SQlite does not have boolean type so 1 == true and 0 == false
		int defaultIsfirstLogin = 1;
		
//		setting up SQL query
		String sqlInitalFacultyData = String.format("INSERT INTO %s (password, isfirstLogin) VALUES (?, ?)", tableName);
		
		PreparedStatement preparedStatement = connection.prepareStatement(sqlInitalFacultyData);
		
//		Values to insert into table
		preparedStatement.setString(1, defaultPassword);
		preparedStatement.setInt(2, defaultIsfirstLogin);
		
//		inserting initial Data
        preparedStatement.executeUpdate();
        preparedStatement.close();
	
		System.out.println("inital/default User Data inserted successfully.");
	}

}
