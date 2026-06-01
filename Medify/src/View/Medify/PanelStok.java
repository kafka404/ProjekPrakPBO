package View.Medify;

import Controller.ControllerObat;
import Model.Medify.ModelObat;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class PanelStok extends JPanel {
    
    private ControllerObat controllerObat;
    private JTextField txtId, txtNama, txtHarga, txtStok;
    private JComboBox<String> cbJenis;
    private JTable table;
    private DefaultTableModel tableModel;
    
    public PanelStok() {
        controllerObat = new ControllerObat();
        setLayout(new BorderLayout());
        setBorder(new EmptyBorder(10, 10, 10, 10));
        
        // Form Panel
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(new Color(100, 100, 100)),
            "Form Obat",
            TitledBorder.LEFT,
            TitledBorder.TOP,
            new Font("Arial", Font.BOLD, 12)
        ));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 10, 8, 10);
        gbc.anchor = GridBagConstraints.WEST;
        
        // ID
        gbc.gridx = 0; gbc.gridy = 0;
        formPanel.add(new JLabel("ID Obat"), gbc);
        gbc.gridx = 1; gbc.gridy = 0;
        txtId = new JTextField(10);
        txtId.setEditable(false);
        txtId.setBackground(new Color(240, 240, 240));
        formPanel.add(txtId, gbc);
        
        // Nama Obat
        gbc.gridx = 0; gbc.gridy = 1;
        formPanel.add(new JLabel("Nama Obat"), gbc);
        gbc.gridx = 1; gbc.gridy = 1;
        txtNama = new JTextField(20);
        formPanel.add(txtNama, gbc);
        
        // Harga
        gbc.gridx = 0; gbc.gridy = 2;
        formPanel.add(new JLabel("Harga"), gbc);
        gbc.gridx = 1; gbc.gridy = 2;
        txtHarga = new JTextField(15);
        formPanel.add(txtHarga, gbc);
        
        // Stok
        gbc.gridx = 0; gbc.gridy = 3;
        formPanel.add(new JLabel("Stok"), gbc);
        gbc.gridx = 1; gbc.gridy = 3;
        txtStok = new JTextField(10);
        formPanel.add(txtStok, gbc);
        
        // Jenis
        gbc.gridx = 0; gbc.gridy = 4;
        formPanel.add(new JLabel("Jenis"), gbc);
        gbc.gridx = 1; gbc.gridy = 4;
        cbJenis = new JComboBox<>(new String[]{"biasa", "resep"});
        formPanel.add(cbJenis, gbc);
        
        // Button Panel
        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        JButton btnAdd = new JButton("Tambah");
        JButton btnUpdate = new JButton("Update");
        JButton btnDelete = new JButton("Hapus");
        JButton btnClear = new JButton("Clear");
        JButton btnRefresh = new JButton("Refresh");
        
        btnAdd.setBackground(new Color(52, 152, 219));
        btnUpdate.setBackground(new Color(52, 152, 219));
        btnDelete.setBackground(new Color(231, 76, 60));
        btnClear.setBackground(new Color(149, 165, 166));
        btnRefresh.setBackground(new Color(46, 204, 113));
        
        for (JButton btn : new JButton[]{btnAdd, btnUpdate, btnDelete, btnClear, btnRefresh}) {
            btn.setForeground(Color.WHITE);
            btn.setFocusPainted(false);
            btn.setPreferredSize(new Dimension(90, 30));
        }
        
        btnAdd.addActionListener(e -> tambahObat());
        btnUpdate.addActionListener(e -> updateObat());
        btnDelete.addActionListener(e -> hapusObat());
        btnClear.addActionListener(e -> clearForm());
        btnRefresh.addActionListener(e -> loadData());
        
        btnPanel.add(btnAdd);
        btnPanel.add(btnUpdate);
        btnPanel.add(btnDelete);
        btnPanel.add(btnClear);
        btnPanel.add(btnRefresh);
        
        // Table
        String[] cols = {"ID", "Nama Obat", "Harga", "Stok", "Jenis"};
        tableModel = new DefaultTableModel(cols, 0) {
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        table = new JTable(tableModel);
        table.setRowHeight(28);
        table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 12));
        table.setFont(new Font("Arial", Font.PLAIN, 12));
        
        table.getSelectionModel().addListSelectionListener(e -> {
            int row = table.getSelectedRow();
            if (row != -1 && !e.getValueIsAdjusting()) {
                txtId.setText(tableModel.getValueAt(row, 0).toString());
                txtNama.setText(tableModel.getValueAt(row, 1).toString());
                
                // BERSIHKAN FORMAT HARGA
                String hargaRaw = tableModel.getValueAt(row, 2).toString();
                hargaRaw = hargaRaw.replace("Rp ", "").replace(".", "");
                txtHarga.setText(hargaRaw);
                
                txtStok.setText(tableModel.getValueAt(row, 3).toString());
                cbJenis.setSelectedItem(tableModel.getValueAt(row, 4).toString());
            }
        });
        
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Data Obat"));
        scrollPane.setPreferredSize(new Dimension(800, 250));
        
        // Layout
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.add(formPanel, BorderLayout.CENTER);
        topPanel.add(btnPanel, BorderLayout.SOUTH);
        
        add(topPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        
        loadData();
    }
    
    private void loadData() {
        tableModel.setRowCount(0);
        List<Object> list = controllerObat.getAll();
        for (Object obj : list) {
            ModelObat o = (ModelObat) obj;
            tableModel.addRow(new Object[]{
                o.getIdObat(),
                o.getNamaObat(),
                "Rp " + String.format("%,d", o.getHarga()),
                o.getStok(),
                o.getJenis()
            });
        }
    }
    
    private void tambahObat() {
        try {
            String nama = txtNama.getText().trim();
            int harga = Integer.parseInt(txtHarga.getText().trim());
            int stok = Integer.parseInt(txtStok.getText().trim());
            String jenis = (String) cbJenis.getSelectedItem();
            
            if (nama.isEmpty()) throw new Exception("Nama obat tidak boleh kosong!");
            if (harga <= 0) throw new Exception("Harga harus lebih dari 0!");
            
            controllerObat.insert(nama, harga, stok, jenis);
            JOptionPane.showMessageDialog(this, "Obat berhasil ditambahkan!");
            clearForm();
            loadData();
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Harga dan Stok harus diisi angka!", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void updateObat() {
        try {
            int id = Integer.parseInt(txtId.getText().trim());
            String nama = txtNama.getText().trim();
            int harga = Integer.parseInt(txtHarga.getText().trim());
            int stok = Integer.parseInt(txtStok.getText().trim());
            String jenis = (String) cbJenis.getSelectedItem();
            
            if (nama.isEmpty()) throw new Exception("Nama obat tidak boleh kosong!");
            if (harga <= 0) throw new Exception("Harga harus lebih dari 0!");
            
            controllerObat.update(id, nama, harga, stok, jenis);
            JOptionPane.showMessageDialog(this, "Obat berhasil diupdate!");
            clearForm();
            loadData();
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Pilih data dari tabel terlebih dahulu!", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void hapusObat() {
        try {
            int id = Integer.parseInt(txtId.getText());
            int confirm = JOptionPane.showConfirmDialog(this, "Yakin ingin menghapus obat ini?", "Konfirmasi", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                controllerObat.delete(id);
                JOptionPane.showMessageDialog(this, "Obat berhasil dihapus!");
                clearForm();
                loadData();
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Pilih obat yang akan dihapus!", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void clearForm() {
        txtId.setText("");
        txtNama.setText("");
        txtHarga.setText("");
        txtStok.setText("");
        cbJenis.setSelectedIndex(0);
        table.clearSelection();
    }
}