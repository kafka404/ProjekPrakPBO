package View.Medify;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainFrame extends JFrame {
    
    private JButton btnAdmin; // Tombol admin dipojok kanan atas
    private CardLayout cardLayout; // layout untuk ganti ganti panel ( window ) 
    private JPanel mainPanel; // panel utama
    private PanelKasir panelKasir; // panel untuk kasir
    private JPanel panelAdmin; // panel admin
    private boolean isAdminMode = false; // status mode ( true = admin , false = kasir )
    
    public MainFrame() {
        setTitle("Apotek Sehat");
        setSize(850, 700); // ukuran window
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // tutup app saat window close
        setLocationRelativeTo(null); // posisi ditengah layar
        
        // Top Panel 
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(new Color(41, 128, 185)); // warna biru
        topPanel.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15)); // padding
        
       
        btnAdmin = new JButton("Admin Login");
        btnAdmin.setBackground(new Color(236, 240, 241)); // abu terang
        btnAdmin.setForeground(new Color(41, 128, 185)); // teks biru
        btnAdmin.setFocusPainted(false); // hilangkan border fokus
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
                    
                    if (login.isLoginSuccess()) { // jika admin login
                        isAdminMode = true;
                        btnAdmin.setText("User Mode"); // pojok kanan atas ganti ini 
                        cardLayout.show(mainPanel, "ADMIN");
                        JOptionPane.showMessageDialog(MainFrame.this, "Selamat datang Admin!", "Sukses", JOptionPane.INFORMATION_MESSAGE);
                    }
                } else {
                    // Logout
                    isAdminMode = false;
                    btnAdmin.setText("Admin Login");
                    cardLayout.show(mainPanel, "CUSTOMER");
                    panelKasir.refreshData();
                    JOptionPane.showMessageDialog(MainFrame.this, "Anda telah logout!", "Info", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });
        
        topPanel.add(btnAdmin, BorderLayout.EAST); // tombol di sebelah kanan
        
        // Main Panel dengan CardLayout
        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);
        
        panelKasir = new PanelKasir();
        panelAdmin = createAdminPanel();
        
        mainPanel.add(panelKasir, "CUSTOMER");
        mainPanel.add(panelAdmin, "ADMIN");
        
        setLayout(new BorderLayout());
        add(topPanel, BorderLayout.NORTH);
        add(mainPanel, BorderLayout.CENTER);
    }
    
    private JPanel createAdminPanel() {
        JTabbedPane tabbedPane = new JTabbedPane(); // tab panel
        tabbedPane.addTab("Manajemen Stok", new PanelStok()); // tab 1
        tabbedPane.addTab("Riwayat Transaksi", new Riwayat()); // tab 2
        
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(tabbedPane, BorderLayout.CENTER);
        return panel;
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new MainFrame().setVisible(true); // tampilkan MainFrame
            }
        });
    }
}
