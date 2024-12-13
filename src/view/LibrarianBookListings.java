package view;

import javax.swing.*;
import java.awt.*;
import javax.swing.border.EmptyBorder;
import java.util.*;
import controller.BookController;
import controller.BorrowingController;
import controller.UserController;
import utility.Database;

/**
 *
 * @author Christine Ann Dejito
 */
public class LibrarianBookListings extends JFrame {
    
    Database db = new Database();
    BookController bookC = new BookController(db);
    BorrowingController borrowC = new BorrowingController(db);
    UserController userC = new UserController(db);
    
    public LibrarianBookListings(){
        initComponents();
    }
    
    private void homeMouseClicked(java.awt.event.MouseEvent evt) {                                     
        LibrarianDashboard ld = new LibrarianDashboard();
        ld.setVisible(true);
        setVisible(false);
    } 
    
    private void blistingsMouseClicked(java.awt.event.MouseEvent evt) {                                     
        setVisible(true);
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
                new LibrarianBookListings().setVisible(true);
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

        username = new JLabel();
        username.setText("Kwesten Ann");
        username.setBounds(40, 600, 200, 200);
        username.setFont(new Font("Serif", Font.PLAIN, 25));
        username.setForeground(Color.WHITE);

        nav = new JPanel();
        nav.setBounds(0, 0, 300, 820);
        nav.setBackground(new Color(0x00233D));
        nav.setLayout(null);

        nav.add(dash);
        nav.add(home);
        nav.add(blistings);
        nav.add(opsm);
        nav.add(thistory);
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

    private JPanel nav;
    private JLabel dash;
    private JLabel home;
    private JLabel blistings;
    private JLabel opsm;
    private JLabel thistory;
    private JLabel username;
    private JLabel searchLabel;
    private JTextField searchField;
    private JButton filterButton;
    private JPanel bookPanel;
    
}
