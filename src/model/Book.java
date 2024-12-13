package model;

import java.sql.Timestamp;

public class Book{
    
    private int bookId;
    private String title;
    private String author;
    private String category;
    private String isbn;
    private String publisher;
    private int publishedYear;
    private int quantity;
    private Timestamp createdAt;
    private Timestamp updatedAt;

    public Book() {}
    
    public Book(String title, String author, String category, String isbn, String publisher, int publishedYear, int quantity) {
        this.title = title;
        this.author = author;
        this.category = category;
        this.isbn = isbn;
        this.publisher = publisher;
        this.publishedYear = publishedYear;
        this.quantity = quantity;
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

    public String getPublisher() {
        return publisher;
    }
    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public int getPublishedYear() {
        return publishedYear;
    }
    public void setPublishedYear(int publishedYear) {
        this.publishedYear = publishedYear;
    }

    public int getQuantity() {
        return quantity;
    }
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }
    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public Timestamp getUpdatedAt() {
        return updatedAt;
    }
    public void setUpdatedAt(Timestamp updatedAt) {
        this.updatedAt = updatedAt;
    }
    
}