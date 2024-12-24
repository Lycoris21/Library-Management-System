package controller;

import java.sql.*;
import java.util.*;
import javax.swing.JOptionPane;
import utility.Database;
import model.Borrowing;
import model.User;

public class BorrowingController{
    
    private Database db;

    public BorrowingController(Database db) {
        this.db = db;
    }
    
    public List<Borrowing> getLatestTransactions() {
        List<Borrowing> transactions = new ArrayList<>();
        String sql = """
            SELECT TOP 10 * FROM (
                SELECT 
                    'Reserved' AS transaction_type,
                    r.reservation_id AS id,
                    r.book_id,
                    book.title AS book_title,
                    r.status,
                    r.updated_at
                FROM 
                    reservations r
                JOIN 
                    books book ON r.book_id = book.book_id
                WHERE 
                    r.status = 'Pending'

                UNION ALL

                SELECT 
                    b.status AS transaction_type,
                    b.borrow_id AS id,
                    b.book_id,
                    book.title AS book_title,
                    b.status,
                    b.updated_at
                FROM 
                    borrowing b
                JOIN 
                    books book ON b.book_id = book.book_id
                WHERE 
                    b.status IN ('Borrowed', 'Returned')
            ) AS combined_transactions
            ORDER BY 
                updated_at DESC
            """;

        try (Connection conn = db.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql); ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                Borrowing transaction = new Borrowing();
                transaction.setTransactionType(rs.getString("transaction_type"));
                transaction.setBorrowId(rs.getInt("id"));
                transaction.setBookId(rs.getInt("book_id"));
                transaction.setStatus(rs.getString("status"));
                transaction.setUpdatedAt(rs.getTimestamp("updated_at"));
                transaction.setBookTitle(rs.getString("book_title"));

                transactions.add(transaction);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return transactions;
    }
    
    public List<Borrowing> getUserLatestTransactions(User user) {
        List<Borrowing> transactions = new ArrayList<>();
        String sql = """
            SELECT TOP 10 * FROM (
                SELECT 
                    'Reserved' AS transaction_type,
                    r.reservation_id AS id,
                    r.book_id,
                    book.title AS book_title,
                    r.status,
                    r.updated_at
                FROM 
                    reservations r
                JOIN 
                    books book ON r.book_id = book.book_id
                WHERE 
                    r.user_id = ? AND r.status = 'Pending'

                UNION ALL

                SELECT 
                    b.status AS transaction_type,
                    b.borrow_id AS id,
                    b.book_id,
                    book.title AS book_title,
                    b.status,
                    b.updated_at
                FROM 
                    borrowing b
                JOIN 
                    books book ON b.book_id = book.book_id
                WHERE 
                    b.user_id = ? AND b.status IN ('Borrowed', 'Returned')
            ) AS combined_transactions
            ORDER BY 
                updated_at DESC
            """;

        try (Connection conn = db.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, user.getUserId());
            pstmt.setInt(2, user.getUserId());

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Borrowing transaction = new Borrowing();
                    transaction.setTransactionType(rs.getString("transaction_type"));
                    transaction.setBorrowId(rs.getInt("id"));
                    transaction.setBookId(rs.getInt("book_id"));
                    transaction.setStatus(rs.getString("status"));
                    transaction.setUpdatedAt(rs.getTimestamp("updated_at"));
                    transaction.setBookTitle(rs.getString("book_title"));

                    transactions.add(transaction);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return transactions;
    }

    public List<Map<String, Object>> getAllUsersWithBorrowedBooksCount() {
        List<Map<String, Object>> userWithBooksCountList = new ArrayList<>();

        String sql = "SELECT u.user_id, u.username, u.role, "
                + "COUNT(CASE WHEN br.status = 'Borrowed' THEN 1 END) AS currentlyBorrowedCount, "
                + "COUNT(CASE WHEN br.status = 'Overdue' THEN 1 END) AS overdueCount, "
                + "COUNT(br.book_id) AS totalBorrowedCount "
                + "FROM users u "
                + "LEFT JOIN borrowing br ON u.user_id = br.user_id "
                + "GROUP BY u.user_id, u.username, u.role";

        try (Connection conn = db.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Map<String, Object> userWithBooksCount = new HashMap<>();
                userWithBooksCount.put("userId", rs.getInt("user_id"));
                userWithBooksCount.put("username", rs.getString("username"));
                userWithBooksCount.put("role", rs.getString("role"));
                userWithBooksCount.put("currentlyBorrowedCount", rs.getInt("currentlyBorrowedCount"));
                userWithBooksCount.put("overdueCount", rs.getInt("overdueCount"));
                userWithBooksCount.put("totalBorrowedCount", rs.getInt("totalBorrowedCount"));

                userWithBooksCountList.add(userWithBooksCount);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return userWithBooksCountList;
    }
    
    public boolean createBorrowing(String username, int bookId) {
        // First, check if the book is available
        if (!isBookAvailable(bookId)) {
            JOptionPane.showMessageDialog(null, "The selected book is not available for borrowing.", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        // Get the user ID from the username
        int userId = getUserIdByUsername(username);
        if (userId == -1) {
            JOptionPane.showMessageDialog(null, "User  does not exist.", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        // Insert the reservation record with status "Pending"
        String reservationSql = "INSERT INTO reservations (user_id, book_id, status, collection_deadline) VALUES (?, ?, 'Pending', DATEADD(WEEK, 1, CURRENT_TIMESTAMP))";
        String updateReservationSql = "UPDATE reservations SET status = 'Collected' WHERE user_id = ? AND book_id = ? AND status = 'Pending'";

        try (Connection conn = db.getConnection()) {
            conn.setAutoCommit(false); // Start transaction

            // Insert the reservation record
            try (PreparedStatement pstmt = conn.prepareStatement(reservationSql)) {
                pstmt.setInt(1, userId);
                pstmt.setInt(2, bookId);
                int rowsAffected = pstmt.executeUpdate();
                if (rowsAffected <= 0) {
                    conn.rollback(); // Rollback if insert fails
                    return false;
                }
            }

            // Update the reservation record to change status to "Collected"
            try (PreparedStatement pstmt = conn.prepareStatement(updateReservationSql)) {
                pstmt.setInt(1, userId);
                pstmt.setInt(2, bookId);
                pstmt.executeUpdate();
            }

            conn.commit(); // Commit transaction
            return true; // Return true if both operations were successful
        } catch (SQLException e) {
            e.printStackTrace();
            return false; // Return false if there was an error
        }
    }

    private boolean isBookAvailable(int bookId) {
        String sql = "SELECT quantity FROM books WHERE book_id = ? AND quantity > 0";
        try (Connection conn = db.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, bookId);
            try (ResultSet rs = pstmt.executeQuery()) {
                return rs.next(); // If a record is found, the book is available
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false; // Return false if there was an error
        }
    }

    private int getUserIdByUsername(String username) {
        String sql = "SELECT user_id FROM users WHERE username = ?";
        try (Connection conn = db.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, username);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("user_id"); // Return the user ID
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1; // Return -1 if the user does not exist
    }
    
}