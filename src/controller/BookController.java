package controller;

import java.sql.*;
import java.util.*;
import utility.Database;
import model.Book;


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
        String sql = "SELECT book_id, title, author, category, isbn, publisher, published_year, quantity, status, created_at, updated_at FROM Books";
        
        try (PreparedStatement pstmt = db.getConnection().prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
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
    
}