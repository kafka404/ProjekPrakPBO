package View.Medify;

import Controller.ControllerTransaksi;
import Model.Medify.ModelTransaksi;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class Riwayat extends JPanel {
    
    private ControllerTransaksi controllerTransaksi;
    private JTable table;
    private DefaultTableModel tableModel;
    
    public Riwayat() {
        controllerTransaksi = new ControllerTransaksi();
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        String[] cols = {"ID", "Customer", "Obat", "Jenis", "Jumlah", "Total", "Tanggal"};
        tableModel = new DefaultTableModel(cols, 0) {
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        table = new JTable(tableModel);
        table.setRowHeight(25);
        table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 12));
        table.setFont(new Font("Arial", Font.PLAIN, 11));
        
        JButton btnRefresh = new JButton("Refresh");
        btnRefresh.setBackground(new Color(52, 152, 219));
        btnRefresh.setForeground(Color.WHITE);
        btnRefresh.setFocusPainted(false);
        btnRefresh.setPreferredSize(new Dimension(100, 30));
        btnRefresh.addActionListener(e -> loadData());
        
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        topPanel.add(btnRefresh);
        
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Riwayat Transaksi"));
        
        add(topPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        
        loadData();
    }
    
    private void loadData() {
        tableModel.setRowCount(0);
        List<ModelTransaksi> list = controllerTransaksi.getAllTransaksi();
        for (ModelTransaksi t : list) {
            tableModel.addRow(new Object[]{
                t.getIdTransaksi(),
                t.getNamaCustomer(),
                t.getNamaObat(),
                t.getJenisObat(),
                t.getJumlahBeli(),
                "Rp " + String.format("%,d", t.getTotalBayar()),
                t.getTanggal()
            });
        }
    }
}