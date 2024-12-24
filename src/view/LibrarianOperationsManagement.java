package view;

import controller.BookController;
import controller.BorrowingController;
import controller.RecordController;
import controller.RecordController.BorrowingDetails;
import controller.RecordController.ReservationDetails;
import controller.UserController;
import javax.swing.*;
import javax.swing.table.*;
import java.util.List;
import java.awt.*;
import java.util.ArrayList;
import model.Book;
import utility.Database;
import utility.UserSession;

public class LibrarianOperationsManagement extends JFrame {

    private final Database db = new Database();
    private final BookController bookC = new BookController(db);
    private final BorrowingController borrowC = new BorrowingController(db);
    private final UserController userC = new UserController(db);
    private final RecordController recordC = new RecordController(db);

    private JTable reservationTable;
    private DefaultTableModel reservationTableModel;
    private JTable borrowingTable;
    private DefaultTableModel borrowingTableModel;
    
    List<RecordController.RecordDisplay> reservationRecords = recordC.getReservationRecords("Pending");
    List<RecordController.RecordDisplay> borrowingRecords = recordC.getBorrowingRecords("Borrowed");

    private JPanel nav;
    private JLabel dash;
    private JLabel home;
    private JLabel blistings;
    private JLabel opsm;
    private JLabel thistory;
    private JLabel username;
    private JLabel searchLabel;
    private JTextField searchField;
    private JButton searchButton;
    private JButton addButton;
    private JLabel reservedLabel;
    private JLabel borrowedLabel;
    
    public LibrarianOperationsManagement(){
        initComponents();
        populateTable();
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
        searchButton.setBounds(1140, 60, 90, 30);
        searchButton.setForeground(Color.WHITE);
        searchButton.setBackground(new Color(0x316FA2));
        searchButton.addActionListener(evt -> searchRecords());
        add(searchButton);

        addButton = new JButton("New Borrowing");
        addButton.setBounds(1240, 60, 150, 30);
        addButton.setForeground(Color.WHITE);
        addButton.setBackground(new Color(0x316FA2));
        addButton.addActionListener(evt -> openNewBorrowingDialog());
        add(addButton);

        // Books Label
        reservedLabel = new JLabel("Reserved - " + bookC.getCurrentlyReservedCount());
        reservedLabel.setFont(new Font("Serif", Font.BOLD, 30));
        reservedLabel.setBounds(360, 120, 200, 30);
        
        // Books Label
        borrowedLabel = new JLabel("Borrowed - " + bookC.getCurrentlyBorrowingCount());
        borrowedLabel.setFont(new Font("Serif", Font.BOLD, 30));
        borrowedLabel.setBounds(930, 120, 200, 30);

        // Table for Reservations
        String[] reservationColumns = {"Username", "Title", "More Details"};
        reservationTableModel = new DefaultTableModel(reservationColumns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 2;
            }
        };
        reservationTable = new JTable(reservationTableModel);
        reservationTable.setRowHeight(40);
        setupTable(reservationTable);

        JScrollPane reservationScrollPane = new JScrollPane(reservationTable);
        reservationScrollPane.setBounds(360, 170, 530, 550);
        add(reservationScrollPane);

