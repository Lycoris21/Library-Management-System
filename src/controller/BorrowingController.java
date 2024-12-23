package controller;

import java.sql.*;
import java.util.*;
import utility.Database;
import model.Borrowing;

public class BorrowingController{
    
    private Database db;

    public BorrowingController(Database db) {
        this.db = db;
    }
    
    public List<Borrowing> getLatestTransactions() {
        List<Borrowing> transactions = new ArrayList<>();
        String sql = """
        SELECT TOP 10
            b.borrow_id, 
            b.user_id, 
            b.book_id, 
            b.borrow_date, 
            b.supposed_return_date, 
            b.actual_return_date, 
            b.status, 
            b.updated_at, 
            book.title AS book_title
        FROM 
            borrowing b
        JOIN 
            books book ON b.book_id = book.book_id
        ORDER BY 
            b.updated_at DESC
    """;

        try (Connection conn = db.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql); ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                Borrowing borrowing = new Borrowing();
                borrowing.setBorrowId(rs.getInt("borrow_id"));
                borrowing.setUserId(rs.getInt("user_id"));
                borrowing.setBookId(rs.getInt("book_id"));
                borrowing.setBorrowDate(rs.getTimestamp("borrow_date"));
                borrowing.setSupposedReturnDate(rs.getTimestamp("supposed_return_date"));
                borrowing.setActualReturnDate(rs.getTimestamp("actual_return_date"));
                borrowing.setStatus(rs.getString("status"));
                borrowing.setUpdatedAt(rs.getTimestamp("updated_at"));
                borrowing.setBookTitle(rs.getString("book_title"));

                transactions.add(borrowing);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return transactions;
    }

    
    private String deriveTransactionStatus(String reservationStatus, Borrowing borrowing) {
        if (reservationStatus != null && reservationStatus.equals("Pending")) {
            return "Reservation"; // If reservation is still pending
        } else if (borrowing.getStatus().equals("Borrowed")) {
            return "Borrowing"; // If the status is "Borrowed"
        } else if (borrowing.getStatus().equals("Returned")) {
            return "Returning"; // If the status is "Returned"
        }
        return "Unknown"; // In case no valid status is found
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
    
    
    
}