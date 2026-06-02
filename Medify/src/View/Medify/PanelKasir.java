package View.Medify;

import Controller.ControllerObat;
import Controller.ControllerTransaksi;
import Model.Medify.ModelObat;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class PanelKasir extends JPanel {
    
    private ControllerObat controllerObat;
    private ControllerTransaksi controllerTransaksi;
    
    // Komponen Form
    private JTextField txtNamaPelanggan;
    private JRadioButton rbBiasa;
    private JRadioButton rbResep;
    private ButtonGroup bgJenis;
    private JComboBox<String> cbNamaObat;
    private JTextField txtQty;
    private JButton btnInput;
    private JButton btnProses;
    
    // Tabel Keranjang
    private JTable table;
    private DefaultTableModel tableModel;
    private JLabel lblGrandTotal;
    
    private List<Object> semuaObat;
    private List<ItemKeranjang> keranjang;
    
    public PanelKasir() {
        controllerObat = new ControllerObat();
        controllerTransaksi = new ControllerTransaksi();
        semuaObat = controllerObat.getAll();
        keranjang = new ArrayList<>();
        
        setLayout(new BorderLayout());
        setBackground(new Color(240, 240, 240));
        setBorder(new EmptyBorder(15, 20, 20, 20));
        
        // ===== PANEL ATAS (Form Input) =====
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(Color.WHITE);
        formPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200)),
            new EmptyBorder(15, 15, 15, 15)
        ));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.anchor = GridBagConstraints.WEST;
        
        // Baris 1: Nama Pelanggan + Radio Button
        gbc.gridx = 0; gbc.gridy = 0;
        formPanel.add(new JLabel("Nama Pelanggan:"), gbc);
        gbc.gridx = 1; gbc.gridy = 0;
        txtNamaPelanggan = new JTextField(15);
        txtNamaPelanggan.setEnabled(false);
        txtNamaPelanggan.setPreferredSize(new Dimension(180, 30));
        formPanel.add(txtNamaPelanggan, gbc);
        
        gbc.gridx = 2; gbc.gridy = 0;
        rbBiasa = new JRadioButton("Obat Biasa");
        rbResep = new JRadioButton("Obat Resep");
        bgJenis = new ButtonGroup();
        bgJenis.add(rbBiasa);
        bgJenis.add(rbResep);
        rbBiasa.setSelected(true);
        
        JPanel radioPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        radioPanel.add(rbBiasa);
        radioPanel.add(rbResep);
        formPanel.add(radioPanel, gbc);
        
        // Event Radio Button
        rbBiasa.addActionListener(e -> {
            txtNamaPelanggan.setEnabled(false);
            txtNamaPelanggan.setText("");
            filterObatByJenis();
        });
        rbResep.addActionListener(e -> {
            txtNamaPelanggan.setEnabled(true);
            filterObatByJenis();
        });
        
        // ===== BARIS 2: PAKAI FLOWLAYOUT BIAR DEKET =====
        JPanel row2Panel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        row2Panel.setOpaque(false);
        
        row2Panel.add(new JLabel("Nama Obat:"));
        cbNamaObat = new JComboBox<>();
        cbNamaObat.setPreferredSize(new Dimension(180, 30));
        row2Panel.add(cbNamaObat);
        
        row2Panel.add(new JLabel("Qty:"));
        txtQty = new JTextField(5);
        txtQty.setPreferredSize(new Dimension(60, 30));
        row2Panel.add(txtQty);
        
        btnInput = new JButton("Input");
        btnInput.setBackground(new Color(52, 152, 219));
        btnInput.setForeground(Color.WHITE);
        btnInput.setFont(new Font("Arial", Font.BOLD, 12));
        btnInput.setFocusPainted(false);
        btnInput.setPreferredSize(new Dimension(90, 32));
        btnInput.addActionListener(e -> tambahKeKeranjang());
        row2Panel.add(btnInput);
        
        // Taruh row2Panel ke formPanel
        gbc.gridx = 0; gbc.gridy = 1;
        gbc.gridwidth = 4;
        gbc.anchor = GridBagConstraints.WEST;
        formPanel.add(row2Panel, gbc);
        
        // ===== TABEL KERANJANG =====
        String[] cols = {"Nama Obat", "Jumlah", "Harga Satuan", "Diskon", "Total"};
        tableModel = new DefaultTableModel(cols, 0) {
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        table = new JTable(tableModel);
        table.setRowHeight(28);
        table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 12));
        table.setFont(new Font("Arial", Font.PLAIN, 12));
        
        // Hapus item dengan klik kanan
        table.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent e) {
                if (SwingUtilities.isRightMouseButton(e)) {
                    int row = table.rowAtPoint(e.getPoint());
                    if (row != -1) {
                        hapusDariKeranjang(row);
                    }
                }
            }
        });
        
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Daftar Belanja"));
        scrollPane.setPreferredSize(new Dimension(600, 200));
        
        // ===== PANEL BAWAH (GRAND TOTAL + TOMBOL PROSES) =====
        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.setBackground(Color.WHITE);
        bottomPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200)),
            new EmptyBorder(10, 15, 10, 15)
        ));
        
        JLabel lblTotalText = new JLabel("TOTAL:");
        lblTotalText.setFont(new Font("Arial", Font.BOLD, 18));
        
        lblGrandTotal = new JLabel("Rp 0");
        lblGrandTotal.setFont(new Font("Arial", Font.BOLD, 20));
        lblGrandTotal.setForeground(new Color(192, 57, 43));
        
        btnProses = new JButton("Proses Transaksi");
        btnProses.setBackground(new Color(46, 204, 113));
        btnProses.setForeground(Color.WHITE);
        btnProses.setFont(new Font("Arial", Font.BOLD, 14));
        btnProses.setFocusPainted(false);
        btnProses.setPreferredSize(new Dimension(180, 40));
        btnProses.addActionListener(e -> prosesTransaksi());
        
        JPanel totalPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 5));
        totalPanel.add(lblTotalText);
        totalPanel.add(lblGrandTotal);
        
        bottomPanel.add(totalPanel, BorderLayout.CENTER);
        bottomPanel.add(btnProses, BorderLayout.EAST);
        
        // Susun semua
        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.add(formPanel, BorderLayout.NORTH);
        centerPanel.add(scrollPane, BorderLayout.CENTER);
        
        add(centerPanel, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);
        
        filterObatByJenis();
    }
    
    private void filterObatByJenis() {
        String jenisDipilih = rbResep.isSelected() ? "resep" : "biasa";
        
        List<ModelObat> filtered = semuaObat.stream()
                .map(obj -> (ModelObat) obj)
                .filter(o -> o.getJenis().equalsIgnoreCase(jenisDipilih))
                .collect(java.util.stream.Collectors.toList());
        
        cbNamaObat.removeAllItems();
        for (ModelObat o : filtered) {
            cbNamaObat.addItem(o.getNamaObat() + " (Stok: " + o.getStok() + ")");
        }
    }
    
    private void tambahKeKeranjang() {
        String selected = (String) cbNamaObat.getSelectedItem();
        if (selected == null) return;
        
        String namaObat = selected.split(" \\(")[0];
        ModelObat obat = (ModelObat) semuaObat.stream()
                .map(obj -> (ModelObat) obj)
                .filter(o -> o.getNamaObat().equals(namaObat))
                .findFirst()
                .orElse(null);
        
        if (obat == null) return;
        
        int qty;
        try {
            qty = Integer.parseInt(txtQty.getText().trim());
            if (qty <= 0) throw new Exception();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Qty harus diisi angka > 0!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        if (qty > obat.getStok()) {
            JOptionPane.showMessageDialog(this, "Stok tidak cukup! Sisa: " + obat.getStok(), "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        int hargaSatuan = obat.getHarga();
        int diskon = (int) obat.getDiskon();
        int hargaSetelahDiskon = obat.getHargaSetelahDiskon();
        int total = hargaSetelahDiskon * qty;
        
        // Cek apakah obat sudah ada di keranjang
        boolean found = false;
        for (ItemKeranjang item : keranjang) {
            if (item.namaObat.equals(namaObat)) {
                item.qty += qty;
                item.total = item.hargaSetelahDiskon * item.qty;
                found = true;
                break;
            }
        }
        
        if (!found) {
            keranjang.add(new ItemKeranjang(namaObat, qty, hargaSatuan, diskon, hargaSetelahDiskon, total));
        }
        
        refreshTabel();
        txtQty.setText("");
    }
    
    private void hapusDariKeranjang(int row) {
        keranjang.remove(row);
        refreshTabel();
    }
    
    private void refreshTabel() {
        tableModel.setRowCount(0);
        int grandTotal = 0;
        
        for (ItemKeranjang item : keranjang) {
            String diskonText = item.diskon > 0 ? item.diskon + "% (Resep)" : "0%";
            tableModel.addRow(new Object[]{
                item.namaObat,
                item.qty,
                "Rp " + String.format("%,d", item.hargaSatuan),
                diskonText,
                "Rp " + String.format("%,d", item.total)
            });
            grandTotal += item.total;
        }
        
        lblGrandTotal.setText("Rp " + String.format("%,d", grandTotal));
    }
    
    private void prosesTransaksi() {
        if (keranjang.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Belum ada obat yang dipilih!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        // Jika obat resep, nama pelanggan wajib diisi
        boolean adaObatResep = keranjang.stream().anyMatch(item -> item.diskon > 0);
        if (adaObatResep) {
            String namaPelanggan = txtNamaPelanggan.getText().trim();
            if (namaPelanggan.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Nama pelanggan wajib diisi untuk obat resep!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
        }
        
        try {
            String namaPelanggan = txtNamaPelanggan.getText().trim();
            if (namaPelanggan.isEmpty()) namaPelanggan = "Umum";
            
            // Proses setiap item di keranjang
            for (ItemKeranjang item : keranjang) {
                ModelObat obat = (ModelObat) semuaObat.stream()
                        .map(obj -> (ModelObat) obj)
                        .filter(o -> o.getNamaObat().equals(item.namaObat))
                        .findFirst()
                        .orElse(null);
                
                if (obat != null) {
                    controllerTransaksi.prosesPenjualan(namaPelanggan, obat.getIdObat(), item.qty);
                }
            }
            
            JOptionPane.showMessageDialog(this, "Transaksi Berhasil!\nTotal: " + lblGrandTotal.getText(), "Sukses", JOptionPane.INFORMATION_MESSAGE);
            
            // Reset
            keranjang.clear();
            refreshTabel();
            txtNamaPelanggan.setText("");
            txtQty.setText("");
            semuaObat = controllerObat.getAll();
            filterObatByJenis();
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public void refreshData() {
        semuaObat = controllerObat.getAll();
        filterObatByJenis();
    }
    
    // Inner class untuk item keranjang
    class ItemKeranjang {
        String namaObat;
        int qty;
        int hargaSatuan;
        int diskon;
        int hargaSetelahDiskon;
        int total;
        
        ItemKeranjang(String namaObat, int qty, int hargaSatuan, int diskon, int hargaSetelahDiskon, int total) {
            this.namaObat = namaObat;
            this.qty = qty;
            this.hargaSatuan = hargaSatuan;
            this.diskon = diskon;
            this.hargaSetelahDiskon = hargaSetelahDiskon;
            this.total = total;
        }
    }
}
