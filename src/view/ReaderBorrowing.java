package view;

import controller.BookController;
import controller.BorrowingController;
import controller.RecordController;
import controller.RecordController.BorrowingDetails;
import controller.UserController;
import javax.swing.*;
import javax.swing.table.*;
import java.util.List;
import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import utility.Database;
import utility.UserSession;

public class ReaderBorrowing extends JFrame {

    private final Database db = new Database();
    private final BookController bookC = new BookController(db);
    private final BorrowingController borrowC = new BorrowingController(db);
    private final UserController userC = new UserController(db);
    private final RecordController recordC = new RecordController(db);
    
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
    private javax.swing.JLabel recordsLabel;

    private JTable table;
    private DefaultTableModel tableModel;
    private List<RecordController.RecordDisplay> records; // Declare a list to hold records
    
    public ReaderBorrowing() {
        initComponents();
        populateTable();
    }
    
    private void homeMouseClicked(java.awt.event.MouseEvent evt) {                                     
        ReaderDashboard rd = new ReaderDashboard();
        rd.setVisible(true);
        setVisible(false);
    } 
    
    private void browseMouseClicked(java.awt.event.MouseEvent evt) {                                     
        ReaderBrowse rb = new ReaderBrowse();
        rb.setVisible(true);
        setVisible(false);
    }
    
    private void reservehMouseClicked(java.awt.event.MouseEvent evt) {
        ReaderReservations rr = new ReaderReservations();
        rr.setVisible(true);
        setVisible(false);
    }
    
    private void borrowhMouseClicked(java.awt.event.MouseEvent evt) {
        setVisible(true);
    } 
    
