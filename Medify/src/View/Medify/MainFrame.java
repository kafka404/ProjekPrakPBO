package View.Medify;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainFrame extends JFrame {
    
    private JButton btnAdmin;
    private CardLayout cardLayout;
    private JPanel mainPanel;
    private PanelCustomer panelCustomer;
    private JPanel panelAdmin;
    private boolean isAdminMode = false;
    
    public MainFrame() {
        setTitle("Apotek Sehat");
        setSize(850, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        // Top Panel 
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(new Color(41, 128, 185));
        topPanel.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));
        
       
        btnAdmin = new JButton("Admin Login");
        btnAdmin.setBackground(new Color(236, 240, 241));
        btnAdmin.setForeground(new Color(41, 128, 185));
        btnAdmin.setFocusPainted(false);
        btnAdmin.setFont(new Font("Arial", Font.BOLD, 12));
        btnAdmin.setPreferredSize(new Dimension(120, 30));

        btnAdmin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Tombol Admin Diklik!"); // Buat debug
                
                if (!isAdminMode) {
                    // Buka dialog login
                    LoginAdmin login = new LoginAdmin(MainFrame.this);
                    login.setVisible(true);
                    
                    if (login.isLoginSuccess()) {
                        isAdminMode = true;
                        btnAdmin.setText("User Mode");
                        cardLayout.show(mainPanel, "ADMIN");
                        JOptionPane.showMessageDialog(MainFrame.this, "Selamat datang Admin!", "Sukses", JOptionPane.INFORMATION_MESSAGE);
                    }
                } else {
                    // Logout
                    isAdminMode = false;
                    btnAdmin.setText("Admin Login");
                    cardLayout.show(mainPanel, "CUSTOMER");
                    panelCustomer.refreshData();
                    JOptionPane.showMessageDialog(MainFrame.this, "Anda telah logout!", "Info", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });
        
        topPanel.add(btnAdmin, BorderLayout.EAST);
        
        // Main Panel dengan CardLayout
        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);
        
        panelCustomer = new PanelCustomer();
        panelAdmin = createAdminPanel();
        
        mainPanel.add(panelCustomer, "CUSTOMER");
        mainPanel.add(panelAdmin, "ADMIN");
        
        setLayout(new BorderLayout());
        add(topPanel, BorderLayout.NORTH);
        add(mainPanel, BorderLayout.CENTER);
    }
    
    private JPanel createAdminPanel() {
        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.addTab("Manajemen Stok", new PanelStok());
        tabbedPane.addTab("Riwayat Transaksi", new Riwayat());
        
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(tabbedPane, BorderLayout.CENTER);
        return panel;
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new MainFrame().setVisible(true);
            }
        });
    }
}
