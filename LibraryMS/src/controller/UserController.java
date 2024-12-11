package controller;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import model.User;
import utility.Database;

/**
 * 
 * @author Christine Ann Dejito
 */
public class UserController {
    
    private Database db;

    // Constructor to initialize the database connection
    public UserController(Database db) {
        this.db = db;
    }

    // CREATE: Add a new user to the database
    public void createUser(User user) {
        String sql = "INSERT INTO users (username, password, role) VALUES (?, ?, ?)";
        try (PreparedStatement pstmt = db.getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, user.getUsername());
            pstmt.setString(2, user.getPassword());
            pstmt.setString(3, user.getRole());
            pstmt.executeUpdate();

            ResultSet generatedKeys = pstmt.getGeneratedKeys();
            if (generatedKeys.next()) {
                user.setId(generatedKeys.getInt(1)); // Set the generated ID
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    // LOGIN: Check if the user exists with the given username and password
    public User loginUser(String username, String password) {
        String sql = "SELECT * FROM users WHERE username = ? AND password = ?";
        try (PreparedStatement pstmt = db.getConnection().prepareStatement(sql)) {
            pstmt.setString(1, username);
            pstmt.setString(2, password);  // In a real application, you should hash the password for comparison!
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return mapResultSetToUser(rs);  // Return the user object if credentials are correct
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;  // Return null if credentials are incorrect
    }

    // READ: Retrieve a user by their ID
    public User getUserById(int id) {
        String sql = "SELECT * FROM users WHERE id = ?";
        try (PreparedStatement pstmt = db.getConnection().prepareStatement(sql)) {
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return mapResultSetToUser(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public User getUserByUsername(String username) {
        Connection connection = db.getConnection();
        User user = null;
        String query = "SELECT * FROM users WHERE username = ?";  // Adjust if necessary

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



    // READ: Retrieve all users
    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        String sql = "SELECT * FROM users";
        try (Statement stmt = db.getConnection().createStatement()) {
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                users.add(mapResultSetToUser(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

    // UPDATE: Update an existing user's details
    public boolean updateUser(User user) {
        String sql = "UPDATE users SET username = ?, password = ?, role = ? WHERE id = ?";
        try (PreparedStatement pstmt = db.getConnection().prepareStatement(sql)) {
            pstmt.setString(1, user.getUsername());
            pstmt.setString(2, user.getPassword());
            pstmt.setString(3, user.getRole());
            pstmt.setInt(4, user.getId());
            return pstmt.executeUpdate() > 0; // Returns true if update was successful
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // DELETE: Remove a user by their ID
    public boolean deleteUser(int id) {
        String sql = "DELETE FROM users WHERE id = ?";
        try (PreparedStatement pstmt = db.getConnection().prepareStatement(sql)) {
            pstmt.setInt(1, id);
            return pstmt.executeUpdate() > 0; // Returns true if deletion was successful
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Helper method: Map a ResultSet row to a User object
    private User mapResultSetToUser(ResultSet rs) throws SQLException {
        return new User(
                rs.getInt("id"),
                rs.getString("username"),
                rs.getString("password"),
                rs.getString("role"),
                rs.getTimestamp("created_at"),
                rs.getTimestamp("updated_at")
        );
    }
    
    public int getUserCount() {
        String sql = "SELECT COUNT(user_id) AS user_count FROM users";
        try (PreparedStatement pstmt = db.getConnection().prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            if (rs.next()) {
                return rs.getInt("user_count");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }  
    
    public String getRole(String username) {
        Connection connection = db.getConnection();
        String query = "SELECT role FROM users WHERE username = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, username);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getString("role");
                }
            }
        } catch (SQLException e) {
            System.err.println("Error fetching user role: " + e.getMessage());
        }
        return null; // Return null if the user doesn't exist or an error occurred
    }
    
}
