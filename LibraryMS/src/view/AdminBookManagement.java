package view;

import controller.BookController;
import controller.BorrowingController;
import controller.UserController;
import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.util.List;
import model.Book;
import utility.Database;

public class AdminBookManagement extends JFrame {

    Database db = new Database();
    BookController bookC = new BookController(db);
    BorrowingController borrowC = new BorrowingController(db);
    UserController userC = new UserController(db);
    private JTable table;

    public AdminBookManagement() {
        initComponents();
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
        AdminRecords ar = new AdminRecords();
        ar.setVisible(true);
        setVisible(false);
    } 
    
    private void searchButtonActionPerformed(java.awt.event.ActionEvent evt) {
        String query = searchField.getText().trim();
        List<Book> filteredBooks = bookC.searchBooks(query);
        populateTable(filteredBooks);
    }

    private void addBookButtonActionPerformed(java.awt.event.ActionEvent evt) {  
        AddBookModal addBookDialog;
        addBookDialog = new AddBookModal(this, true);
        addBookDialog.setVisible(true);

        if (addBookDialog.isSaved()) {
            Book book = addBookDialog.getBook();
            boolean isInserted = bookC.addBook(book);

            if (isInserted) {
                JOptionPane.showMessageDialog(this, "Book added successfully!");
                populateTable(); // Refresh table data
            } else {
                JOptionPane.showMessageDialog(this, "Failed to add book!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public static void main(String[] args) {
        new AdminBookManagement().setVisible(true);
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

        records = new JLabel();
        records.setText("Records");
        records.setBounds(40, 380, 300, 50);
        records.setFont(new Font("Serif", Font.PLAIN, 25));
        records.setForeground(Color.WHITE);
        records.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                recordsMouseClicked(evt);
            }
        });

        ImageIcon pp = new ImageIcon("src/images/jingliu.jpg");

        username = new JLabel();
        username.setText("Kwesten Ann");
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
        nav.add(records);
        nav.add(username);

        // Search Label and TextField
        searchLabel = new JLabel("Search");
        searchLabel.setFont(new Font("Serif", Font.PLAIN, 20));
        searchLabel.setBounds(400, 67, 100, 30);

        searchField = new JTextField();
        searchField.setBounds(470, 70, 500, 30);

        // Search and Add Book Buttons
        searchButton = new JButton("Search");
        searchButton.setBounds(990, 70, 100, 30);
        searchButton.setForeground(Color.WHITE);
        searchButton.setBackground(new Color(0x316FA2));
        searchButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                searchButtonActionPerformed(evt);
            }
        });

        addBookButton = new JButton("Add Book");
        addBookButton.setBounds(1110, 70, 100, 30);
        addBookButton.setForeground(Color.WHITE);
        addBookButton.setBackground(new Color(0x316FA2));
        addBookButton.addActionListener(new java.awt.event.ActionListener(){
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                    addBookButtonActionPerformed(evt);
                } 
        });

        // Books Label
        booksLabel = new JLabel("Books");
        booksLabel.setFont(new Font("Serif", Font.BOLD, 30));
        booksLabel.setBounds(400, 120, 200, 30);

        // Table with Headers
        String[] columns = {"ID", "Title", "Author", "Category", "ISBN", "Quantity", "Action"};
        DefaultTableModel model = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 6;
            }
        };

        table = new JTable(model); // Initialize table here
        table.setRowHeight(40);
        
        
        // Center cell values
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        for (int i = 0; i < table.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }

        // Apply the ButtonRenderer and ButtonEditor to the "Action" column (index 6)
        table.getColumnModel().getColumn(6).setCellRenderer(new ButtonRenderer());
        table.getColumnModel().getColumn(6).setCellEditor(new ButtonEditor(nav));

        // Populate table with data from the database
        populateTable();

        // Table header customization
        JTableHeader header = table.getTableHeader();
        header.setFont(new Font("Serif", Font.BOLD, 16));
        header.setBackground(new Color(0x00233D));
        header.setForeground(Color.WHITE);

        // Scroll pane for table
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(400, 160, 1000, 600);

        // Add components to frame
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1535, 820);
        setLayout(null);
        add(nav);
        add(searchLabel);
        add(searchField);
        add(searchButton);
        add(addBookButton);
        add(booksLabel);
        add(scrollPane);
    }
    
        private javax.swing.JPanel nav;
        private javax.swing.JLabel dash;
        private javax.swing.JLabel home;
        private javax.swing.JLabel bookm;
        private javax.swing.JLabel userm;
        private javax.swing.JLabel records;
        private javax.swing.JLabel username;
        private javax.swing.JLabel searchLabel;
        private javax.swing.JTextField searchField;
        private javax.swing.JButton searchButton;
        private javax.swing.JButton addBookButton;
        private javax.swing.JLabel booksLabel;


        private void populateTable() {
            List<Book> books = bookC.getAllBooks();
            populateTable(books);
        }

        private void populateTable(List<Book> books) {
            DefaultTableModel model = (DefaultTableModel) table.getModel();
            model.setRowCount(0); // Clear existing data

            for (Book book : books) {
                model.addRow(new Object[]{
                    book.getBookId(),
                    book.getTitle(),
                    book.getAuthor(),
                    book.getCategory(),
                    book.getIsbn(),
                    book.getQuantity(),
                    "" // Placeholder for action buttons
                });
            }
        }

        // Add the new method for delete confirmation
        private void showDeleteConfirmation(Book book) {
            int result = JOptionPane.showConfirmDialog(
                    this,
                    "Are you sure you want to delete the book: " + book.getTitle() + "?",
                    "Delete Confirmation",
                    JOptionPane.YES_NO_OPTION
            );

            if (result == JOptionPane.YES_OPTION) {
                boolean isDeleted = bookC.deleteBook(book.getBookId());
                if (isDeleted) {
                    JOptionPane.showMessageDialog(this, "Book deleted successfully!");
                    populateTable();
                } else {
                    JOptionPane.showMessageDialog(this, "Failed to delete book!", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        }

        // Custom Renderer for Buttons
    class ButtonRenderer extends JPanel implements TableCellRenderer {
        JButton editButton = new JButton("Edit");
        JButton deleteButton = new JButton("Delete");

        public ButtonRenderer() {
            setLayout(new FlowLayout(FlowLayout.CENTER, 5, 0));

            editButton.setForeground(Color.WHITE);
            editButton.setBackground(new Color(0x316FA2));

            deleteButton.setForeground(Color.WHITE);
            deleteButton.setBackground(new Color(0x316FA2));

            add(editButton);
            add(deleteButton);
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            return this;
        }
    }
        
        // Ensure your ButtonEditor uses the updated handleEditAction and handleDeleteAction
        class ButtonEditor extends AbstractCellEditor implements TableCellEditor {
            JPanel panel = new JPanel();
            JButton editButton = new JButton("Edit");
            JButton deleteButton = new JButton("Delete");

            public ButtonEditor(JPanel parent) {
                panel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 0));

                editButton.setForeground(Color.WHITE);
                editButton.setBackground(new Color(0x316FA2));

                deleteButton.setForeground(Color.WHITE);
                deleteButton.setBackground(new Color(0x316FA2));

                panel.add(editButton);
                panel.add(deleteButton);

                editButton.addActionListener(e -> handleEditAction());
                deleteButton.addActionListener(e -> handleDeleteAction());
            }

            private void handleEditAction() {
                int row = table.getSelectedRow();
                int bookId = (int) table.getValueAt(row, 0);
                Book book = bookC.getBookById(bookId);

                EditBookModal editBookDialog = new EditBookModal((Frame) SwingUtilities.getWindowAncestor(panel), true, book);
                editBookDialog.setVisible(true);

                if (editBookDialog.isSaved()) {
                    Book updatedBook = editBookDialog.getBook();
                    boolean isUpdated = bookC.updateBook(updatedBook);
                    if (isUpdated) {
                        JOptionPane.showMessageDialog(panel, "Book updated successfully!");
                        populateTable();
                    } else {
                        JOptionPane.showMessageDialog(panel, "Failed to update book!", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
                fireEditingStopped();
            }

            private void handleDeleteAction() {
                int row = table.getSelectedRow();
                if (row >= 0) {
                    int bookId = (int) table.getValueAt(row, 0);
                    Book book = bookC.getBookById(bookId);
                    
                    showDeleteConfirmation(book);

                    // Ensure the table model is updated correctly before stopping the editing
                    DefaultTableModel model = (DefaultTableModel) table.getModel();
                    model.removeRow(row);
                    
                    fireEditingStopped();
                }
            }

            @Override
            public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
                return panel;
            }

            @Override
            public Object getCellEditorValue() {
                return null;
            }
        }
}