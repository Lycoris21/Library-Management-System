package view;

import javax.swing.*;
import java.awt.*;
import javax.swing.border.EmptyBorder;
import java.util.List;
import controller.BookController;
import controller.BorrowingController;
import controller.UserController;
import utility.Database;
import utility.UserSession;

/**
 *
 * @author Christine Ann Dejito
 */
public class ReaderBrowse extends JFrame {
    
    Database db = new Database();
    BookController bookC = new BookController(db);
    BorrowingController borrowC = new BorrowingController(db);
    UserController userC = new UserController(db);
    
    public ReaderBrowse() {
        initComponents();
    }
    
    private void homeMouseClicked(java.awt.event.MouseEvent evt) {                                     
        ReaderDashboard rd = new ReaderDashboard();
        rd.setVisible(true);
        setVisible(false);
    } 
    
    private void browseMouseClicked(java.awt.event.MouseEvent evt) {                                     
        setVisible(true);
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
                new ReaderBrowse().setVisible(true);
            }
        });
        
    }

    private void initComponents() {
        // Dashboard and Navigation Setup
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

        username = new JLabel();
        username.setText(UserSession.getInstance().getUsername());
        username.setBounds(40, 600, 200, 200);
        username.setFont(new Font("Serif", Font.PLAIN, 25));
        username.setForeground(Color.WHITE);

        nav = new JPanel();
        nav.setBounds(0, 0, 300, 820);
        nav.setBackground(new Color(0x00233D));
        nav.setLayout(null);

        nav.add(dash);
        nav.add(home);
        nav.add(browse);
        nav.add(reserveh);
        nav.add(borrowh);
        nav.add(profile);
        nav.add(username);
        
        // Search Label and TextField
        searchLabel = new JLabel("Search");
        searchLabel.setFont(new Font("Serif", Font.PLAIN, 20));
        searchLabel.setBounds(360, 67, 100, 30);
    
        searchField = new JTextField();
        searchField.setBounds(430, 70, 500, 30);
    
        // Filter and Add Book Buttons
        filterButton = new JButton("Filter");
        filterButton.setBounds(950, 70, 100, 30);
        filterButton.setForeground(Color.WHITE);
        filterButton.setBackground(new Color(0x316FA2));

        // Main Panel for Books
        bookPanel = new JPanel();
        bookPanel.setLayout(new GridLayout(0, 1, 10, 10)); // Vertical list of books
        bookPanel.setBackground(Color.WHITE);

        // Sample book items
        for (int i = 0; i < 8; i++) {
            JPanel bookItem = new JPanel();
            bookItem.setLayout(new BorderLayout(10, 10)); // Add spacing between components
            bookItem.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 10)); // Add padding around the panel
            bookItem.setBackground(new Color(0x004165));

            JLabel bookInfo = new JLabel("<html>Title: Sample Book " + (i + 1) + "<br>Author: Author Name<br>Category: Fiction<br>ISBN: 123456789<br>Quantity Available: 10</html>");
            bookInfo.setForeground(Color.WHITE);
            bookInfo.setFont(new Font("Serif", Font.PLAIN, 16));
            bookInfo.setBorder(new EmptyBorder(0, 20, 0, 0));

            JLabel bookImage = new JLabel();
            bookImage.setPreferredSize(new Dimension(100, 100));
            bookImage.setOpaque(true);
            bookImage.setBackground(Color.LIGHT_GRAY); // Placeholder for book image
            
            JButton reserveButton = new JButton();
            reserveButton.setText("Reserve");
            reserveButton.setForeground(new Color(0x316FA2));

            bookItem.add(bookImage, BorderLayout.WEST);
            bookItem.add(bookInfo, BorderLayout.CENTER);
            bookItem.add(reserveButton, BorderLayout.EAST);
            

            bookPanel.add(bookItem);
            
        }

        // Scroll Pane for Books
        JScrollPane scrollPane = new JScrollPane(bookPanel);
        scrollPane.setBounds(360, 150, 1100, 600);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        // Frame Setup
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1535, 820);
        setResizable(false);
        getContentPane().setBackground(Color.WHITE);
        setTitle("Library Management System");
        setLayout(null);

        add(nav);
        add(searchLabel);
        add(searchField);
        add(filterButton);
        add(scrollPane);
    }

    private javax.swing.JPanel nav;
    private javax.swing.JLabel dash;
    private javax.swing.JLabel home;
    private javax.swing.JLabel browse;
    private javax.swing.JLabel reserveh;
    private javax.swing.JLabel borrowh;
    private javax.swing.JLabel profile;
    private javax.swing.JLabel username;
    private javax.swing.JLabel searchLabel;
    private javax.swing.JTextField searchField;
    private javax.swing.JButton filterButton;
    private javax.swing.JPanel bookPanel;
    
}
