package view;

import javax.swing.*;
import java.awt.*;
import model.Book;

public class AddBookModal extends JDialog {

    private final JTextField titleField;
    private final JTextField authorField;
    private final JTextField categoryField;
    private final JTextField isbnField;
    private final JTextField publisherField;
    private final JSpinner publishedYearSpinner;
    private final JSpinner quantitySpinner;
    private final JPanel panel;
    private boolean saved;
    private Book book;

    public AddBookModal(Frame parent, boolean modal) {
        super(parent, modal);
        setTitle("Add Book");
        setSize(500, 500);
        panel = new JPanel(new GridLayout(8, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        setContentPane(panel);
        setLocationRelativeTo(parent);
        

        // Fields
        panel.add(new JLabel("Title:"));
        titleField = new JTextField();
        panel.add(titleField);

        panel.add(new JLabel("Author:"));
        authorField = new JTextField();
        panel.add(authorField);

        panel.add(new JLabel("Category:"));
        categoryField = new JTextField();
        panel.add(categoryField);

        panel.add(new JLabel("ISBN:"));
        isbnField = new JTextField();
        panel.add(isbnField);
        
        panel.add(new JLabel("Publisher:"));
        publisherField = new JTextField();
        panel.add(publisherField);
        
        panel.add(new JLabel("Published Year:"));
        publishedYearSpinner = new JSpinner(new SpinnerNumberModel(2000, 1, Integer.MAX_VALUE, 1));
        panel.add(publishedYearSpinner);

        panel.add(new JLabel("Quantity:"));
        quantitySpinner = new JSpinner(new SpinnerNumberModel(1, 1, Integer.MAX_VALUE, 1));
        panel.add(quantitySpinner);

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

        if (title.isEmpty() || author.isEmpty() || category.isEmpty() || isbn.isEmpty() || publisher.isEmpty()) {
            JOptionPane.showMessageDialog(this, "All fields must be filled!", "Validation Error", JOptionPane.WARNING_MESSAGE);
            return;
        }

        book = new Book(title, author, category, isbn, publisher, publishedYear, quantity);
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