        // Table for Borrowings
        String[] borrowingColumns = {"Username", "Title", "More Details"};
        borrowingTableModel = new DefaultTableModel(borrowingColumns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 2;
            }
        };
        borrowingTable = new JTable(borrowingTableModel);
        borrowingTable.setRowHeight(40);
        setupTable(borrowingTable);

        JScrollPane borrowingScrollPane = new JScrollPane(borrowingTable);
        borrowingScrollPane.setBounds(930, 170, 530, 550);
        add(borrowingScrollPane);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1535, 820);
        setResizable(false);
        getContentPane().setBackground(Color.WHITE);
        setTitle("Library Management System");
        setLayout(null);
        setVisible(true);

        add(nav);
        add(searchField);
        add(reservedLabel);
        add(borrowedLabel);

    }
    
    private void openNewBorrowingDialog() {
        JDialog dialog = new JDialog(this, "New Borrowing", true);
        dialog.setLayout(new BorderLayout()); // Use BorderLayout to center the panel

        // Create a panel with GridLayout for the dialog
        JPanel panel = new JPanel(new GridLayout(3, 2, 10, 10)); // 4 rows, 2 columns, with horizontal and vertical gaps
        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15)); // Set the border for padding

        // Username field
        JTextField usernameField = new JTextField();
        panel.add(new JLabel("Username:"));
        panel.add(usernameField);

        // Book dropdown
        JComboBox<Book> bookDropdown = new JComboBox<>();
        populateBookDropdown(bookDropdown);
        panel.add(new JLabel("Book Title:"));
        panel.add(bookDropdown);

        // Add buttons
        JButton submitButton = new JButton("Submit");
        submitButton.addActionListener(e -> {
            String username = usernameField.getText().trim();
            Book selectedBook = (Book) bookDropdown.getSelectedItem();

            if (username.isEmpty() || selectedBook == null) {
                JOptionPane.showMessageDialog(dialog, "Please enter a username and select a book.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Check if the user exists
            if (!userC.userExists(username)) {
                JOptionPane.showMessageDialog(dialog, "User  does not exist.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Create a new borrowing record
            boolean success = borrowC.createBorrowing(username, selectedBook.getBookId());
            if (success) {
                JOptionPane.showMessageDialog(dialog, "Borrowing created successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
                dialog.dispose();
                populateTable(); // Refresh the tables
            } else {
                JOptionPane.showMessageDialog(dialog, "Failed to create borrowing.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        panel.add(submitButton);

        // Cancel button
        JButton cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(e -> dialog.dispose()); // Close the dialog
        panel.add(cancelButton);

        // Add the panel to the dialog
        dialog.add(panel, BorderLayout.CENTER); // Center the panel in the dialog

        dialog.setSize(400, 200);
        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);
    }

    private void populateBookDropdown(JComboBox<Book> dropdown) {
        List<Book> availableBooks = bookC.getAvailableBooks(); // Get available books
        for (Book book : availableBooks) {
            dropdown.addItem(book); // This will now call the overridden toString() method
        }
    }

    private void setupTable(JTable table) {
        // Change table header background color and font color
        table.getTableHeader().setBackground(new Color(0x00233D));
        table.getTableHeader().setForeground(Color.WHITE);
        table.getTableHeader().setFont(new Font("Serif", Font.BOLD, 14));

        // Center-align text in rows
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        for (int i = 0; i < table.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }

        // Set preferred width for each column
        TableColumnModel columnModel = table.getColumnModel();
        columnModel.getColumn(0).setPreferredWidth(50);   // Username
        columnModel.getColumn(1).setPreferredWidth(200); // Title
        columnModel.getColumn(2).setPreferredWidth(80); // More Details

        TableColumn editColumn = table.getColumnModel().getColumn(2);
        editColumn.setCellRenderer(new ButtonRenderer("More Details"));
        editColumn.setCellEditor(new ButtonEditor("More Details", table)); // Pass the table reference
    }

    private void populateTable() {
        reservationTableModel.setRowCount(0); // Clear existing rows
        borrowingTableModel.setRowCount(0); // Clear existing rows

        // Populate the reservation table
        for (RecordController.RecordDisplay record : reservationRecords) {
            reservationTableModel.addRow(new Object[]{
                record.getUsername(),
                record.getTitle(),
                "More Details"
            });
        }

        // Populate the borrowing table
        for (RecordController.RecordDisplay record : borrowingRecords) {
            borrowingTableModel.addRow(new Object[]{
                record.getUsername(),
                record.getTitle(),
                "More Details"
            });
        }
    }

    private void searchRecords() {
        String query = searchField.getText().trim().toLowerCase(); // Get the search query and convert to lowercase
        reservationTableModel.setRowCount(0); // Clear existing rows for reservation table
        borrowingTableModel.setRowCount(0); // Clear existing rows for borrowing table

        // Filter and populate the reservation table
        for (RecordController.RecordDisplay record : reservationRecords) {
            if (record.getUsername().toLowerCase().contains(query)
                    || record.getTitle().toLowerCase().contains(query)) {
                reservationTableModel.addRow(new Object[]{
                    record.getUsername(),
                    record.getTitle(),
                    "More Details"
                });
            }
        }

        // Filter and populate the borrowing table
        for (RecordController.RecordDisplay record : borrowingRecords) {
            if (record.getUsername().toLowerCase().contains(query)
                    || record.getTitle().toLowerCase().contains(query)) {
                borrowingTableModel.addRow(new Object[]{
                    record.getUsername(),
                    record.getTitle(),
                    "More Details"
                });
            }
        }

        // Check if any records were found
        if (reservationTableModel.getRowCount() == 0 && borrowingTableModel.getRowCount() == 0) {
            JOptionPane.showMessageDialog(this, "No records found matching the search criteria.", "Information", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    // Button Renderer Class
    class ButtonRenderer extends JButton implements TableCellRenderer {
        private String label;

        public ButtonRenderer(String label) {
            setOpaque(true);
            this.label = label;
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            setText(label);
            return this;
        }
    }

    // Button Editor Class with Edit and Delete Functionality
    class ButtonEditor extends DefaultCellEditor {
    private JButton button;
    private String action;
    private boolean clicked;
    private JTable currentTable; // Store the reference to the table

    public ButtonEditor(String action, JTable table) {
        super(new JCheckBox());
        this.action = action;
        this.currentTable = table; // Store the table reference
        button = new JButton();
        button.setOpaque(true);

        button.addActionListener(evt -> handleAction());
    }

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        button.setText(action);
        clicked = true;
        return button;
    }

    @Override
    public Object getCellEditorValue() {
        clicked = false;
        return action;
    }

    @Override
    public boolean stopCellEditing() {
        clicked = false;
        return super.stopCellEditing();
    }

    private void handleAction() {
        stopCellEditing();
        if (action.equals("More Details")) {
            try {
                int rowIndex = currentTable.convertRowIndexToModel(currentTable.getEditingRow()); // Get the selected row index
                if (rowIndex < 0) {
                    JOptionPane.showMessageDialog(LibrarianOperationsManagement.this, "Please select a record to view details.", "Error", JOptionPane.ERROR_MESSAGE);
                    return; // Exit if no row is selected
                }

                if (currentTable == reservationTable) {
                    // Handle reservation details
                    RecordController.RecordDisplay record = reservationRecords.get(rowIndex);
                    ReservationDetails reservationDetails = recordC.getReservationDetails(record.getReservationId());
                    if (reservationDetails != null) {
                        String[] details = {
                            "Reservation ID:", String.valueOf(reservationDetails.getId()),
                            "User  ID:", String.valueOf(reservationDetails.getUserId()),
                            "Username:", reservationDetails.getUsername(),
                            "Book ID:", String.valueOf(reservationDetails.getBookId()),
                            "Book Title:", reservationDetails.getBookTitle(),
                            "Reservation Status:", reservationDetails.getStatus(),
                            "Collection Deadline:", reservationDetails.getCollectionDeadline(),
                            "Reserved On:", reservationDetails.getCreatedAt(),
                            "Last Updated:", reservationDetails.getUpdatedAt()
                        };
                        new DetailsDialog((Frame) SwingUtilities.getWindowAncestor(currentTable), "Reservation Details", details);
                    }
                } else if (currentTable == borrowingTable) {
                    // Handle borrowing details
                    RecordController.RecordDisplay record = borrowingRecords.get(rowIndex);
                    BorrowingDetails borrowingDetails = recordC.getBorrowingDetails(record.getBorrowingId());
                    if (borrowingDetails != null) {
                        String[] details = {
                            "Borrowing ID:", String.valueOf(borrowingDetails.getId()),
                            "User  ID:", String.valueOf(borrowingDetails.getUserId()),
                            "Username:", borrowingDetails.getUsername(),
                            "Book ID:", String.valueOf(borrowingDetails.getBookId()),
                            "Book Title:", borrowingDetails.getBookTitle(),
                            "Borrowing Status:", borrowingDetails.getStatus(),
                            "Borrowed On:", borrowingDetails.getBorrowDate(),
                            "Supposed Return Date:", borrowingDetails.getSupposedReturnDate(),
                            "Actual Return Date:", borrowingDetails.getActualReturnDate(),
                            "Last Updated:", borrowingDetails.getUpdatedAt()
                        };
                        new DetailsDialog((Frame) SwingUtilities.getWindowAncestor(currentTable), "Borrowing Details", details);
                    }
                }
            } catch (IndexOutOfBoundsException e) {
                JOptionPane.showMessageDialog(LibrarianOperationsManagement.this, "Please select a record to view details.", "Error", JOptionPane.ERROR_MESSAGE);
            } catch (ClassCastException e) {
                JOptionPane.showMessageDialog(LibrarianOperationsManagement.this, "An error occurred while retrieving details. Please try again.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}

    public class DetailsDialog extends JDialog {
        private final JPanel panel;

        public DetailsDialog(Frame parent, String title, String[] details) {
            super(parent, title, true);
            setTitle("More Details");
            panel = new JPanel(new GridLayout(details.length / 2, 2));
            panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
            setContentPane(panel);

            for (int i = 0; i < details.length; i += 2) {
                panel.add(new JLabel(details[i])); // Label
                panel.add(new JLabel(details[i + 1])); // Value
            }

            setSize(400, 300);
            setLocationRelativeTo(parent);
            setVisible(true);
        }
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new LibrarianOperationsManagement().setVisible(true));
    }
}
