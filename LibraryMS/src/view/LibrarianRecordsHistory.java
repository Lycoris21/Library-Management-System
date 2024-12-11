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

public class LibrarianRecordsHistory extends JFrame {

    Database db = new Database();
    BookController bookC = new BookController(db);
    BorrowingController borrowC = new BorrowingController(db);
    UserController userC = new UserController(db);
    
    public LibrarianRecordsHistory(){
        initComponents();
    }
    
    private void homeMouseClicked(java.awt.event.MouseEvent evt) {                                     
        LibrarianDashboard ld = new LibrarianDashboard();
        ld.setVisible(true);
        setVisible(false);
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
        setVisible(true);
    } 
    
    public static void main(String[] args) {
        
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(AdminDashboard.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(AdminDashboard.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(AdminDashboard.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(AdminDashboard.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new LibrarianRecordsHistory().setVisible(true);
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
    private javax.swing.JLabel blistings;
    private javax.swing.JLabel opsm;
    private javax.swing.JLabel thistory;
    private javax.swing.JLabel username;
    
}
