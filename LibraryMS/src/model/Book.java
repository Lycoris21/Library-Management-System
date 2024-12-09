package model;

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
    
}
