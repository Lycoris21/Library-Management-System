package view;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import controller.BookController;
import controller.BorrowingController;
import controller.UserController;
import java.text.SimpleDateFormat;
import model.Book;
import model.Borrowing;
import utility.Database;
import utility.UserSession;

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
        
        GridLayout gridLayout = new GridLayout(2, 1);
        gridLayout.setVgap(-50);
        
        //BOX1
        reservationsl = new JLabel("RESERVATIONS", SwingConstants.CENTER);
        reservationsl.setFont(new Font("Serif", Font.BOLD, 25));
        reservationsl.setForeground(Color.WHITE);
        
        reservationsc = new JLabel(""+bookC.getCurrentlyReservedCount(), SwingConstants.CENTER);
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
        
        top10 = new JPanel();
        top10.setBounds(350,230,1100,500);
        top10.setBackground(null);
        top10.setLayout(new GridLayout(1,3,50,70));

        // Most Borrowed Books Panel
        box4 = new JPanel();
        box4.setBounds(350, 250, 1100, 500);
        box4.setBackground(Color.decode("#D1D5DB"));
        box4.setLayout(new BoxLayout(box4, BoxLayout.Y_AXIS));

        JLabel box4title = new JLabel("Most Borrowed Books");
        box4title.setFont(new Font("Serif", Font.BOLD, 24));
        box4title.setAlignmentX(Component.CENTER_ALIGNMENT);
        box4title.setBorder(BorderFactory.createEmptyBorder(15, 0, 15, 0));
        box4.add(box4title);

// Get most borrowed books from BookController
        List<Book> mostBorrowedBooks = bookC.getMostBorrowedBooks();

        if (mostBorrowedBooks.isEmpty()) {
            // Display a message if no books are found
            JLabel noBooksLabel = new JLabel("No books have been borrowed yet");
            noBooksLabel.setForeground(Color.DARK_GRAY);
            noBooksLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            box4.add(noBooksLabel);
        } else {
            for (int i = 0; i < mostBorrowedBooks.size(); i++) {
                Book book = mostBorrowedBooks.get(i);

                box4bookPanel = new JPanel();
                box4bookPanel.setBackground(new Color(0x134671));
                box4bookPanel.setLayout(new BorderLayout(10, 0)); // Add horizontal gap of 10 pixels

                // Rank Label
                JLabel rankLabel = new JLabel(String.valueOf(i + 1));
                rankLabel.setForeground(Color.WHITE);
                rankLabel.setFont(new Font("SansSerif", Font.PLAIN, 14));
                rankLabel.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 5)); // Add left and right padding
                box4bookPanel.add(rankLabel, BorderLayout.WEST);

                // Book Title Label
                JLabel titleLabel = new JLabel(
                        book.getTitle() != null ? book.getTitle() : "Unknown Title"
                );
                titleLabel.setForeground(Color.WHITE);
                titleLabel.setFont(new Font("SansSerif", Font.PLAIN, 18));
                box4bookPanel.add(titleLabel, BorderLayout.CENTER);

                // Category Label
                JLabel categoryLabel = new JLabel(
                        book.getCategory() != null ? book.getCategory() : "Uncategorized"
                );
                categoryLabel.setForeground(Color.WHITE);
                categoryLabel.setFont(new Font("SansSerif", Font.PLAIN, 14));
                categoryLabel.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 10)); // Add left and right padding
                box4bookPanel.add(categoryLabel, BorderLayout.EAST);

                // Set maximum size and add some vertical padding
                box4bookPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 60));
                box4bookPanel.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 0)); // Vertical padding

                box4.add(box4bookPanel);
                box4.add(Box.createRigidArea(new Dimension(0, 10)));
            }
        }
        
        
        
//=======   BOX 5   ===================================        
        // Newest Books Panel
        box5 = new JPanel();
        box5.setBounds(350, 250, 1100, 500);
        box5.setBackground(Color.decode("#D1D5DB"));
        box5.setLayout(new BoxLayout(box5, BoxLayout.Y_AXIS));

        JLabel box5titleLabel = new JLabel("Newest Books");
        box5titleLabel.setFont(new Font("Serif", Font.BOLD, 24));
        box5titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        box5titleLabel.setBorder(BorderFactory.createEmptyBorder(15, 0, 15, 0));
        box5.add(box5titleLabel);

