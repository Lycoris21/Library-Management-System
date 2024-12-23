package view;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import controller.BookController;
import controller.BorrowingController;
import controller.UserController;
import model.Book;
import model.Borrowing;
import utility.Database;
import utility.UserSession;

public class ReaderDashboard extends JFrame {

    Database db = new Database();
    BookController bookC = new BookController(db);
    BorrowingController borrowC = new BorrowingController(db);
    UserController userC = new UserController(db);
    
    public ReaderDashboard(){
        initComponents();
    }
    
    private void homeMouseClicked(java.awt.event.MouseEvent evt) {                                     
        setVisible(true);
    } 
    
    private void browseMouseClicked(java.awt.event.MouseEvent evt) {                                     
        ReaderBrowse rb = new ReaderBrowse();
        rb.setVisible(true);
        setVisible(false);
    }
    
    private void reservehMouseClicked(java.awt.event.MouseEvent evt) {                                     
        ReaderReservations rr = new ReaderReservations();
        rr.setVisible(true);
        setVisible(false);
    }
    
    private void borrowhMouseClicked(java.awt.event.MouseEvent evt) {                                     
        ReaderBorrowing rbh = new ReaderBorrowing();
        rbh.setVisible(true);
        setVisible(false);
    } 
    
    private void profileMouseClicked(java.awt.event.MouseEvent evt) {                                     
        ReaderProfile rp = new ReaderProfile();
        rp.setVisible(true);
        setVisible(false);
    }
    
    public static void main(String[] args) {
        
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ReaderDashboard().setVisible(true);
            }
        });
        
    }
    
    private void initComponents(){
        
        dash = new JLabel();
        dash.setText("Dashboard");
        dash.setBounds(75, 50, 300, 50);
        dash.setFont(new Font("Serif", Font.BOLD, 30));
        dash.setForeground(Color.WHITE);
        
        home = new JLabel();
        home.setText("Home");
        home.setBounds(40, 200, 300, 50);
        home.setFont(new Font("Serif", Font.PLAIN, 25));
        home.setForeground(Color.WHITE);
        home.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                homeMouseClicked(evt);
            }
        });
        
        browse = new JLabel();
        browse.setText("Browse Books");
        browse.setBounds(40, 260, 300, 50);
        browse.setFont(new Font("Serif", Font.PLAIN, 25));
        browse.setForeground(Color.WHITE);
        browse.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                browseMouseClicked(evt);
            }
        });
        
        reserveh = new JLabel();
        reserveh.setText("Reservations");
        reserveh.setBounds(40, 320, 300, 50);
        reserveh.setFont(new Font("Serif", Font.PLAIN, 25));
        reserveh.setForeground(Color.WHITE);
        reserveh.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                reservehMouseClicked(evt);
            }
        });
        
        borrowh = new JLabel();
        borrowh.setText("Borrowing");
        borrowh.setBounds(40, 380, 300, 50);
        borrowh.setFont(new Font("Serif", Font.PLAIN, 25));
        borrowh.setForeground(Color.WHITE);
        borrowh.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                borrowhMouseClicked(evt);
            }
        });
        
        profile = new JLabel();
        profile.setText("Profile Page");
        profile.setBounds(40, 440, 300, 50);
        profile.setFont(new Font("Serif", Font.PLAIN, 25));
        profile.setForeground(Color.WHITE);
        profile.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                profileMouseClicked(evt);
            }
        });
        
        ImageIcon pp = new ImageIcon("src/images/jingliu.jpg");
        
        username = new JLabel();
        username.setText(UserSession.getInstance().getUsername());
        username.setBounds(40, 600, 200, 200);
        username.setFont(new Font("Serif", Font.PLAIN, 25));
        username.setForeground(Color.WHITE);
//        username.setIcon(pp);
        username.setHorizontalTextPosition(JLabel.LEFT);
        username.setVerticalTextPosition(JLabel.CENTER);
        username.setLayout(null);
        
        nav = new JPanel();
        nav.setBounds(0,0,300, 820);
        nav.setBackground(new Color(0x00233D));
        nav.setLayout(null);
        
        nav.add(dash);
        nav.add(home);
        nav.add(browse);
        nav.add(reserveh);
        nav.add(borrowh);
        nav.add(profile);
        nav.add(username);
        
        
        
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1535,820);
        setResizable(false);
        getContentPane().setBackground(Color.WHITE);
        setTitle("Library Management System");
        setLayout(null);
        setVisible(true);
        
        add(nav);
        //add(div2);
    }
    
    private javax.swing.JPanel nav;
    private javax.swing.JLabel dash;
    private javax.swing.JLabel home;
    private javax.swing.JLabel browse;
    private javax.swing.JLabel reserveh;
    private javax.swing.JLabel borrowh;
    private javax.swing.JLabel profile;
    private javax.swing.JLabel username;
    
    

    
    
}
