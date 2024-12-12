package controller;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import model.Record;
import utility.Database;

public class RecordController {
    private Database db;

    public RecordController(Database db) {
        this.db = db;
    }

    public void addRecord(Record record) {
        String sql = "INSERT INTO BorrowHistory (history_id, borrow_id, user_id, book_id, borrow_date, return_date, actual_return_date, status) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement pstmt = db.getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setInt(1, record.getHistoryId());
            pstmt.setInt(2, record.getBorrowId());
            pstmt.setInt(3, record.getUserId());
            pstmt.setInt(4, record.getBookId());
            pstmt.setDate(5, new java.sql.Date(record.getBorrowDate().getTime()));
            pstmt.setDate(6, new java.sql.Date(record.getReturnDate().getTime()));
            pstmt.setDate(7, new java.sql.Date(record.getActualReturnDate().getTime()));
            pstmt.setString(8, record.getStatus());
            pstmt.executeUpdate();

            ResultSet generatedKeys = pstmt.getGeneratedKeys();
            if (generatedKeys.next()) {
                record.setHistoryId(generatedKeys.getInt(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean updateRecord(Record record) {
        String sql = "UPDATE BorrowHistory SET borrow_id = ?, user_id = ?, book_id = ?, borrow_date = ?, return_date = ?, actual_return_date = ?, status = ? WHERE history_id = ?";
        try (PreparedStatement pstmt = db.getConnection().prepareStatement(sql)) {
            pstmt.setInt(1, record.getBorrowId());
            pstmt.setInt(2, record.getUserId());
            pstmt.setInt(3, record.getBookId());
            pstmt.setDate(4, new java.sql.Date(record.getBorrowDate().getTime()));
            pstmt.setDate(5, new java.sql.Date(record.getReturnDate().getTime()));
            pstmt.setDate(6, new java.sql.Date(record.getActualReturnDate().getTime()));
            pstmt.setString(7, record.getStatus());
            pstmt.setInt(8, record.getHistoryId());
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean deleteRecord(int historyId) {
        String sql = "DELETE FROM BorrowHistory WHERE history_id = ?";
        try (PreparedStatement pstmt = db.getConnection().prepareStatement(sql)) {
            pstmt.setInt(1, historyId);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public List<Record> searchRecords(String query) {
        List<Record> records = new ArrayList<>();
        String sql = "SELECT * FROM BorrowHistory WHERE history_id LIKE ? OR borrow_id LIKE ? OR user_id LIKE ? OR book_id LIKE ? OR borrow_date LIKE ? OR return_date LIKE ? OR actual_return_date LIKE ? OR status LIKE ?";
        try (PreparedStatement pstmt = db.getConnection().prepareStatement(sql)) {
            String searchQuery = "%" + query + "%";
            pstmt.setString(1, searchQuery);
            pstmt.setString(2, searchQuery);
            pstmt.setString(3, searchQuery);
            pstmt.setString(4, searchQuery);
            pstmt.setString(5, searchQuery);
            pstmt.setString(6, searchQuery);
            pstmt.setString(7, searchQuery);
            pstmt.setString(8, searchQuery);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    records.add(mapResultSetToRecord(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return records;
    }

    public List<Record> getAllRecords() {
        List<Record> records = new ArrayList<>();
        String sql = "SELECT * FROM BorrowHistory";
        try (Statement stmt = db.getConnection().createStatement()) {
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                records.add(mapResultSetToRecord(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return records;
    }

    // Helper method: Map a ResultSet row to a Record object
    private Record mapResultSetToRecord(ResultSet rs) throws SQLException {
        Record record = new Record();
        record.setHistoryId(rs.getInt("history_id"));
        record.setBorrowId(rs.getInt("borrow_id"));
        record.setUserId(rs.getInt("user_id"));
        record.setBookId(rs.getInt("book_id"));
        record.setBorrowDate(rs.getDate("borrow_date"));
        record.setReturnDate(rs.getDate("return_date"));
        record.setActualReturnDate(rs.getDate("actual_return_date"));
        record.setStatus(rs.getString("status"));
        return record;
    }
}