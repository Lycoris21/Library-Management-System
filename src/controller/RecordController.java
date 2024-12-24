package controller;

import model.User;
import model.Book;
import model.Borrowing;
import model.Reservation; // Make sure this model exists
import java.sql.*;
import java.util.List;
import java.util.ArrayList;
import utility.Database;

public class RecordController {

    private final Database db;

    public RecordController(Database db) {
        this.db = db;
    }

    public boolean cancelReservation(int reservationId) {
        String sql = "UPDATE Reservations SET status = 'Void' WHERE reservation_id = ? AND status = 'Pending'";

        try (Connection conn = db.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, reservationId);
            int affectedRows = pstmt.executeUpdate();

            return affectedRows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public List<RecordDisplay> getRecords() {
        List<RecordDisplay> records = new ArrayList<>();

        String sql = """
         SELECT u.username, 
                b.title, 
                r.reservation_id AS reservation_id, 
                b2.borrow_id AS borrowing_id, 
                r.status AS reservation_status, 
                b2.status AS borrowing_status, 
                COALESCE(GREATEST(r.updated_at, b2.updated_at), r.updated_at, b2.updated_at) AS last_updated 
         FROM Users u 
         LEFT JOIN Reservations r ON u.user_id = r.user_id 
         LEFT JOIN Books b ON r.book_id = b.book_id 
         LEFT JOIN Borrowing b2 ON u.user_id = b2.user_id AND b.book_id = b2.book_id 
         WHERE r.status IS NOT NULL OR b2.status IS NOT NULL 
         GROUP BY u.username, b.title, r.reservation_id, b2.borrow_id, r.status, b2.status, r.updated_at, b2.updated_at 
         ORDER BY last_updated DESC;"""; // Sort by last_updated in descending order

        try (Connection connection = db.getConnection(); PreparedStatement stmt = connection.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                String username = rs.getString("username");
                String title = rs.getString("title");
                String reservationStatus = rs.getString("reservation_status");
                String borrowingStatus = rs.getString("borrowing_status");
                int reservationId = rs.getInt("reservation_id"); // Get the reservation ID from the result set
                int borrowingId = rs.getInt("borrowing_id"); // Get the borrowing ID from the result set

                // Use the determineStatus method to get the final status
                String status = determineStatus(reservationStatus, borrowingStatus);
                Timestamp lastUpdated = rs.getTimestamp("last_updated");

                // Pass both IDs to RecordDisplay
                records.add(new RecordDisplay(reservationId, borrowingId, username, title, status, lastUpdated));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return records;
    }
    
    public List<RecordDisplay> getRecordsForUser(String username, String recordType) {
        List<RecordDisplay> userRecords = new ArrayList<>();

        String sql;
        if ("reservations".equalsIgnoreCase(recordType)) {
            sql = """
            SELECT u.username, 
                   b.title, 
                   r.reservation_id AS record_id, 
                   r.status AS record_status, 
                   r.collection_deadline AS collection_deadline,
                   r.collected_on AS collected_on,
                   r.updated_at AS last_updated 
            FROM Users u 
            JOIN Reservations r ON u.user_id = r.user_id 
            JOIN Books b ON r.book_id = b.book_id 
            WHERE u.username = ? AND r.status IN ('Pending', 'Collected', 'Void')
            ORDER BY 
                CASE 
                    WHEN r.status = 'Pending' THEN 0 
                    ELSE 1 
                END,
                r.updated_at DESC;""";
        } else if ("borrowing".equalsIgnoreCase(recordType)) {
            sql = """
            SELECT u.username, 
                   b.title, 
                   b2.borrow_id AS record_id, 
                   b2.status AS record_status, 
                   b2.borrow_date,
                   b2.supposed_return_date,
                   b2.actual_return_date,
                   b2.updated_at AS last_updated 
            FROM Users u 
            JOIN Borrowing b2 ON u.user_id = b2.user_id 
            JOIN Books b ON b2.book_id = b.book_id 
            WHERE u.username = ? AND b2.status IN ('Borrowed', 'Returned', 'Overdue')
            ORDER BY 
                CASE 
                    WHEN b2.status = 'Borrowed' THEN 0 
                    ELSE 1 
                END,
                b2.updated_at DESC;""";
        } else {
            throw new IllegalArgumentException("Invalid record type. Use 'reservations' or 'borrowing'.");
        }

        try (Connection connection = db.getConnection(); PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setString(1, username);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    if ("reservations".equalsIgnoreCase(recordType)) {
                        userRecords.add(new RecordDisplay(
                                rs.getInt("record_id"),
                                username,
                                rs.getString("title"),
                                rs.getString("record_status"),
                                rs.getTimestamp("last_updated"),
                                rs.getTimestamp("collection_deadline"),
                                rs.getTimestamp("collected_on")
                        ));
                    } else {
                        userRecords.add(new RecordDisplay(
                                rs.getInt("record_id"),
                                username,
                                rs.getString("title"),
                                rs.getString("record_status"),
                                rs.getTimestamp("last_updated"),
                                rs.getString("borrow_date"),
                                rs.getString("supposed_return_date"),
                                rs.getString("actual_return_date")
                        ));
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return userRecords;
    }
    
    public List<RecordDisplay> getReservationRecords(String status) {
        List<RecordDisplay> reservationRecords = new ArrayList<>();

        String sql = """
        SELECT u.username, 
               b.title, 
               r.reservation_id AS reservation_id, 
               r.status AS reservation_status, 
               r.updated_at AS last_updated 
        FROM Users u 
        JOIN Reservations r ON u.user_id = r.user_id 
        JOIN Books b ON r.book_id = b.book_id 
        WHERE r.status = ? 
        ORDER BY r.updated_at DESC;
    """;

        try (Connection connection = db.getConnection(); PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, status);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    String username = rs.getString("username");
                    String title = rs.getString("title");
                    int reservationId = rs.getInt("reservation_id");
                    String reservationStatus = rs.getString("reservation_status");
                    Timestamp lastUpdated = rs.getTimestamp("last_updated");

                    reservationRecords.add(new RecordDisplay(reservationId, username, title, reservationStatus, lastUpdated, null, null));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return reservationRecords;
    }

    public List<RecordDisplay> getBorrowingRecords(String status) {
        List<RecordDisplay> borrowingRecords = new ArrayList<>();

        String sql = """
        SELECT u.username, 
               b.title, 
               b2.borrow_id AS borrowing_id, 
               b2.status AS borrowing_status, 
               b2.updated_at AS last_updated 
        FROM Users u 
        JOIN Borrowing b2 ON u.user_id = b2.user_id 
        JOIN Books b ON b2.book_id = b.book_id 
        WHERE b2.status = ? 
        ORDER BY b2.updated_at DESC;
    """;

        try (Connection connection = db.getConnection(); PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, status);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    String username = rs.getString("username");
                    String title = rs.getString("title");
                    int borrowingId = rs.getInt("borrowing_id");
                    String borrowingStatus = rs.getString("borrowing_status");
                    Timestamp lastUpdated = rs.getTimestamp("last_updated");

                    borrowingRecords.add(new RecordDisplay(0, borrowingId, username, title, borrowingStatus, lastUpdated));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return borrowingRecords;
    }

    private String determineStatus(String reservationStatus, String borrowingStatus) {
        if (reservationStatus != null) {
            switch (reservationStatus) {
                case "Pending":
                    return "Reserved";
                case "Collected":
                    switch (borrowingStatus) {
                        case "Borrowed":
                            return "Borrowed";
                        case "Returned":
                            return "Returned";
                        case "Overdue":
                            return "Overdue";
                        default:
                            return "Unknown";
                    }
                case "Void":
                    return "Voided";
                default:
                    return "Unknown";
            }
        }

        return "Unknown"; // Fallback status
    }
    
    public ReservationDetails getReservationDetails(int reservationId) {
        // Existing minimal query
        String minimalSql = """
        SELECT r.reservation_id, r.user_id, u.username, r.book_id, 
               b.title, r.status, r.collection_deadline, r.created_at, r.updated_at 
        FROM Reservations r 
        JOIN Users u ON r.user_id = u.user_id 
        JOIN Books b ON r.book_id = b.book_id 
        WHERE r.reservation_id = ?
    """;

        // Detailed query
        String detailedSql = """
        SELECT r.reservation_id, r.user_id, u.username, r.book_id, 
               b.title, b.author, b.category, b.isbn, b.publisher, b.published_year,
               r.status, r.collection_deadline, r.collected_on, r.created_at, r.updated_at 
        FROM Reservations r 
        JOIN Users u ON r.user_id = u.user_id 
        JOIN Books b ON r.book_id = b.book_id 
        WHERE r.reservation_id = ?
    """;

        try (Connection connection = db.getConnection(); PreparedStatement stmt = connection.prepareStatement(detailedSql)) {
            stmt.setInt(1, reservationId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                // Check if detailed fields exist
                String author = rs.getString("author");
                if (author != null) {
                    // Return detailed reservation details
                    return new ReservationDetails(
                            rs.getInt("reservation_id"),
                            rs.getInt("user_id"),
                            rs.getString("username"),
                            rs.getInt("book_id"),
                            rs.getString("title"),
                            rs.getString("author"),
                            rs.getString("category"),
                            rs.getString("isbn"),
                            rs.getString("publisher"),
                            rs.getInt("published_year"),
                            rs.getString("status"),
                            rs.getString("collection_deadline"),
                            rs.getString("collected_on"),
                            rs.getString("created_at"),
                            rs.getString("updated_at")
                    );
                } else {
                    // Return minimal reservation details
                    return new ReservationDetails(
                            rs.getInt("reservation_id"),
                            rs.getInt("user_id"),
                            rs.getString("username"),
                            rs.getInt("book_id"),
                            rs.getString("title"),
                            rs.getString("status"),
                            rs.getString("collection_deadline"),
                            rs.getString("collected_on"),
                            rs.getString("created_at"),
                            rs.getString("updated_at")
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    } // Return null if no details found

    public BorrowingDetails getBorrowingDetails(int borrowingId) {
    String sql = """
        SELECT 
            b.borrow_id, 
            b.user_id, 
            u.username, 
            b.book_id, 
            bk.title, 
            b.status, 
            b.borrow_date, 
            b.supposed_return_date, 
            b.actual_return_date, 
            b.updated_at,
            bk.author,
            bk.category,
            bk.isbn,
            bk.publisher,
            bk.published_year
        FROM Borrowing b 
        JOIN Users u ON b.user_id = u.user_id 
        JOIN Books bk ON b.book_id = bk.book_id 
        WHERE b.borrow_id = ?
    """;

    try (Connection connection = db.getConnection(); 
         PreparedStatement stmt = connection.prepareStatement(sql)) {
        stmt.setInt(1, borrowingId);
        ResultSet rs = stmt.executeQuery();

        if (rs.next()) {
            // Check if detailed fields exist
            String author = rs.getString("author");
            if (author != null) {
                return new BorrowingDetails(
                    rs.getInt("borrow_id"),
                    rs.getInt("user_id"),
                    rs.getString("username"),
                    rs.getInt("book_id"),
                    rs.getString("title"),
                    rs.getString("status"),
                    rs.getString("borrow_date"),
                    rs.getString("supposed_return_date"),
                    rs.getString("actual_return_date"),
                    rs.getString("updated_at"),
                    rs.getString("author"),
                    rs.getString("category"),
                    rs.getString("isbn"),
                    rs.getString("publisher"),
                    rs.getInt("published_year")
                );
            } else {
                // Return minimal borrowing details if book details are not available
                return new BorrowingDetails(
                    rs.getInt("borrow_id"),
                    rs.getInt("user_id"),
                    rs.getString("username"),
                    rs.getInt("book_id"),
                    rs.getString("title"),
                    rs.getString("status"),
                    rs.getString("borrow_date"),
                    rs.getString("supposed_return_date"),
                    rs.getString("actual_return_date"),
                    rs.getString("updated_at")
                );
            }
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return null;
}

    // Inner class to represent the display record
    public static class RecordDisplay {

        private int reservationId; // Add reservation ID
        private int borrowingId; // Add borrowing ID
        private String username;
        private String title;
        private String status;
        private Timestamp lastUpdated;
        private Timestamp collectionDeadline;
        private Timestamp collectedOn;
        
        private String borrowDate;
        private String supposedReturnDate;
        private String actualReturnDate;

        public RecordDisplay(int reservationId, int borrowingId, String username, String title, String status, Timestamp lastUpdated) {
            this.reservationId = reservationId; // Initialize reservation ID
            this.borrowingId = borrowingId; // Initialize borrowing ID
            this.username = username;
            this.title = title;
            this.status = status;
            this.lastUpdated = lastUpdated;
        }
        
        public RecordDisplay(int reservationId, String username, String title, String status, Timestamp lastUpdated, Timestamp collectionDeadline, Timestamp collectedOn) {
            this.reservationId = reservationId;
            this.username = username;
            this.title = title;
            this.status = status;
            this.lastUpdated = lastUpdated;
            this.collectionDeadline = collectionDeadline;
            this.collectedOn = collectedOn;
        }
        
        public RecordDisplay(int borrowingId, String username, String title, String status, Timestamp lastUpdated, String borrowDate, String supposedReturnDate, String actualReturnDate) {
            this.borrowingId = borrowingId;
            this.username = username;
            this.title = title;
            this.status = status;
            this.lastUpdated = lastUpdated;
            this.borrowDate = borrowDate;
            this.supposedReturnDate = supposedReturnDate;
            this.actualReturnDate = actualReturnDate;
        }

        // Getters
        public int getReservationId() {
            return reservationId; // Getter for reservation ID
        }

        public int getBorrowingId() {
            return borrowingId; // Getter for borrowing ID
        }

        public String getUsername() {
            return username;
        }

        public String getTitle() {
            return title;
        }

        public String getStatus() {
            return status;
        }

        public Timestamp getLastUpdated() {
            return lastUpdated;
        }
        
        public Timestamp getCollectionDeadline() {
            return collectionDeadline;
        }
        
        public Timestamp getCollectedOn() {
            return collectedOn;
        }
        
        public String getBorrowDate() {
            return borrowDate;
        }

        public String getSupposedReturnDate() {
            return supposedReturnDate;
        }

        public String getActualReturnDate() {
            return actualReturnDate;
        }
    }
    
    public class ReservationDetails {

        private int id;
        private int userId;
        private String username;
        private int bookId;
        private String bookTitle;
        
        private String bookAuthor = "";
        private String bookCategory = "";
        private String bookIsbn = "";
        private String bookPublisher = "";
        private int bookPublishedYear = 0;
        
        private String status;
        private String collectionDeadline;
        private String collectedOn;
        private String createdAt;
        private String updatedAt;

        // Constructor, getters, and setters
        public ReservationDetails(int id, int userId, String username, int bookId, String bookTitle, String status, String collectionDeadline, String collectedOn, String createdAt, String updatedAt) {
            this.id = id;
            this.userId = userId;
            this.username = username;
            this.bookId = bookId;
            this.bookTitle = bookTitle;
            this.status = status;
            this.collectionDeadline = collectionDeadline;
            this.collectedOn = collectedOn;
            this.createdAt = createdAt;
            this.updatedAt = updatedAt;
        }
        
        public ReservationDetails(int id, int userId, String username, int bookId,
                String bookTitle, String bookAuthor, String bookCategory,
                String bookIsbn, String bookPublisher, int bookPublishedYear,
                String status, String collectionDeadline, String collectedOn,
                String createdAt, String updatedAt) {
            this(id, userId, username, bookId, bookTitle, status, collectionDeadline, collectedOn, createdAt, updatedAt);
            this.bookAuthor = bookAuthor;
            this.bookCategory = bookCategory;
            this.bookIsbn = bookIsbn;
            this.bookPublisher = bookPublisher;
            this.bookPublishedYear = bookPublishedYear;
        }

        // Getters
        public int getId() {
            return id;
        }
        public int getUserId() {
            return userId;
        }

        public String getUsername() {
            return username;
        }

        public int getBookId() {
            return bookId;
        }

        public String getBookTitle() {
            return bookTitle;
        }

        public String getStatus() {
            return status;
        }

        public String getCollectionDeadline() {
            return collectionDeadline;
        }
        
        public String getCollectedOn() {
            return collectedOn;
        }

        public String getCreatedAt() {
            return createdAt;
        }

        public String getUpdatedAt() {
            return updatedAt;
        }
        
        public String getBookAuthor() {
            return bookAuthor;
        }

        public String getBookCategory() {
            return bookCategory;
        }

        public String getBookIsbn() {
            return bookIsbn;
        }

        public String getBookPublisher() {
            return bookPublisher;
        }

        public int getBookPublishedYear() {
            return bookPublishedYear;
        }
    }
    
    public class BorrowingDetails {

        private int id;
        private int userId;
        private String username;
        private int bookId;
        private String bookTitle;
        private String status;
        private String borrowDate;
        private String supposedReturnDate;
        private String actualReturnDate;
        private String updatedAt;

        private String bookAuthor = "";
        private String bookCategory = "";
        private String bookIsbn = "";
        private String bookPublisher = "";
        private int bookPublishedYear = 0;
        
        // Constructor, getters, and setters
        public BorrowingDetails(int id, int userId, String username, int bookId, String bookTitle, String status, String borrowDate, String supposedReturnDate, String actualReturnDate, String updatedAt) {
            this.id = id;
            this.userId = userId;
            this.username = username;
            this.bookId = bookId;
            this.bookTitle = bookTitle;
            this.status = status;
            this.borrowDate = borrowDate;
            this.supposedReturnDate = supposedReturnDate;
            this.actualReturnDate = actualReturnDate;
            this.updatedAt = updatedAt;
        }
        
        public BorrowingDetails(int id, int userId, String username, int bookId, String bookTitle, String status, String borrowDate, String supposedReturnDate, String actualReturnDate, String updatedAt, String bookAuthor, String bookCategory, String bookIsbn, String bookPublisher, int bookPublishedYear) {
            this(id, userId, username, bookId, bookTitle, status, borrowDate, 
                 supposedReturnDate, actualReturnDate, updatedAt);
            this.bookAuthor = bookAuthor;
            this.bookCategory = bookCategory;
            this.bookIsbn = bookIsbn;
            this.bookPublisher = bookPublisher;
            this.bookPublishedYear = bookPublishedYear;
        }

        // Getters
        public int getId() {
            return id;
        }
        public int getUserId() {
            return userId;
        }

        public String getUsername() {
            return username;
        }

        public int getBookId() {
            return bookId;
        }

        public String getBookTitle() {
            return bookTitle;
        }
        
        public String getBookAuthor() {
            return bookAuthor;
        }

        public String getBookCategory() {
            return bookCategory;
        }

        public String getBookIsbn() {
            return bookIsbn;
        }

        public String getBookPublisher() {
            return bookPublisher;
        }

        public int getBookPublishedYear() {
            return bookPublishedYear;
        }

        public String getStatus() {
            return status;
        }

        public String getBorrowDate() {
            return borrowDate;
        }

        public String getSupposedReturnDate() {
            return supposedReturnDate;
        }

        public String getActualReturnDate() {
            return actualReturnDate;
        }

        public String getUpdatedAt() {
            return updatedAt;
        }
    }
}