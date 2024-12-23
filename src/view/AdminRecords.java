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
import utility.Database;
import utility.UserSession;

public class AdminRecords extends JFrame {

    private final Database db = new Database();
    private final BookController bookC = new BookController(db);
    private final BorrowingController borrowC = new BorrowingController(db);
    private final UserController userC = new UserController(db);
    private final RecordController recordC = new RecordController(db);
    
    private javax.swing.JPanel nav;
    private javax.swing.JLabel dash;
    private javax.swing.JLabel home;
    private javax.swing.JLabel bookm;
    private javax.swing.JLabel userm;
    private javax.swing.JLabel recordsl;
    private javax.swing.JLabel username;
    private javax.swing.JLabel searchLabel;
    private javax.swing.JTextField searchField;
    private javax.swing.JButton searchButton;
    private javax.swing.JButton reservationsButton;
    private javax.swing.JButton borrowingButton;
    private javax.swing.JLabel recordsLabel;
    
    private JTable table;
    private DefaultTableModel tableModel;
    private List<RecordController.RecordDisplay> records; // Declare a list to hold records
    
    public AdminRecords() {
        initComponents();
        populateTable();
    }
    
    private void homeMouseClicked(java.awt.event.MouseEvent evt) {                                     
        AdminDashboard ad = new AdminDashboard();
        ad.setVisible(true);
        setVisible(false);
    } 
    
    private void bookmMouseClicked(java.awt.event.MouseEvent evt) {                                     
        AdminBookManagement abm = new AdminBookManagement();
        abm.setVisible(true);
        setVisible(false);
    }
    
    private void usermMouseClicked(java.awt.event.MouseEvent evt) {                                     
        AdminUserManagement asm = new AdminUserManagement();
        asm.setVisible(true);
        setVisible(false);
    } 
    
    private void recordsMouseClicked(java.awt.event.MouseEvent evt) {                                     
        setVisible(true);
    }

