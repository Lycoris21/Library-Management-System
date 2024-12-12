package model;

import java.sql.Timestamp;

/**
 * 
 * @author Christine Ann Dejito
 */
public class User {
    private int id;
    private String username;
    private String password;
    private String role;
    private Timestamp createdAt;
    private Timestamp updatedAt;
    private int userCount;
    private int currentlyBorrowingCount;
    private int overdueCount;
    private int totalCount;
    
    public User(int id, String username, String password, String role, Timestamp createdAt, Timestamp updatedAt){
        this.id = id;
        this.username = username;
        this.password = password;
        this.role = role;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
    
    public User() {}
    
    public User(String username, String password, String role){
        this.username = username;
        this.password = password;
        this.role = role;
    }
    
    public int getId(){
        return id;
    }
    
    public void setId(int id){
        this.id = id;
    }
    
    public String getUsername(){
        return username;
    }
    
    public void setUsername(String username){
        this.username = username;
    }
    
    public String getPassword(){
        return password;
    }
    
    public void setPassword(String password){
        this.password = password;
    }
    
    public String getRole(){
        return role;
    }
    
    public void setRole(String role){
        this.role = role;
    }
    
    public Timestamp getCreatedAt(){
        return createdAt;
    }
    
    public void setCreatedAt(Timestamp createdAt){
        this.createdAt = createdAt;
    }
    
    public Timestamp getUpdatedAt(){
        return updatedAt;
    }
    
    public void setUpdatedAt(Timestamp updatedAt){
        this.updatedAt = updatedAt;
    }
    
    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                ", role='" + role + '\'' +
                '}';
    }
    
    public int getUserCount(){
        return userCount;
    }
    
    public void setBookCount(int userCount){
        this.userCount = userCount;
    }
    
    public int getCurrentlyBorrowingCount() {
        return currentlyBorrowingCount;
    }

    public void setCurrentlyBorrowingCount(int currentlyBorrowingCount) {
        this.currentlyBorrowingCount = currentlyBorrowingCount;
    }

    public int getOverdueCount() {
        return overdueCount;
    }

    public void setOverdueCount(int overdueCount) {
        this.overdueCount = overdueCount;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }
    
}
