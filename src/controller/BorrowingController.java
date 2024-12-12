package controller;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import model.Borrowing; // Assuming you have a model for borrowing records
import utility.Database;

public class BorrowingController {
    
    private Database db;

    // Constructor to initialize the database connection
    public BorrowingController(Database db) {
        this.db = db;
    }

    public List<Borrowing> getCurrentBorrowingBooks(int userId) {
        List<Borrowing> records = new ArrayList<>();
        String sql = "SELECT b.book_id, b.title, br.borrow_id, br.borrow_date, br.return_date, br.status " +
                     "FROM Borrowing br " +
                     "JOIN Books b ON br.book_id = b.book_id " +
                     "WHERE br.user_id = ? AND br.status = 'Borrowed'";
        
        try (PreparedStatement pstmt = db.getConnection().prepareStatement(sql)) {
            pstmt.setInt(1, userId);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Borrowing record = new Borrowing();
                    record.setBorrowId(rs.getInt("borrow_id"));
                    record.setBookId(rs.getInt("book_id"));
                    record.setTitle(rs.getString("title"));
                    record.setBorrowDate(rs.getTimestamp("borrow_date"));
                    record.setReturnDate(rs.getTimestamp("return_date"));
                    record.setStatus(rs.getString("status"));
                    records.add(record);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return records;
    }

    public List<Borrowing> getTop10RecentlyBorrowedBooks() {
        List<Borrowing> records = new ArrayList<>();
        String sql = "SELECT b.book_id, b.title, br.borrow_id, br.borrow_date, br.return_date, br.status " +
                     "FROM Borrowing br " +
                     "JOIN Books b ON br.book_id = b.book_id " +
                     "ORDER BY br.borrow_date DESC " +
                     "OFFSET 0 ROWS FETCH NEXT 10 ROWS ONLY";
        
        try (PreparedStatement pstmt = db.getConnection().prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                Borrowing record = new Borrowing();
                record.setBorrowId(rs.getInt("borrow_id"));
                record.setBookId(rs.getInt("book_id"));
                record.setTitle(rs.getString("title"));
                record.setBorrowDate(rs.getTimestamp("borrow_date"));
                record.setReturnDate(rs.getTimestamp("return_date"));
                record.setStatus(rs.getString("status"));
                records.add(record);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return records;
    }

    public List<Borrowing> getBorrowingHistory(int userId) {
        List<Borrowing> records = new ArrayList<>();
        String sql = "SELECT b.book_id, b.title, br.borrow_id, br.borrow_date, br.return_date, br.actual_return_date, br.status " +
                     "FROM BorrowHistory br " +
                     "JOIN Books b ON br.book_id = b.book_id " +
                     "WHERE br.user_id = ?";
        
        try (PreparedStatement pstmt = db.getConnection().prepareStatement(sql)) {
            pstmt.setInt(1, userId);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Borrowing record = new Borrowing();
                    record.setBorrowId(rs.getInt("borrow_id"));
                    record.setBookId(rs.getInt("book_id"));
                    record.setTitle(rs.getString("title"));
                    record.setBorrowDate(rs.getTimestamp("borrow_date"));
                    record.setReturnDate(rs.getTimestamp("return_date"));
                    record.setActualReturnDate(rs.getTimestamp("actual_return_date"));
                    record.setStatus(rs.getString("status"));
                    records.add(record);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return records;
    }
}