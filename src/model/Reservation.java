package model;

import java.sql.Timestamp;

public class Reservation {

    private int reservationId;
    private int userId;
    private int bookId;
    private String status;
    private Timestamp reservedUntil;
    private Timestamp createdAt;
    private Timestamp updatedAt;

    // Constructor
    public Reservation() {
    }

    // Getters and Setters
    public int getReservationId() {
        return reservationId;
    }

    public void setReservationId(int reservationId) {
        this.reservationId = reservationId;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        if (status.equals("Pending") || status.equals("Collected") || status.equals("Void")) {
            this.status = status;
        } else {
            throw new IllegalArgumentException("Invalid status value");
        }
    }

    public Timestamp getReservedUntil() {
        return reservedUntil;
    }

    public void setReservedUntil(Timestamp reservedUntil) {
        this.reservedUntil = reservedUntil;
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