    private void initComponents() {
        // Dashboard and Navigation Setup
        dash = new JLabel("Dashboard");
        dash.setBounds(75, 50, 300, 50);
        dash.setFont(new Font("Serif", Font.BOLD, 30));
        dash.setForeground(Color.WHITE);
    
        home = new JLabel("Home");
        home.setBounds(40, 200, 300, 50);
        home.setFont(new Font("Serif", Font.PLAIN, 25));
        home.setForeground(Color.WHITE);
        home.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                homeMouseClicked(evt);
            }
        });
    
        bookm = new JLabel("Book Management");
        bookm.setBounds(40, 260, 300, 50);
        bookm.setFont(new Font("Serif", Font.PLAIN, 25));
        bookm.setForeground(Color.WHITE);
        bookm.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                bookmMouseClicked(evt);
            }
        });
        
        userm = new JLabel();
        userm.setText("User Management");
        userm.setBounds(40, 320, 300, 50);
        userm.setFont(new Font("Serif", Font.PLAIN, 25));
        userm.setForeground(Color.WHITE);
        userm.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                usermMouseClicked(evt);
            }
        });
        
        recordsl = new JLabel();
        recordsl.setText("Records");
        recordsl.setBounds(40, 380, 300, 50);
        recordsl.setFont(new Font("Serif", Font.PLAIN, 25));
        recordsl.setForeground(Color.WHITE);
        recordsl.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                recordsMouseClicked(evt);
            }
        });
        
        username = new JLabel();
        username.setText(UserSession.getInstance().getUsername());
        username.setBounds(40, 600, 200, 200);
        username.setFont(new Font("Serif", Font.PLAIN, 25));
        username.setForeground(Color.WHITE);
        //username.setIcon(pp);
        username.setHorizontalTextPosition(JLabel.LEFT);
        username.setVerticalTextPosition(JLabel.CENTER);
        username.setLayout(null);
        
        nav = new JPanel();
        nav.setBounds(0, 0, 300, 820);
        nav.setBackground(new Color(0x00233D));
        nav.setLayout(null);
    
        nav.add(dash);
        nav.add(home);
        nav.add(bookm);
        nav.add(userm);
        nav.add(recordsl);
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
        
        reservationsButton = new JButton("Reservations");
        reservationsButton.setBounds(1240, 60, 110, 30);
        reservationsButton.setForeground(Color.WHITE);
        reservationsButton.setBackground(new Color(0x316FA2));
        reservationsButton.addActionListener(evt -> showReservations());
        add(reservationsButton);
        
        borrowingButton = new JButton("Borrowing");
        borrowingButton.setBounds(1360, 60, 100, 30);
        borrowingButton.setForeground(Color.WHITE);
        borrowingButton.setBackground(new Color(0x316FA2));
        borrowingButton.addActionListener(evt -> showBorrowing());
        add(borrowingButton);
    
        // Books Label
        recordsLabel = new JLabel("Records");
        recordsLabel.setFont(new Font("Serif", Font.BOLD, 30));
        recordsLabel.setBounds(360, 120, 200, 30);
    
        // Table with Headers
        String[] columns = {"Username", "Title", "Status", "Last Updated", "More Details"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 4;
            }
        };
        table = new JTable(tableModel);
        table.setRowHeight(40);

        // Change table header background color and font color
        table.getTableHeader().setBackground(new Color(0x00233D));  // Set header background color
        table.getTableHeader().setForeground(Color.WHITE);  // Set header font color
        table.getTableHeader().setFont(new Font("Serif", Font.BOLD, 14));  // Set header font style and size

        // Center-align text in rows
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        for (int i = 0; i < table.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }

        // Set preferred width for each column
        TableColumnModel columnModel = table.getColumnModel();
        columnModel.getColumn(0).setPreferredWidth(50);   // ID
        columnModel.getColumn(1).setPreferredWidth(200); // Title
        columnModel.getColumn(2).setPreferredWidth(150); // Author
        columnModel.getColumn(3).setPreferredWidth(100); // Category
        columnModel.getColumn(4).setPreferredWidth(80); // Delete

        TableColumn editColumn = table.getColumnModel().getColumn(4);
        editColumn.setCellRenderer(new ButtonRenderer("More Details"));
        editColumn.setCellEditor(new ButtonEditor("More Details"));

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(360, 170, 1100, 550);
        add(scrollPane);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1535, 820);
        setResizable(false);
        getContentPane().setBackground(Color.WHITE);
        setTitle("Library Management System");
        setLayout(null);
        setVisible(true);

        add(nav);
        add(searchField);
        add(recordsLabel);

    }

    private void populateTable() {
        tableModel.setRowCount(0); // Clear existing rows

        // Retrieve records from the RecordController
        records = recordC.getRecords(); // Store the records in the class variable

        // Check if the list is empty and handle accordingly
        if (records.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No records found.", "Information", JOptionPane.INFORMATION_MESSAGE);
        } else {
            // Populate the table with record data
            for (RecordController.RecordDisplay record : records) {
                tableModel.addRow(new Object[]{
                    record.getUsername(), // Username
                    record.getTitle(), // Book title
                    record.getStatus(), // Status (Reserved, Borrowed, etc.)
                    record.getLastUpdated(), // Last updated timestamp
                    "More Details" // Placeholder for a button or link to show more details
                });
            }
        }
    }

    private void searchRecords() {
        String query = searchField.getText().trim().toLowerCase(); // Get the search query and convert to lowercase
        tableModel.setRowCount(0); // Clear existing rows

        // Retrieve all records from the RecordController
        List<RecordController.RecordDisplay> allRecords = recordC.getRecords();
        List<RecordController.RecordDisplay> filteredRecords = new ArrayList<>();

        // Filter records based on the search query
        for (RecordController.RecordDisplay record : allRecords) {
            // Check if the username, title, or status contains the search query
            if (record.getUsername().toLowerCase().contains(query)
                    || record.getTitle().toLowerCase().contains(query)
                    || record.getStatus().toLowerCase().contains(query)) {
                filteredRecords.add(record);
            }
        }

        // Populate the table with filtered records
        if (filteredRecords.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No records found matching the search criteria.", "Information", JOptionPane.INFORMATION_MESSAGE);
        } else {
            for (RecordController.RecordDisplay record : filteredRecords) {
                tableModel.addRow(new Object[]{
                    record.getUsername(), // Username
                    record.getTitle(), // Book title
                    record.getStatus(), // Status (Reserved, Borrowed, etc.)
                    record.getLastUpdated(), // Last updated timestamp
                    "More Details" // Placeholder for a button or link to show more details
                });
            }
        }
    }
    
    private void showReservations() {
        tableModel.setRowCount(0); // Clear existing rows
        List<RecordController.RecordDisplay> records = recordC.getRecords(); // Get all records

        // Filter records to show only reservations
        for (RecordController.RecordDisplay record : records) {
            if (record.getStatus().equals("Reserved") || record.getStatus().equals("Voided")) { // Adjust this condition based on your status logic
                tableModel.addRow(new Object[]{
                    record.getUsername(),
                    record.getTitle(),
                    record.getStatus(),
                    record.getLastUpdated(),
                    "More Details"
                });
            }
        }
    }

    private void showBorrowing() {
        tableModel.setRowCount(0); // Clear existing rows
        List<RecordController.RecordDisplay> records = recordC.getRecords(); // Get all records

        // Filter records to show only borrowing
        for (RecordController.RecordDisplay record : records) {
            if (record.getStatus().equals("Borrowed") || record.getStatus().equals("Overdue") || record.getStatus().equals("Returned")) { // Adjust this condition based on your status logic
                tableModel.addRow(new Object[]{
                    record.getUsername(),
                    record.getTitle(),
                    record.getStatus(),
                    record.getLastUpdated(),
                    "More Details"
                });
            }
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
        private int row;

        public ButtonEditor(String action) {
            super(new JCheckBox());
            this.action = action;
            button = new JButton();
            button.setOpaque(true);

            button.addActionListener(evt -> handleAction());
        }

        @Override
        public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
            this.row = row;
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
                    // Retrieve the selected row index
                    int rowIndex = table.getSelectedRow(); // Get the selected row index
                    if (rowIndex < 0) {
                        JOptionPane.showMessageDialog(AdminRecords.this, "Please select a record to view details.", "Error", JOptionPane.ERROR_MESSAGE);
                        return; // Exit if no row is selected
                    }

                    RecordController.RecordDisplay record = records.get(rowIndex); // Get the RecordDisplay object

                    String status = record.getStatus(); // Get the status

                    if (status.equals("Reserved") || status.equals("Voided")) {
                        int reservationId = record.getReservationId(); // Get the reservation ID
                        ReservationDetails reservationDetails = recordC.getReservationDetails(reservationId);
                        if (reservationDetails != null) {
                            String[] details = {
                                "Reservation ID:", String.valueOf(reservationDetails.getId()),
                                "User ID:", String.valueOf(reservationDetails.getUserId()),
                                "Username:", reservationDetails.getUsername(),
                                "Book ID:", String.valueOf(reservationDetails.getBookId()),
                                "Book Title:", reservationDetails.getBookTitle(),
                                "Reservation Status:", reservationDetails.getStatus(),
                                "Collection Deadline:", reservationDetails.getCollectionDeadline(),
                                "Reserved On:", reservationDetails.getCreatedAt(),
                                "Last Updated:", reservationDetails.getUpdatedAt()
                            };
                            new DetailsDialog((Frame) SwingUtilities.getWindowAncestor(table), "Reservation Details", details);
                        }
                    } else if (status.equals("Borrowed") || status.equals("Overdue") || status.equals("Returned")) {
                        int borrowingId = record.getBorrowingId(); // Get the borrowing ID
                        BorrowingDetails borrowingDetails = recordC.getBorrowingDetails(borrowingId);
                        if (borrowingDetails != null) {
                            String[] details = {
                                "Borrowing ID:", String.valueOf(borrowingDetails.getId()),
                                "User ID:", String.valueOf(borrowingDetails.getUserId()),
                                "Username:", borrowingDetails.getUsername(),
                                "Book ID:", String.valueOf(borrowingDetails.getBookId()),
                                "Book Title:", borrowingDetails.getBookTitle(),
                                "Borrowing Status:", borrowingDetails.getStatus(),
                                "Borrowed On:", borrowingDetails.getBorrowDate(),
                                "Supposed Return Date:", borrowingDetails.getSupposedReturnDate(),
                                "Actual Return Date:", borrowingDetails.getActualReturnDate(),
                                "Last Updated:", borrowingDetails.getUpdatedAt()
                            };
                            new DetailsDialog((Frame) SwingUtilities.getWindowAncestor(table), "Borrowing Details", details);
                        }
                    }
                } catch (IndexOutOfBoundsException e) {
                    JOptionPane.showMessageDialog(AdminRecords.this, "Please select a record to view details.", "Error", JOptionPane.ERROR_MESSAGE);
                } catch (ClassCastException e) {
                    JOptionPane.showMessageDialog(AdminRecords.this, "An error occurred while retrieving details. Please try again.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }
    
    public class DetailsDialog extends JDialog {
        
        private final JPanel panel;
        
        public DetailsDialog(Frame parent, String title, String[] details) {
            super(parent, title, true);
            setTitle("More Details");
            panel = new JPanel(new GridLayout(details.length/2, 2));
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
        SwingUtilities.invokeLater(() -> new AdminRecords().setVisible(true));
    }
}