package model;

import java.sql.Timestamp;

public class Borrowing{
    
    private int borrowId;
    private int userId;
    private int bookId;
    private Timestamp borrowDate;
    private Timestamp supposedReturnDate;
    private Timestamp actualReturnDate;
    private String status;
    private Timestamp updatedAt;
    
    private String bookTitle; //added coz izza bijj
    
    public int getBorrowId() {
        return borrowId;
    }
    public void setBorrowId(int borrowId) {
        this.borrowId = borrowId;
    }

    public int getUserId() {
        return userId;
    }
    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getBookId() {
        return bookId;
    }
    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public Timestamp getBorrowDate() {
        return borrowDate;
    }
    public void setBorrowDate(Timestamp borrowDate) {
        this.borrowDate = borrowDate;
    }

    public Timestamp getSupposedReturnDate() {
        return supposedReturnDate;
    }
    public void setSupposedReturnDate(Timestamp supposedReturnDate) {
        this.supposedReturnDate = supposedReturnDate;
    }

    public Timestamp getActualReturnDate() {
        return actualReturnDate;
    }
    public void setActualReturnDate(Timestamp actualReturnDate) {
        this.actualReturnDate = actualReturnDate;
    }

    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    
    public Timestamp getUpdatedAt() {
        return updatedAt;
    }
    public void setUpdatedAt(Timestamp updatedAt) {
        this.updatedAt = updatedAt;
    }
    
    public String getBookTitle() {
        return bookTitle;
    }

    public void setBookTitle(String bookTitle) {
        this.bookTitle = bookTitle;
    }
    
}