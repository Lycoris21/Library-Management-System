package controller;

import java.sql.*;
import java.util.*;
import utility.Database;
import model.User;
import utility.PasswordHasher;


public class UserController{
    
    private Database db;

    public UserController(Database db) {
        this.db = db;
    }
    
    public User getUserById(int id) {
        User user = null;
        String sql = "SELECT * FROM users WHERE user_id = ?";
        try (PreparedStatement pstmt = db.getConnection().prepareStatement(sql)) {
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                int userId = rs.getInt("user_id");
                String username = rs.getString("username");
                String password = rs.getString("password");  // Hashed password stored in DB
                String role = rs.getString("role");
                Timestamp dateCreated = rs.getTimestamp("created_at");
                Timestamp dateUpdated = rs.getTimestamp("updated_at");

                user = new User(userId, username, password, role, dateCreated, dateUpdated);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
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
                String retrievedPassword = rs.getString("password");

                // Ensure the password is hashed if it's not already
                if (retrievedPassword == null) {
                    return null;
                }

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
    
    public boolean updateUserPassword(int userId, String newHashedPassword) {
        String query = "UPDATE users SET password = ?, updated_at = CURRENT_TIMESTAMP WHERE user_id = ?";
        try (Connection conn = db.getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, newHashedPassword);
            stmt.setInt(2, userId);

            int result = stmt.executeUpdate();
            return result > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
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
    
    public boolean deleteUser(int userId) {
        String query = "DELETE FROM users WHERE user_id = ?";
        try (Connection conn = db.getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, userId);
            int result = stmt.executeUpdate();
            return result > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateUser(User user) {
        String query = "UPDATE users SET username = ?, "
                + (user.getPassword() != null ? "password = ?, " : "")
                + "updated_at = CURRENT_TIMESTAMP WHERE user_id = ?";

        try (Connection conn = db.getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {

            int paramIndex = 1;
            stmt.setString(paramIndex++, user.getUsername());

            if (user.getPassword() != null) {
                stmt.setString(paramIndex++, user.getPassword());
            }

            stmt.setInt(paramIndex, user.getUserId());

            int result = stmt.executeUpdate();
            return result > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    private boolean isPasswordHashed(String password) {
        // A SHA-256 hash is always 64 characters long and contains only hex characters
        return password != null && 
               password.length() == 64 && 
               password.matches("^[0-9a-f]+$");
    }
    
    public boolean createUser(User user) {
        String sql = "INSERT INTO users (username, password, role) VALUES (?, ?, ?)";
        try (PreparedStatement pstmt = db.getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            // Hash the password before storing
            String hashedPassword = PasswordHasher.hashPassword(user.getPassword());

            pstmt.setString(1, user.getUsername());
            pstmt.setString(2, hashedPassword);
            pstmt.setString(3, user.getRole());

            int result = pstmt.executeUpdate();

            ResultSet generatedKeys = pstmt.getGeneratedKeys();
            if (generatedKeys.next()) {
                user.setUserId(generatedKeys.getInt(1)); // Set the generated ID
            }
            return result > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
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
    
    public List<Map<String, Object>> searchUsers(String query) {
        List<Map<String, Object>> filteredUsers = new ArrayList<>();

        String sql = "SELECT u.user_id, u.username, u.role, "
                + "COUNT(bb.book_id) AS totalBorrowedCount, "
                + "COUNT(CASE WHEN bb.status = 'Borrowed' THEN 1 END) AS currentlyBorrowedCount, "
                + "COUNT(CASE WHEN bb.status = 'Overdue' THEN 1 END) AS overdueCount "
                + "FROM users u "
                + "LEFT JOIN borrowing bb ON u.user_id = bb.user_id "
                + "WHERE u.username LIKE ? OR u.role LIKE ? "
                + // Search by username or role
                "GROUP BY u.user_id, u.username, u.role";

        try (Connection conn = db.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            // Use '%' for wildcard search
            String searchPattern = "%" + query + "%";
            stmt.setString(1, searchPattern);
            stmt.setString(2, searchPattern);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Map<String, Object> user = new HashMap<>();
                    user.put("userId", rs.getInt("user_id"));
                    user.put("username", rs.getString("username"));
                    user.put("role", rs.getString("role"));
                    user.put("totalBorrowedCount", rs.getInt("totalBorrowedCount"));
                    user.put("currentlyBorrowedCount", rs.getInt("currentlyBorrowedCount"));
                    user.put("overdueCount", rs.getInt("overdueCount"));

                    filteredUsers.add(user);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return filteredUsers;
    }
    
    public boolean isUsernameTaken(String username) {
        String query = "SELECT COUNT(*) AS count FROM users WHERE username = ?";
        try (Connection connection = db.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, username);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getInt("count") > 0;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    
    public boolean userExists(String username) {
        String query = "SELECT COUNT(*) AS count FROM users WHERE username = ?";
        try (Connection connection = db.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, username);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getInt("count") > 0; // Return true if count is greater than 0
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false; // Return false if there was an error or user does not exist
    }
    
}