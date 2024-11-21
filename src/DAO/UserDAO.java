package DAO;

import models.User;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDAO extends BaseDAO<User> {

    @Override
    protected User mapResultSetToObject(ResultSet rs) throws SQLException {
        User user = new User();
        user.setId(rs.getInt("id"));
        user.setUsername(rs.getString("username"));
        user.setPassword(rs.getString("password"));
        return user;
    }

    public boolean registerUser(User user) {
        String query = "INSERT INTO Users (username, password) VALUES (?, ?)";
        return add(query, user.getUsername(), user.getPassword());
    }

    public User loginUser(String username, String password) {
        String query = "SELECT * FROM Users WHERE username = ? AND password = ?";
        return getById(query, username, password);
    }

    public User getUserByUsername(String username) {
        String query = "SELECT * FROM Users WHERE username = ?";
        return getById(query, username);
    }
}
