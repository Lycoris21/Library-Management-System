package controller;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import model.Book;
import utility.Database;


/**
 *
 * @author Christine Ann Dejito
 */
public class BookController {
    
    private Database db;

    // Constructor to initialize the database connection
    public BookController(Database db) {
        this.db = db;
    }
    
    public int getBookCount() {
        String sql = "SELECT COUNT(book_id) AS book_count FROM books";
        try (PreparedStatement pstmt = db.getConnection().prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            if (rs.next()) {
                return rs.getInt("book_count");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }  
    
    public int getReservationCount() {
        String sql = "SELECT COUNT(reservation_id) AS reservation_count FROM reservations";
        try (PreparedStatement pstmt = db.getConnection().prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            if (rs.next()) {
                return rs.getInt("reservation_count");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }  
    
    public int getCurrentlyBorrowingCount() {
        String sql = "SELECT COUNT(borrow_id) AS currentlyBorrowing_count FROM borrowing";
        try (PreparedStatement pstmt = db.getConnection().prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            if (rs.next()) {
                return rs.getInt("currentlyBorrowing_count");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }  
    
    public int getOverdueCount() {
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
    
    public int getTotalCount() {
        String sql = "SELECT COUNT(history_id) AS total_count FROM borrowhistory";
        try (PreparedStatement pstmt = db.getConnection().prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            if (rs.next()) {
                return rs.getInt("total_count");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }  
    
    public List<Book> getAllBooks() {
        List<Book> books = new ArrayList<>();
        String sql = "SELECT book_id, title, author, category, isbn, quantity, created_at, updated_at FROM Books";
        
        try (PreparedStatement pstmt = db.getConnection().prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                Book book = new Book();
                book.setBookId(rs.getInt("book_id"));
                book.setTitle(rs.getString("title"));
                book.setAuthor(rs.getString("author"));
                book.setCategory(rs.getString("category"));
                book.setIsbn(rs.getString("isbn"));
                book.setQuantity(rs.getInt("quantity"));
                book.setCreatedAt(rs.getTimestamp("created_at"));
                book.setUpdatedAt(rs.getTimestamp("updated_at"));
                books.add(book);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return books;
    }
    
    public boolean addBook(Book book) {
        String query = "INSERT INTO books (title, author, category, isbn, quantity) VALUES (?, ?, ?, ?, ?)";
        
        try (Connection conn = db.getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(query)) {

            preparedStatement.setString(1, book.getTitle());
            preparedStatement.setString(2, book.getAuthor());
            preparedStatement.setString(3, book.getCategory());
            preparedStatement.setString(4, book.getIsbn());
            preparedStatement.setInt(5, book.getQuantity());

            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
}
