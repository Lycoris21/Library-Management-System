package view;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import controller.BookController;
import controller.BorrowingController;
import controller.RecordController;
import controller.UserController;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import model.Book;
import model.Borrowing;
import utility.Database;
import utility.UserSession;

public class LibrarianOperationsManagement extends JFrame {

    private final Database db = new Database();
    private final BookController bookC = new BookController(db);
    private final BorrowingController borrowC = new BorrowingController(db);
    private final UserController userC = new UserController(db);

    private JTable table;
    private DefaultTableModel tableModel;
    
    private javax.swing.JPanel nav;
    private javax.swing.JLabel dash;
    private javax.swing.JLabel home;
    private javax.swing.JLabel blistings;
    private javax.swing.JLabel opsm;
    private javax.swing.JLabel thistory;
    private javax.swing.JLabel username;
    private javax.swing.JLabel searchLabel;
    private javax.swing.JTextField searchField;
    private javax.swing.JButton searchButton;
    private javax.swing.JButton addBookButton;
    private javax.swing.JLabel booksLabel;
    
    public LibrarianOperationsManagement(){
        initComponents();
//        setupTables();
//        populateTable();
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
        setVisible(true);
    } 
    
    private void thistoryMouseClicked(java.awt.event.MouseEvent evt) {                                     
        LibrarianRecords lrh = new LibrarianRecords();
        lrh.setVisible(true);
        setVisible(false);
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
        thistory.setText("Records");
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
        nav.add(blistings);
        nav.add(opsm);
        nav.add(thistory);
        nav.add(username);
        
        searchLabel = new JLabel("Search:");
        searchLabel.setFont(new Font("Serif", Font.PLAIN, 20));
        searchLabel.setBounds(360, 60, 100, 30);
        add(searchLabel);

        searchField = new JTextField();
        searchField.setBounds(430, 60, 700, 30);
        add(searchField);

        searchButton = new JButton("Search");
        searchButton.setBounds(1140, 60, 100, 30);
        searchButton.setForeground(Color.WHITE);
        searchButton.setBackground(new Color(0x316FA2));
//        searchButton.addActionListener(evt -> searchBooks());
        add(searchButton);



        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1535, 820);
        setResizable(false);
        getContentPane().setBackground(Color.WHITE);
        setTitle("Library Management System");
        setLayout(null);
        setVisible(true);

        add(nav);
        add(searchField);
        add(booksLabel);

    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new LibrarianOperationsManagement().setVisible(true));
    }
}
