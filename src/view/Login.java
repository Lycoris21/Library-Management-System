package view;

import java.sql.*;
import javax.swing.*;
import controller.UserController;
import java.awt.*;
import model.User;
import view.Register;
import utility.Database;
import utility.PasswordHasher;
import utility.UserSession;

/**
 * 
 * @author Christine Ann Dejito
 */
public class Login extends javax.swing.JFrame {
    
    Database db = new Database();
    UserController userC = new UserController(db);
    User user;
    

    public Login() {
        initComponents();
    }
    
    private void loginActionPerformed(java.awt.event.ActionEvent evt) {
        String username = uname.getText();
        String password = new String(pass.getPassword());

        user = userC.getUserByUsername(username);

        if (user != null && user.getPassword() != null) {
            // Try different authentication methods
            boolean isAuthenticated = false;
            String hashedInputPassword = PasswordHasher.hashPassword(password);

            // Check if password matches stored password (either plain or hashed)
            if (password.equals(user.getPassword()) || 
                hashedInputPassword.equals(user.getPassword())) {
                isAuthenticated = true;

                // If the password was plain text, update to hashed
                if (password.equals(user.getPassword())) {
                    user.setPassword(hashedInputPassword);
                    userC.updateUserPassword(user.getUserId(), hashedInputPassword);
                }
            }

            if (isAuthenticated) {
                // Set the current user in the session
                UserSession.getInstance().setCurrentUser(user);

                JOptionPane.showMessageDialog(this, "Login successful! Welcome, " + user.getUsername() + "!");
                String role = user.getRole();

                // Navigate based on role
                if (role == null) {
                    new ReaderDashboard().setVisible(true);
                } else {
                    switch (role.toLowerCase()) {
                        case "admin" ->
                            new AdminDashboard().setVisible(true);
                        case "librarian" ->
                            new LibrarianDashboard().setVisible(true);
                        default ->
                            new ReaderDashboard().setVisible(true);
                    }
                }
                setVisible(false);
            } else {
                JOptionPane.showMessageDialog(this, "Invalid username or password");
            }
        } else {
            JOptionPane.showMessageDialog(this, "Invalid username or password");
        }
    }
    
    private void regMouseClicked(java.awt.event.MouseEvent evt) {                                      
            Register r = new Register();
            r.setVisible(true);
            setVisible(false);
    } 
    
    public static void main(String[] args) {

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Login().setVisible(true);
            }
        });
        
    }
    
   private boolean validateLogin(String username, String password) {
        User user = userC.getUserByUsername(username);

        if (user != null && user.getPassword() != null) {
            // Compare the passwords (assuming plaintext for simplicity; use hashed comparison in production)
            return password.equals(user.getPassword());
        }

        // If user doesn't exist or password is null
        return false;
    }

    
    private void initComponents(){
        title = new JLabel();
        title.setText("HIRAYA");
        title.setBounds(600, 80, 300, 50);
        title.setFont(new Font("Serif", Font.BOLD, 70));
        title.setVerticalAlignment(JLabel.CENTER);
        title.setHorizontalAlignment(JLabel.CENTER);
        
        username = new JLabel();
        username.setText("Username");
        username.setFont(new Font("Serif", Font.PLAIN, 20));
        username.setBounds(100, 85, 200, 50);
      
        uname = new JTextField();
        uname.setBounds(100, 135, 700, 50);
        
        password = new JLabel();
        password.setText("Password");
        password.setFont(new Font("Serif", Font.PLAIN, 20));
        password.setBounds(100, 185, 200, 50);
        
        
        pass = new JPasswordField();
        pass.setBounds(100, 235, 700, 50);
        
        login = new JButton();
        login.setText("Login");
        login.setBounds(100, 340, 700, 60);
        login.setFont(new Font("Serif", Font.BOLD, 30));
        login.setForeground(Color.WHITE);
        login.setBackground(new Color(0x316FA2));
        
        reg = new JLabel();
        reg.setText("Don't have an account? Register");
        reg.setBounds(100, 400, 400, 50);
        reg.setFont(new Font("Serif", Font.ITALIC, 15));
        reg.setForeground(new Color(0x316FA2));
        
        panel1 = new JPanel();
        panel1.setBounds(300, 180, 900, 500);
        panel1.setBackground(new Color(182, 206, 229));
        panel1.setLayout(null);
        
        panel1.add(username);
        panel1.add(uname);
        panel1.add(password);
        panel1.add(pass);
        panel1.add(login);
        panel1.add(reg);
        

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1535,820);
        setResizable(false);
        getContentPane().setBackground(new Color(109, 173, 208));
        setTitle("Library Management System");
        setLayout(null);
        setVisible(true);
        
        add(title);
        add(panel1);
        
        
        login.addActionListener(new java.awt.event.ActionListener(){
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                    loginActionPerformed(evt);
                } 
        });
        
        reg.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                regMouseClicked(evt);
            }     
        });

    }
    
    private JLabel title;
    private JLabel username;
    private JTextField uname;
    private JLabel password;
    private JPasswordField pass;
    private JButton login;
    private JLabel reg;
    private JPanel panel1;
    
}
