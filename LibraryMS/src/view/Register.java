package view;

import java.awt.*;
import javax.swing.*;


/**
 * @author Christine Ann Dejito
 */
public class Register extends javax.swing.JFrame {

    public Register() {
        initComponents();
    }
    
    private void logMouseClicked(java.awt.event.MouseEvent evt) {                                      
            Login l = new Login();
            l.setVisible(true);
            setVisible(false);
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
            java.util.logging.Logger.getLogger(Register.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Register.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Register.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Register.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Register().setVisible(true);
            }
        });
        
    }
    
    private void initComponents(){
        title = new JLabel();
        title.setText("HIRAYA");
        title.setBounds(600, 75, 300, 50);
        title.setFont(new Font("Serif", Font.BOLD, 70));
        title.setVerticalAlignment(JLabel.CENTER);
        title.setHorizontalAlignment(JLabel.CENTER);
        
        username = new JLabel();
        username.setText("Username");
        username.setFont(new Font("Serif", Font.PLAIN, 20));
        username.setBounds(100, 25, 200, 50);
      
        uname = new JTextField();
        uname.setBounds(100, 75, 700, 50);
        
        password = new JLabel();
        password.setText("Password");
        password.setFont(new Font("Serif", Font.PLAIN, 20));
        password.setBounds(100, 125, 200, 50);
        
        pass = new JTextField();
        pass.setBounds(100, 175, 700, 50);
        
        confirm = new JLabel();
        confirm.setText("Confirm Password");
        confirm.setFont(new Font("Serif", Font.PLAIN, 20));
        confirm.setBounds(100, 225, 200, 50);
        conf = new JTextField();
        conf.setBounds(100, 275, 700, 50);
        
        register = new JButton();
        register.setText("Register");
        register.setBounds(100, 355, 700, 60);
        register.setFont(new Font("Serif", Font.BOLD, 30));
        register.setForeground(Color.WHITE);
        register.setBackground(new Color(0x316FA2));
        
        log = new JLabel();
        log.setText("Already have an account? Login");
        log.setBounds(100, 415, 400, 50);
        log.setFont(new Font("Serif", Font.ITALIC, 15));
        log.setForeground(new Color(0x316FA2));
        
        panel1 = new JPanel();
        panel1.setBounds(300, 180, 900, 500);
        panel1.setBackground(new Color(182, 206, 229));
        panel1.setLayout(null);
        
        panel1.add(username);
        panel1.add(uname);
        panel1.add(password);
        panel1.add(pass);
        panel1.add(confirm);
        panel1.add(conf);
        panel1.add(register);
        panel1.add(log);
        

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1535,820);
        setResizable(false);
        getContentPane().setBackground(new Color(109, 173, 208));
        setTitle("Library Management System");
        setLayout(null);
        setVisible(true);
        
        add(title);
        add(panel1);
        
        log.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                logMouseClicked(evt);
            }     
        });
    }
    
    private JLabel title;
    private JLabel username;
    private JTextField uname;
    private JLabel password;
    private JTextField pass;
    private JLabel confirm;
    private JTextField conf;
    private JButton register;
    private JLabel log;
    private JPanel panel1;
    
}
