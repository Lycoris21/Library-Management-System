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
        // SQL query to select the latest transactions, including transaction type and book title
        String sql = "SELECT br.borrow_id, br.book_id, br.user_id, br.borrow_date, br.return_date, br.actual_return_date, "
                + "b.title, r.status AS reservation_status "
                + "FROM borrowings br "
                + "JOIN books b ON br.book_id = b.book_id "
                + "LEFT JOIN reservations r ON br.book_id = r.book_id AND br.user_id = r.user_id "
                + "ORDER BY br.borrow_date DESC "
                + "LIMIT 10";

        try (PreparedStatement pstmt = db.getConnection().prepareStatement(sql); ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                Borrowing borrowing = new Borrowing();
                borrowing.setBorrowId(rs.getInt("borrow_id"));
                borrowing.setBookId(rs.getInt("book_id"));
                borrowing.setUserId(rs.getInt("user_id"));
                borrowing.setBorrowDate(rs.getTimestamp("borrow_date"));
                borrowing.setReturnDate(rs.getTimestamp("return_date"));
                borrowing.setActualReturnDate(rs.getTimestamp("actual_return_date"));

                // Derive the transaction status
                String transactionType = deriveTransactionStatus(rs.getString("reservation_status"), borrowing);
                borrowing.setStatus(transactionType);

                borrowing.setBookTitle(rs.getString("title")); // Assuming Borrowing model has a `bookTitle` field
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


    
}