package view;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import controller.BookController;
import controller.BorrowingController;
import controller.UserController;
import java.util.stream.Collectors;
import model.Book;
import model.User;
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
        searchField.addActionListener(evt -> searchBooks());
    
        // Filter and Add Book Buttons
        searchButton = new JButton("Search");
        searchButton.setBounds(950, 70, 100, 30);
        searchButton.setForeground(Color.WHITE);
        searchButton.setBackground(new Color(0x316FA2));
        searchButton.addActionListener(evt -> searchBooks());

        
        JPanel mainPanel = new JPanel();
        mainPanel.setBounds(350, 150, 1100, 600);
        mainPanel.setLayout(new BorderLayout());
        mainPanel.setBackground(new Color(229, 231, 235)); // Gray background

        // Create the BookPanel
        bookPanel = new BookPanel();

        // Wrap the BookPanel in a JScrollPane
        scrollPane = new JScrollPane(bookPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        // Add the JScrollPane to the main panel
        mainPanel.add(scrollPane, BorderLayout.CENTER);
        
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
        add(searchButton);
        add(mainPanel);
    }
    
    private void searchBooks() {
        String query = searchField.getText().trim().toLowerCase();

        // If query is empty, reset to show all books
        if (query.isEmpty()) {
            resetBookList();
            return;
        }

        // Get all books from BookController
        List<Book> allBooks = bookC.getAllBooks();

        // Filter books based on search query
        List<Book> filteredBooks = allBooks.stream()
                .filter(book
                        -> book.getTitle().toLowerCase().contains(query)
                || book.getAuthor().toLowerCase().contains(query)
                || book.getCategory().toLowerCase().contains(query)
                || book.getPublisher().toLowerCase().contains(query)
                || String.valueOf(book.getPublishedYear()).contains(query)
                )
                .collect(Collectors.toList());

        // Update the book panel with filtered results
        updateBookPanel(filteredBooks);
    }

// Method to reset book list to show all books
    private void resetBookList() {
        List<Book> allBooks = bookC.getAllBooks();
        updateBookPanel(allBooks);
    }