    private void profileMouseClicked(java.awt.event.MouseEvent evt) {                                     
        ReaderProfile rp = new ReaderProfile();
        rp.setVisible(true);
        setVisible(false);
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
        borrowh.addMouseListener(new java.awt.event .MouseAdapter() {
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
        
        // Books Label
        recordsLabel = new JLabel("Borrowing");
        recordsLabel.setFont(new Font("Serif", Font.BOLD, 30));
        recordsLabel.setBounds(360, 120, 300, 40);
    
        // Table with Headers
        String[] columns = {"Row", "Title", "Status", "Supposed Return Date", "Actual Return Date", "Borrowed On", "More Details"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 6;
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
        columnModel.getColumn(0).setPreferredWidth(50);   // Row
        columnModel.getColumn(1).setPreferredWidth(200);  // Title
        columnModel.getColumn(2).setPreferredWidth(150);  // Status
        columnModel.getColumn(3).setPreferredWidth(100);  // Borrow Date
        columnModel.getColumn(4).setPreferredWidth(100);  // Supposed Return Date
        columnModel.getColumn(5).setPreferredWidth(100);  // Actual Return Date
        columnModel.getColumn(6).setPreferredWidth(80); // More Details

        TableColumn editColumn = table.getColumnModel().getColumn(6);
        editColumn.setCellRenderer(new ButtonRenderer("More Details"));
        editColumn.setCellEditor(new ButtonEditor("More Details"));

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(360, 170, 1100, 550);
        add(scrollPane);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1535, 820);
        setResizable(false);
        getContentPane().setBackground(Color.WHITE);
        setTitle("Library Management System - Borrowing Records");
        setLayout(null);
        setVisible(true);

        add(nav);
        add(searchField);
        add(recordsLabel);
    }

    private void populateTable() {
        tableModel.setRowCount(0); // Clear existing rows

        // Get the current logged-in user's username
        String currentUsername = UserSession.getInstance().getUsername();

        // Retrieve records from the RecordController
        records = recordC.getRecordsForUser (currentUsername, "borrowing"); // Adjust this method to fetch borrowing records

        // Check if the list is empty and handle accordingly
        if (records.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No borrowing records found.", "Information", JOptionPane.INFORMATION_MESSAGE);
        } else {
            // Populate the table with record data
            for (int i = 0; i < records.size(); i++) {
                RecordController.RecordDisplay record = records.get(i);
                tableModel.addRow(new Object[]{
                    i + 1, // Row Number
                    record.getTitle(), // Title
                    record.getStatus(), // Status
                    record.getSupposedReturnDate(), // Supposed Return Date
                    record.getActualReturnDate(), // Actual Return Date
                    record.getBorrowDate(), // Borrow Date
                    "More Details"
                });
            }
        }
    }

    private void searchRecords() {
        String query = searchField.getText().trim().toLowerCase();
        tableModel.setRowCount(0);

        // Get the current logged-in user's username
        String currentUsername = UserSession.getInstance().getUsername();

        // Retrieve records for the current user
        List<RecordController.RecordDisplay> allRecords = recordC.getRecordsForUser (currentUsername, "borrowing");
        List<RecordController.RecordDisplay> filteredRecords = new ArrayList<>();

        // Filter records based on the search query
        for (RecordController.RecordDisplay record : allRecords) {
            if (record.getTitle().toLowerCase().contains(query)
                    || record.getStatus().toLowerCase().contains(query)) {
                filteredRecords.add(record);
            }
        }

        // Populate the table with filtered records
        if (filteredRecords.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No borrowing records found matching the search criteria.", "Information", JOptionPane.INFORMATION_MESSAGE);
        } else {
            for (int i = 0; i < filteredRecords.size(); i++) {
                RecordController.RecordDisplay record = filteredRecords.get(i);
                tableModel.addRow(new Object[]{
                    i + 1, // Row Number
                    record.getTitle(), // Title
                    record.getStatus(), // Status
                    record.getSupposedReturnDate(), // Supposed Return Date
                    record.getActualReturnDate(), // Actual Return Date
                    record.getBorrowDate(), // Borrow Date
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
                    int rowIndex = table.getSelectedRow();
                    if (rowIndex < 0) {
                        JOptionPane.showMessageDialog(ReaderBorrowing.this, "Please select a record to view details.", "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    RecordController.RecordDisplay record = records.get(rowIndex);
                    int borrowingId = record.getBorrowingId();
                    BorrowingDetails borrowingDetails = recordC.getBorrowingDetails(borrowingId);

                    if (borrowingDetails != null) {
                        List<String> detailsList = new ArrayList<>(Arrays.asList(
                                "Row:", "" + (rowIndex + 1),
                                "Book Title:", borrowingDetails.getBookTitle(),
                                "Book Author:", borrowingDetails.getBookAuthor(),
                                "Book Category:", borrowingDetails.getBookCategory(),
                                "Book ISBN:", borrowingDetails.getBookIsbn(),
                                "Book Publisher:", borrowingDetails.getBookPublisher(),
                                "Book Published Year:", "" + borrowingDetails.getBookPublishedYear(),
                                "Borrowing Status:", borrowingDetails.getStatus(),
                                "Borrowed On:", borrowingDetails.getBorrowDate(),
                                "Supposed Return Date:", borrowingDetails.getSupposedReturnDate(),
                                "Actual Return Date:", borrowingDetails.getActualReturnDate(),
                                "Last Updated:", borrowingDetails.getUpdatedAt()
                        ));

                        String[] details = detailsList.toArray(new String[0]);
                        new DetailsDialog((Frame) SwingUtilities.getWindowAncestor(table), "Borrowing Details", details);
                    }
                } catch (IndexOutOfBoundsException e) {
                    JOptionPane.showMessageDialog(ReaderBorrowing.this, "Please select a record to view details.", "Error", JOptionPane.ERROR_MESSAGE);
                } catch (ClassCastException e) {
                    JOptionPane.showMessageDialog(ReaderBorrowing.this, "An error occurred while retrieving details. Please try again.", "Error", JOptionPane.ERROR_MESSAGE);
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
        SwingUtilities.invokeLater(() -> new ReaderBorrowing().setVisible(true));
    }
}