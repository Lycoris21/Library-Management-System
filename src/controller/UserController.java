package controller;

import java.sql.*;
import java.util.*;
import utility.Database;
import model.User;


public class UserController{
    
    private Database db;

    public UserController(Database db) {
        this.db = db;
    }
    
    public User getUserByUsername(String username) {
        Connection connection = db.getConnection();
        User user = null;
        String query = "SELECT * FROM users WHERE username = ?";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                int id = rs.getInt("user_id");
                String retrievedUsername = rs.getString("username");
                String retrievedPassword = rs.getString("password");  // Hashed password stored in DB
                String role = rs.getString("role");
                Timestamp dateCreated = rs.getTimestamp("created_at");
                Timestamp dateUpdated = rs.getTimestamp("updated_at");

                user = new User(id, retrievedUsername, retrievedPassword, role, dateCreated, dateUpdated);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }
    
    public String getRole(String username) {
        String query = "SELECT role FROM users WHERE username = ?";
        try (Connection connection = db.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, username);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getString("role");
                } else {
                    System.out.println("No role found for user: " + username);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error fetching user role: " + e.getMessage());
        }
        return null;
    }
    
    public int getUserCount() {
        String sql = "SELECT COUNT(user_id) AS user_count FROM users";
        try (PreparedStatement pstmt = db.getConnection().prepareStatement(sql); ResultSet rs = pstmt.executeQuery()) {
            if (rs.next()) {
                return rs.getInt("user_count");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }  
    
}