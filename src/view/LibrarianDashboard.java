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

public class LibrarianDashboard extends JFrame {

    Database db = new Database();
    BookController bookC = new BookController(db);
    BorrowingController borrowC = new BorrowingController(db);
    UserController userC = new UserController(db);
    
    public LibrarianDashboard(){
        initComponents();
    }
    
    private void homeMouseClicked(java.awt.event.MouseEvent evt) {                                     
        setVisible(true);
    } 
    
    private void blistingsMouseClicked(java.awt.event.MouseEvent evt) {                                     
        LibrarianBookListings lbl = new LibrarianBookListings();
        lbl.setVisible(true);
        setVisible(false);
    }
    
    private void opsmMouseClicked(java.awt.event.MouseEvent evt) {                                     
        LibrarianOperationsManagement lom = new LibrarianOperationsManagement();
        lom.setVisible(true);
        setVisible(false);
    } 
    
    private void thistoryMouseClicked(java.awt.event.MouseEvent evt) {                                     
        LibrarianRecords lrh = new LibrarianRecords();
        lrh.setVisible(true);
        setVisible(false);
    } 
    
    public static void main(String[] args) {
        
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new LibrarianDashboard().setVisible(true);
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
        
        blistings = new JLabel();
        blistings.setText("Book Listings");
        blistings.setBounds(40, 260, 300, 50);
        blistings.setFont(new Font("Serif", Font.PLAIN, 25));
        blistings.setForeground(Color.WHITE);
        blistings.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                blistingsMouseClicked(evt);
            }
        });
        
        opsm = new JLabel();
        opsm.setText("Operations Management");
        opsm.setBounds(40, 320, 300, 50);
        opsm.setFont(new Font("Serif", Font.PLAIN, 25));
        opsm.setForeground(Color.WHITE);
        opsm.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                opsmMouseClicked(evt);
            }
        });
        
        thistory = new JLabel();
        thistory.setText("Transaction History");
        thistory.setBounds(40, 380, 300, 50);
        thistory.setFont(new Font("Serif", Font.PLAIN, 25));
        thistory.setForeground(Color.WHITE);
        thistory.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                thistoryMouseClicked(evt);
            }
        });
        
        ImageIcon pp = new ImageIcon("src/images/jingliu.jpg");
        
        username = new JLabel();
        username.setText("Kwesten Ann");
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
        nav.add(blistings);
        nav.add(opsm);
        nav.add(thistory);
        nav.add(username);
        
        GridLayout gridLayout = new GridLayout(2, 1);
        gridLayout.setVgap(-50);
        
        //BOX1
        reservationsl = new JLabel("RESERVATIONS", SwingConstants.CENTER);
        reservationsl.setFont(new Font("Serif", Font.BOLD, 25));
        reservationsl.setForeground(Color.WHITE);
        
        reservationsc = new JLabel(""+bookC.getCurrentlyOverdueCount(), SwingConstants.CENTER);
        reservationsc.setFont(new Font("Serif", Font.BOLD, 40));
        reservationsc.setForeground(Color.WHITE);
        
        box1 = new JPanel();
        box1.setBackground(new Color(0x00233D));
        box1.setLayout(gridLayout);
        box1.add(reservationsl);
        box1.add(reservationsc);
        
        //BOX2
        borrowingl = new JLabel("BORROWING", SwingConstants.CENTER);
        borrowingl.setFont(new Font("Serif", Font.BOLD, 25));
        borrowingl.setForeground(Color.WHITE);
        
        borrowingc = new JLabel(""+bookC.getCurrentlyBorrowingCount(), SwingConstants.CENTER);
        borrowingc.setFont(new Font("Serif", Font.BOLD, 40));
        borrowingc.setForeground(Color.WHITE);
        
        box2 = new JPanel();
        box2.setBackground(new Color(0x00233D));
        box2.setLayout(gridLayout);
        box2.add(borrowingl);
        box2.add(borrowingc);
        
        
        //BOX3
        overduel = new JLabel("OVERDUE", SwingConstants.CENTER);
        overduel.setPreferredSize(new Dimension(80, 50));
        overduel.setSize(reservationsl.getPreferredSize());
        overduel.setFont(new Font("Serif", Font.BOLD, 25));
        overduel.setForeground(Color.WHITE);
        
        overduec = new JLabel(""+bookC.getCurrentlyOverdueCount(), SwingConstants.CENTER);
        overduec.setFont(new Font("Serif", Font.BOLD, 40));
        overduec.setForeground(Color.WHITE);
        
        box3 = new JPanel();
        box3.setBackground(new Color(0x00233D));
        box3.setLayout(gridLayout);
        box3.add(overduel);
        box3.add(overduec);
        
        counts = new JPanel();
        counts.setBounds(350,50,1100,150);
        counts.setBackground(null);
        counts.setLayout(new GridLayout(1,3,50,70));
        
        counts.add(box1);
        counts.add(box2);
        counts.add(box3);
        
//        top10 = new JPanel();
//        top10.setBounds(350,50,1100,500);
//        top10.setBackground(null);
//        top10.setLayout(new GridLayout(1,3,50,70));
//        
//        top10.add(box1);
//        top10.add(box2);
//        top10.add(box3);
        
        
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1535,820);
        setResizable(false);
        getContentPane().setBackground(Color.WHITE);
        setTitle("Library Management System");
        setLayout(null);
        setVisible(true);
        
        add(nav);
        add(counts);
        //add(top10);
    }
    
    private javax.swing.JPanel nav;
    private javax.swing.JLabel dash;
    private javax.swing.JLabel home;
    private javax.swing.JLabel blistings;
    private javax.swing.JLabel opsm;
    private javax.swing.JLabel thistory;
    private javax.swing.JLabel username;
    private javax.swing.JPanel box1;
    private javax.swing.JPanel box2;
    private javax.swing.JPanel box3;
    private javax.swing.JLabel reservationsl;
    private javax.swing.JLabel borrowingl;
    private javax.swing.JLabel overduel;
    private javax.swing.JLabel reservationsc;
    private javax.swing.JLabel borrowingc;
    private javax.swing.JLabel overduec;
    private javax.swing.JPanel counts;
    private javax.swing.JPanel top10;
    
}
