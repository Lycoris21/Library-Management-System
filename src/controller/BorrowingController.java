package controller;

import java.sql.*;
import java.util.*;
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
    
    
    
}