// Method to update book panel with given list of books
    private void updateBookPanel(List<Book> books) {
        // Remove existing components from book panel
        bookPanel.removeAll();

        // Set background color
        bookPanel.setBackground(new Color(229, 231, 235));

        // Check if filtered list is empty
        if (books.isEmpty()) {
            JLabel noBookLabel = new JLabel("No books found");
            noBookLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            noBookLabel.setForeground(Color.GRAY);
            bookPanel.add(noBookLabel);
        } else {
            // Add filtered books to panel
            for (Book book : books) {
                bookPanel.add(bookPanel.createBookItem(book));
                bookPanel.add(Box.createRigidArea(new Dimension(0, 10)));
            }
        }

        // Revalidate and repaint the panel
        bookPanel.revalidate();
        bookPanel.repaint();
    }
    
    class BookPanel extends JPanel {

        public BookPanel() {
            setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
            setBackground(new Color(229, 231, 235)); // Gray background

            // Get books from BookController
            List<Book> books = bookC.getAllBooks(); // Assuming this method exists

            if (books.isEmpty()) {
                JLabel noBookLabel = new JLabel("No books available");
                noBookLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
                add(noBookLabel);
            } else {
                for (Book book : books) {
                    add(createBookItem(book));
                    add(Box.createRigidArea(new Dimension(0, 10))); // Space between items
                }
            }
        }

        JPanel createBookItem(Book book) {
            JPanel bookItem = new JPanel(new GridLayout(1, 3, 10, 0));
            bookItem.setBackground(new Color(13, 42, 84)); // Blue background
            bookItem.setBorder(BorderFactory.createEmptyBorder(10, 100, 10, 10)); // Padding

            bookItem.setPreferredSize(new Dimension(1082, 100)); // Width, Height
            bookItem.setMaximumSize(new Dimension(1082, 100));
            bookItem.setMinimumSize(new Dimension(1082, 100));

            // Left panel (Book Details)
            JPanel leftPanel = new JPanel(new GridLayout(4, 1, 0, 5));
            leftPanel.setBackground(new Color(13, 42, 84));

            JLabel titleLabel = new JLabel("Title: " + book.getTitle());
            JLabel authorLabel = new JLabel("Author: " + book.getAuthor());
            JLabel categoryLabel = new JLabel("Category: " + book.getCategory());
            JLabel isbnLabel = new JLabel("ISBN: " + book.getIsbn());

            // Styling left panel labels
            JLabel[] leftLabels = {titleLabel, authorLabel, categoryLabel, isbnLabel};
            for (JLabel label : leftLabels) {
                label.setForeground(Color.WHITE);
                label.setFont(new Font("Serif", Font.PLAIN, 12));
            }

            leftPanel.add(titleLabel);
            leftPanel.add(authorLabel);
            leftPanel.add(categoryLabel);
            leftPanel.add(isbnLabel);

            // Middle panel (Additional Book Info)
            JPanel middlePanel = new JPanel(new GridLayout(4, 1, 0, 5));
            middlePanel.setBackground(new Color(13, 42, 84));
            middlePanel.setBorder(BorderFactory.createEmptyBorder(0, 50, 0, 0));

            JLabel publisherLabel = new JLabel("Publisher: " + book.getPublisher());
            JLabel yearLabel = new JLabel("Published Year: " + book.getPublishedYear());
            JLabel quantityLabel = new JLabel("Quantity: " + book.getQuantity());

            // Styling middle panel labels
            JLabel[] middleLabels = {publisherLabel, yearLabel, quantityLabel};
            for (JLabel label : middleLabels) {
                label.setForeground(Color.WHITE);
                label.setFont(new Font("Serif", Font.PLAIN, 12));
            }

            middlePanel.add(publisherLabel);
            middlePanel.add(yearLabel);
            middlePanel.add(quantityLabel);
            middlePanel.add(new JLabel()); // Empty label to maintain grid layout

            // Reserve button panel
            JPanel buttonPanel = new JPanel(new GridBagLayout());
            buttonPanel.setBackground(new Color(13, 42, 84));
            buttonPanel.setBorder(BorderFactory.createEmptyBorder(0, 80, 0, 0));

            JButton reserveButton = new JButton("Reserve");
            reserveButton.setBackground(new Color(59, 130, 246)); // Blue button background
            reserveButton.setForeground(Color.WHITE);
            reserveButton.setFocusPainted(false);
            reserveButton.setPreferredSize(new Dimension(120, 50)); // Set a specific size for the button
            reserveButton.setFont(new Font("Serif", Font.BOLD, 12));

            // Add action listener for reservation
            reserveButton.addActionListener(e -> reserveBook(book));

            // Center the button in its panel
            buttonPanel.add(reserveButton);

            // Add panels to book item
            bookItem.add(leftPanel);
            bookItem.add(middlePanel);
            bookItem.add(buttonPanel);

            return bookItem;
        }

        private void reserveBook(Book book) {
            // Get the current user from UserSession
            User currentUser = UserSession.getInstance().getCurrentUser();

            if (currentUser == null) {
                JOptionPane.showMessageDialog(this,
                        "Please log in to reserve a book.",
                        "Login Required",
                        JOptionPane.WARNING_MESSAGE);
                return;
            }

            // Call the reserveBook method in BookController
            boolean success = bookC.reserveBook(currentUser.getUserId(), book.getBookId());

            // If successful, you might want to refresh the book list
            if (success) {
                // Optionally, refresh the book list
                refreshBookList();
            }
        }

        private void refreshBookList() {
            // Remove all existing components
            removeAll();

            // Repopulate with updated book list
            List<Book> books = bookC.getAllBooks();

            if (books.isEmpty()) {
                JLabel noBookLabel = new JLabel("No books available");
                noBookLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
                add(noBookLabel);
            } else {
                for (Book book : books) {
                    add(createBookItem(book));
                    add(Box.createRigidArea(new Dimension(0, 10))); // Space between items
                }
            }

            // Revalidate and repaint
            revalidate();
            repaint();
        }
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
    private javax.swing.JButton searchButton;
    private BookPanel bookPanel;
    private JScrollPane scrollPane;
    
    
}
