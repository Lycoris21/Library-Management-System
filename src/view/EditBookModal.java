package view;

import javax.swing.*;
import java.awt.*;
import model.Book;

public class EditBookModal extends JDialog {

    private final JTextField titleField;
    private final JTextField authorField;
    private final JTextField categoryField;
    private final JTextField isbnField;
    private final JSpinner quantitySpinner;
    private boolean saved;
    private Book book;

    public EditBookModal(Frame parent, boolean modal, Book book) {
        super(parent, modal);
        setTitle("Edit Book");
        setSize(400, 300);
        setLocationRelativeTo(parent);
        setLayout(new GridLayout(6, 2, 10, 10));
        this.book = book;

        // Fields
        add(new JLabel("Title:"));
        titleField = new JTextField(book.getTitle());
        add(titleField);

        add(new JLabel("Author:"));
        authorField = new JTextField(book.getAuthor());
        add(authorField);

        add(new JLabel("Category:"));
        categoryField = new JTextField(book.getCategory());
        add(categoryField);

        add(new JLabel("ISBN:"));
        isbnField = new JTextField(book.getIsbn());
        add(isbnField);

        add(new JLabel("Quantity:"));
        quantitySpinner = new JSpinner(new SpinnerNumberModel(book.getQuantity(), 1, Integer.MAX_VALUE, 1));
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

        book.setTitle(title);
        book.setAuthor(author);
        book.setCategory(category);
        book.setIsbn(isbn);
        book.setQuantity(quantity);
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