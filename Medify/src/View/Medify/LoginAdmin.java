/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package View.Medify;
import javax.swing.*;
import java.awt.*;
/**
 *
 * @author USER
 */
public class LoginAdmin extends JDialog {
    
    private JTextField txtUsername;
    private JPasswordField txtPassword;
    private boolean loginSuccess = false;
    
    public LoginAdmin(JFrame parent) {
        super(parent, "Admin Login", true);
        setSize(350, 180);
        setLayout(new BorderLayout());
        setLocationRelativeTo(parent);
        
        // Panel Form
        JPanel formPanel = new JPanel(new GridLayout(2, 2, 10, 10));
        formPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        
        formPanel.add(new JLabel("Username:"));
        txtUsername = new JTextField();
        formPanel.add(txtUsername);
        
        formPanel.add(new JLabel("Password:"));
        txtPassword = new JPasswordField();
        formPanel.add(txtPassword);
        
        // Panel Tombol
        JPanel btnPanel = new JPanel(new FlowLayout());
        JButton btnLogin = new JButton("Login");
        JButton btnCancel = new JButton("Cancel");
        
        btnLogin.addActionListener(e -> {
            String username = txtUsername.getText().trim();
            String password = new String(txtPassword.getPassword());
            
            System.out.println("Username: " + username + ", Password: " + password);
            
            if (username.equals("admin") && password.equals("123")) {
                loginSuccess = true;
                System.out.println("Login BERHASIL!");
                dispose(); // Tutup dialog
            } else {
                System.out.println("Login GAGAL!");
                JOptionPane.showMessageDialog(this, "Username atau password salah!", "Login Gagal", JOptionPane.ERROR_MESSAGE);
            }
        });
        
        btnCancel.addActionListener(e -> {
            loginSuccess = false;
            dispose();
        });
        
        btnPanel.add(btnLogin);
        btnPanel.add(btnCancel);
        
        add(formPanel, BorderLayout.CENTER);
        add(btnPanel, BorderLayout.SOUTH);
        
        // Biar tekan Enter bisa login
        getRootPane().setDefaultButton(btnLogin);
    }
    
    public boolean isLoginSuccess() {
        return loginSuccess;
    }
}