// Get newest books from BookController
        List<Book> newestBooks = bookC.getNewestBooks();

        if (newestBooks.isEmpty()) {
            // Display a message if no books are found
            JLabel noBooksLabel = new JLabel("No new books have been added");
            noBooksLabel.setForeground(Color.DARK_GRAY);
            noBooksLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            box5.add(noBooksLabel);
        } else {
            for (int i = 0; i < newestBooks.size(); i++) {
                Book book = newestBooks.get(i);

                box5bookPanel = new JPanel();
                box5bookPanel.setBackground(new Color(0x134671));
                box5bookPanel.setLayout(new BorderLayout(10,0));

                // Rank Label
                JLabel rankLabel = new JLabel(String.valueOf(i + 1));
                rankLabel.setForeground(Color.WHITE);
                rankLabel.setFont(new Font("SansSerif", Font.PLAIN, 14));
                rankLabel.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 5));
                box5bookPanel.add(rankLabel, BorderLayout.WEST);

                // Book Title Label
                JLabel titleLabel = new JLabel(
                        book.getTitle() != null ? book.getTitle() : "Unknown Title"
                );
                titleLabel.setForeground(Color.WHITE);
                titleLabel.setFont(new Font("SansSerif", Font.PLAIN, 18));
                box5bookPanel.add(titleLabel, BorderLayout.CENTER);

                // Created Date Label
                JLabel categoryLabel = new JLabel(
                        book.getCategory() != null ? book.getCategory() : "Uncategorized"
                );
                categoryLabel.setForeground(Color.WHITE);
                categoryLabel.setFont(new Font("SansSerif", Font.PLAIN, 14));
                categoryLabel.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 10));
                box5bookPanel.add(categoryLabel, BorderLayout.EAST);

                box5bookPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 60));
                box5bookPanel.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 0));
                
                box5.add(box5bookPanel);
                box5.add(Box.createRigidArea(new Dimension(0, 10)));
            }
        }
        
        
        
//=======   BOX 6   ===================================
        box6 = new JPanel();
        box6.setBounds(350, 250, 1100, 500); // Set specific bounds
        box6.setBackground(Color.decode("#D1D5DB"));
        box6.setLayout(new BoxLayout(box6, BoxLayout.Y_AXIS));

        JLabel box6titleLabel = new JLabel("Latest Transactions");
        box6titleLabel.setFont(new Font("Serif", Font.BOLD, 24));
        box6titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        box6titleLabel.setBorder(BorderFactory.createEmptyBorder(15, 0, 15, 0));
        box6.add(box6titleLabel);

// Get latest transactions from BorrowingController
        List<Borrowing> latestTrans = borrowC.getLatestTransactions();

        if (latestTrans.isEmpty()) {
            // Display a message if no transactions are found
            JLabel noTransactionsLabel = new JLabel("No recent transactions");
            noTransactionsLabel.setForeground(Color.DARK_GRAY);
            noTransactionsLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            box6.add(noTransactionsLabel);
        } else {
            for (int i = 0; i < latestTrans.size(); i++) {
                Borrowing borrow = latestTrans.get(i);

                box6recordPanel = new JPanel();
                box6recordPanel.setBackground(new Color(0x134671));
                box6recordPanel.setLayout(new BorderLayout(10, 0));

                // Transaction Type Label
                JLabel statusLabel = new JLabel(borrow.getTransactionType());
                statusLabel.setForeground(Color.WHITE);
                statusLabel.setFont(new Font("SansSerif", Font.PLAIN, 10));
                statusLabel.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 5));
                box6recordPanel.add(statusLabel, BorderLayout.WEST);

                // Book Title Label
                JLabel bookTitleLabel = new JLabel(
                        borrow.getBookTitle() != null ? borrow.getBookTitle() : "Unknown Book"
                );
                bookTitleLabel.setForeground(Color.WHITE);
                bookTitleLabel.setFont(new Font("SansSerif", Font.PLAIN, 18));
                box6recordPanel.add(bookTitleLabel, BorderLayout.CENTER);

                // Timestamp Label
                JLabel timestampLabel = new JLabel(
                    borrow.getUpdatedAt() != null
                    ? "<html>" + 
                      "<div style='text-align:center;'>" +
                      new SimpleDateFormat("yyyy-MM-dd").format(borrow.getUpdatedAt()) + "<br>" + 
                      new SimpleDateFormat("HH:mm:ss").format(borrow.getUpdatedAt()) + 
                      "</div></html>"
                    : "N/A"
                );
                timestampLabel.setForeground(Color.WHITE);
                timestampLabel.setFont(new Font("SansSerif", Font.PLAIN, 8));
                timestampLabel.setHorizontalAlignment(SwingConstants.RIGHT);
                timestampLabel.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 10));
                box6recordPanel.add(timestampLabel, BorderLayout.EAST);

                box6recordPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 60));
                box6recordPanel.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 0));

                box6.add(box6recordPanel);
                box6.add(Box.createRigidArea(new Dimension(0, 10)));
            }
        }
        
        top10.add(box4);
        top10.add(box5);
        top10.add(box6);
        
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1535,820);
        setResizable(false);
        getContentPane().setBackground(Color.WHITE);
        setTitle("Library Management System");
        setLayout(null);
        setVisible(true);
        
        add(nav);
        add(counts);
        add(top10);
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
    private javax.swing.JPanel box4;
    private javax.swing.JPanel box4bookPanel;
    private javax.swing.JPanel box5;
    private javax.swing.JPanel box5bookPanel;
    private javax.swing.JPanel box6;
    private javax.swing.JPanel box6recordPanel;
    
}
