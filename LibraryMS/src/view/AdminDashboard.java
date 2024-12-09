package view;

import java.awt.*;
import javax.swing.*;
import controller.BookController;
import controller.UserController;
import model.Book;
import model.User;
import utility.Database;

/**
 * 
 * @author Christine Ann Dejito
 */
public class AdminDashboard extends javax.swing.JFrame{
    
    Database db = new Database();
    BookController BC = new BookController(db);
    UserController UC = new UserController(db);
    
    public AdminDashboard(){
        initComponents();
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
                new AdminDashboard().setVisible(true);
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
        
        bookm = new JLabel();
        bookm.setText("Book Management");
        bookm.setBounds(40, 260, 300, 50);
        bookm.setFont(new Font("Serif", Font.PLAIN, 25));
        bookm.setForeground(Color.WHITE);
        
        userm = new JLabel();
        userm.setText("User Management");
        userm.setBounds(40, 320, 300, 50);
        userm.setFont(new Font("Serif", Font.PLAIN, 25));
        userm.setForeground(Color.WHITE);
        
        apps = new JLabel();
        apps.setText("Application Settings");
        apps.setBounds(40, 380, 300, 50);
        apps.setFont(new Font("Serif", Font.PLAIN, 25));
        apps.setForeground(Color.WHITE);
        
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
        nav.add(bookm);
        nav.add(userm);
        nav.add(apps);
        nav.add(username);
        
        GridLayout gridLayout = new GridLayout(2, 1);
        gridLayout.setVgap(-120);
        
        //BOX1
        booksl = new JLabel("BOOKS COUNT", SwingConstants.CENTER);
        booksl.setFont(new Font("Serif", Font.BOLD, 25));
        booksl.setForeground(Color.WHITE);
        
        booksc = new JLabel(""+BC.getBookCount(), SwingConstants.CENTER);
        booksc.setFont(new Font("Serif", Font.BOLD, 40));
        booksc.setForeground(Color.WHITE);
        
        box1 = new JPanel();
        box1.setBackground(new Color(0x00233D));
        box1.setLayout(gridLayout);
        box1.add(booksl);
        box1.add(booksc);
        
        //BOX2
        usersl = new JLabel("USERS COUNT", SwingConstants.CENTER);
        usersl.setFont(new Font("Serif", Font.BOLD, 25));
        usersl.setForeground(Color.WHITE);
        
        usersc = new JLabel(""+UC.getUserCount(), SwingConstants.CENTER);
        usersc.setFont(new Font("Serif", Font.BOLD, 40));
        usersc.setForeground(Color.WHITE);
        
        box2 = new JPanel();
        box2.setBackground(new Color(0x00233D));
        box2.setLayout(gridLayout);
        box2.add(usersl);
        box2.add(usersc);
        
        
        //BOX3
        reservationsl = new JLabel("<html>RESERVATIONS COUNT</html>", SwingConstants.CENTER);
        reservationsl.setPreferredSize(new Dimension(80, 50));
        reservationsl.setSize(reservationsl.getPreferredSize());
        reservationsl.setFont(new Font("Serif", Font.BOLD, 25));
        reservationsl.setForeground(Color.WHITE);
        
        reservationsc = new JLabel(""+BC.getReservationCount(), SwingConstants.CENTER);
        reservationsc.setFont(new Font("Serif", Font.BOLD, 40));
        reservationsc.setForeground(Color.WHITE);
        
        box3 = new JPanel();
        box3.setBackground(new Color(0x00233D));
        box3.setLayout(gridLayout);
        box3.add(reservationsl);
        box3.add(reservationsc);
        
        
        //BOX4
        borrowl = new JLabel("<html>CURRENTLY BORROWING COUNT</html>", SwingConstants.CENTER);
//        borrowl.setPreferredSize(new Dimension(120, 60)); // Set width and height
//        borrowl.setSize(borrowl.getPreferredSize());
        borrowl.setFont(new Font("Serif", Font.BOLD, 25));
        borrowl.setForeground(Color.WHITE);
        
        borrowc = new JLabel(""+BC.getCurrentlyBorrowingCount(), SwingConstants.CENTER);
        borrowc.setFont(new Font("Serif", Font.BOLD, 40));
        borrowc.setForeground(Color.WHITE);
  
        box4 = new JPanel();
        box4.setBackground(new Color(0x00233D));
        box4.setLayout(gridLayout);
        box4.add(borrowl);
        box4.add(borrowc);
        
        
        //BOX5
        overduel = new JLabel("<html>OVERDUE BOOKS COUNT</html>", SwingConstants.CENTER);
        overduel.setFont(new Font("Serif", Font.BOLD, 25));
        overduel.setForeground(Color.WHITE);
        
        overduec = new JLabel(""+BC.getOverdueCount(), SwingConstants.CENTER);
        overduec.setFont(new Font("Serif", Font.BOLD, 40));
        overduec.setForeground(Color.WHITE);
        
        box5 = new JPanel();
        box5.setBackground(new Color(0x00233D));
        box5.setLayout(gridLayout);
        box5.add(overduel);
        box5.add(overduec);
        
        
        //BOX6
        overduel = new JLabel("<html>TOTAL BORROWED COUNT</html>", SwingConstants.CENTER);
        overduel.setFont(new Font("Serif", Font.BOLD, 25));
        overduel.setForeground(Color.WHITE);
        
        overduec = new JLabel(""+BC.getTotalCount(), SwingConstants.CENTER);
        overduec.setFont(new Font("Serif", Font.BOLD, 40));
        overduec.setForeground(Color.WHITE);
        
        box6 = new JPanel();
        box6.setBackground(new Color(0x00233D));
        box6.setLayout(gridLayout);
        box6.add(overduel);
        box6.add(overduec);
        
        
        //BOXES DIV
        div1 = new JPanel();
        div1.setBounds(400, 100, 1020, 570);
        div1.setBackground(null);
        div1.setLayout(new GridLayout(2,3, 50, 70));
        
        div1.add(box1);
        div1.add(box2);
        div1.add(box3);
        div1.add(box4);
        div1.add(box5);
        div1.add(box6);
        
//        div2 = new JPanel();
//        div2.setBounds(400, 100, 1020, 570);
//        div2.setBackground(Color.green);
//        div2.setLayout(new FlowLayout(FlowLayout.CENTER, 50, 50));
        
        
        
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1535,820);
        setResizable(false);
        getContentPane().setBackground(Color.WHITE);
        setTitle("Library Management System");
        setLayout(null);
        setVisible(true);
        
        add(nav);
        add(div1);
        //add(div2);
    }
    
    private javax.swing.JPanel nav;
    private javax.swing.JLabel dash;
    private javax.swing.JLabel home;
    private javax.swing.JLabel bookm;
    private javax.swing.JLabel userm;
    private javax.swing.JLabel apps;
    private javax.swing.JLabel username;
    
    
    private javax.swing.JLabel booksl;
    private javax.swing.JLabel booksc;
    private javax.swing.JLabel usersl;
    private javax.swing.JLabel usersc;
    private javax.swing.JLabel reservationsl;
    private javax.swing.JLabel reservationsc;
    private javax.swing.JLabel borrowl;
    private javax.swing.JLabel borrowc;
    private javax.swing.JLabel overduel;
    private javax.swing.JLabel overduec;
    private javax.swing.JLabel totalbl;
    private javax.swing.JLabel totalbc;
    
    private javax.swing.JPanel div1;
    private javax.swing.JPanel box1;
    private javax.swing.JPanel box2;
    private javax.swing.JPanel box3;
    private javax.swing.JPanel box4;
    private javax.swing.JPanel box5;
    private javax.swing.JPanel box6;
    
    
}
