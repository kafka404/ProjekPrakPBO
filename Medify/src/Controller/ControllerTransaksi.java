/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;
import Model.Connector;
import Model.Medify.*;
import java.sql.*;
import java.util.*;

/**
 *
 * @author USER
 */
public class ControllerTransaksi {
    private ControllerObat controllerObat;
    
    public ControllerTransaksi() {
        controllerObat = new ControllerObat();
    }

    // Proses Penjualan
    public void prosesPenjualan(String namaCustomer, int idObat, int jumlah) throws Exception {
        // Validasi input
        if (namaCustomer.trim().isEmpty()) {
            throw new Exception("Nama customer tidak boleh kosong!");
        }
        if (jumlah <= 0) {
            throw new Exception("Jumlah beli harus lebih dari 0!");
        }
        
        // Ambil data obat
        ModelObat obat = controllerObat.cariObat(idObat);
        if (obat == null) {
            throw new Exception("Obat tidak ditemukan!");
        }
        
        // Cek stok
        if (obat.getStok() < jumlah) {
            throw new Exception("Stok tidak cukup! Sisa stok: " + obat.getStok());
        }
        
        // Hitung total (dengan diskon dari polymorphism)
        int totalBayar = obat.getHargaSetelahDiskon() * jumlah;
        
        // Kurangi stok
        controllerObat.KurangiStok(idObat, jumlah);
        
        // Simpan transaksi ke database
        String query = "INSERT INTO transaksi (nama_customer, nama_obat, jenis_obat, jumlah_beli, harga_satuan, total_bayar) VALUES (?,?,?,?,?,?)";
        try {
            PreparedStatement statement = Connector.Connect().prepareStatement(query);
            statement.setString(1, namaCustomer);
            statement.setString(2, obat.getNamaObat());
            statement.setString(3, obat.getJenis());
            statement.setInt(4, jumlah);
            statement.setInt(5, obat.getHarga());
            statement.setInt(6, totalBayar);
            statement.executeUpdate();
            new Thread(() -> {
        try {
            Thread.sleep(1000);
            System.out.println("Transaksi " + namaCustomer + " selesai diproses");
        } catch (InterruptedException ex) {
            System.out.println("Error: " + ex.getMessage());
        }
    }).start();
        } catch (Exception e) {
            throw new Exception("Gagal menyimpan transaksi: " + e.getMessage());
        }
    }

    // Get All Transaksi (untuk riwayat admin)
    public List<ModelTransaksi> getAllTransaksi() {
        List<ModelTransaksi> listTransaksi = new ArrayList<>();
        try {
            Statement statement = Connector.Connect().createStatement();
            String query = "SELECT * FROM transaksi ORDER BY tanggal DESC";
            ResultSet rs = statement.executeQuery(query);
            
            while (rs.next()) {
                ModelTransaksi transaksi = new ModelTransaksi();
                transaksi.setIdTransaksi(rs.getInt("id_transaksi"));
                transaksi.setNamaCustomer(rs.getString("nama_customer"));
                transaksi.setNamaObat(rs.getString("nama_obat"));
                transaksi.setJenisObat(rs.getString("jenis_obat"));
                transaksi.setJumlahBeli(rs.getInt("jumlah_beli"));
                transaksi.setHargaSatuan(rs.getInt("harga_satuan"));
                transaksi.setTotalBayar(rs.getInt("total_bayar"));
                transaksi.setTanggal(rs.getTimestamp("tanggal"));
                listTransaksi.add(transaksi);
            }
        } catch (Exception e) {
            System.out.println("Get All Transaksi Gagal: " + e.getMessage());
        }
        return listTransaksi;
    }
}
