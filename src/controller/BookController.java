package controller;

import java.sql.*;
import java.util.*;
import javax.swing.JOptionPane;
import utility.Database;
import model.Book;
import model.User;


public class BookController{
    
    private final Database db;

    public BookController(Database db) {
        this.db = db;
    }
    
    public List<Book> searchBooks(String query) {
        List<Book> books = new ArrayList<>();
        String sql = "SELECT book_id, title, author, category, isbn, publisher, published_year, quantity, status, created_at, updated_at FROM books WHERE title LIKE ? OR author LIKE ? OR category LIKE ? OR isbn LIKE ? OR publisher LIKE ? OR published_year LIKE ? OR status LIKE ?";
        try (PreparedStatement pstmt = db.getConnection().prepareStatement(sql)) {
            String searchQuery = "%" + query + "%";
            pstmt.setString(1, searchQuery);
            pstmt.setString(2, searchQuery);
            pstmt.setString(3, searchQuery);
            pstmt.setString(4, searchQuery);
            pstmt.setString(5, searchQuery);
            pstmt.setString(6, searchQuery);
            pstmt.setString(7, searchQuery);
            
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Book book = new Book();
                    book.setBookId(rs.getInt("book_id"));
                    book.setTitle(rs.getString("title"));
                    book.setAuthor(rs.getString("author"));
                    book.setCategory(rs.getString("category"));
                    book.setIsbn(rs.getString("isbn"));
                    book.setPublisher(rs.getString("publisher"));
                    book.setPublishedYear(rs.getInt("published_year"));
                    book.setQuantity(rs.getInt("quantity"));
                    book.setStatus(rs.getString("status"));
                    book.setCreatedAt(rs.getTimestamp("created_at"));
                    book.setUpdatedAt(rs.getTimestamp("updated_at"));
                    books.add(book);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return books;
    }
    
    public boolean addBook(Book book) {
        String query = "INSERT INTO books (title, author, category, isbn, publisher, published_year, quantity, status) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        
        try (Connection conn = db.getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(query)) {

            preparedStatement.setString(1, book.getTitle());
            preparedStatement.setString(2, book.getAuthor());
            preparedStatement.setString(3, book.getCategory());
            preparedStatement.setString(4, book.getIsbn());
            preparedStatement.setString(5, book.getPublisher());
            preparedStatement.setInt(6, book.getPublishedYear());
            preparedStatement.setInt(7, book.getQuantity());
            preparedStatement.setString(8, book.getStatus());

            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public boolean updateBook(Book book) {
        String sql = "UPDATE books SET title = ?, author = ?, category = ?, isbn = ?, publisher = ?, published_year = ?, quantity = ?, status = ?, updated_at = CURRENT_TIMESTAMP WHERE book_id = ?";
        try (PreparedStatement pstmt = db.getConnection().prepareStatement(sql)) {
            pstmt.setString(1, book.getTitle());
            pstmt.setString(2, book.getAuthor());
            pstmt.setString(3, book.getCategory());
            pstmt.setString(4, book.getIsbn());
            pstmt.setString(5, book.getPublisher());
            pstmt.setInt(6, book.getPublishedYear());
            pstmt.setInt(7, book.getQuantity());
            pstmt.setString(8, book.getStatus());
            pstmt.setInt(9, book.getBookId());
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean deleteBook(int bookId) {
        String sql = "DELETE FROM books WHERE book_id = ?";
        try (PreparedStatement pstmt = db.getConnection().prepareStatement(sql)) {
            pstmt.setInt(1, bookId);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    
    public Book getBookById(int bookId) {
        String sql = "SELECT book_id, title, author, category, isbn, publisher, published_year, quantity, status, created_at, updated_at FROM books WHERE book_id = ?";
        try (PreparedStatement pstmt = db.getConnection().prepareStatement(sql)) {
            pstmt.setInt(1, bookId);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    Book book = new Book();
                    book.setBookId(rs.getInt("book_id"));
                    book.setTitle(rs.getString("title"));
                    book.setAuthor(rs.getString("author"));
                    book.setCategory(rs.getString("category"));
                    book.setIsbn(rs.getString("isbn"));
                    book.setPublisher(rs.getString("publisher"));
                    book.setPublishedYear(rs.getInt("published_year"));
                    book.setQuantity(rs.getInt("quantity"));
                    book.setStatus(rs.getString("status"));
                    book.setCreatedAt(rs.getTimestamp("created_at"));
                    book.setUpdatedAt(rs.getTimestamp("updated_at"));
                    return book;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public int getBookCount() {
        String sql = "SELECT COUNT(book_id) AS book_count FROM books WHERE NOT status = 'Deleted'";
        try (PreparedStatement pstmt = db.getConnection().prepareStatement(sql); ResultSet rs = pstmt.executeQuery()) {
            if (rs.next()) {
                return rs.getInt("book_count");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
    
    public int getCurrentlyReservedCount() {
        String sql = "SELECT COUNT(reservation_id) AS reservation_count FROM reservations where status = 'Pending'";
        try (PreparedStatement pstmt = db.getConnection().prepareStatement(sql); ResultSet rs = pstmt.executeQuery()) {
            if (rs.next()) {
                return rs.getInt("reservation_count");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public int getCurrentlyBorrowingCount() {
        String sql = "SELECT COUNT(borrow_id) AS currentlyBorrowing_count FROM borrowing where status = 'Borrowed'";
        try (PreparedStatement pstmt = db.getConnection().prepareStatement(sql); ResultSet rs = pstmt.executeQuery()) {
            if (rs.next()) {
                return rs.getInt("currentlyBorrowing_count");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
    
    public int getCurrentlyOverdueCount() {
        String sql = "SELECT COUNT(borrow_id) AS overdue_count FROM borrowing WHERE status = 'Overdue'";
        try (PreparedStatement pstmt = db.getConnection().prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            if (rs.next()) {
                return rs.getInt("overdue_count");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
    
    // User-specific methods
    public int getUserCurrentlyReservedCount(User user) {
        String sql = "SELECT COUNT(reservation_id) AS reservation_count FROM reservations WHERE user_id = ? AND status = 'Pending'";
        try (PreparedStatement pstmt = db.getConnection().prepareStatement(sql)) {
            pstmt.setInt(1, user.getUserId());
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("reservation_count");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public int getUserCurrentlyBorrowingCount(User user) {
        String sql = "SELECT COUNT(borrow_id) AS borrowing_count FROM borrowing WHERE user_id = ? AND status = 'Borrowed'";
        try (PreparedStatement pstmt = db.getConnection().prepareStatement(sql)) {
            pstmt.setInt(1, user.getUserId());
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("borrowing_count");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public int getUserCurrentlyOverdueCount(User user) {
        String sql = "SELECT COUNT(borrow_id) AS overdue_count FROM borrowing WHERE user_id = ? AND status = 'Overdue'";
        try (PreparedStatement pstmt = db.getConnection().prepareStatement(sql)) {
            pstmt.setInt(1, user.getUserId());
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("overdue_count");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
    
    public int getTotalBorrowingCount() {
        String sql = "SELECT COUNT(borrow_id) AS totalBorrowing_count FROM borrowing";
        try (PreparedStatement pstmt = db.getConnection().prepareStatement(sql); ResultSet rs = pstmt.executeQuery()) {
            if (rs.next()) {
                return rs.getInt("totalBorrowing_count");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
    
    public List<Book> getAllBooks() {
        List<Book> books = new ArrayList<>();
        String sql = "SELECT * FROM Books WHERE status != 'Deleted'";

        try (Connection conn = db.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql); ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                Book book = new Book();
                book.setBookId(rs.getInt("book_id"));
                book.setTitle(rs.getString("title"));
                book.setAuthor(rs.getString("author"));
                book.setCategory(rs.getString("category"));
                book.setIsbn(rs.getString("isbn"));
                book.setPublisher(rs.getString("publisher"));
                book.setPublishedYear(rs.getInt("published_year"));
                book.setQuantity(rs.getInt("quantity"));
                book.setStatus(rs.getString("status"));
                
                books.add(book);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return books;
    }
    
    public List<Book> getMostBorrowedBooks() {
        List<Book> books = new ArrayList<>();
        String sql = """
        SELECT TOP 10
            b.book_id, 
            b.title, 
            b.category, 
            b.author,
            COUNT(br.book_id) AS borrow_count 
        FROM 
            books b
        LEFT JOIN 
            borrowing br ON b.book_id = br.book_id
        GROUP BY 
            b.book_id, b.title, b.category, b.author
        ORDER BY 
            borrow_count DESC
    """;

        try (Connection conn = db.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql); ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                Book book = new Book();
                book.setBookId(rs.getInt("book_id"));
                book.setTitle(rs.getString("title"));
                book.setCategory(rs.getString("category"));
                book.setAuthor(rs.getString("author"));
                books.add(book);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return books;
    }
    
    public List<Book> getNewestBooks() {
        List<Book> books = new ArrayList<>();
        String sql = """
        SELECT TOP 10
            book_id, 
            title, 
            category, 
            author, 
            created_at
        FROM 
            books
        WHERE 
            status != 'Deleted'
        ORDER BY 
            created_at DESC
    """;

        try (Connection conn = db.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql); ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                Book book = new Book();
                book.setBookId(rs.getInt("book_id"));
                book.setTitle(rs.getString("title"));
                book.setCategory(rs.getString("category"));
                book.setAuthor(rs.getString("author"));
                book.setCreatedAt(rs.getTimestamp("created_at"));
                books.add(book);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return books;
    }
    
    public boolean reserveBook(int userId, int bookId) {
        // First, show a confirmation dialog
        int confirm = JOptionPane.showConfirmDialog(
                null,
                "Are you sure you want to reserve this book?",
                "Confirm Reservation",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE
        );

        // If user doesn't confirm, return false
        if (confirm != JOptionPane.YES_OPTION) {
            return false;
        }

        String checkBookAvailabilitySql
                = "SELECT quantity, title, author FROM Books WHERE book_id = ?";

        String checkExistingReservationSql
                = "SELECT COUNT(*) FROM Reservations "
                + "WHERE user_id = ? AND book_id = ? AND status = 'Pending'";

        String insertReservationSql
                = "INSERT INTO Reservations (user_id, book_id, status, collection_deadline) "
                + "VALUES (?, ?, 'Pending', DATEADD(WEEK, 1, GETDATE()))";

        Connection conn = null;
        try {
            conn = db.getConnection();
            conn.setAutoCommit(false);

            // Check book availability with additional details
            String bookTitle = "";
            String bookAuthor = "";
            try (PreparedStatement availabilityStmt = conn.prepareStatement(checkBookAvailabilitySql)) {
                availabilityStmt.setInt(1, bookId);
                try (ResultSet rs = availabilityStmt.executeQuery()) {
                    if (rs.next()) {
                        int quantity = rs.getInt("quantity");
                        bookTitle = rs.getString("title");
                        bookAuthor = rs.getString("author");

                        if (quantity <= 0) {
                            JOptionPane.showMessageDialog(null,
                                    "Sorry, this book is currently out of stock.",
                                    "Reservation Failed",
                                    JOptionPane.ERROR_MESSAGE);
                            return false;
                        }
                    } else {
                        JOptionPane.showMessageDialog(null,
                                "Book not found.",
                                "Reservation Failed",
                                JOptionPane.ERROR_MESSAGE);
                        return false;
                    }
                }
            }

            // Check for existing pending reservation
            try (PreparedStatement checkStmt = conn.prepareStatement(checkExistingReservationSql)) {
                checkStmt.setInt(1, userId);
                checkStmt.setInt(2, bookId);

                try (ResultSet rs = checkStmt.executeQuery()) {
                    if (rs.next() && rs.getInt(1) > 0) {
                        JOptionPane.showMessageDialog(null,
                                "You already have a pending reservation for this book.",
                                "Reservation Failed",
                                JOptionPane.ERROR_MESSAGE);
                        return false;
                    }
                }
            }

            // Insert new reservation
            try (PreparedStatement insertStmt = conn.prepareStatement(insertReservationSql)) {
                insertStmt.setInt(1, userId);
                insertStmt.setInt(2, bookId);
                insertStmt.executeUpdate();
            }

            // Commit transaction
            conn.commit();

            // Show success message with book details
            JOptionPane.showMessageDialog(null,
                    "Book Reserved Successfully!\n\n"
                    + "Book Details:\n"
                    + "Title: " + bookTitle + "\n"
                    + "Author: " + bookAuthor + "\n\n"
                    + "You have one week to collect the book.",
                    "Reservation Successful",
                    JOptionPane.INFORMATION_MESSAGE);

            return true;
        } catch (SQLException e) {
            // Rollback transaction
            if (conn != null) {
                try {
                    conn.rollback();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }

            // Detailed error handling
            if (e.getMessage().contains("duplicate")) {
                JOptionPane.showMessageDialog(null,
                        "You have already reserved this book.",
                        "Reservation Failed",
                        JOptionPane.ERROR_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null,
                        "An error occurred while reserving the book: " + e.getMessage(),
                        "Reservation Error",
                        JOptionPane.ERROR_MESSAGE);
            }

            e.printStackTrace();
            return false;
        } finally {
            // Ensure connection is closed and reset
            if (conn != null) {
                try {
                    conn.setAutoCommit(true);
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    
    public List<Book> getAvailableBooks() {
        List<Book> availableBooks = new ArrayList<>();
        String sql = "SELECT * FROM books WHERE quantity > 0 AND status = 'Available'"; // Adjust the status as needed

        try (Connection conn = db.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql); ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                Book book = new Book();
                book.setBookId(rs.getInt("book_id"));
                book.setTitle(rs.getString("title"));
                book.setAuthor(rs.getString("author"));
                book.setCategory(rs.getString("category"));
                book.setIsbn(rs.getString("isbn"));
                book.setPublisher(rs.getString("publisher"));
                book.setPublishedYear(rs.getInt("published_year"));
                book.setQuantity(rs.getInt("quantity"));
                book.setStatus(rs.getString("status"));
                availableBooks.add(book);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return availableBooks;
    }
    
}