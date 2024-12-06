package com.example.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.example.models.User;
import com.example.utils.BCryptUtils;
import com.example.utils.DbUtils;

public class UserDao {

	public boolean registerUser(User user) throws SQLException, ClassNotFoundException {
        String query = "INSERT INTO users (username, password, role) VALUES (?, ?, ?)";
        try (Connection con = DbUtils.connectDB();
             PreparedStatement ps = con.prepareStatement(query)) {
            ps.setString(1, user.getUsername());
            ps.setString(2, BCryptUtils.hashPassword(user.getPassword()));
            ps.setString(3, user.getRole());
            return ps.executeUpdate() > 0;
        }
    }
	
	
	public User validateLogin(String username, String password) throws SQLException, ClassNotFoundException {
        String query = "SELECT * FROM users WHERE username = ?";
        try (Connection con = DbUtils.connectDB();
                PreparedStatement ps = con.prepareStatement(query)){
                	ps.setString(1, username);
            ResultSet rs = ps.executeQuery();
            if (rs.next() && BCryptUtils.checkPassword(password, rs.getString("password"))) {
                User user = new User();
                user.setId(rs.getInt("id"));
                user.setUsername(rs.getString("username"));
                user.setRole(rs.getString("role"));
                return user;
            }
        }
        return null;
}
}
