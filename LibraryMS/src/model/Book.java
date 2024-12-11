package model;

import java.util.*;

/**
 *
 * @author Christine Ann Dejito
 */
public class Book {
    
    private int bookCount;
    private int reservationCount;
    private int currentlyBorrowingCount;
    private int overdueCount;
    private int totalCount;
    
    private int bookId;
    private String title;
    private String author;
    private String category;
    private String isbn;
    private int quantity;
    private Date createdAt;
    private Date updatedAt;
    
    public Book(){
        
    }
    
    public Book(String title, String author, String category, String isbn, int quantity) {
        this.title = title;
        this.author = author;
        this.category = category;
        this.isbn = isbn;
        this.quantity = quantity;
    }
    
    public int getBookCount(){
        return bookCount;
    }
    
    public void setBookCount(int bookCount){
        this.bookCount = bookCount;
    }
    
    public int getReservationCount(){
        return reservationCount;
    }
    
    public void setReservationCount(int reservationCount){
        this.reservationCount = reservationCount;
    }
    
    public int getCurrentlyBorrowingCount(){
        return currentlyBorrowingCount;
    }
    
    public void setCurrentlyBorrowingCount(int currentlyBorrowingCount){
        this.currentlyBorrowingCount = currentlyBorrowingCount;
    }
    
    public int getOverdueCount(){
        return overdueCount;
    }
    
    public void setOverdueCount(int overdueCount){
        this.overdueCount = overdueCount;
    }
    
    public int getTotalCount(){
        return totalCount;
    }
    
    public void setTotalCount(int totalCount){
        this.totalCount = totalCount;
    }
    
    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    @Override
    public String toString() {
        return title + " by " + author + " (ISBN: " + isbn + ")"; // Customize the string representation as needed
    }
    
    
}
