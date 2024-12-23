package view;

import controller.UserController;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import model.User;
import utility.Database;
import utility.PasswordHasher;
import utility.UserSession;

public class ReaderProfile extends JFrame {

    private final Database db = new Database();
    private final UserController userController = new UserController(db);

    private javax.swing.JPanel nav;
    private javax.swing.JLabel dash;
    private javax.swing.JLabel home;
    private javax.swing.JLabel browse;
    private javax.swing.JLabel reserveh;
    private javax.swing.JLabel borrowh;
    private javax.swing.JLabel profile;
    private javax.swing.JLabel username;
    
    // Profile components
    private JTextField usernameField;
    private JPasswordField currentPasswordField;
    private JPasswordField newPasswordField;
    private JPasswordField confirmNewPasswordField;
    private JButton confirmButton;
    
    public ReaderProfile() {
        initComponents();
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
        ReaderBorrowing rbh = new ReaderBorrowing();
        rbh.setVisible(true);
        setVisible(false);
    } 
    
    private void profileMouseClicked(java.awt.event.MouseEvent evt) {                                     
        setVisible(true);
    }

    public static void main(String[] args) {

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ReaderProfile().setVisible(true);
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
        nav.add(browse);
        nav.add(reserveh);
        nav.add(borrowh);
        nav.add(profile);
        nav.add(username);
        
        JPanel profilePanel = new JPanel();
        profilePanel.setLayout(null);
        profilePanel.setBounds(570, 100, 650, 550); // Increased height
        profilePanel.setBackground(new Color(0x00233D));

        // Current User Details
        User currentUser = UserSession.getInstance().getCurrentUser();

        JLabel usernameLabel = new JLabel("Username:");
        usernameLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        usernameLabel.setForeground(Color.WHITE);
        usernameLabel.setBounds(70, 30, 300, 30);
        profilePanel.add(usernameLabel);

        usernameField = new JTextField(currentUser.getUsername());
        usernameField.setBounds(70, 70, 500, 40); // Adjusted width to remove edit button
        usernameField.setBackground(new Color(0x4F9AB1));
        usernameField.setForeground(Color.WHITE);
        usernameField.setFont(new Font("Arial", Font.PLAIN, 16));
        usernameField.setHorizontalAlignment(JTextField.CENTER);
        profilePanel.add(usernameField);
        
        // Current Password Section
        JLabel currentPasswordLabel = new JLabel("Current Password:");
        currentPasswordLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        currentPasswordLabel.setForeground(Color.WHITE);
        currentPasswordLabel.setBounds(70, 130, 200, 30);
        profilePanel.add(currentPasswordLabel);

        currentPasswordField = new JPasswordField();
        currentPasswordField.setBounds(70, 170, 500, 40);
        currentPasswordField.setBackground(new Color(0x4F9AB1));
        currentPasswordField.setForeground(Color.WHITE);
        currentPasswordField.setFont(new Font("Arial", Font.PLAIN, 16));
        currentPasswordField.setHorizontalAlignment(JTextField.CENTER);
        profilePanel.add(currentPasswordField);

        // New Password Section
        JLabel newPasswordLabel = new JLabel("New Password:");
        newPasswordLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        newPasswordLabel.setForeground(Color.WHITE);
        newPasswordLabel.setBounds(70, 230, 200, 30);
        profilePanel.add(newPasswordLabel);

        newPasswordField = new JPasswordField();
        newPasswordField.setBounds(70, 270, 500, 40);
        newPasswordField.setBackground(new Color(0x4F9AB1));
        newPasswordField.setForeground(Color.WHITE);
        newPasswordField.setFont(new Font("Arial", Font.PLAIN, 16));
        newPasswordField.setHorizontalAlignment(JTextField.CENTER);
        profilePanel.add(newPasswordField);

        // Confirm New Password Section
        JLabel confirmNewPasswordLabel = new JLabel("Confirm New Password:");
        confirmNewPasswordLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        confirmNewPasswordLabel.setForeground(Color.WHITE);
        confirmNewPasswordLabel.setBounds(70, 330, 200, 30);
        profilePanel.add(confirmNewPasswordLabel);

        confirmNewPasswordField = new JPasswordField();
        confirmNewPasswordField.setBounds(70, 370, 500, 40);
        confirmNewPasswordField.setBackground(new Color(0x4F9AB1));
        confirmNewPasswordField.setForeground(Color.WHITE);
        confirmNewPasswordField.setFont(new Font("Arial", Font.PLAIN, 16));
        confirmNewPasswordField.setHorizontalAlignment(JTextField.CENTER);
        profilePanel.add(confirmNewPasswordField);

        // Confirm Changes button
        confirmButton = new JButton("Confirm Changes");
        confirmButton.setBounds(230, 460, 200, 40);
        confirmButton.setBackground(new Color(0x4F9AB1));
        confirmButton.setForeground(Color.WHITE);
        confirmButton.setBorder(BorderFactory.createLineBorder(Color.WHITE));
        confirmButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateProfile();
            }
        });
        profilePanel.add(confirmButton);

        // Frame setup (keep existing setup)
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1535,820);
        setResizable(false);
        getContentPane().setBackground(Color.WHITE);
        setTitle("Library Management System");
        setLayout(null);
        
        add(nav);
        add(profilePanel);
        setVisible(true);
    }
    
    private void updateProfile() {
        User currentUser = UserSession.getInstance().getCurrentUser();

        // Validate current password
        String currentPassword = new String(currentPasswordField.getPassword());
        String hashedInputPassword = PasswordHasher.hashPassword(currentPassword);

        // Debug print statements
        System.out.println("Stored password hash: " + currentUser.getPassword());
        System.out.println("Input password hash: " + hashedInputPassword);

        // Check if password field is empty
        if (currentPassword.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Please enter your current password to confirm changes.",
                    "Password Confirmation Required",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Verify password
        if (!currentUser.getPassword().equals(hashedInputPassword)) {
            // If the stored password is not hashed, hash it and compare
            if (currentUser.getPassword().equals(currentPassword)) {
                // Update the password to its hashed version
                String hashedPassword = PasswordHasher.hashPassword(currentPassword);
                currentUser.setPassword(hashedPassword);
                userController.updateUserPassword(currentUser.getUserId(), hashedPassword);
            } else {
                JOptionPane.showMessageDialog(this,
                        "Current password is incorrect.",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }
        }

        // Username validation
        String newUsername = usernameField.getText().trim();

        // Check if username is empty
        if (newUsername.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Username cannot be empty.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Check username length
        if (newUsername.length() < 3 || newUsername.length() > 20) {
            JOptionPane.showMessageDialog(this,
                    "Username must be between 3 and 20 characters.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Check if username contains only alphanumeric characters and underscores
        if (!newUsername.matches("^[a-zA-Z0-9_]+$")) {
            JOptionPane.showMessageDialog(this,
                    "Username can only contain letters, numbers, and underscores.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Check if username is already taken
        User existingUser = userController.getUserByUsername(newUsername);
        if (existingUser != null && existingUser.getUserId() != currentUser.getUserId()) {
            JOptionPane.showMessageDialog(this,
                    "Username is already taken.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Check if new username is different from current username
        boolean usernameChanged = !newUsername.equals(currentUser.getUsername());

        // Check if password is empty when username is changed
        if (usernameChanged) {
            currentUser.setUsername(newUsername);
        }

        // Password change logic (optional)
        String newPassword = new String(newPasswordField.getPassword());
        String confirmNewPassword = new String(confirmNewPasswordField.getPassword());

        if (!newPassword.isEmpty()) {
            // Validate new password
            if (newPassword.length() < 8) {
                JOptionPane.showMessageDialog(this,
                        "New password must be at least 8 characters long.",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (!newPassword.equals(confirmNewPassword)) {
                JOptionPane.showMessageDialog(this,
                        "New passwords do not match.",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Update password in the database
            currentUser.setPassword(PasswordHasher.hashPassword(newPassword));
        }

        // Update user in the database
        if (userController.updateUser(currentUser)) {
            // Update the user in the session
            UserSession.getInstance().setCurrentUser(currentUser);

            JOptionPane.showMessageDialog(this,
                    "Profile updated successfully.",
                    "Success",
                    JOptionPane.INFORMATION_MESSAGE);

            // Refresh username in navigation
            username.setText(currentUser.getUsername());

            // Clear password fields
            currentPasswordField.setText("");
            newPasswordField.setText("");
            confirmNewPasswordField.setText("");
        } else {
            JOptionPane.showMessageDialog(this,
                    "Failed to update profile.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }
    
}