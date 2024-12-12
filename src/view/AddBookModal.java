package view;

import javax.swing.*;
import java.awt.*;
import model.Book;

public class AddBookModal extends JDialog {

    private final JTextField titleField;
    private final JTextField authorField;
    private final JTextField categoryField;
    private final JTextField isbnField;
    private final JSpinner quantitySpinner;
    private boolean saved;
    private Book book;

    public AddBookModal(Frame parent, boolean modal) {
        super(parent, modal);
        setTitle("Add Book");
        setSize(400, 300);
        setLocationRelativeTo(parent);
        setLayout(new GridLayout(6, 2, 10, 10));

        // Fields
        add(new JLabel("Title:"));
        titleField = new JTextField();
        add(titleField);

        add(new JLabel("Author:"));
        authorField = new JTextField();
        add(authorField);

        add(new JLabel("Category:"));
        categoryField = new JTextField();
        add(categoryField);

        add(new JLabel("ISBN:"));
        isbnField = new JTextField();
        add(isbnField);

        add(new JLabel("Quantity:"));
        quantitySpinner = new JSpinner(new SpinnerNumberModel(1, 1, Integer.MAX_VALUE, 1));
        add(quantitySpinner);

        // Buttons
        JButton saveButton = new JButton("Save");
        saveButton.addActionListener(e -> saveBook());
        add(saveButton);

        JButton cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(e -> dispose());
        add(cancelButton);
    }

    private void saveBook() {
        String title = titleField.getText().trim();
        String author = authorField.getText().trim();
        String category = categoryField.getText().trim();
        String isbn = isbnField.getText().trim();
        int quantity = (int) quantitySpinner.getValue();

        if (title.isEmpty() || author.isEmpty() || category.isEmpty() || isbn.isEmpty()) {
            JOptionPane.showMessageDialog(this, "All fields must be filled!", "Validation Error", JOptionPane.WARNING_MESSAGE);
            return;
        }

        book = new Book(title, author, category, isbn, quantity);
        saved = true;
        dispose();
    }

    public boolean isSaved() {
        return saved;
    }

    public Book getBook() {
        return book;
    }
}
