package view;

import javax.swing.*;
import java.awt.*;
import model.Book;

public class EditBookModal extends JDialog {

    private final JTextField titleField;
    private final JTextField authorField;
    private final JTextField categoryField;
    private final JTextField isbnField;
    private final JTextField publisherField;
    private final JSpinner publishedYearSpinner;
    private final JSpinner quantitySpinner;
    private final JComboBox<String> statusComboBox;
    private final JPanel panel;
    private boolean saved;
    private Book book;

    public EditBookModal(Frame parent, boolean modal, Book book) {
        super(parent, modal);
        setTitle("Edit Book");
        setSize(500, 550);
        panel = new JPanel(new GridLayout(9, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        setContentPane(panel);
        setLocationRelativeTo(parent);        
        this.book = book;

        // Fields
        panel.add(new JLabel("Title:"));
        titleField = new JTextField(book.getTitle());
        panel.add(titleField);

        panel.add(new JLabel("Author:"));
        authorField = new JTextField(book.getAuthor());
        panel.add(authorField);

        panel.add(new JLabel("Category:"));
        categoryField = new JTextField(book.getCategory());
        panel.add(categoryField);

        panel.add(new JLabel("ISBN:"));
        isbnField = new JTextField(book.getIsbn());
        panel.add(isbnField);
        
        panel.add(new JLabel("Publisher:"));
        publisherField = new JTextField(book.getPublisher());
        panel.add(publisherField);
        
        panel.add(new JLabel("Published Year:"));
        publishedYearSpinner = new JSpinner(new SpinnerNumberModel(book.getPublishedYear(), 1, Integer.MAX_VALUE, 1));
        panel.add(publishedYearSpinner);

        panel.add(new JLabel("Quantity:"));
        quantitySpinner = new JSpinner(new SpinnerNumberModel(book.getQuantity(), 1, Integer.MAX_VALUE, 1));
        panel.add(quantitySpinner);
        
        panel.add(new JLabel("Status:"));
        statusComboBox = new JComboBox<>(new String[]{"Available", "Deleted"});
        statusComboBox.setSelectedItem(book.getStatus());
        panel.add(statusComboBox);

        // Buttons
        JButton saveButton = new JButton("Save");
        saveButton.addActionListener(e -> saveBook());
        panel.add(saveButton);

        JButton cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(e -> dispose());
        panel.add(cancelButton);
    }

    private void saveBook() {
        String title = titleField.getText().trim();
        String author = authorField.getText().trim();
        String category = categoryField.getText().trim();
        String isbn = isbnField.getText().trim();
        String publisher = publisherField.getText().trim();
        int publishedYear = (int) publishedYearSpinner.getValue();
        int quantity = (int) quantitySpinner.getValue();
        String status = (String) statusComboBox.getSelectedItem();

        if (title.isEmpty() || author.isEmpty() || category.isEmpty() || isbn.isEmpty() || publisher.isEmpty()) {
            JOptionPane.showMessageDialog(this, "All fields must be filled!", "Validation Error", JOptionPane.WARNING_MESSAGE);
            return;
        }

        book.setTitle(title);
        book.setAuthor(author);
        book.setCategory(category);
        book.setIsbn(isbn);
        book.setPublisher(publisher);
        book.setPublishedYear(publishedYear);
        book.setQuantity(quantity);
        book.setStatus(status);
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