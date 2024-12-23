package view;

import javax.swing.*;
import java.awt.*;
import model.User;

public class EditUserModal extends JDialog {

    private final JTextField titleField;
    private final JTextField authorField;
    private final JComboBox<String> roleComboBox;
    private final JPanel panel;
    private boolean saved;
    private User user;

    public EditUserModal(Frame parent, boolean modal, User user) {
        super(parent, modal);
        setTitle("Edit User");
        setSize(350, 250);
        panel = new JPanel(new GridLayout(4, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        setContentPane(panel);
        setLocationRelativeTo(parent);        
        this.user = user;

        // Fields
        panel.add(new JLabel("Username:"));
        titleField = new JTextField(user.getUsername());
        panel.add(titleField);

        panel.add(new JLabel("Password:"));
        authorField = new JTextField(user.getPassword());
        panel.add(authorField);

        panel.add(new JLabel("Role:"));
        roleComboBox = new JComboBox<>(new String[]{"Reader", "Librarian", "Admin", "Deleted"});
        roleComboBox.setSelectedItem(user.getRole());
        panel.add(roleComboBox);


        // Buttons
        JButton saveButton = new JButton("Save");
        saveButton.addActionListener(e -> saveUser());
        panel.add(saveButton);

        JButton cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(e -> dispose());
        panel.add(cancelButton);
    }

    private void saveUser() {
        String username = titleField.getText().trim();
        String password = authorField.getText().trim();
        String role = (String) roleComboBox.getSelectedItem();

        if (username.isEmpty() || password.isEmpty() || role.isEmpty()) {
            JOptionPane.showMessageDialog(this, "All fields must be filled!", "Validation Error", JOptionPane.WARNING_MESSAGE);
            return;
        }

        user.setUsername(username);
        user.setPassword(password);
        user.setRole(role);
        saved = true;
        dispose();
    }

    public boolean isSaved() {
        return saved;
    }

    public User getUser() {
        return user;
    }
}