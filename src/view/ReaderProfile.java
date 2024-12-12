package view;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ReaderProfile extends JFrame {

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
    
    private void borrowhMouseClicked(java.awt.event.MouseEvent evt) {                                     
        ReaderBorrowHistory rbh = new ReaderBorrowHistory();
        rbh.setVisible(true);
        setVisible(false);
    } 
    
    private void profileMouseClicked(java.awt.event.MouseEvent evt) {                                     
        setVisible(true);
    }

    public static void main(String[] args) {
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(AdminDashboard.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(AdminDashboard.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(AdminDashboard.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(AdminDashboard.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }

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

        borrowh = new JLabel();
        borrowh.setText("Borrow History");
        borrowh.setBounds(40, 320, 300, 50);
        borrowh.setFont(new Font("Serif", Font.PLAIN, 25));
        borrowh.setForeground(Color.WHITE);
        borrowh.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                borrowhMouseClicked(evt);
            }
        });

        profile = new JLabel();
        profile.setText("Profile Page");
        profile.setBounds(40, 380, 300, 50);
        profile.setFont(new Font("Serif", Font.PLAIN, 25));
        profile.setForeground(Color.WHITE);
        profile.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                profileMouseClicked(evt);
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
        nav.add(browse);
        nav.add(borrowh);
        nav.add(profile);
        nav.add(username);
        
        JPanel profilePanel = new JPanel();
        profilePanel.setLayout(null);
        profilePanel.setBounds(570, 100, 650, 500); // Adjust the size and position as needed
        profilePanel.setBackground(new Color(0x00233D)); // Dark blue background

        // Profile Picture Section
        JLabel changePicLabel = new JLabel("Change Profile Picture");
        changePicLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        changePicLabel.setForeground(Color.WHITE);
        changePicLabel.setBounds(250, 180, 200, 20);
        profilePanel.add(changePicLabel);

        JPanel picPanel = new JPanel();
        picPanel.setBounds(250, 15, 150, 150);
        picPanel.setBackground(new Color(0x4F9AB1)); // Light blue color for circle
        picPanel.setLayout(new BorderLayout());
        picPanel.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2)); // Border around circle
        picPanel.setPreferredSize(new Dimension(150, 150));
        picPanel.setLayout(null);
        picPanel.add(new JLabel("")); // Empty label to center the circle
        profilePanel.add(picPanel);

        // Username Section
        JLabel usernameLabel = new JLabel("Username:");
        usernameLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        usernameLabel.setForeground(Color.WHITE);
        usernameLabel.setBounds(70, 220, 300, 30);
        profilePanel.add(usernameLabel);

        JTextField usernameField = new JTextField("Lycoris");
        usernameField.setEditable(false);  // Make it uneditable by default
        usernameField.setBounds(70, 260, 400, 40);
        usernameField.setBackground(new Color(0x4F9AB1)); // Light blue
        usernameField.setForeground(Color.WHITE);
        usernameField.setFont(new Font("Arial", Font.PLAIN, 16));
        usernameField.setHorizontalAlignment(JTextField.CENTER);
        profilePanel.add(usernameField);

        // Edit button for username
        JButton editUsernameButton = new JButton("Edit");
        editUsernameButton.setBounds(500, 260, 80, 40);
        editUsernameButton.setBackground(new Color(0x4F9AB1));
        editUsernameButton.setForeground(Color.WHITE);
        editUsernameButton.setBorder(BorderFactory.createLineBorder(Color.WHITE));
        editUsernameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                usernameField.setEditable(true);
            }
        });
        profilePanel.add(editUsernameButton);

        // Password Section
        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        passwordLabel.setForeground(Color.WHITE);
        passwordLabel.setBounds(70, 320, 200, 30);
        profilePanel.add(passwordLabel);

        JTextField passwordField = new JTextField("****");
        passwordField.setEditable(false);  // Make it uneditable by default
        passwordField.setBounds(70, 360, 400, 40);
        passwordField.setBackground(new Color(0x4F9AB1)); // Light blue
        passwordField.setForeground(Color.WHITE);
        passwordField.setFont(new Font("Arial", Font.PLAIN, 16));
        passwordField.setHorizontalAlignment(JTextField.CENTER);
        profilePanel.add(passwordField);

        // Edit button for password
        JButton editPasswordButton = new JButton("Edit");
        editPasswordButton.setBounds(500, 360, 80, 40);
        editPasswordButton.setBackground(new Color(0x4F9AB1));
        editPasswordButton.setForeground(Color.WHITE);
        editPasswordButton.setBorder(BorderFactory.createLineBorder(Color.WHITE));
        editPasswordButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                passwordField.setEditable(true);
            }
        });
        profilePanel.add(editPasswordButton);

        // Confirm Changes button
        JButton confirmButton = new JButton("Confirm Changes");
        confirmButton.setBounds(230, 420, 200, 40);
        confirmButton.setBackground(new Color(0x4F9AB1));
        confirmButton.setForeground(Color.WHITE);
        confirmButton.setBorder(BorderFactory.createLineBorder(Color.WHITE));
        profilePanel.add(confirmButton);

    
        // Add components to frame
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1535,820);
        setResizable(false);
        getContentPane().setBackground(Color.WHITE);
        setTitle("Library Management System");
        setLayout(null);
        setVisible(true);
        add(nav);
        add(profilePanel);
    }


    private javax.swing.JPanel nav;
    private javax.swing.JLabel dash;
    private javax.swing.JLabel home;
    private JLabel browse;
    private JLabel borrowh;
    private JLabel profile;
    private javax.swing.JLabel username;

}